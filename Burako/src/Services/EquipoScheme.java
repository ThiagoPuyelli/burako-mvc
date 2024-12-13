package Services;

import modelo.ICombinacion;
import modelo.IFicha;
import modelo.IJugador;

import java.io.Serializable;
import java.util.ArrayList;

public class EquipoScheme implements Serializable {
    private ArrayList<IJugador> jugadores = new ArrayList<>();
    private ArrayList<ICombinacion> combinaciones = new ArrayList<>();
    private int score = 0;
    private boolean turno;
    private int turnoJugador = 0;
    private int tamanio;
    private IFicha[] muertos;

    public EquipoScheme(ArrayList<IJugador> jugadores, ArrayList<ICombinacion> combinaciones, int score, boolean turno, int turnoJugador, int tamanio, IFicha[] muertos) {
        this.jugadores = jugadores;
        this.combinaciones = combinaciones;
        this.score = score;
        this.turno = turno;
        this.turnoJugador = turnoJugador;
        this.tamanio = tamanio;
        this.muertos = muertos;
    }

    public ArrayList<IJugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<ICombinacion> getCombinaciones() {
        return combinaciones;
    }

    public int getScore() {
        return score;
    }

    public boolean getTurno () {
        return turno;
    }

    public int getTurnoJugador() {
        return turnoJugador;
    }

    public int getTamanio() {
        return tamanio;
    }

    public IFicha[] getMuertos() {
        return muertos;
    }
}
