package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Vista.Consola.ConsoleGUI;
import Vista.Grafica.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElegirVista extends JFrame {
    private JLabel label;
    private JButton boton1;
    private JButton boton2;
    private JTextArea ranking;
    private Controlador controlador;

    public ElegirVista (Controlador controlador) {
        // Configuración de la ventana
        this.controlador = controlador;
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        label = new JLabel("           Elige el tipo de partida");
        boton1 = new JButton("Grafica");
        boton2 = new JButton("Consola");

        // Configurar diseño
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        ranking = new JTextArea();
        ranking.setEditable(false);
        add(panelBotones, BorderLayout.SOUTH);
        add(ranking, BorderLayout.EAST);

        // Configurar acciones de los botones
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    Grafica vista = new Grafica(controlador);
                    controlador.setVista(vista);
                    controlador.conectarJugador();
                    controlador.reconectarJugador();
                    controlador.limpiarPartidas();
                    vista.iniciar();
                    vista.iniciarControlador();
                });

            }
        });

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ConsoleGUI vista = new ConsoleGUI(controlador);
                controlador.setVista(vista);
                controlador.conectarJugador();
                controlador.reconectarJugador();
                controlador.limpiarPartidas();
                vista.iniciar();
                vista.iniciarControlador();
            }
        });
    }

    public void iniciar () {
        setVisible(true);
    }
}
