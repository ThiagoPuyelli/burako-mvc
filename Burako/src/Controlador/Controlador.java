package Controlador;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import Vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.*;

public class Controlador implements IControladorRemoto {
  ITablero tablero;
  IJugador jugador;
  IVista vista;

  public void setJugador (IJugador jugador) {
    this.jugador = jugador;
  }
  public String getNombre () {
      try {
          return jugador.getNombre();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
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
      try {
          tablero.setStart(true);
          tablero.notificarObservadores(Eventos.ACTUALIZAR_PARTIDA);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public void mostrarTurno () {
      try {
          vista.mostrarTurno(tablero.getTurno());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public boolean getStart () {
      try {
          return tablero.getStart();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public void mostrarFichas () {
    vista.mostrarFichas();
  }
  public ArrayList<IFicha> getPozo () {
      try {
          return tablero.getPozo();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public ArrayList<ICombinacion> getCombinaciones () {
      try {
          return tablero.getCombinaciones(jugador.getNombre());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public ArrayList<ICombinacion> getCombinacionesContrario () {
      try {
          return tablero.getCombinacionesContrario(jugador.getNombre());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public ArrayList<IFicha> getFichas () {
      try {
          return jugador.getFichas();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void agarrarPozo () {
      try {
          tablero.agarrarPozo(jugador.getId());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public void agarrarMazo () {
      try {
          tablero.agarrarMazo(jugador.getId());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public int getEstadoTurno () {
      try {
          return jugador.getEstadoTurno();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public int getEstadoTurno (String nombre) {
      try {
          IJugador jugador = tablero.getJugador(nombre);
          if (jugador != null) {
            return jugador.getEstadoTurno();
          } else {
            return -1;
          }
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void soltarFicha (int f) {
      try {
          tablero.soltarFicha(f);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void combinacion (ArrayList<Integer> fichas) {
      try {
          tablero.combinacion(fichas);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void agregarFichaComb (int c, int f) {
      try {
          tablero.agregarFichaComb(c, f);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public String getGanador () {
      try {
          return tablero.getGanador();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public int getScore () {
      try {
          return tablero.getScore(jugador.getNombre());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  @Override
  public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
    tablero = (ITablero)t;
  }

  @Override
  public void actualizar(IObservableRemoto iObservableRemoto, Object valor) throws RemoteException {
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
}
