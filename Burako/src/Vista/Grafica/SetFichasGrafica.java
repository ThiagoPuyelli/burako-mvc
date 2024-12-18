package Vista.Grafica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SetFichasGrafica extends JPanel {
    private ArrayList<FichaGrafica> fichas = new ArrayList<>();
    private int pos;

    public SetFichasGrafica() {
        this.setOpaque(false);
        this.setLayout(new FlowLayout());
    }

    public int getPos () {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void actualizarDisenio () {
        for (FichaGrafica f : fichas) {
            f.actualizarDisenio();
        }
        this.revalidate();
        this.repaint();
    }

    public ArrayList<FichaGrafica> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<FichaGrafica> fichas) {
        this.fichas = fichas;
        this.removeAll();

        for (FichaGrafica ficha : fichas) {
            this.add(ficha);
        }
    }
}
