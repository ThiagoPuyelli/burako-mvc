package Vista.Grafica;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FichaGraficaSet extends FichaGrafica {
    boolean marked = false;
    int pos;
    Grafica grafica;

    public FichaGraficaSet(int numero, Color color, int pos, Grafica grafica) {
        super(numero, color);
        this.pos = pos;
        this.grafica = grafica;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (grafica.getEstadoTurno() == 1) {
                    if (!marked) {
                        setBackground(Color.ORANGE);
                        grafica.agregarPosicion(pos);
                    } else {
                        setBackground(Color.WHITE);
                        grafica.eliminarPosicion(pos);
                    }
                    marked = !marked;
                }
            }
        });
    }

    public void reset () {
        setBackground(Color.WHITE);
        marked = false;
    }
}
