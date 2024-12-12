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
            System.out.println("SE METIO EN EL IF");
            if (color == Color.RED) {
                BufferedImage buffer;
                try {
                    buffer = ImageIO.read(new File("src/Imagenes/comodinRojo.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Image img = buffer.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(img);
                numLabel.setIcon(icon);
            } else {
                BufferedImage buffer;
                try {
                    buffer = ImageIO.read(new File("src/Imagenes/comodinVerde.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Image img = buffer.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(img);
                numLabel.setIcon(icon);
            }
        }
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numLabel.setForeground(color);
        this.add(numLabel, BorderLayout.CENTER);
    }



    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Suavizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar borde de la ficha
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);

        // Dibujar número
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        String text = String.valueOf(numero);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // Centrar el número
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 10;

        g2d.drawString(text, x, y);
    }*/

    public void actualizarDisenio () {
        revalidate();
        repaint();
    }

}
