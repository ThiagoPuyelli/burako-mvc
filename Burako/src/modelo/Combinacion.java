package modelo;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Combinacion {
    protected ArrayList<Ficha> fichas;
    public Combinacion (ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }

    public abstract boolean agregarFicha(Ficha ficha);

    public ArrayList<Ficha> getFichas () {
        return fichas;
    }

    public static TipoCombinacion verificarCombinacion(ArrayList<Ficha> combinacion) {
        int comodines = tieneComodin(combinacion);
        if (comodines > 0) {
            combinacion.removeIf(f -> f.getNumero() == 50);
        }

        if (combinacion.size() < 3) {
            return null;
        }

        combinacion.sort(Comparator.comparing(Ficha::getNumero));

        if (esEscalera(combinacion, comodines)) {
            return TipoCombinacion.Escalera;
        }

        if (esPierna(combinacion)) {
            return TipoCombinacion.Pierna;
        }

        return null;
    }

    private static int tieneComodin (ArrayList<Ficha> fichas) {
        int comodines = 0;
        for (Ficha f : fichas) {
            if (f.getNumero() == 50) {
                comodines++;
            }
        }
        return comodines;
    }
    private static boolean esEscalera(ArrayList<Ficha> combinacion, int comodines) {
        for (int i = 1; i < combinacion.size(); i++) {
            if (!verificarColor(combinacion.get(i - 1), combinacion.get(i))) {
                return false;
            }
            if (!verificarNumero(combinacion.get(i - 1), combinacion.get(i))) {
                if (!(comodines > 0 || esConComodin(combinacion, i))) {
                    return false;
                } else {
                    comodines--;
                }
            }
        }
        if (comodines > 0) {
            for (int i = 0;i < comodines;i++) {
              combinacion.add(new Ficha(null, 50));
            }
        }
        return true;
    }

    private static boolean esConComodin(ArrayList<Ficha> combinacion, int i) {
        return combinacion.get(i - 1).getNumero() == combinacion.get(i).getNumero() - 2;
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

    protected static boolean verificarColor(Ficha f1, Ficha f2) {
        return f1.getColor() == f2.getColor();
    }

    protected static boolean verificarNumero(Ficha f1, Ficha f2) {
        return f1.getNumero() == f2.getNumero() - 1;
    }

}
