package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Equipo implements Serializable {
    //protected IJugador jugador1;
    private Mazo mazo;
    private Pozo pozo;
    private ArrayList<IJugador> jugadores = new ArrayList<>();
    protected ArrayList<ICombinacion> combinaciones = new ArrayList<>();
    protected int score = 0;
    private boolean turno = false;
    private int turnoJugador = 0;
    private int tamanio;
    private IFicha[] muertos;

    public ArrayList<String> getNombreJugadores() throws RemoteException {
        ArrayList<String> nombres = new ArrayList<>();
        for (IJugador j : jugadores) {
            nombres.add(j.getNombre());
        }
        return nombres;
    }

    public Equipo (int tamanio, Mazo mazo, Pozo pozo) throws RemoteException {
        this.tamanio = tamanio;
        this.mazo = mazo;
        generarFichas();
        this.muertos = GeneradorPartida.generarMuerto(mazo);
        this.pozo = pozo;
    }

    public boolean lleno () {
        return jugadores.size() >= tamanio;
    }
    public void agregarJugador (IJugador jugador) throws RemoteException {
        if (!lleno()) {
            int cantidadF = tamanio == 1 ? 12 : 11;
            jugador.setFichas(mazo.obtenerFichas(cantidadF));
            jugadores.add(jugador);
        }
    }
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

    public void generarFichas () throws RemoteException {
        for (IJugador j : jugadores) {
            j.setFichas(mazo.obtenerFichas(11));
        }
    }

    private IJugador jugadorEnTurno () {
        return jugadores.get(turnoJugador);
    }

    //protected void setFichas (ArrayList<IFicha> fichas) throws RemoteException {
    //    jugador1.setFichas(fichas);
    //}

    public boolean verificarJugador (String nombre) throws RemoteException {
        for (IJugador ju : jugadores) {
            if (ju.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
        //return jugador1.getNombre().equals(nombre);
    }

    public String turnoJugador () throws RemoteException {
        return jugadorEnTurno().getNombre();
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

    public void agarrarPozo(String nombre) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        if (jugador.getNombre().equals(nombre) && jugador.getEstadoTurno() == 2) {
            agregarFichas(pozo.obtenerPozo());
            jugador.setEstadoTurno(1);
        }
    }

    public void agarrarMazo (String nombre) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        if (jugador.getNombre().equals(nombre) && jugador.getEstadoTurno() == 2) {
            agregarFichas(mazo.obtenerFicha());
            jugador.setEstadoTurno(1);
        }
    }

    public void setEstadoTurno(int estado) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        jugador.setEstadoTurno(estado);
        if (estado == 0) {
            rotarTurno();
        }
    }

    private void rotarTurno () {
        if (turnoJugador < jugadores.size() - 1) {
            turnoJugador++;
        } else {
            turnoJugador = 0;
        }
    }

    public IJugador getJugador (String nombre) throws RemoteException {
        for (IJugador ju : jugadores) {
            if (ju.getNombre().equals(nombre)) {
                return ju;
            }
        }
        return null;
        //IJugador jugador = jugadorEnTurno();
        //if (jugador.getNombre().equals(nombre)) {
        //    return jugador;
        //} else {
        //  return null;
        //}
    }

    public IFicha soltarFicha(int f) throws RemoteException {
        IJugador jugador = jugadorEnTurno();
        IFicha ficha = jugador.soltarFicha(f);
        pozo.agregarFicha(ficha);
        return ficha;
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

    public boolean actualizarMuertos () throws RemoteException {
        if (sinFichas() && muertos[0] != null) {
            int i = 0;
            for (IFicha f : muertos) {
                agregarFichas(f);
                muertos[i] = null;
                i++;
            }
            return true;
        }
        return false;
    }

    public int getScore () throws RemoteException {
        return this.score;
    }

    public String listarJugadores () throws RemoteException {
        String s = "";
        for (IJugador j : jugadores) {
            s += j.getNombre() + " ";
        }
        return s;
    }

}
