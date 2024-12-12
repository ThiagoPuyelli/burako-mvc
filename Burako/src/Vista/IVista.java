package Vista;

import modelo.Combinacion;
import modelo.Ficha;

import java.util.ArrayList;

public interface IVista {
    void iniciar();
    void mostrarTurno (String nombre);
    void mostrarFichas ();
    void setMuerto ();
    void terminarPartida ();
    void iniciarPartida ();
    void iniciarControlador();
}