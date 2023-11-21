package Controlador;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import Vista.IVista;
import modelo.*;

public class Controlador implements IObserver {
  ITablero tablero;
  IJugador jugador;
  IVista vista;

  public Controlador (ITablero tablero) {
    this.tablero = tablero;
  }

  public void setJugador (IJugador jugador) {
    this.jugador = jugador;
  }
  public String getNombre () { return jugador.getNombre(); }
  public void actualizar (Object valor) {
    if (valor instanceof Eventos) {
      if (valor == Eventos.INICIAR_PARTIDA) {
        vista.iniciarPartida();
        mostrarTurno();
        mostrarFichas();
      } else if (valor == Eventos.ACTUALIZAR_PARTIDA) {
        mostrarTurno();
        mostrarFichas();
      } else if (valor == Eventos.SET_MUERTO) {
        vista.setMuerto();
      } else if (valor == Eventos.TERMINAR_PARTIDA) {
        vista.terminarPartida();
      }
    }
  }
  public void setVista (IVista vista) { this.vista = vista; }
  public void iniciarPartida () {
    tablero.setStart(true);
    tablero.notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
  }
  public void mostrarTurno () {
    vista.mostrarTurno(tablero.getTurno());
  }

  public boolean getStart () {
    return tablero.getStart();
  }
  public void mostrarFichas () {
    vista.mostrarFichas();
  }
  public ArrayList<Ficha> getPozo () {
    return tablero.getPozo();
  }
  public ArrayList<Combinacion> getCombinaciones () {
    return tablero.getCombinaciones(jugador.getNombre());
  }
  public ArrayList<Combinacion> getCombinacionesContrario () {
    return tablero.getCombinacionesContrario(jugador.getNombre());
  }
  public ArrayList<Ficha> getFichas () {
    return jugador.getFichas();
  }

  public void agarrarPozo () {
    tablero.agarrarPozo(jugador.getId());
  }
  public void agarrarMazo () {
    tablero.agarrarMazo(jugador.getId());
  }

  public int getEstadoTurno () {
    return jugador.getEstadoTurno();
  }

  public int getEstadoTurno (String nombre) {
    IJugador jugador = tablero.getJugador(nombre);
    if (jugador != null) {
      return jugador.getEstadoTurno();
    } else {
      return -1;
    }
  }

  public void soltarFicha (int f) {
    tablero.soltarFicha(f);
  }

  public void combinacion (ArrayList<Integer> fichas) {
    tablero.combinacion(fichas);
  }

  public void agregarFichaComb (int c, int f) {
    tablero.agregarFichaComb(c, f);
  }

  public String getGanador () {
    return tablero.getGanador();
  }
  public int getScore () { return tablero.getScore(jugador.getNombre()); }
}
