package modelo;

import java.util.ArrayList;

public class EquipoDuo extends Equipo {
    private Jugador jugador1;
    private Jugador jugador2;
    private int turnoJugador = 0;

    public EquipoDuo (Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public void setFichas (ArrayList<Ficha> fichas) {
        if (jugador1.getFichas() == null || jugador1.getFichas().isEmpty()) {
            jugador1.setFichas(fichas);
        } else {
            jugador2.setFichas(fichas);
        }
    }

    public boolean verificarJugador (String nombre) {
        return jugador1.getNombre().equals(nombre) || jugador2.getNombre().equals(nombre);
    }

    private Jugador jugadorEnTurno () {
        if (turnoJugador == 0) {
            return jugador1;
        } else {
            return jugador2;
        }
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

    public Jugador getJugador (String nombre) {
        if (jugador1.getNombre().equals(nombre)) {
            return jugador1;
        } else if (jugador2.getNombre().equals(nombre)) {
            return jugador2;
        } else {
            return null;
        }
    }

    public Ficha soltarFicha(int f) {
        if (jugadorEnTurno() == jugador1) {
            return jugador2.soltarFicha(f);
        } else {
            return jugador1.soltarFicha(f);
        }
    }

    public void combinacion (ArrayList<Integer> posiciones) {
        Combinacion combinacion = jugadorEnTurno().combinacion(posiciones);
        if (combinacion != null) {
            super.combinaciones.add(combinacion);
        }
        for (Ficha f : combinacion.getFichas()) {
            score += sumarScore(f);
        }
        if (combinacion.getFichas().size() >= 7) {
            score += 200;
        }
    }

    public void agregarFichaComb (int c, int f) {
        Combinacion combinacion = combinaciones.get(c);
        Jugador jugador = jugadorEnTurno();
        if (combinacion.agregarFicha(jugador.getFichas().get(f))) {
            score += sumarScore(jugador.getFichas().get(f));
            jugador.getFichas().remove(f);
            if (combinacion.getFichas().size() == 7) {
                score += 200;
            }
        }

    }

    public String turnoJugador () {
        return jugadorEnTurno().getNombre();
    }

    public boolean sinFichas () {
        return jugador1.getFichas().isEmpty() || jugador2.getFichas().isEmpty();
    }

    public void setMuerto (ArrayList<Ficha> muerto) {
        Jugador jugador;
        if (jugador1.getFichas().isEmpty()) {
            jugador = jugador1;
        } else {
            jugador = jugador2;
        }
        ArrayList<Ficha> Jfichas = jugador.getFichas();
        Jfichas.addAll(muerto);
        jugador.setFichas(Jfichas);
    }

    public int getScore () {
        return this.score;
    }

    public String listarJugadores () {
        return jugador1.getNombre() + " " + jugador2.getNombre();
    }
}
