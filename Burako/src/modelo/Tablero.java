package modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tablero extends ObservableRemoto implements ITablero {
  ArrayList<IObserver> observers = new ArrayList<IObserver>();
  ArrayList<IFicha> mazo = new ArrayList<>();
  ArrayList<IFicha> pozo = new ArrayList<>();
  IFicha[] muerto1 = new IFicha[11];
  IFicha[] muerto2 = new IFicha[11];
  Equipo equipo1;
  Equipo equipo2;
  String turno;
  String ganador;
  boolean start = false;

  public Tablero () {
    generarMazo();
    generarMuertos();
  }

  public ArrayList<IFicha> getPozo () throws RemoteException {
    return this.pozo;
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

  private void generarMazo () {
    for (ColorFicha c : ColorFicha.values()) {
      for (int i = 1;i <= 13;i++) {
        mazo.add(new Ficha(c, i));
        mazo.add(new Ficha(c, i));
        Ficha.ID++;
      }
    }
    Ficha.ID++;
    mazo.add(new Ficha(ColorFicha.rojo, 50));
    mazo.add(new Ficha(ColorFicha.azul, 50));
    Collections.shuffle(mazo);
  }

  private void generarMuertos () {
    for (int i = 0;i < 11;i++) {
      muerto1[i] = this.obtenerFicha();
      muerto2[i] = this.obtenerFicha();
    }
  }

  private ArrayList<IFicha> obtenerFichas (int cantidad) {
    ArrayList<IFicha> fichas = new ArrayList<>();
    for (int i = 0;i < cantidad;i++) {
      fichas.add(this.obtenerFicha());
    }
    return fichas;
  }

  public void setEquipos (Equipo eq) throws RemoteException {
    if (equipo1 == null) {
      equipo1 = eq;
    } else {
      equipo2 = eq;
      this.generarFichasEquipos();
      this.elegirTurno();
      notificarObservadores(Eventos.INICIAR_PARTIDA);
    }
  }

  public void agregarObservador (IObserver observer) throws RemoteException {
    this.observers.add(observer);
  }

  public void notificarObservadores (Object valor) throws RemoteException {
    for (IObserver o : observers) {
      o.actualizar(valor);
    }
  }

  protected void generarFichasEquipos () throws RemoteException {
    if (equipo1.getClass() == Equipo.class) {
        equipo1.setFichas(this.obtenerFichas(12));
        equipo2.setFichas(this.obtenerFichas(12));
    } else {
        equipo1.setFichas(this.obtenerFichas(11));
        equipo1.setFichas(this.obtenerFichas(11));
        equipo2.setFichas(this.obtenerFichas(11));
        equipo2.setFichas(this.obtenerFichas(11));
    }
  }

  private void elegirTurno () throws RemoteException {
    Random random = new Random();
    int value = random.nextInt(2);
    if (value == 0) {
      equipo1.setTurno(true);
      this.turno = equipo1.turnoJugador();
    } else {
      equipo2.setTurno(true);
      this.turno = equipo2.turnoJugador();
    }
  }

  private IFicha obtenerFicha () {
    return this.mazo.remove(0);
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

  public void agarrarPozo (int id) throws RemoteException {
    equipo1.agarrarPozo(id, pozo);
    equipo2.agarrarPozo(id, pozo);
    pozo.clear();
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public void agarrarMazo (int id) throws RemoteException {
    IFicha ficha = this.obtenerFicha();
    equipo1.agarrarMazo(id, ficha);
    equipo2.agarrarMazo(id, ficha);
    notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }

  public Jugador getJugador (String nombre) throws RemoteException {
    Jugador jugador = equipo1.getJugador(nombre);
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
    pozo.add(eq.soltarFicha(f));
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
    if (equipo1.sinFichas()) {
      if (muerto1[0] != null) {
        ArrayList<IFicha> muertoAct = new ArrayList<>();
        int i = 0;
        for (IFicha m : muerto1) {
          muertoAct.add(m);
          muerto1[i] = null;
          i++;
        }
        equipo1.setMuerto(muertoAct);
        notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
        notificarObservadores(Eventos.SET_MUERTO);
      } else {
        ganador = equipo1.listarJugadores();
        notificarObservadores(Eventos.TERMINAR_PARTIDA);
      }
    } else if (equipo2.sinFichas()) {
      if (muerto2[0] != null) {
        ArrayList<IFicha> muertoAct = new ArrayList<>();
        int i = 0;
        for (IFicha m : muerto2) {
          muertoAct.add(m);
          muerto2[i] = null;
          i++;
        }
        equipo2.setMuerto(muertoAct);
        notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
        notificarObservadores(Eventos.SET_MUERTO);
      } else {
        ganador = equipo2.listarJugadores();
        notificarObservadores(Eventos.TERMINAR_PARTIDA);
      }
    }
  }

  private void verificarScoreWin () throws RemoteException {
    if (equipo1.getScore() >= 100) {
      ganador = equipo1.listarJugadores();
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    } else if (equipo2.getScore() >= 100) {
      ganador = equipo2.listarJugadores();
      notificarObservadores(Eventos.TERMINAR_PARTIDA);
    }
  }

  public String getGanador () throws RemoteException {
    return ganador;
  }
}
