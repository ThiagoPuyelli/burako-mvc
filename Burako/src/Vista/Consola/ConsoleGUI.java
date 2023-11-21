package Vista.Consola;

import Controlador.Controlador;
import Vista.IVista;
import Vista.VistaPlay;
import modelo.ColorFicha;
import modelo.Combinacion;
import modelo.Ficha;

import javax.swing.*;
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
    JTextArea textArea;
    boolean inicioPartida = false;
    boolean terminaPartida = false;
    public ConsoleGUI (Controlador controlador) {
        super(controlador);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);

        textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setText("Ingrese 'empezar' para iniciar la partida");

        JTextField inputField = new JTextField(20);
        JButton sendButton = new JButton("Enviar");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                if (!inicioPartida) {
                    if (inputText.equals("empezar")) {
                        if (!controlador.getStart()) {
                            controlador.iniciarPartida();
                            inicioPartida = true;
                        } else {
                            textArea.append("\n La partida ya comenzo");
                        }
                    }
                } else {
                    if (!terminaPartida) {
                        if (inputText.startsWith("agarrar")) {
                            if (controlador.getEstadoTurno() == 2) {
                                String sub = "agarrar ";
                                String input = inputText.substring(sub.length());
                                if (input.startsWith("pozo")) {
                                    if (controlador.getPozo().isEmpty()) {
                                        textArea.append("El pozo esta vacio\n");
                                    } else {
                                        controlador.agarrarPozo();
                                    }
                                } else if (input.startsWith("mazo")) {
                                    controlador.agarrarMazo();
                                    mostrarTurno(controlador.getNombre());
                                    mostrarFichas();
                                } else {
                                    textArea.append("\nComando equivocado");
                                }
                            } else {
                                textArea.append("\nNo podes agarrar una ficha en este momento");
                            }
                        } else if (inputText.startsWith("soltar")) {
                            if (controlador.getEstadoTurno() == 1) {
                                String sub = "soltar ";
                                String input = inputText.substring(sub.length());
                                int f = Integer.parseInt(input);
                                if (f >= controlador.getFichas().size()) {
                                    textArea.append("\nFicha invalida");
                                } else {
                                    controlador.soltarFicha(f);
                                }
                            } else {
                                textArea.append("\nNo podes soltar una ficha en este momento");
                            }
                        } else if (inputText.startsWith("combinacion")) {
                            if (controlador.getEstadoTurno() == 1) {
                                String sub = "combinacion ";
                                String input = inputText.substring(sub.length());
                                String[] inputSplit = input.split(" ");
                                ArrayList<Integer> posiciones = new ArrayList<>();
                                boolean verify = false;
                                for (String s : inputSplit) {
                                    verify = Integer.parseInt(s) >= controlador.getFichas().size();
                                    posiciones.add(Integer.parseInt(s));
                                }
                                if (verify) {
                                    textArea.append("\nUna posicion es invalida");
                                } else {
                                  controlador.combinacion(posiciones);
                                }
                            } else {
                                textArea.append("\nNo podes hacer una combinacion en este momento");
                            }
                        } else if (inputText.startsWith("agregar")) {
                            if (controlador.getEstadoTurno() == 1) {
                                String sub = "agregar ";
                                String input = inputText.substring(sub.length());
                                String[] inputSplit = input.split(" ");
                                int c = Integer.parseInt(inputSplit[0]);
                                int f = Integer.parseInt(inputSplit[1]);
                                if (c >= controlador.getCombinaciones().size() || f >= controlador.getFichas().size()) {
                                    textArea.append("\nUna de las posiciones es invalida");
                                }
                                controlador.agregarFichaComb(c, f);
                            } else {
                                textArea.append("\nNo podes hacer un agregar en este momento");
                            }
                        }
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
    public void actualizar (Object valor) {
        textArea.append(controlador.getNombre() + ": " + (String) valor);
    }
    public void setMuerto () {
        textArea.append("\nSe agarro una pila muerta");
    }

    public void terminarPartida () {
        terminaPartida = true;
        textArea.setText("Partida finalizada ganador: " + controlador.getGanador());
    }

    public void mostrarTurno (String nombre) {
        textArea.setText("Jugador " + controlador.getNombre() + "\n");
        textArea.append("Turno de " + nombre);
        int estadoTurno = controlador.getEstadoTurno(nombre);
        if (estadoTurno == 2) {
            textArea.append(" agarra ficha");
        } else if (estadoTurno == 1) {
            textArea.append(" juega");
        }
        textArea.append("\n");
    }
    public void mostrarFichas () {
        textArea.append("Fichas: ");
        for (Ficha f : controlador.getFichas()) {
           mostrarFicha(f);
        }
        textArea.append("\nScore: " + controlador.getScore());
        textArea.append("\nPozo: ");
        for (Ficha f : controlador.getPozo()) {
            mostrarFicha(f);
        }
        textArea.append("\nCombinaciones: ");
        for (Combinacion c : controlador.getCombinaciones()) {
            textArea.append("\n");
            for (Ficha f : c.getFichas()) {
                mostrarFicha(f);
            }
        }
        textArea.append("\nCombinaciones del otro equipo:");
        for (Combinacion c : controlador.getCombinacionesContrario()) {
            textArea.append("\n");
            for (Ficha f : c.getFichas()) {
                mostrarFicha(f);
            }
        }
    }
    private void mostrarFicha (Ficha f) {
        if (ColorFicha.rojo == f.getColor()) {
            textArea.append(f.getNumero() + "RO  ");
        } else if (ColorFicha.verde == f.getColor()) {
            textArea.append(f.getNumero() + "VE  ");
        } else if (ColorFicha.amarillo == f.getColor()) {
            textArea.append(f.getNumero() + "AM  ");
        } else if (ColorFicha.azul == f.getColor()) {
            textArea.append(f.getNumero() + "AZ  ");
        }
    }
}