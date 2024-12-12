package modelo;

import Services.RankingScheme;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ITablero extends IObservableRemoto {
    void agregarJugador (String jugador, int equipo) throws RemoteException;
    ArrayList<IFicha> getPozo () throws RemoteException;
    ArrayList<ICombinacion> getCombinaciones(String nombre) throws RemoteException;
    String getTurno () throws RemoteException;
    void agarrarPozo (String nombre) throws RemoteException;
    void agarrarMazo (String nombre) throws RemoteException;
    IJugador getJugador (String nombre) throws RemoteException;
    void soltarFicha (int f) throws RemoteException;
    void combinacion (ArrayList<Integer> posiciones) throws RemoteException;
    void agregarFichaComb (int c, int f) throws RemoteException;
    ArrayList<ICombinacion> getCombinacionesContrario (String nombre) throws RemoteException;
    String getGanador () throws RemoteException;
    int getScore(String nombre) throws RemoteException;
    int getScoreContrario(String nombre) throws RemoteException;
    void iniciarPartida () throws RemoteException;
    ArrayList<IFicha> getFichas (String nombre) throws RemoteException;
    void setCantJugadores (int cantJugadores) throws RemoteException;
    ArrayList<RankingScheme> getRanking () throws RemoteException;
    int cantidadMazo () throws RemoteException;
    ArrayList<IJugadorProxy> getJugadoresProxy(String nombreJugador) throws RemoteException;
    boolean getPartidaCreada () throws RemoteException;
    void setPartidaCreada (boolean partidaCreada) throws RemoteException;
    boolean equipo1Lleno () throws RemoteException;
    boolean equipo2Lleno() throws RemoteException;
}
