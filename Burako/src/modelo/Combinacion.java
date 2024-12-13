package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Combinacion implements ICombinacion, Serializable {
    @Serial
    private static final long serialVersionUID = 4428795919731834499L;
    protected ArrayList<IFicha> fichas;

    public Combinacion (ArrayList<IFicha> fichas) {
        this.fichas = fichas;
    }

    public abstract boolean agregarFicha(IFicha ficha);

    public ArrayList<IFicha> getFichas () {
        return fichas;
    }
}
