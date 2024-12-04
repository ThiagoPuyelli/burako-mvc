package modelo;

import java.util.ArrayList;

public class Pierna extends Combinacion {

    public Pierna (ArrayList<Ficha> fichas) {
        super(fichas);
    }

    public boolean agregarFicha(Ficha ficha) {
        ArrayList<Ficha> copia = new ArrayList<>();
        copia.addAll(fichas);
        copia.add(ficha);
        int comodines = FabricaCombinacion.contarComodines(copia);

        if (comodines > 1) {
            return false;
        }
        if (FabricaCombinacion.esPierna(copia)) {
            fichas = copia;
            return true;
        }
        return false;
    }
}
