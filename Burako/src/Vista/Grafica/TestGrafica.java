package Vista.Grafica;

import Controlador.Controlador;
import modelo.Tablero;

import java.rmi.RemoteException;

public class TestGrafica {
    public static void main(String[] args) {
        Tablero tablero;
        try {
            tablero = new Tablero();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Controlador controlador = new Controlador();
        Controlador controlador2 = new Controlador();
        try {
            controlador.setModeloRemoto(tablero);
            controlador2.setModeloRemoto(tablero);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Grafica g = new Grafica(controlador);
        controlador.setJugador("pepe");
        controlador2.setJugador("pepe2");
        Grafica g2 = new Grafica(controlador2);
        g2.iniciar();
        g.iniciar();

    }
}
