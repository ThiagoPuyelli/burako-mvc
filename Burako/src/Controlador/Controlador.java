package Controlador;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import Services.RankingScheme;
import Services.TableroScheme;
import Vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.*;

public class Controlador implements IControladorRemoto {
  ITablero tablero;
  String nombreJugador;
  int equipo;
  IVista vista;

  public void setJugador (String jugador)  {
      this.nombreJugador = jugador;
  }

  public boolean existeJugador () {
      try {
          return tablero.getJugador(nombreJugador) != null;
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void setEquipo (int equipo) {
      this.equipo = equipo;
  }

  public boolean getPartidaCreada () {
      try {
          return tablero.getPartidaCreada();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

    public boolean equipo1Lleno () {
        try {
            return tablero.equipo1Lleno();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equipo2Lleno() {
        try {
            return tablero.equipo2Lleno();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

  public void crearPartida () {
      try {
          tablero.setPartidaCreada(true);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public boolean getPartidaIniciada () {
      try {
          return tablero.partidaIniciada();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void conectarJugador () {
      try {
          tablero.agregarJugador(nombreJugador, equipo);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

    public void desconectarJugador() {
        try {
            System.out.println("EJECUTADO");
            tablero.cerrar(this, nombreJugador, equipo);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void limpiarPartidas () {

    }

    public ArrayList<TableroScheme> obtenerPartidas () {
        try {
            return tablero.obtenerPartidas();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean posibleRecuperar () {
        try {
            for (IJugadorProxy j : this.tablero.getJugadoresARecuperar()) {
                if (j.getNombre().equals(nombreJugador)) return true;
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void elegirPartida (int posTablero) {
        try {
            tablero.elegirPartida(posTablero);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

  public int cantidadMazo () {
      try {
          return tablero.cantidadMazo();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public ArrayList<IJugadorProxy> getJugadoresProxy () {
      try {
          return tablero.getJugadoresProxy(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public String getNombre () {
      return nombreJugador;
  }

  public void setVista (IVista vista) { this.vista = vista; }

  public void iniciarPartida () {
      try {
          tablero.iniciarPartida();
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public void mostrarTurno () {
      try {
          System.out.println("FRANCO HACE EL COITO");
          vista.mostrarTurno(tablero.getTurno());
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public ArrayList<RankingScheme> obtenerRanking () {
      try {
          return tablero.getRanking();
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
          System.out.println(nombreJugador + " Que gordo este mique");
          return tablero.getCombinaciones(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public ArrayList<ICombinacion> getCombinacionesContrario () {
      try {
          System.out.println(nombreJugador + " Que contrario este mique");
          return tablero.getCombinacionesContrario(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public ArrayList<IFicha> getFichas () {
      try {
          return tablero.getFichas(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void agarrarPozo () {
      try {
          tablero.agarrarPozo(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }
  public void agarrarMazo () {
      try {
          tablero.agarrarMazo(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public int getEstadoTurno () {
      try {
          IJugador jugador = tablero.getJugador(nombreJugador);
          if (jugador != null) {
              return jugador.getEstadoTurno();
          } else {
              return -1;
          }
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
          return tablero.getScore(nombreJugador);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

    public int getScoreContrario () {
        try {
            return tablero.getScoreContrario(nombreJugador);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

  public void setCantJugadores (int cantJugadores) {
      try {
          tablero.setCantJugadores(cantJugadores);
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }
  }

  public void reconectarJugador () {
      try {
          tablero.reconectarJugador(nombreJugador);
          equipo = tablero.getEquipo(nombreJugador);
      } catch (RemoteException e) {
          e.printStackTrace();
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
      } else if (valor == Eventos.DESCONECTADO) {
        vista.setDesconexion();
      }
    }
  }
}
