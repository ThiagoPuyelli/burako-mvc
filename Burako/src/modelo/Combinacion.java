package modelo;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Combinacion {
    protected ArrayList<IFicha> fichas;

    public Combinacion (ArrayList<IFicha> fichas) {
        this.fichas = fichas;
    }

    public abstract boolean agregarFicha(IFicha ficha);

    public ArrayList<IFicha> getFichas () {
        return fichas;
    }
}
