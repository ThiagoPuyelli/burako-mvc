package modelo;

import java.util.ArrayList;

public interface ICombinacion {
    boolean agregarFicha(IFicha ficha);
    ArrayList<IFicha> getFichas ();
}
