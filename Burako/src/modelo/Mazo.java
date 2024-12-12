package modelo;

import java.util.ArrayList;

public class Mazo {
    private ArrayList<IFicha> mazo;

    public Mazo (ArrayList<IFicha> mazo) {
        this.mazo = mazo;
    }

    public IFicha obtenerFicha () {
        System.out.println("Momento mique");
        return this.mazo.remove(0);
    }

    public ArrayList<IFicha> obtenerFichas (int cantidad) {
        ArrayList<IFicha> fichas = new ArrayList<>();
        System.out.println("Se sarpan " + cantidad);
        for (int i = 0;i < cantidad;i++) {
            fichas.add(this.obtenerFicha());
        }
        return fichas;
    }

    public int cantidadFichas () {
        return mazo.size();
    }
}
