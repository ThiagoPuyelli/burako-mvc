package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class PanelMadera extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        g.drawImage(new ImageIcon("src/Imagenes/fondoFichas.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
