package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Equipo implements Serializable {
    protected IJugador jugador1;
    protected ArrayList<ICombinacion> combinaciones = new ArrayList<>();
    protected int score = 0;
    public boolean turno = false;

    public boolean lleno () {
        return jugador1 != null;
    }
    public void agregarJugador (IJugador jugador) throws RemoteException { this.jugador1 = jugador; }
    public boolean getTurno () {
        return turno;
    }
    public void setTurno (boolean turno) throws RemoteException {
        this.turno = turno;
        if (turno) {
            setEstadoTurno(2);
        } else {
            setEstadoTurno(0);
        }
    }
    public ArrayList<ICombinacion> getCombinaciones () {
        return combinaciones;
    }
    static int sumarScore (IFicha f) {
        if (f.getNumero() == 1) {
            return 15;
        } else if (f.getNumero() == 2) {
            return 20;
        } else if (f.getNumero() >= 3 && f.getNumero() <= 7) {
            return 5;
        } else {
            return 10;
        }
    }

    private IJugador jugadorEnTurno () { return jugador1; }

    protected void setFichas (ArrayList<IFicha> fichas) throws RemoteException {
        jugador1.setFichas(fichas);
    }

    public boolean verificarJugador (String nombre) throws RemoteException {
        return jugador1.getNombre().equals(nombre);
    }

    public String turnoJugador () throws RemoteException {
        return jugador1.getNombre();
    }

    public void agregarFichas (ArrayList<IFicha> fichas) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        ArrayList<IFicha> Jfichas = jugador.getFichas();
        Jfichas.addAll(fichas);
        jugador.setFichas(Jfichas);
    }

    public void agregarFichas (IFicha ficha) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        ArrayList<IFicha> Jfichas = jugador.getFichas();
        Jfichas.add(ficha);
        jugador.setFichas(Jfichas);
    }

    public void agarrarPozo(int id, ArrayList<IFicha> pozo) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(pozo);
            jugador.setEstadoTurno(1);
        }
    }

    public void agarrarMazo (int id, IFicha ficha) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(ficha);
            jugador.setEstadoTurno(1);
        }
    }

    public void setEstadoTurno(int estado) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        jugador.setEstadoTurno(estado);
    }

    public IJugador getJugador (String nombre) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        if (jugador.getNombre().equals(nombre)) {
            return jugador;
        } else {
          return null;
        }
    }

    public IFicha soltarFicha(int f) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        return jugador.soltarFicha(f);
    }

    public void combinacion (ArrayList<Integer> posiciones) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        ICombinacion combinacion = jugador.combinacion(posiciones);
        if (combinacion != null) {
          combinaciones.add(combinacion);
        }
        for (IFicha f : combinacion.getFichas()) {
            score += sumarScore(f);
        }
        if (combinacion.getFichas().size() >= 7) {
            score += 200;
        }
    }

    public void agregarFichaComb (int c, int f) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        ICombinacion combinacion = combinaciones.get(c);
        if (combinacion.agregarFicha(jugador.getFichas().get(f))) {
            score += sumarScore(jugador.getFichas().get(f));
            jugador.getFichas().remove(f);
            if (combinacion.getFichas().size() == 7) {
                score += 200;
            }
        }
    }

    public boolean sinFichas () throws RemoteException {
        return jugadorEnTurno().getFichas().isEmpty();
    }

    public void setMuerto (ArrayList<IFicha> muerto) throws RemoteException {
        agregarFichas(muerto);
    }

    public int getScore () throws RemoteException {
        return this.score;
    }

    public String listarJugadores () throws RemoteException {
        return jugador1.getNombre();
    }
}
