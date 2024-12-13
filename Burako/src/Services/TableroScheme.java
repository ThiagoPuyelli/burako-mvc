package Services;

import modelo.JugadorDesconectado;
import modelo.Mazo;
import modelo.Pozo;

import java.io.Serializable;
import java.util.ArrayList;

public class TableroScheme implements Serializable {
    private Mazo mazo;
    private Pozo pozo;
    private EquipoScheme equipo1;
    private EquipoScheme equipo2;
    private String turno;
    private String ganador;
    private ArrayList<JugadorDesconectado> jugadoresDesconectados = new ArrayList<>();

    public TableroScheme(Mazo mazo, Pozo pozo, EquipoScheme equipo1, EquipoScheme equipo2, String turno, String ganador, ArrayList<JugadorDesconectado> jugadoresDesconectados) {
        this.mazo = mazo;
        this.pozo = pozo;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.turno = turno;
        this.ganador = ganador;
        this.jugadoresDesconectados = jugadoresDesconectados;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public EquipoScheme getEquipo1() {
        return equipo1;
    }

    public EquipoScheme getEquipo2() {
        return equipo2;
    }

    public String getTurno() {
        return turno;
    }

    public String getGanador() {
        return ganador;
    }

    public ArrayList<JugadorDesconectado> getJugadoresDesconectados () {
        return this.jugadoresDesconectados;
    }
}
