package Vista.Menus.MenusCliente;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElegirEquipo extends JFrame {
    private JLabel label;
    private JButton boton1;
    private JButton boton2;
    private JButton boton3;

    public ElegirEquipo(Controlador controlador) {
        // Configuración de la ventana
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes

        if (controlador.equipo1Lleno() && controlador.equipo2Lleno()) {
            label = new JLabel("           Los equipos estan llenos");
        } else {
            label = new JLabel("           Elige el equipo");
        }

        boton3 = new JButton("Salir");
        boton1 = new JButton("Equipo 1");
        boton2 = new JButton("Equipo 2");

        // Configurar diseño
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        if (!controlador.equipo1Lleno()) panelBotones.add(boton1);
        if (!controlador.equipo2Lleno()) panelBotones.add(boton2);

        add(panelBotones, BorderLayout.SOUTH);

        // Configurar acciones de los botones
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controlador.setEquipo(1);
                //controlador.conectarJugador();
                ElegirVista elegirVista = new ElegirVista(controlador);
                elegirVista.setVisible(true);
            }
        });


        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controlador.setEquipo(2);
                //controlador.conectarJugador();
                ElegirVista elegirVista = new ElegirVista(controlador);
                elegirVista.setVisible(true);
            }
        });

        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void iniciar () {
        setVisible(true);
    }
}
