package modelo;

import java.util.ArrayList;

public class Pierna extends Combinacion {

    public Pierna (ArrayList<Ficha> fichas) {
        super(fichas);
    }

    public boolean agregarFicha(Ficha ficha) {
        if (fichas.get(0).getNumero() == ficha.getNumero()) {
            fichas.add(ficha);
            return true;
        } else {
            return false;
        }
    }
}
