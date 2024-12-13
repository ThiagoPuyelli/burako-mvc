package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Mazo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7872907716008503380L;
    private ArrayList<IFicha> mazo;

    public Mazo (ArrayList<IFicha> mazo) {
        this.mazo = mazo;
    }

    public IFicha obtenerFicha () {
        return this.mazo.remove(0);
    }

    public ArrayList<IFicha> obtenerFichas (int cantidad) {
        ArrayList<IFicha> fichas = new ArrayList<>();
        for (int i = 0;i < cantidad;i++) {
            fichas.add(this.obtenerFicha());
        }
        return fichas;
    }

    public int cantidadFichas () {
        return mazo.size();
    }
}
