package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Services.TableroScheme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ElegirPartida extends JFrame {
    private JLabel label;
    private ArrayList<TableroScheme> partidas;

    public ElegirPartida(Controlador controlador) {
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        label = new JLabel("           Elije una partida a iniciar");

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        //panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        //panelBotones.setAlignmentX(SwingConstants.CENTER);
        JButton nuevaPartida = new JButton();
        nuevaPartida.setText("Nueva partida");
        nuevaPartida.addActionListener(e -> {
            setVisible(false);
            ElegirJugadores elegirJugadores = new ElegirJugadores(controlador);
            elegirJugadores.setVisible(true);
        });
        panelBotones.add(nuevaPartida);

        int i = 1;
        partidas = controlador.obtenerPartidas();
        for (TableroScheme t : partidas) {
            JButton botonPartida = new JButton();
            botonPartida.setText("Cargar partida");
            botonPartida.addActionListener(e -> {
                setVisible(false);
                controlador.elegirPartida(0);
                ElegirVista elegirVista = new ElegirVista(controlador);
                elegirVista.setVisible(true);
            });
            panelBotones.add(botonPartida);
            i++;
        }

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void iniciar () {
        setVisible(true);
    }
}
