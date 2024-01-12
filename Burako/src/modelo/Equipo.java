package modelo;

import java.util.ArrayList;

public class Equipo {
    protected Jugador jugador1;
    protected ArrayList<Combinacion> combinaciones = new ArrayList<>();
    protected int score = 0;
    public boolean turno = false;

    public Equipo(Jugador jugador1) {
        this.jugador1 = jugador1;
    }
    public boolean getTurno () {
        return turno;
    }
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

    private Jugador jugadorEnTurno () { return jugador1; }

    protected void setFichas (ArrayList<Ficha> fichas) {
        jugador1.setFichas(fichas);
    }

    public boolean verificarJugador (String nombre) {
        return jugador1.getNombre().equals(nombre);
    }

    public String turnoJugador () {
        return jugador1.getNombre();
    }

    public void agregarFichas (ArrayList<Ficha> fichas) {
        Jugador jugador = jugadorEnTurno();
        ArrayList<Ficha> Jfichas = jugador.getFichas();
        Jfichas.addAll(fichas);
        jugador.setFichas(Jfichas);
    }

    public void agregarFichas (Ficha ficha) {
        Jugador jugador = jugadorEnTurno();
        ArrayList<Ficha> Jfichas = jugador.getFichas();
        Jfichas.add(ficha);
        jugador.setFichas(Jfichas);
    }

    public void agarrarPozo(int id, ArrayList<Ficha> pozo) {
        Jugador jugador = jugadorEnTurno();
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(pozo);
            jugador.setEstadoTurno(1);
        }
    }

    public void agarrarMazo (int id, Ficha ficha) {
        Jugador jugador = jugadorEnTurno();
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(ficha);
            jugador.setEstadoTurno(1);
        }
    }

    public void setEstadoTurno(int estado) {
        Jugador jugador = jugadorEnTurno();
        jugador.setEstadoTurno(estado);
    }

    public Jugador getJugador (String nombre) {
        Jugador jugador = jugadorEnTurno();
        if (jugador.getNombre().equals(nombre)) {
            return jugador;
        } else {
          return null;
        }
    }

    public Ficha soltarFicha(int f) {
        Jugador jugador = jugadorEnTurno();
        return jugador.soltarFicha(f);
    }

    public void combinacion (ArrayList<Integer> posiciones) {
        Jugador jugador = jugadorEnTurno();
        Combinacion combinacion = jugador.combinacion(posiciones);
        if (combinacion != null) {
          combinaciones.add(combinacion);
        }
        for (Ficha f : combinacion.getFichas()) {
            score += sumarScore(f);
        }
        if (combinacion.getFichas().size() >= 7) {
            score += 200;
        }
    }

    public void agregarFichaComb (int c, int f) {
        Jugador jugador = jugadorEnTurno();
        Combinacion combinacion = combinaciones.get(c);
        if (combinacion.agregarFicha(jugador.getFichas().get(f))) {
            score += sumarScore(jugador.getFichas().get(f));
            jugador.getFichas().remove(f);
            if (combinacion.getFichas().size() == 7) {
                score += 200;
            }
        }
    }

    public boolean sinFichas () {
        return jugadorEnTurno().getFichas().isEmpty();
    }

    public void setMuerto (ArrayList<Ficha> muerto) {
        agregarFichas(muerto);
    }

    public int getScore () {
        return this.score;
    }

    public String listarJugadores () {
        return jugador1.getNombre();
    }
}
