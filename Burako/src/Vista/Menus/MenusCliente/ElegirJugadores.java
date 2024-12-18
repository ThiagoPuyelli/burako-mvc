package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Services.RankingScheme;
import Vista.Grafica.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ElegirJugadores extends JFrame {
    private JLabel label;
    private JButton boton1;
    private JButton boton2;

    public ElegirJugadores(Controlador controlador) {
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("           Elige el tipo de partida");
        boton1 = new JButton("Solo");
        boton2 = new JButton("Duo");

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        add(panelBotones, BorderLayout.SOUTH);

        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controlador.crearPartida();
                controlador.setCantJugadores(1);
                ElegirEquipo elegirEquipo = new ElegirEquipo(controlador);
                elegirEquipo.setVisible(true);
            }
        });

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                controlador.crearPartida();
                controlador.setCantJugadores(2);
                ElegirEquipo elegirEquipo = new ElegirEquipo(controlador);
                elegirEquipo.setVisible(true);
            }
        });
    }

    public void iniciar () {
        setVisible(true);
    }
}
