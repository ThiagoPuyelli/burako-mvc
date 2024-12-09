package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Services.RankingScheme;
import Services.RankingSerializador;

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
    private Controlador controlador;

    public MenuCliente(Controlador controlador) {
        // Configuración de la ventana
        this.controlador = controlador;
        setTitle("Ejemplo Swing");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        label = new JLabel("           Bienvenido a el Burako!");
        boton1 = new JButton("Unirse a partida");
        boton2 = new JButton("Ranking");

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
                //Tablero tablero = new Tablero(1);
                //VistaNombre nombre1 = new VistaNombre(tablero);
                //VistaNombre nombre2 = new VistaNombre(tablero);
                //nombre1.setVisible(true);
                //nombre2.setVisible(true);
                //setVisible(false);
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
                //ranking.setText(controlador.obtenerRanking().toString());
                //Tablero tablero = new Tablero(2);
                //VistaNombreDuo nombre1 = new VistaNombreDuo(tablero);
                //VistaNombreDuo nombre2 = new VistaNombreDuo(tablero);
                //nombre1.setVisible(true);
                //nombre2.setVisible(true);
                //setVisible(false);
            }
        });
    }

    public void iniciar () {
        setVisible(true);
    }
}
