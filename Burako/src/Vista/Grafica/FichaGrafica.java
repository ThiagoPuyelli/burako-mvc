package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class FichaGrafica extends JPanel {
    private int numero;
    private Color color;

    // Constructor
    public FichaGrafica(int numero, Color color) {
        this.numero = numero;
        this.color = color;
        this.setPreferredSize(new Dimension(60, 90)); // Tamaño de la ficha
        this.setBackground(Color.WHITE); // Fondo blanco de la ficha
    }

    @Override
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
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics fm = g2d.getFontMetrics();
        String text = String.valueOf(numero);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // Centrar el número
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 10;

        g2d.drawString(text, x, y);
    }

    // Métodos para cambiar el número o el color si es necesario
    //public void setNumero(int numero) {
    //    this.numero = numero;
    //    repaint();
    //}
//
    //public void setColor(Color color) {
    //    this.color = color;
    //    repaint();
    //}
}
