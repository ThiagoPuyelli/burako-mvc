package modelo;

import java.util.ArrayList;

public class Escalera extends Combinacion {
    public Escalera (ArrayList<Ficha> fichas) {
        super(fichas);
    }

    public boolean agregarFicha(Ficha ficha) {
        if (verificarNumero(ficha, fichas.get(0))) {
            fichas.add(0, ficha);
            return true;
        } else if (verificarNumero(fichas.get(fichas.size() - 1), ficha)){
            fichas.add(ficha);
            return true;
        }
        return false;
    }


}
