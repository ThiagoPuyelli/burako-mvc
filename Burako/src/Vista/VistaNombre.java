package Vista;

import Controlador.Controlador;
import Vista.Consola.ConsoleGUI;
import modelo.EquipoDuo;
import modelo.EquipoSolo;
import modelo.Jugador;
import modelo.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaNombre extends JFrame {
    private JLabel mensajeLabel;
    private JTextField nombreTextField;
    private JButton enviarButton;

    public VistaNombre(Tablero tablero) {
        // Configuración de la ventana
        setTitle("Ingreso de Nombre");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        mensajeLabel = new JLabel("Ingrese el nombre:");
        nombreTextField = new JTextField(20);
        enviarButton = new JButton("Enviar");

        // Configurar diseño
        setLayout(new BorderLayout());

        JPanel panelMensaje = new JPanel();
        panelMensaje.add(mensajeLabel);
        add(panelMensaje, BorderLayout.NORTH);

        JPanel panelEntrada = new JPanel();
        panelEntrada.add(nombreTextField);
        panelEntrada.add(enviarButton);
        add(panelEntrada, BorderLayout.CENTER);

        // Configurar acción del botón
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreTextField.getText();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nombre invalido");
                } else {
                    Jugador jugador = new Jugador(nombre);
                    EquipoSolo equipo = new EquipoSolo(jugador);
                    Controlador controlador = new Controlador(tablero);
                    ConsoleGUI consola = new ConsoleGUI(controlador);
                    controlador.setJugador(jugador);
                    controlador.setVista(consola);
                    consola.iniciar();
                    setVisible(false);
                    tablero.agregarObservador(controlador);
                    tablero.setEquipos(equipo);
                }
            }
        });
    }
}