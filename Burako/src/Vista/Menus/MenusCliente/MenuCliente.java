package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Services.RankingScheme;
import Services.RankingSerializador;
import Vista.Grafica.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuCliente extends JFrame {
    private JLabel label;
    private JButton boton1;
    private JButton boton2;
    private JTextArea ranking;

    public MenuCliente(Controlador controlador) {
        // Configuración de la ventana
        setTitle("Ejemplo Swing");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        label = new JLabel("Bienvenido a el Burako!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        boton1 = new JButton("Unirse a partida");
        boton2 = new JButton("Ranking");

        // Configurar diseño
        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        ranking = new JTextArea();
        ranking.setEditable(false);
        ranking.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        add(panelBotones, BorderLayout.CENTER);
        add(ranking, BorderLayout.SOUTH);

        // Configurar acciones de los botones
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                if (!controlador.getPartidaCreada()) {
                    if (controlador.obtenerPartidas().size() > 0) {
                        ElegirPartida elegirPartida = new ElegirPartida(controlador);
                        elegirPartida.setVisible(true);
                    } else {
                        ElegirJugadores elegirJugadores = new ElegirJugadores(controlador);
                        elegirJugadores.setVisible(true);
                    }
                } else {
                    if (!controlador.getPartidaIniciada()) {
                        if (!controlador.existeJugador()) {
                            ElegirEquipo elegirEquipo = new ElegirEquipo(controlador);
                            elegirEquipo.setVisible(true);
                        } else {
                            new UsuarioRepetido(controlador);
                        }
                    } else {
                        if (controlador.posibleRecuperar()) {
                            ElegirVista elegirVista = new ElegirVista(controlador);
                            elegirVista.setVisible(true);
                        } else {
                            Error error = new Error("Su jugador no se encuentra en la partida");
                            error.setVisible(true);
                        }
                    }
                }
            }
        });

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<RankingScheme> rankings = controlador.obtenerRanking();
                String s = "";
                for (RankingScheme r : rankings) {
                    s += r.getNombre() + " " + r.getPartidasGanadas() + "\n";
                }
                ranking.setText(s);
            }
        });
    }

    public void iniciar () {
        setVisible(true);
    }
}
