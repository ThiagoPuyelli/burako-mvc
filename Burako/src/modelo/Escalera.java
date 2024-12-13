package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Escalera extends Combinacion implements Serializable {
    @Serial
    private static final long serialVersionUID = -1267587181846267711L;

    public Escalera (ArrayList<IFicha> fichas) {
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
        if (FabricaCombinacion.esEscalera(copia)) {
            fichas = copia;
            return true;
        }
        //if (ficha.getNumero() != 50) {
        //    if (FabricaCombinacion.verificarNumero(ficha, fichas.get(0))) {
        //        fichas.add(0, ficha);
        //        return true;
        //    } else if (FabricaCombinacion.verificarNumero(fichas.get(fichas.size() - 1), ficha)){
        //        fichas.add(ficha);
        //        return true;
        //    }
        //}
        return false;
    }


}
