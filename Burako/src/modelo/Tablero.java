package modelo;

import Controlador.Controlador;
import Services.*;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Tablero extends ObservableRemoto implements ITablero, Serializable {
  @Serial
  private static final long serialVersionUID = 463107072063859071L;
  private Mazo mazo;
  private Pozo pozo = new Pozo();
  private final int LIMITE = 150;
  private Equipo equipo1;
  private Equipo equipo2;
  private String turno;
  private String ganador;
  private boolean partidaCreada = false;
  private boolean partidaIniciada = false;
  private ArrayList<JugadorDesconectado> jugadoresDesconectados = new ArrayList<>();

  public Tablero () throws RemoteException {
    mazo = GeneradorPartida.generarMazo();
  }

  public void setCantJugadores (int cantJugadores) throws RemoteException {
    if (equipo1 == null) {
        equipo1 = new Equipo(cantJugadores, mazo, pozo);
        equipo1.generarMuertos();
        equipo2 = new Equipo(cantJugadores, mazo, pozo);
        equipo2.generarMuertos();
    }
  }

  public ArrayList<IFicha> getPozo () throws RemoteException {
    return this.pozo.verFichas();
  }

  public ArrayList<RankingScheme> getRanking () throws RemoteException {
    return RankingSerializador.obtenerPrimeros10();
  }

  public ArrayList<IFicha> getFichas (String nombre) throws RemoteException {
    IJugador jugador = equipo1.getJugador(nombre);
    if (jugador == null) {
      jugador = equipo2.getJugador(nombre);
    }
    return jugador.getFichas();
  }

  public boolean getPartidaCreada () throws RemoteException {
    return partidaCreada;
  }
  public void setPartidaCreada (boolean partidaCreada) throws RemoteException {
    this.partidaCreada = partidaCreada;
  }

  public String getTurno () throws RemoteException {
    return turno;
  }

  public void agregarJugador (String nombreJugador, int equipo) throws RemoteException {
    if (!partidaIniciada) {
        IJugador newJugador = new Jugador(nombreJugador);
        if (equipo == 1) {
          equipo1.agregarJugador(newJugador);
        } else {
          equipo2.agregarJugador(newJugador);
        }
    }
  }

  public void reconectarJugador (String nombreJugador) throws RemoteException {
    JugadorDesconectado jD = null;
    for (int i = 0;i < jugadoresDesconectados.size();i++) {
      JugadorDesconectado j = jugadoresDesconectados.get(i);
      if (j.getNombre().equals(nombreJugador)) {
        jD = j;
        jugadoresDesconectados.remove(i);
      }
    }
    if (jD != null) {
      Equipo instEquipo = jD.getEquipo() == 1 ? equipo1 : equipo2;
      Jugador jugador = new Jugador(nombreJugador);
      jugador.setEstadoTurno(jD.getEstadoTurno());
      jugador.setFichas(jD.getFichas());
      instEquipo.agregarJugador(jugador);
      if (equipo1.lleno() && equipo2.lleno()) {
        notificarObservadores(Eventos.INICIAR_PARTIDA);
      }
    }
  }

  public int getEquipo (String nombreJugador) throws RemoteException {
    if (equipo1.verificarJugador(nombreJugador)) {
      return 1;
    }
    return 2;
  }

  @Override
  public boolean partidaIniciada() throws RemoteException {
    return partidaIniciada;
  }

  public ArrayList<IJugadorProxy> getJugadoresARecuperar () {
    ArrayList<IJugadorProxy> jugadores = new ArrayList<>();
    jugadores.addAll(jugadoresDesconectados);
    return jugadores;
  }

  public void iniciarPartida () throws RemoteException {
    if (equipo1.lleno() && equipo2.lleno() && !partidaIniciada) {
      partidaIniciada = true;
      this.elegirTurno();
      equipo1.asignarFichas();
      equipo2.asignarFichas();
      notificarObservadores(Eventos.INICIAR_PARTIDA);
    }
  }

  public boolean equipo1Lleno () throws RemoteException {
    return equipo1.lleno();
  }

  public boolean equipo2Lleno() throws RemoteException {
    return equipo2.lleno();
  }

  @Override
  public void cerrar (IObservadorRemoto controlador, String nombreJugador, int equipo) throws RemoteException  {
    IJugador jugador;
    Equipo instanciaEquipo = equipo == 1 ? equipo1 : equipo2;
    if (instanciaEquipo.verificarJugador(nombreJugador)) {
       jugador = instanciaEquipo.getJugador(nombreJugador);
       instanciaEquipo.eliminarJugador(nombreJugador);
       this.removerObservador(controlador);
       if (partidaIniciada) {
           jugadoresDesconectados.add(new JugadorDesconectado(nombreJugador, jugador.getEstadoTurno(), equipo, jugador.getFichas()));
           notificarObservadores(Eventos.DESCONECTADO);
       }
    }
    if (equipo1.vacio() && equipo2.vacio()) {
      this.resetTablero();
    }
  }

  private void resetTablero () {
    if (ganador == null) persistirPartida();
    mazo = GeneradorPartida.generarMazo();
    pozo = new Pozo();
    equipo1 = null;
    equipo2 = null;
    turno = null;
    ganador = null;
    partidaCreada = false;
    partidaIniciada = false;
    jugadoresDesconectados = new ArrayList<>();
  }

  private void persistirPartida () {
    PartidaSerializador.cargarPartida(new TableroScheme(mazo, pozo, equipo1.generarEquipoScheme(), equipo2.generarEquipoScheme(), turno, ganador, jugadoresDesconectados));
  }

  public ArrayList<TableroScheme> obtenerPartidas () throws RemoteException {
    ArrayList<TableroScheme> tableros = PartidaSerializador.obtenerTableros();
    return tableros;
  }

  public ArrayList<JugadorDesconectado> getJugadoresDesconectados () {
    return jugadoresDesconectados;
  }

  public void elegirPartida (int posTablero) throws RemoteException {
    ArrayList<TableroScheme> tableros = PartidaSerializador.obtenerTableros();
    TableroScheme t = tableros.get(posTablero);
    PartidaSerializador.eliminarPartida();
    EquipoScheme equipoScheme1 = t.getEquipo1();
    EquipoScheme equipoScheme2 = t.getEquipo2();

    equipo1 = new Equipo(equipoScheme1.getTamanio(), t.getMazo(), t.getPozo());
    equipo1.asignarValoresEquipo(equipoScheme1);
    equipo2 = new Equipo(equipoScheme2.getTamanio(), t.getMazo(), t.getPozo());
    equipo2.asignarValoresEquipo(equipoScheme2);

    mazo = t.getMazo();
    pozo = t.getPozo();
    turno = t.getTurno();
    ganador = null;
    partidaCreada = true;
    partidaIniciada = true;
    jugadoresDesconectados = t.getJugadoresDesconectados();
  }

  private void elegirTurno () throws RemoteException {
      equipo1.setTurno(true);
      this.turno = equipo1.turnoJugador();
  }

  public int cantidadMazo () throws RemoteException {
    return mazo.cantidadFichas();
  }

  public ArrayList<ICombinacion> getCombinaciones (String nombre) throws RemoteException {
    ArrayList<ICombinacion> combReturns = new ArrayList<>();
    if (equipo1.verificarJugador(nombre)) {
      combReturns.addAll(equipo1.getCombinaciones());
    } else {
      combReturns.addAll(equipo2.getCombinaciones());
    }
    return combReturns;
  }

  public ArrayList<ICombinacion> getCombinacionesContrario (String nombre) throws RemoteException {
    ArrayList<ICombinacion> combReturns = new ArrayList<>();
    if (equipo1.verificarJugador(nombre)) {
      combReturns.addAll(equipo2.getCombinaciones());
    } else {
      combReturns.addAll(equipo1.getCombinaciones());
    }
    return combReturns;
  }

  public int getScore (String nombre) throws RemoteException {
    if (equipo1.verificarJugador(nombre)) {
      return equipo1.getScore();
    } else {
      return equipo2.getScore();
    }
  }

  public ArrayList<IJugadorProxy> getJugadoresProxy (String nombreJugador) throws RemoteException {
    ArrayList<IJugador> j1 = equipo1.getJugadores();
    ArrayList<IJugador> j2 = equipo2.getJugadores();
    ArrayList<IJugadorProxy> jugadoresProxy = new ArrayList<>();
    int i = 0;
    while (j1.size() > i && j2.size() > i) {
      if (equipo1.verificarJugador(nombreJugador)) {
          jugadoresProxy.add(j1.get(i));
          jugadoresProxy.add(j2.get(i));
      } else {
        jugadoresProxy.add(j2.get(i));
        jugadoresProxy.add(j1.get(i));
      }
      i++;
    }
    return jugadoresProxy;
  }

  public int getScoreContrario (String nombre) throws RemoteException {
    if (!equipo1.verificarJugador(nombre)) {
      return equipo1.getScore();
    } else {
      return equipo2.getScore();
    }
  }

  public void agarrarPozo (String nombre) throws RemoteException {
    equipo1.agarrarPozo(nombre);
    equipo2.agarrarPozo(nombre);
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public void agarrarMazo (String nombre) throws RemoteException {
    equipo1.agarrarMazo(nombre);
    equipo2.agarrarMazo(nombre);
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public IJugador getJugador (String nombre) throws RemoteException {
    IJugador jugador = equipo1.getJugador(nombre);
    if (jugador == null) {
      jugador = equipo2.getJugador(nombre);
    }
    return jugador;
  }

  public void soltarFicha (int f) throws RemoteException {
    Equipo eq = equipo1;
    if (!equipo1.getTurno()) {
      eq = equipo2;
      equipo2.setTurno(false);
      equipo1.setTurno(true);
      turno = equipo1.turnoJugador();
    } else {
      equipo1.setTurno(false);
      equipo2.setTurno(true);
      turno = equipo2.turnoJugador();
    }
    eq.soltarFicha(f);
    actualizarMuertos();
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public void combinacion (ArrayList<Integer> posiciones) throws RemoteException {
    if (equipo1.getTurno()) {
      equipo1.combinacion(posiciones);
    } else {
      equipo2.combinacion(posiciones);
    }
    actualizarMuertos();
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
    verificarScoreWin();
  }

  public void agregarFichaComb (int c, int f) throws RemoteException {
    Equipo eq = equipo1;
    if (!equipo1.getTurno()) {
      eq = equipo2;
    }
    eq.agregarFichaComb(c, f);
    actualizarMuertos();
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
    verificarScoreWin();
  }

  public void actualizarMuertos () throws RemoteException {
    boolean set1 = equipo1.actualizarMuertos();
    boolean set2 = equipo2.actualizarMuertos();
    if (set1 || set2) {
      notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
      notificarObservadores(Eventos.SET_MUERTO);
    } else if (equipo1.sinFichas() || equipo2.sinFichas()) {
      ganador = equipo2.listarJugadores();
      Equipo eqGanador = equipo1;
      Equipo eqPerdedor = equipo2;

      if (equipo2.sinFichas()) {
        ganador = equipo1.listarJugadores();
        eqGanador = equipo1;
        eqPerdedor = equipo2;
      }

      actualizarRanking(eqGanador, eqPerdedor);

      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    }
  }

  private void actualizarRanking (Equipo ganador, Equipo perdedor) throws RemoteException {
    ArrayList<String> ganadores = ganador.getNombreJugadores();
    ArrayList<String> perdedores = perdedor.getNombreJugadores();

    for (String s : ganadores) {
      RankingSerializador.cargarJugador(s, 1);
    }

    for (String s : perdedores) {
      RankingSerializador.cargarJugador(s, 0);
    }
  }

  private void verificarScoreWin () throws RemoteException {
    if (equipo1.getScore() >= LIMITE) {
      ganador = equipo1.listarJugadores();
      actualizarRanking(equipo1, equipo2);
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    } else if (equipo2.getScore() >= LIMITE) {
      ganador = equipo2.listarJugadores();
      actualizarRanking(equipo2, equipo1);
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    }
  }

  public String getGanador () throws RemoteException {
    return ganador;
  }

  public void limpiarPartida () throws RemoteException {
    PartidaSerializador.eliminarPartida();
  }
}
