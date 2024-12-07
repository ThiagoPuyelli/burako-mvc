package Vista;

import modelo.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JLabel label;
    private JButton boton1;
    private JButton boton2;

    public Menu() {
        // Configuración de la ventana
        setTitle("Ejemplo Swing");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        label = new JLabel("           Elige el modo de juego");
        boton1 = new JButton("Solo");
        boton2 = new JButton("Duo");

        // Configurar diseño
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        add(panelBotones, BorderLayout.SOUTH);

        // Configurar acciones de los botones
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tablero tablero = new Tablero(1);
                VistaNombre nombre1 = new VistaNombre(tablero);
                VistaNombre nombre2 = new VistaNombre(tablero);
                nombre1.setVisible(true);
                nombre2.setVisible(true);
                setVisible(false);
            }
        });

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tablero tablero = new Tablero(2);
                VistaNombreDuo nombre1 = new VistaNombreDuo(tablero);
                VistaNombreDuo nombre2 = new VistaNombreDuo(tablero);
                nombre1.setVisible(true);
                nombre2.setVisible(true);
                setVisible(false);
            }
        });
    }
}