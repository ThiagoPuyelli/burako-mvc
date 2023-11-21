package modelo;

import java.util.ArrayList;
import java.util.Comparator;

public class Combinacion {
    private ArrayList<Ficha> fichas;
    private TipoCombinacion tipo;
    public Combinacion (ArrayList<Ficha> fichas, TipoCombinacion tipo) {
        this.fichas = fichas;
        this.tipo = tipo;
    }

    public boolean agregarFicha(Ficha ficha) {
        if (tipo == TipoCombinacion.Escalera) {
            if (verificarNumero(ficha, fichas.get(0))) {
                fichas.add(0, ficha);
                return true;
            } else if (verificarNumero(fichas.get(fichas.size() - 1), ficha)){
                fichas.add(ficha);
                return true;
            }
            return false;
        } else {
            if (fichas.get(0).getNumero() == ficha.getNumero()) {
                fichas.add(ficha);
                return true;
            } else {
                return false;
            }
        }
    };

    public ArrayList<Ficha> getFichas () {
        return fichas;
    }

    public TipoCombinacion getTipo () {
        return tipo;
    }

    public static TipoCombinacion verificarCombinacion(ArrayList<Ficha> combinacion) {
        if (combinacion.size() < 3) {
            return null;
        }

        combinacion.sort(Comparator.comparing(Ficha::getNumero));

        if (esEscalera(combinacion)) {
            return TipoCombinacion.Escalera;
        }

        if (esPierna(combinacion)) {
            return TipoCombinacion.Pierna;
        }

        return null;
    }

    private static boolean esEscalera(ArrayList<Ficha> combinacion) {
        for (int i = 1; i < combinacion.size(); i++) {
            if (!verificarNumero(combinacion.get(i - 1), combinacion.get(i)) || !verificarColor(combinacion.get(i - 1), combinacion.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean esPierna(ArrayList<Ficha> combinacion) {
        int numeroBase = combinacion.get(0).getNumero();
        int c = 0;
        for (int i = 1; i < combinacion.size(); i++) {
            if (numeroBase != combinacion.get(i).getNumero()) {
                return false;
            }
            if (!verificarColor(combinacion.get(i - 1), combinacion.get(i))) {
                c++;
            }
        }
        if (c == 0) {
            return false;
        }
        return true;
    }

    private static boolean verificarColor(Ficha f1, Ficha f2) {
        return f1.getColor() == f2.getColor();
    }

    private static boolean verificarNumero(Ficha f1, Ficha f2) {
        return f1.getNumero() == f2.getNumero() - 1;
    }

}
