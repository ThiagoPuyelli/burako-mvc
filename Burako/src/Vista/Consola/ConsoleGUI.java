package Vista.Consola;

import Controlador.Controlador;
import Vista.IVista;
import Vista.VistaPlay;
import modelo.ColorFicha;
import modelo.Combinacion;
import modelo.Ficha;
import modelo.IFicha;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ConsoleGUI extends VistaPlay implements IVista  {
    private final JFrame frame = new JFrame();
    private final JTextPane textPane;
    private final StyledDocument doc;
    boolean inicioPartida = false;
    boolean terminaPartida = false;

    public ConsoleGUI (Controlador controlador) {
        super(controlador);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        textPane = new JTextPane();
        textPane.setEditable(false);
        doc = textPane.getStyledDocument();

        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        JTextField inputField = new JTextField(20);
        JButton sendButton = new JButton("Enviar");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        textPane.setFont(new Font("Arial", Font.PLAIN, 18));

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                    if (!terminaPartida && inicioPartida) {
                        if (inputText.startsWith("agarrar")) {
                            agarrarDelMazo(inputText);
                        } else if (inputText.startsWith("soltar")) {
                            soltarFicha(inputText);
                        } else if (inputText.startsWith("combinacion")) {
                            combinarFichas(inputText);
                        } else if (inputText.startsWith("agregar")) {
                            agregarFicha(inputText);
                        }
                    }
                inputField.setText("");
            }
        });
    }
    public void iniciar () {
        frame.setVisible(true);
    }

    public void iniciarPartida () {
        inicioPartida = true;
    }
    private void vaciarContenido () {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    public void actualizar (Object valor) {
        agregarTexto(controlador.getNombre() + ": " + (String) valor, Color.BLACK);
    }
    public void setMuerto () {
        agregarTexto("\nSe agarro una pila muerta", Color.BLACK);
    }

    public void terminarPartida () {
        terminaPartida = true;
        agregarTexto("Partida finalizada ganador: " + controlador.getGanador(), Color.BLACK);
    }

    public void mostrarTurno (String nombre) {
        vaciarContenido();
        agregarTexto("Jugador " + controlador.getNombre() + "\n", Color.BLACK);
        agregarTexto("Turno de " + nombre, Color.BLACK);
        int estadoTurno = controlador.getEstadoTurno(nombre);
        if (estadoTurno == 2) {
            agregarTexto(" agarra ficha", Color.BLACK);
        } else if (estadoTurno == 1) {
            agregarTexto(" juega", Color.BLACK);
        }
        agregarTexto("\n", Color.BLACK);
    }
    public void mostrarFichas () {
        agregarTexto("Fichas: ", Color.BLACK);
        int i = 1;
        for (IFicha f : controlador.getFichas()) {
            //agregarTexto(i + ". ", Color.LIGHT_GRAY);
            mostrarFicha(f);
            i++;
        }
        i = 1;
        agregarTexto("\nScore: " + controlador.getScore(), Color.BLACK);
        agregarTexto("\nPozo: ", Color.BLACK);
        for (IFicha f : controlador.getPozo()) {
            mostrarFicha(f);
        }
        agregarTexto("\nCombinaciones: ", Color.BLACK);
        for (Combinacion c : controlador.getCombinaciones()) {
            agregarTexto("\n" + i + ". ", Color.BLACK);
            for (IFicha f : c.getFichas()) {
                mostrarFicha(f);
            }
            i++;
        }
        agregarTexto("\nCombinaciones del otro equipo:", Color.BLACK);
        for (Combinacion c : controlador.getCombinacionesContrario()) {
            agregarTexto("\n", Color.BLACK);
            for (IFicha f : c.getFichas()) {
                mostrarFicha(f);
            }
        }
    }
    private void mostrarFicha (IFicha f) {
        if (ColorFicha.rojo == f.getColor()) {
            agregarTexto(f.getNumero() + "  ", Color.RED);
        } else if (ColorFicha.verde == f.getColor()) {
            agregarTexto(f.getNumero() + "  ", Color.BLACK);
        } else if (ColorFicha.amarillo == f.getColor()) {
            agregarTexto(f.getNumero() + "  ", Color.GRAY);
        } else if (ColorFicha.azul == f.getColor()) {
            agregarTexto(f.getNumero() + "  ", Color.green);
        }
    }

    private void agregarTexto(String texto, Color color) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
        try {
            doc.insertString(doc.getLength(), texto, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void agarrarDelMazo (String inputText) {
        if (controlador.getEstadoTurno() == 2) {
            String sub = "agarrar ";
            String input = inputText.substring(sub.length());
            if (input.startsWith("pozo")) {
                if (controlador.getPozo().isEmpty()) {
                    agregarTexto("El pozo esta vacio\n", Color.BLACK);
                } else {
                    controlador.agarrarPozo();
                }
            } else if (input.startsWith("mazo")) {
                controlador.agarrarMazo();
                mostrarTurno(controlador.getNombre());
                mostrarFichas();
            } else {
                agregarTexto("\nComando equivocado", Color.BLACK);
            }
        } else {
            agregarTexto("\nNo podes agarrar una ficha en este momento", Color.BLACK);
        }
    }

    private void soltarFicha (String inputText) {
        if (controlador.getEstadoTurno() == 1) {
            String sub = "soltar ";
            String input = inputText.substring(sub.length());
            int f = Integer.parseInt(input) - 1;
            if (f >= controlador.getFichas().size()) {
                agregarTexto("\nFicha invalida", Color.BLACK);
            } else {
                controlador.soltarFicha(f);
            }
        } else {
            agregarTexto("\nNo podes soltar una ficha en este momento", Color.BLACK);
        }
    }

    private void combinarFichas (String inputText) {
        if (controlador.getEstadoTurno() == 1) {
            String sub = "combinacion ";
            String input = inputText.substring(sub.length());
            String[] inputSplit = input.split(" ");
            ArrayList<Integer> posiciones = new ArrayList<>();
            boolean verify = false;
            for (String s : inputSplit) {
                verify = Integer.parseInt(s) - 1 >= controlador.getFichas().size();
                posiciones.add(Integer.parseInt(s) - 1);
            }
            if (verify) {
                agregarTexto("\nUna posicion es invalida", Color.BLACK);
            } else {
                controlador.combinacion(posiciones);
            }
        } else {
            agregarTexto("\nNo podes hacer una combinacion en este momento", Color.BLACK);
        }
    }

    private void agregarFicha (String inputText) {
        if (controlador.getEstadoTurno() == 1) {
            String sub = "agregar ";
            String input = inputText.substring(sub.length());
            String[] inputSplit = input.split(" ");
            int c = Integer.parseInt(inputSplit[0]);
            int f = Integer.parseInt(inputSplit[1]);
            if (c >= controlador.getCombinaciones().size() || f >= controlador.getFichas().size()) {
                agregarTexto("\nUna de las posiciones es invalida", Color.BLACK);
            }
            controlador.agregarFichaComb(c - 1, f - 1);
        } else {
            agregarTexto("\nNo podes hacer un agregar en este momento", Color.BLACK);
        }
    }
}