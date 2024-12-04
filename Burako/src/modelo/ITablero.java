package modelo;

import java.util.ArrayList;

public interface ITablero {
    void notificarObservadores(Object valor);
    void agregarObservador (IObserver observer);
    ArrayList<IFicha> getPozo ();
    ArrayList<Combinacion> getCombinaciones(String nombre);
    String getTurno ();
    boolean getStart ();
    void setStart (boolean start);
    void agarrarPozo (int id);
    void agarrarMazo (int id);
    Jugador getJugador (String nombre);
    void soltarFicha (int f);
    void combinacion (ArrayList<Integer> posiciones);
    void agregarFichaComb (int c, int f);
    ArrayList<Combinacion> getCombinacionesContrario (String nombre);
    String getGanador ();
    int getScore(String nombre);
}
