package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class JugadorDesconectado implements IJugadorProxy, Serializable {
    @Serial
    private static final long serialVersionUID = -5188948314412774811L;
    private String nombre;
    private int estadoTurno;
    private ArrayList<IFicha> fichas;
    private int equipo;

    public JugadorDesconectado(String nombre, int estadoTurno, int equipo, ArrayList<IFicha> fichas) {
        this.nombre = nombre;
        this.estadoTurno = estadoTurno;
        this.equipo = equipo;
        this.fichas = fichas;
    }

    public ArrayList<IFicha> getFichas() {
        return fichas;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int cantFichas() throws RemoteException {
        return fichas.size();
    }

    public int getEstadoTurno() {
        return estadoTurno;
    }

    public int getEquipo() {
        return equipo;
    }
}
