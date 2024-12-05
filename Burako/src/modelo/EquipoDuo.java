package modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EquipoDuo extends Equipo {
    private Jugador jugador2;
    private int turnoJugador = 0;

    public EquipoDuo (Jugador jugador1, Jugador jugador2) {
        super(jugador1);
        this.jugador2 = jugador2;
    }

    public void setFichas (ArrayList<IFicha> fichas) throws RemoteException {
        if (jugador1.getFichas() == null || jugador1.getFichas().isEmpty()) {
            jugador1.setFichas(fichas);
        } else {
            jugador2.setFichas(fichas);
        }
    }

    public boolean verificarJugador (String nombre) throws RemoteException {
        return jugador1.getNombre().equals(nombre) || jugador2.getNombre().equals(nombre);
    }

    private Jugador jugadorEnTurno () {
        if (turnoJugador == 0) {
            return jugador1;
        } else {
            return jugador2;
        }
    }

    public void setEstadoTurno(int estado) throws RemoteException {
        jugadorEnTurno().setEstadoTurno(estado);
        if (estado == 0) {
            rotarTurno();
        }
    }

    private void rotarTurno () {
        if (turnoJugador == 0) {
            turnoJugador = 1;
        } else {
            turnoJugador = 0;
        }
    }

    public Jugador getJugador (String nombre) throws RemoteException {
        if (jugador1.getNombre().equals(nombre)) {
            return jugador1;
        } else if (jugador2.getNombre().equals(nombre)) {
            return jugador2;
        } else {
            return null;
        }
    }

    public IFicha soltarFicha(int f) throws RemoteException {
        if (jugadorEnTurno() == jugador1) {
            return jugador2.soltarFicha(f);
        } else {
            return jugador1.soltarFicha(f);
        }
    }

    public String turnoJugador () throws RemoteException {
        return jugadorEnTurno().getNombre();
    }

    public boolean sinFichas () throws RemoteException {
        return jugador1.getFichas().isEmpty() || jugador2.getFichas().isEmpty();
    }

    public void setMuerto (ArrayList<IFicha> muerto) throws RemoteException {
        Jugador jugador;
        if (jugador1.getFichas().isEmpty()) {
            jugador = jugador1;
        } else {
            jugador = jugador2;
        }
        ArrayList<IFicha> Jfichas = jugador.getFichas();
        Jfichas.addAll(muerto);
        jugador.setFichas(Jfichas);
    }

    public int getScore () {
        return this.score;
    }

    public String listarJugadores () throws RemoteException {
        return jugador1.getNombre() + " " + jugador2.getNombre();
    }
}
