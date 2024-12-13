package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazoGrafica extends FichaGrafica {

    public MazoGrafica(int numero, Grafica grafica) {
        super(numero, Color.BLACK);
        if (numero == 50) {
            JLabel numLabel = new JLabel();
            numLabel.setText("" + numero);
            numLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            numLabel.setHorizontalAlignment(SwingConstants.CENTER);
            numLabel.setForeground(Color.BLACK);
            this.removeAll();
            this.add(numLabel, BorderLayout.CENTER);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grafica.agarrarDelMazo();
            }
        });
    }
}
