package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class PanelMensaje extends JPanel {
    private Image backgroundImage = new ImageIcon("src/Imagenes/fondoCentro.jpg").getImage();

    public PanelMensaje (String mensaje) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        this.add(label);
        add(label, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
