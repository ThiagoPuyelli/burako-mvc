package Vista.Consola;

import Controlador.Controlador;
import Vista.IVista;
import Vista.VistaPlay;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import modelo.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ConsoleGUI extends VistaPlay implements IVista  {
    private final JFrame frame = new JFrame();
    private final JTextPane textPane;
    private final StyledDocument doc;
    boolean inicioPartida = false;
    boolean terminaPartida = false;
    ArrayList<IInput> inputs = new ArrayList<>();

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
        setPendiente();

        generarInputs();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputField.getText();
                if (!terminaPartida && inicioPartida) {
                    inputs.get(0).ejecutarComando(inputText);
                }
                inputField.setText("");
            }
        });
    }

    public void iniciar () {
        frame.setVisible(true);
    }

    private void setPendiente () {
        textPane.setText("La partida comenzara cuando todos se unan");
    }

    public void setDesconexion () {
        if (!terminaPartida) {
            textPane.setText("Un jugador se desconecto, cuando vuelvan todos se sigue jugando");
            inicioPartida = false;
        }
    }

    public void iniciarPartida () {
        inicioPartida = true;
    }

    private void generarInputs () {
        InputAgarrar inputAgarrar = new InputAgarrar(this, controlador);
        InputSoltar inputSoltar = new InputSoltar(this, controlador);
        inputAgarrar.setSiguiente(inputSoltar);
        InputCombinacion inputCombinacion = new InputCombinacion(this, controlador);
        inputSoltar.setSiguiente(inputCombinacion);
        InputAgregar inputAgregar = new InputAgregar(this, controlador);
        inputCombinacion.setSiguiente(inputAgregar);
        InputInvalido inputInvalido = new InputInvalido(this, controlador);
        inputAgregar.setSiguiente(inputInvalido);

        inputs.add(inputAgarrar);
        inputs.add(inputSoltar);
        inputs.add(inputCombinacion);
        inputs.add(inputAgregar);
        inputs.add(inputInvalido);
    }

    public void iniciarControlador () {
        //controlador.conectarJugador();
        controlador.iniciarPartida();
    }
    private void vaciarContenido () {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void setMuerto () {
        agregarTexto("\nSe agarro una pila muerta", Color.BLACK);
    }

    public void terminarPartida () {
        terminaPartida = true;
        textPane.setText("Partida finalizada ganador: " + controlador.getGanador());
        controlador.limpiarPartida();
    }

    public void mostrarTurno (String nombre) {
        vaciarContenido();
        mostrarJugadores();
        agregarTexto("Turno de " + nombre, Color.BLACK);
        int estadoTurno = controlador.getEstadoTurno(nombre);
        if (estadoTurno == 2) {
            agregarTexto(" agarra ficha", Color.BLACK);
        } else if (estadoTurno == 1) {
            agregarTexto(" juega", Color.BLACK);
        }
        agregarTexto("\n", Color.BLACK);
    }

    private void mostrarJugadores () {
        ArrayList<IJugadorProxy> jugadoresProxy = controlador.getJugadoresProxy();
        try {
            String texto = "Jugador " + jugadoresProxy.get(0).getNombre() + " cantidad de fichas " + jugadoresProxy.get(0).cantFichas();
            if (jugadoresProxy.size() == 2) {
                texto += "\nContrincante " + jugadoresProxy.get(1).getNombre() + " cantidad de fichas " + jugadoresProxy.get(1).cantFichas();
            } else {
                texto += "\nCompaniero " + jugadoresProxy.get(2).getNombre() + " cantidad de fichas " + jugadoresProxy.get(2).cantFichas();
                texto += "\nContrincante " + jugadoresProxy.get(1).getNombre() + " cantidad de fichas " + jugadoresProxy.get(1).cantFichas();
                texto += "\nContrincante " + jugadoresProxy.get(3).getNombre() + " cantidad de fichas " + jugadoresProxy.get(3).cantFichas();
            }
            texto += "\n";
            agregarTexto(texto, Color.BLACK);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void mostrarFichas () {
        agregarTexto("Fichas: ", Color.BLACK);
        int i = 1;
        for (IFicha f : controlador.getFichas()) {
            mostrarFicha(f);
            i++;
        }
        i = 1;
        agregarTexto("\nScore: " + controlador.getScore(), Color.BLACK);
        agregarTexto("\nScore enemigo: " + controlador.getScoreContrario(), Color.BLACK);
        agregarTexto("\nPozo: ", Color.BLACK);
        for (IFicha f : controlador.getPozo()) {
            mostrarFicha(f);
        }
        agregarTexto("\nCombinaciones: ", Color.BLACK);
        for (ICombinacion c : controlador.getCombinaciones()) {
            agregarTexto("\n" + i + ". ", Color.BLACK);
            for (IFicha f : c.getFichas()) {
                mostrarFicha(f);
            }
            i++;
        }
        agregarTexto("\nCombinaciones del otro equipo:", Color.BLACK);
        for (ICombinacion c : controlador.getCombinacionesContrario()) {
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

    private ColorFicha generarColorFicha (char color) {
        if (color == 'R') {
            return ColorFicha.rojo;
        } else if (color == 'N') {
            return ColorFicha.verde;
        } else if (color == 'G') {
            return ColorFicha.amarillo;
        } else {
            return ColorFicha.azul;
        }
    }

    public void agregarTexto(String texto, Color color) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
        try {
            doc.insertString(doc.getLength(), texto, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public int posicionInputFicha (String input, ArrayList<Integer> posiciones) {
        if (input.length() == 2 || input.length() == 3) {
            int numero;
            char color;
            if (input.length() == 2) {
                numero = Character.getNumericValue(input.charAt(0));
                color = input.charAt(1);
            } else {
                color = input.charAt(2);
                numero = Integer.parseInt(input.substring(0, 2));
            }
            ArrayList<IFicha> fichas = controlador.getFichas();

            if (("RNGV").indexOf(color) != -1) {
                ColorFicha colorFicha = generarColorFicha(color);
                int posicion = IntStream.range(0, fichas.size())
                        .filter(i -> fichas.get(i).getColor() == colorFicha && fichas.get(i).getNumero() == numero && (posiciones == null || !posiciones.contains(i))) // Cambia la condición según tus necesidades
                        .findFirst()
                        .orElse(-1);
                return posicion;
            }
        }
        return -1;
    }
}