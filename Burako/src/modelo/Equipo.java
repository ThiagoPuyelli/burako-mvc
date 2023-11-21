package modelo;

import java.util.ArrayList;

public abstract class Equipo {
    protected ArrayList<Combinacion> combinaciones = new ArrayList<>();
    protected int score = 0;
    public boolean turno = false;

    //public abstract void agarrarFicha ();
    //public abstract void jugar (ArrayList<Integer> fichas);
    public boolean getTurno () {
        return turno;
    }
    public abstract boolean verificarJugador (String nombre);
    public abstract String turnoJugador ();

    public void setTurno (boolean turno) {
        this.turno = turno;
        if (turno) {
            setEstadoTurno(2);
        } else {
            setEstadoTurno(0);
        }
    }

    public ArrayList<Combinacion> getCombinaciones () {
        return combinaciones;
    }

    protected abstract void setFichas (ArrayList<Ficha> fichas);
    protected abstract void agregarFichas (ArrayList<Ficha> fichas);
    protected abstract void agregarFichas (Ficha ficha);
    public abstract void agarrarPozo(int id, ArrayList<Ficha> pozo);
    public abstract void agarrarMazo(int id, Ficha ficha);
    public abstract void setEstadoTurno (int estado) ;
    public abstract Jugador getJugador (String nombre) ;
    public abstract Ficha soltarFicha (int f);
    public abstract void combinacion (ArrayList<Integer> posiciones);
    public abstract void agregarFichaComb (int c, int f);
    public abstract boolean sinFichas ();
    public abstract void setMuerto (ArrayList<Ficha> fichas);
    public abstract int getScore ();
    static int sumarScore (Ficha f) {
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

    public abstract String listarJugadores ();
}
