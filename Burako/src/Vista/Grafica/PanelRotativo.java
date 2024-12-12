package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class PanelRotativo extends JPanel {
    private double rotationAngle; // Ángulo de rotación en grados
    private JLabel label1;
    private JPanel f;
    private JLabel label2;
    private JPanel panel = new JPanel();

    public PanelRotativo(double rotationAngle, JLabel label1, JPanel f, JLabel label2) {
        this.rotationAngle = Math.toRadians(rotationAngle); // Convertir a radianes
        this.label1 = label1;
        this.f = f;
        this.label2 = label2;
        panel.add(label1);
        panel.add(f);
        panel.add(label2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Centro del panel para rotación
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        // Aplicar la rotación
        g2d.rotate(this.rotationAngle, centroX, centroY);

        // Dibujar cada componente rotado manualmente
        paintRotatedComponent(g2d, label1, centroX - 50, centroY - 50);
        paintRotatedComponent(g2d, f, centroX, centroY - 50);
        paintRotatedComponent(g2d, label2, centroX + 50, centroY - 50);

        g2d.dispose();
    }

    private void paintRotatedComponent(Graphics2D g2d, JComponent component, int x, int y) {
        // Crear un gráfico temporal para el componente
        Graphics2D tempG2d = (Graphics2D) g2d.create();

        // Traducir al lugar donde se dibujará el componente
        tempG2d.translate(x, y);

        // Pintar el componente en su nueva ubicación
        //component.setBounds(0, 0, component.getPreferredSize().width, component.getPreferredSize().height);
        component.paint(tempG2d);

        tempG2d.dispose();
    }

}
