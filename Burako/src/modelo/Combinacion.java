package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Combinacion implements ICombinacion, Serializable {
    protected ArrayList<IFicha> fichas;

    public Combinacion (ArrayList<IFicha> fichas) {
        this.fichas = fichas;
    }

    public abstract boolean agregarFicha(IFicha ficha);

    public ArrayList<IFicha> getFichas () {
        return fichas;
    }
}
