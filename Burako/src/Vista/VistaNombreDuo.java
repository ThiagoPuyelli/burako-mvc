package Vista;

import Controlador.Controlador;
import Vista.Consola.ConsoleGUI;
import modelo.Jugador;
import modelo.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VistaNombreDuo extends JFrame {
    private JLabel mensajeLabel;
    private JTextField nombre1TextField;
    private JTextField nombre2TextField;
    private JButton enviarButton;

    public VistaNombreDuo(Tablero tablero) {
        // Configuración de la ventana
        setTitle("Ingreso de Nombre");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        mensajeLabel = new JLabel("Ingrese los nombres:");
        nombre1TextField = new JTextField(20);
        nombre2TextField = new JTextField(20);
        enviarButton = new JButton("Enviar");

        // Configurar diseño
        setLayout(new BorderLayout());

        JPanel panelMensaje = new JPanel();
        panelMensaje.add(mensajeLabel);
        add(panelMensaje, BorderLayout.NORTH);

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(nombre1TextField);
        panelEntrada.add(nombre2TextField);
        panelEntrada.add(enviarButton);
        add(panelEntrada, BorderLayout.CENTER);

        // Configurar acción del botón
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre1 = nombre1TextField.getText();
                String nombre2 = nombre2TextField.getText();
                if (nombre1.isEmpty() || nombre2.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nombres invalidos");
                } else {
                    Jugador jugador1 = new Jugador(nombre1);
                    Jugador jugador2 = new Jugador(nombre2);
                    //EquipoDuo equipo = new EquipoDuo();
                    Controlador controlador1 = new Controlador();
                    ConsoleGUI consola1 = new ConsoleGUI(controlador1);
                    controlador1.setVista(consola1);
                    controlador1.setJugador("pepe");
                    consola1.iniciar();
                    Controlador controlador2 = new Controlador();
                    ConsoleGUI consola2 = new ConsoleGUI(controlador2);
                    controlador2.setVista(consola2);
                    controlador2.setJugador("pepe2");
                    consola2.iniciar();
                    setVisible(false);
                    try {
                        tablero.agregarObservador(controlador2);
                        tablero.agregarObservador(controlador1);
                        //tablero.setEquipos(equipo);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}