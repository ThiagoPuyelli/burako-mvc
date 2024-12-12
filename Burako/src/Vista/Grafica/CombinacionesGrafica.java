package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CombinacionesGrafica extends JPanel {

    private JTextArea titulo = new JTextArea();
    private ArrayList<SetFichasGrafica> combinaciones = new ArrayList<>();
    private Grafica grafica;

    public CombinacionesGrafica(String titulo, Grafica grafica) {
        this.titulo.setText(titulo);
        this.titulo.setForeground(Color.WHITE);
        this.titulo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        this.titulo.setOpaque(false);
        this.grafica = grafica;
        this.setOpaque(false);
        this.add(this.titulo);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setBackground(Color.BLACK);
    }

    public void agregarCombinacion(SetFichasGrafica combinacion) {
        combinaciones.add(combinacion);
        this.add(combinacion);
    }

    public void setCombinaciones (ArrayList<SetFichasGrafica> combinaciones) {
        this.combinaciones = combinaciones;
        this.removeAll();
        this.add(this.titulo);
        int i = 0;
        for (SetFichasGrafica s : combinaciones) {
            s.setPos(i);
            this.add(s);
            s.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    grafica.generarCombinacion(s.getPos());
                }
            });
            i++;
        }
    }

    public void actualizarDisenio () {
        for (SetFichasGrafica s : combinaciones) {
            s.actualizarDisenio();
        }
        this.revalidate(); // Actualizar el diseño
        this.repaint();
    }
}
