package Vista.Grafica;

import Controlador.Controlador;
import modelo.IJugadorProxy;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GraficaJugadores extends JPanel {
    Controlador controlador;
    JPanel panel;

    public GraficaJugadores(Controlador controlador, JPanel panel) {
        this.controlador = controlador;
        this.panel = panel;
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void mostrarTurnos (String nombreEnTurno) {
        ArrayList<IJugadorProxy> jugadores =  controlador.getJugadoresProxy();
        int i = 0;
        JPanel text;
        this.removeAll();
        add(panel, BorderLayout.CENTER);
        JPanel panelTotal1 = new PanelMadera();
        JPanel panelTotal2 = new PanelMadera();
        if (jugadores.size() == 4) {
            JPanel panel1 = this.generarTexto(jugadores.get(0), nombreEnTurno, i);
            JPanel panel2 = this.generarTexto(jugadores.get(1), nombreEnTurno, i);
            JPanel panel3 = this.generarTexto(jugadores.get(2), nombreEnTurno, i);
            JPanel panel4 = this.generarTexto(jugadores.get(3), nombreEnTurno, i);
            panelTotal1.add(panel1);
            panelTotal1.add(panel3);
            panelTotal2.add(panel2);
            panelTotal2.add(panel4);
            add(panelTotal1, BorderLayout.SOUTH);
            add(panelTotal2, BorderLayout.NORTH);
        } else {
            JPanel panel1 = this.generarTexto(jugadores.get(0), nombreEnTurno, i);
            JPanel panel2 = this.generarTexto(jugadores.get(1), nombreEnTurno, i);
            panelTotal1.add(panel1);
            panelTotal2.add(panel2);
            add(panelTotal1, BorderLayout.SOUTH);
            add(panelTotal2, BorderLayout.NORTH);
        }
    }

    private JPanel generarTexto (IJugadorProxy j, String nombreEnTurno, int numJugador) {
        try {
            String textoEnPantalla1 = j.getNombre();
            String textoEnPantalla2 = "";
            if (j.getNombre().equals(nombreEnTurno)) {
                textoEnPantalla2 += this.estado(controlador.getEstadoTurno(nombreEnTurno));
            }

            JLabel label1 = new JLabel(textoEnPantalla1);
            label1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
            label1.setForeground(Color.WHITE);

            JPanel f = new JPanel();
            f.setPreferredSize(new Dimension(20, 30)); // Tamaño de la ficha
            f.setBackground(Color.WHITE); // Fondo blanco de la ficha
            f.setForeground(Color.black);
            f.setLayout(new BorderLayout());
            f.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel numLabel = new JLabel( "" + j.cantFichas());
            numLabel.setHorizontalAlignment(SwingConstants.CENTER);
            numLabel.setForeground(Color.black);
            numLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
            f.add(numLabel, BorderLayout.CENTER);

            JLabel label2 = new JLabel(textoEnPantalla2);
            label2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
            label2.setForeground(Color.WHITE);

            JPanel newPanel;
            newPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            newPanel.setOpaque(false);
            newPanel.add(label1);
            newPanel.add(f);
            newPanel.add(label2);
            return newPanel;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private String estado (int estadoTurno) {
        if (estadoTurno == 1) {
            return "Soltar ficha";
        } else {
            return "Agarrar ficha";
        }
    }

}
