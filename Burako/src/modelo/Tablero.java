package modelo;

import Services.RankingScheme;
import Services.RankingSerializador;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tablero extends ObservableRemoto implements ITablero, Serializable {
  private Mazo mazo;
  private Pozo pozo = new Pozo();
  //private IFicha[] muerto1 = new IFicha[11];
  //private IFicha[] muerto2 = new IFicha[11];
  private Equipo equipo1;
  private Equipo equipo2;
  private String turno;
  private String ganador;
  private boolean start = false;

  public Tablero () throws RemoteException {
    mazo = GeneradorPartida.generarMazo();

  }

  public void setCantJugadores (int cantJugadores) throws RemoteException {
    equipo1 = new Equipo(cantJugadores, mazo, pozo);
    equipo2 = new Equipo(cantJugadores, mazo, pozo);
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

  public boolean getStart () throws RemoteException {
    return start;
  }
  public void setStart (boolean start) throws RemoteException {
    this.start = start;
  }

  public String getTurno () throws RemoteException {
    return turno;
  }

  public void agregarJugador (String jugador, int equipo) throws RemoteException {
    IJugador newJugador = new Jugador(jugador);
    if (equipo == 1) {
      equipo1.agregarJugador(newJugador);
    } else {
      equipo2.agregarJugador(newJugador);
    }
  }

  public void iniciarPartida () throws RemoteException {
    System.out.println("DALEEEEEEE");
    System.out.println(equipo1.lleno() + " " + equipo2.lleno());
    if (equipo1.lleno() && equipo2.lleno()) {
      System.out.println(equipo1.listarJugadores() + " : " + equipo2.listarJugadores());
      //this.generarFichasEquipos();
      this.elegirTurno();
      notificarObservadores(Eventos.INICIAR_PARTIDA);
    }
  }

  // PROXIMO A ELIMINAR
  public void setEquipos (Equipo eq) throws RemoteException {
    if (equipo1 == null) {
      equipo1 = eq;
    } else {
      equipo2 = eq;
      //this.generarFichasEquipos();
      this.elegirTurno();
      notificarObservadores(Eventos.INICIAR_PARTIDA);
    }
  }

  //protected void generarFichasEquipos () throws RemoteException {
  //  equipo1.generarFichas(mazo);
  //  equipo2.generarFichas(mazo);
  //  //if (equipo1.getClass() == Equipo.class) {
  //  //    equipo1.setFichas(this.obtenerFichas(12));
  //  //    equipo2.setFichas(this.obtenerFichas(12));
  //  //} else {
  //  //    equipo1.setFichas(this.obtenerFichas(11));
  //  //    equipo1.setFichas(this.obtenerFichas(11));
  //  //    equipo2.setFichas(this.obtenerFichas(11));
  //  //    equipo2.setFichas(this.obtenerFichas(11));
  //  //}
  //}

  private void elegirTurno () throws RemoteException {
    //Random random = new Random();
    //int value = random.nextInt(2);
    //if (value == 0) {
      equipo1.setTurno(true);
      this.turno = equipo1.turnoJugador();
    //} else {
    //  equipo2.setTurno(true);
    //  this.turno = equipo2.turnoJugador();
    //}
  }

  private IFicha obtenerFicha () {
    return mazo.obtenerFicha();
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

  public void agarrarPozo (String nombre) throws RemoteException {
    equipo1.agarrarPozo(nombre);
    equipo2.agarrarPozo(nombre);
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public void agarrarMazo (String nombre) throws RemoteException {
    IFicha ficha = this.obtenerFicha();
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
    //System.out.println("Combinacion Equipo1: " + equipo1.listarJugadores() + " " + equipo1.getTurno());
    //System.out.println("Combinacion Equipo2: " + equipo2.listarJugadores() + " " + equipo2.getTurno());
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
    if (equipo1.getScore() >= 100) {
      ganador = equipo1.listarJugadores();
      actualizarRanking(equipo1, equipo2);
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    } else if (equipo2.getScore() >= 100) {
      ganador = equipo2.listarJugadores();
      actualizarRanking(equipo2, equipo1);
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    }
  }

  public String getGanador () throws RemoteException {
    return ganador;
  }
}
