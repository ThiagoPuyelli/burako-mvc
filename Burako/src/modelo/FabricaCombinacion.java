package modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FabricaCombinacion {
    public static Combinacion crearCombinacion (ArrayList<Ficha> combinacion) {
        int comodines = contarComodines(combinacion);

        if (comodines > 1) {
            return null;
        }

        if (combinacion.size() < 3) {
            return null;
        }

        if (esPierna(combinacion)) {
            return new Pierna(combinacion);
        }

        if (esEscalera(combinacion)) {
            return new Escalera(combinacion);
        }

        return null;
    }

    public static int contarComodines (ArrayList<Ficha> fichas) {
        int comodines = 0;
        for (Ficha f : fichas) {
            if (f.getNumero() == 50) {
                comodines++;
            }
        }
        return comodines;
    }

    public static boolean esEscalera(ArrayList<Ficha> combinacion) {
        boolean escalera = true;
        ordenarFichas(combinacion);
        for (int i = 1; i < combinacion.size(); i++) {
            if (!verificarColor(combinacion.get(i - 1), combinacion.get(i))) {
                return false;
            }
            if (!verificarNumero(combinacion.get(i - 1), combinacion.get(i))) {
                escalera = false;
            }
        }
        if (escalera) {
            return true;
        }
        if (combinacion.get(combinacion.size() - 1).getNumero() == 50) {
            Ficha co = combinacion.remove(combinacion.size() - 1);
            int pos = combinacionConComodin(combinacion);
            if (pos == -1) return false;

            combinacion.add(pos, co);
            return true;
        }
        List<Ficha> fichasFiltradas = combinacion.stream()
                .filter(fi -> fi.getNumero() == 2)
                .toList();
        if (fichasFiltradas.size() == 1) {
            Ficha fi2 = fichasFiltradas.get(0);
            combinacion.removeIf(fi -> fi.getNumero() == 2);
            boolean escaleraSin2 = true;
            for (int i = 1; i < combinacion.size(); i++) {
                if (!verificarNumero(combinacion.get(i - 1), combinacion.get(i))) {
                    escaleraSin2 = false;
                }
            }
            if (escaleraSin2) {
                combinacion.add(fi2);
                return true;
            }
            int pos = combinacionConComodin(combinacion);

            if (pos == -1) return false;
            combinacion.add(pos, fi2);
            return true;
        }
        return false;
    }

    private static int combinacionConComodin(ArrayList<Ficha> combinacion) {
        int pos = 1;
        boolean como = true;
        for (int i = 1;i < combinacion.size();i++) {
            if (esConComodin(combinacion, pos)) {
                if(como) {
                    como = false;
                } else {
                    return -1;
                }
            } else if (como) {
                pos++;
            }
        }
        return pos < combinacion.size() ? pos : -1;
    }

    private static boolean esConComodin(ArrayList<Ficha> combinacion, int i) {
        return combinacion.get(i - 1).getNumero() == combinacion.get(i).getNumero() - 2 || combinacion.get(i - 1).getNumero() - 13 == combinacion.get(i).getNumero() - 2;
    }

    public static boolean esPierna(ArrayList<Ficha> combinacion) {
        ordenarFichas(combinacion);

        if (verificarPiernaBase(combinacion)) {
            return true;
        }
        if (combinacion.get(combinacion.size() - 1).getNumero() != 50) {
            List<Ficha> fichas2 = combinacion.stream()
                    .filter(fi -> fi.getNumero() == 2)
                    .toList();
            if (fichas2.size() != 1) {
                return false;
            }
            Ficha f = fichas2.get(0);
            combinacion.removeIf(fi -> fi.getNumero() == 2);
            if (verificarPiernaBase(combinacion)) {
                combinacion.add(f);
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean verificarPiernaBase (ArrayList<Ficha> combinacion) {
        int numeroBase = combinacion.get(0).getNumero();
        int c = 0;
        for (int i = 1; i < combinacion.size(); i++) {
            if (numeroBase != combinacion.get(i).getNumero() || combinacion.get(i).getNumero() == 50) {
                return false;
            }
        }
        return true;
    }

    protected static boolean verificarColor(Ficha f1, Ficha f2) {
        return f1.getColor() == f2.getColor() || f1.getNumero() == 50 || f2.getNumero() == 50;
    }

    public static boolean verificarNumero(Ficha f1, Ficha f2) {
        return f1.getNumero() == f2.getNumero() - 1 || (f1.getNumero() == 13 && f2.getNumero() == 1) || f1.getNumero() == 50 || f2.getNumero() == 50;
    }

    public static void ordenarFichas (ArrayList<Ficha> fichas) {
        fichas.sort(Comparator.comparing(Ficha::getNumero));
    }

}
