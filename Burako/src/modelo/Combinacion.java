package modelo;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Combinacion {
    protected ArrayList<Ficha> fichas;

    public Combinacion (ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }

    public abstract boolean agregarFicha(Ficha ficha);

    public ArrayList<Ficha> getFichas () {
        return fichas;
    }
}
