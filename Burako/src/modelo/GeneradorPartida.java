package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class GeneradorPartida {

    public static Mazo generarMazo () {
        ArrayList<IFicha> fichas = new ArrayList<>();
        for (ColorFicha c : ColorFicha.values()) {
            for (int i = 1;i <= 13;i++) {
                fichas.add(new Ficha(c, i));
                fichas.add(new Ficha(c, i));
            }
        }
        fichas.add(new Ficha(ColorFicha.rojo, 50));
        fichas.add(new Ficha(ColorFicha.azul, 50));
        Collections.shuffle(fichas);
        return new Mazo(fichas);
    }

    public static IFicha[] generarMuerto (Mazo mazo) {
        IFicha[] muerto = new IFicha[11];
        for (int i = 0;i < 11;i++) {
            muerto[i] = mazo.obtenerFicha();
        }
        return muerto;
    }

}
