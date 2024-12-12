package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PozoGrafica extends JPanel {
    int cantFichas;
    SetFichasGrafica pozo = new SetFichasGrafica();
    Grafica grafica;

    public PozoGrafica (int cantFichas, Grafica grafica) {
        this.cantFichas = cantFichas;
        this.grafica = grafica;

        this.addComponentes();
        this.setOpaque(false);

    }

    public void setPozo (SetFichasGrafica pozo) {
        this.pozo = pozo;
        this.removeAll();
        this.addComponentes();
    }

    public void setCantFichas (int cantFichas) {
        this.cantFichas = cantFichas;
        this.removeAll();
        this.addComponentes();
    }

    private void addComponentes () {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel content = new JPanel(new FlowLayout());
        content.setOpaque(false);
        content.add(new MazoGrafica(cantFichas, grafica));
        content.add(pozo);

        this.add(content, gbc);
        pozo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grafica.agarrarDelPozo();
            }
        });
    }

}
