package Vista.Grafica;

import Controlador.Controlador;
import Vista.IVista;
import Vista.VistaPlay;

import javax.swing.*;

public class Grafica extends VistaPlay implements IVista {
    private final JFrame frame = new JFrame();
    boolean inicioPartida = false;
    boolean terminaPartida = false;

    public Grafica(Controlador controlador) {
        super(controlador);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
    }

    @Override
    public void iniciar() {

    }

    @Override
    public void mostrarTurno(String nombre) {

    }

    @Override
    public void mostrarFichas() {

    }

    @Override
    public void setMuerto() {

    }

    @Override
    public void terminarPartida() {

    }

    @Override
    public void iniciarPartida() {

    }
}
