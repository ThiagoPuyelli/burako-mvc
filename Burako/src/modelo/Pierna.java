package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Pierna extends Combinacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 2876642017763209637L;

    public Pierna (ArrayList<IFicha> fichas) {
        super(fichas);
    }

    public boolean agregarFicha(IFicha ficha) {
        ArrayList<IFicha> copia = new ArrayList<>();
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
