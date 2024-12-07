package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ITablero extends IObservableRemoto {
    IJugador agregarJugador (String jugador, int equipo) throws RemoteException;
    ArrayList<IFicha> getPozo () throws RemoteException;
    ArrayList<ICombinacion> getCombinaciones(String nombre) throws RemoteException;
    String getTurno () throws RemoteException;
    boolean getStart () throws RemoteException;
    void setStart (boolean start) throws RemoteException;
    void agarrarPozo (int id) throws RemoteException;
    void agarrarMazo (int id) throws RemoteException;
    IJugador getJugador (String nombre) throws RemoteException;
    void soltarFicha (int f) throws RemoteException;
    void combinacion (ArrayList<Integer> posiciones) throws RemoteException;
    void agregarFichaComb (int c, int f) throws RemoteException;
    ArrayList<ICombinacion> getCombinacionesContrario (String nombre) throws RemoteException;
    String getGanador () throws RemoteException;
    int getScore(String nombre) throws RemoteException;
    void iniciarPartida () throws RemoteException;
}
