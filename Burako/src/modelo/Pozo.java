package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Pozo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8066453324149449292L;
    private ArrayList<IFicha> fichas = new ArrayList<>();

    public void agregarFicha (IFicha ficha) {
        fichas.add(ficha);
    }

    public ArrayList<IFicha> verFichas () {
        return fichas;
    }

    public ArrayList<IFicha> obtenerPozo () {
        ArrayList<IFicha> pozo = new ArrayList<>();
        pozo.addAll(fichas);
        fichas.clear();
        return pozo;
    }
}
