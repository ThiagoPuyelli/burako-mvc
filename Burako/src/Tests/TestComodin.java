package Tests;

import modelo.ColorFicha;
import modelo.Combinacion;
import modelo.FabricaCombinacion;
import modelo.Ficha;

import java.util.ArrayList;

public class TestComodin {
    public static void main(String[] args) {
        ArrayList<Ficha> fichas = new ArrayList<>();
        fichas.add(new Ficha(ColorFicha.azul, 2));
        fichas.add(new Ficha(ColorFicha.azul, 1));
        fichas.add(new Ficha(ColorFicha.azul, 3));
        ArrayList<Ficha> fichas2 = new ArrayList<>();
        fichas2.add(new Ficha(ColorFicha.azul, 2));
        fichas2.add(new Ficha(ColorFicha.azul, 1));
        fichas2.add(new Ficha(ColorFicha.verde, 50));
        ArrayList<Ficha> fichas3 = new ArrayList<>();
        fichas3.add(new Ficha(ColorFicha.azul, 2));
        fichas3.add(new Ficha(ColorFicha.azul, 1));
        fichas3.add(new Ficha(ColorFicha.verde, 50));
        ArrayList<Ficha> fichas4 = new ArrayList<>();
        fichas4.add(new Ficha(ColorFicha.azul, 2));
        fichas4.add(new Ficha(ColorFicha.azul, 1));
        fichas4.add(new Ficha(ColorFicha.azul, 4));
        fichas4.add(new Ficha(ColorFicha.verde, 50));
        ArrayList<Ficha> fichas5 = new ArrayList<>();
        //for (int i = 1;i <= 13;i++) {
        //    fichas5.add(new Ficha(ColorFicha.azul, i));
        //}
        fichas5.add(new Ficha(ColorFicha.verde, 5));
        fichas5.add(new Ficha(ColorFicha.azul, 5));
        fichas5.add(new Ficha(ColorFicha.rojo, 5));
        fichas5.add(new Ficha(ColorFicha.azul, 5));
        //fichas5.add(new Ficha(ColorFicha.azul, 11));
        //mostrarComb(FabricaCombinacion.crearCombinacion(fichas), 1);
        //mostrarComb(FabricaCombinacion.crearCombinacion(fichas2), 2);
        //mostrarComb(FabricaCombinacion.crearCombinacion(fichas3), 3);
        //mostrarComb(FabricaCombinacion.crearCombinacion(fichas4), 4);
        Combinacion comb5 = FabricaCombinacion.crearCombinacion(fichas5);
        mostrarComb(comb5, 5);
        //comb5.agregarFicha(new Ficha(ColorFicha.azul, 7));
        //mostrarComb(comb5, 5);
    }

    public static void mostrarComb (Combinacion fichas, int num) {
        System.out.println("Numero de vaina " + num);
        if (fichas != null) {
            String pepe = "";
            for (Ficha f : fichas.getFichas()) {
                pepe += f.getColor() + " " + f.getNumero() + " ";
            }
            System.out.println(pepe);
        } else {
            System.out.println("Nope");
        }
    }
}
