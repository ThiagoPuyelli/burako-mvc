package modelo;

import java.util.ArrayList;

public class EquipoSolo extends Equipo {
    private Jugador jugador;

    public EquipoSolo (Jugador jugador) {
        this.jugador = jugador;
    }

    protected void setFichas (ArrayList<Ficha> fichas) {
        jugador.setFichas(fichas);
    }

    public boolean verificarJugador (String nombre) {
        return jugador.getNombre().equals(nombre);
    }

    public String turnoJugador () {
        return jugador.getNombre();
    }

    public void agregarFichas (ArrayList<Ficha> fichas) {
        ArrayList<Ficha> Jfichas = jugador.getFichas();
        Jfichas.addAll(fichas);
        jugador.setFichas(Jfichas);
    }

    public void agregarFichas (Ficha ficha) {
        ArrayList<Ficha> Jfichas = jugador.getFichas();
        Jfichas.add(ficha);
        jugador.setFichas(Jfichas);
    }

    public void agarrarPozo(int id, ArrayList<Ficha> pozo) {
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(pozo);
            jugador.setEstadoTurno(1);
        }
    }

    public void agarrarMazo (int id, Ficha ficha) {
        if (jugador.getId() == id && jugador.getEstadoTurno() == 2) {
            agregarFichas(ficha);
            jugador.setEstadoTurno(1);
        }
    }

    @Override
    public void setEstadoTurno(int estado) {
        jugador.setEstadoTurno(estado);
    }

    public Jugador getJugador (String nombre) {
        if (jugador.getNombre().equals(nombre)) {
            return jugador;
        } else {
          return null;
        }
    }

    @Override
    public Ficha soltarFicha(int f) {
        return jugador.soltarFicha(f);
    }

    public void combinacion (ArrayList<Integer> posiciones) {
        Combinacion combinacion = jugador.combinacion(posiciones);
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
        if (combinacion.agregarFicha(jugador.getFichas().get(f))) {
            score += sumarScore(jugador.getFichas().get(f));
            jugador.getFichas().remove(f);
            if (combinacion.getFichas().size() == 7) {
                score += 200;
            }
        }
    }

    public boolean sinFichas () {
        return jugador.getFichas().isEmpty();
    }

    public void setMuerto (ArrayList<Ficha> muerto) {
        agregarFichas(muerto);
    }

    public int getScore () {
        return this.score;
    }

    public String listarJugadores () {
        return jugador.getNombre();
    }
}
