package modelo;

import java.util.ArrayList;

public class Pozo {
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
