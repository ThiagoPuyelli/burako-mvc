package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class PanelMensaje extends JPanel {
    private Image backgroundImage = new ImageIcon("src/Imagenes/fondoCentro.jpg").getImage();
    private JLabel label;

    public PanelMensaje (String mensaje) {
        setLayout(new BorderLayout());
        label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        this.add(label);
        add(label, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setTextoLabel (String texto) {
        label.setText(texto);
    }
}
