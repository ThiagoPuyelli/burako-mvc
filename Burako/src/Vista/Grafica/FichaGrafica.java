package Vista.Grafica;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FichaGrafica extends JPanel {
    private int numero;
    private Color color;

    // Constructor
    public FichaGrafica(int numero, Color color) {
        this.numero = numero;
        this.color = color;
        this.setPreferredSize(new Dimension(30, 50)); // Tamaño de la ficha
        this.setBackground(Color.WHITE); // Fondo blanco de la ficha
        this.setForeground(color);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel numLabel = new JLabel();
        if (numero != 50) {
            numLabel.setText("" + numero);
            numLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        } else {
            String urlImage = color == Color.RED ? "src/Imagenes/comodinRojo.png" : "src/Imagenes/comodinVerde.png";
            BufferedImage buffer;
            try {
                buffer = ImageIO.read(new File(urlImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image img = buffer.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            numLabel.setIcon(icon);
        }
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numLabel.setForeground(color);
        this.add(numLabel, BorderLayout.CENTER);
    }

    public void actualizarDisenio () {
        revalidate();
        repaint();
    }

}
