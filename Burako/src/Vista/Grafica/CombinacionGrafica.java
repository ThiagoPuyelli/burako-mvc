package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CombinacionGrafica extends JPanel {
    private ArrayList<FichaGrafica> fichas;

    public CombinacionGrafica(ArrayList<FichaGrafica> fichas) {
        this.fichas = fichas;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (FichaGrafica f : fichas) {
            this.agregarFicha(f);
        }
    }

    public void agregarFicha(FichaGrafica ficha) {
        fichas.add(ficha);
        this.add(ficha);
        this.revalidate(); // Actualizar el diseño
        this.repaint();
    }
}
