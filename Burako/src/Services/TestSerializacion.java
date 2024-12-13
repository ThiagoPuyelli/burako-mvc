package Services;

import java.util.ArrayList;

public class TestSerializacion {
    public static void main (String[] args) {
        RankingSerializador.cargarJugador("Pepe", 1);
        RankingSerializador.cargarJugador("Pepe", 1);
        RankingSerializador.cargarJugador("Pepe", 1);
        RankingSerializador.cargarJugador("Pepe", 1);
        RankingSerializador.cargarJugador("Pepe", 1);
        RankingSerializador.cargarJugador("Franco", 1);
        RankingSerializador.cargarJugador("Franco", 1);
        RankingSerializador.cargarJugador("Franco", 1);
        RankingSerializador.cargarJugador("Franco", 1);
        RankingSerializador.cargarJugador("Franco", 1);
        RankingSerializador.cargarJugador("Juan", 1);
        RankingSerializador.cargarJugador("Juan", 1);
        RankingSerializador.cargarJugador("Walter", 1);
        RankingSerializador.cargarJugador("Walter", 1);
        RankingSerializador.cargarJugador("Walter", 1);
        RankingSerializador.cargarJugador("Pablo", 1);
        RankingSerializador.cargarJugador("Pablo", 1);
        RankingSerializador.cargarJugador("Pablo", 1);
        RankingSerializador.cargarJugador("Pablo", 1);
        System.out.println("GOLA");
        ArrayList<RankingScheme> pepe = RankingSerializador.obtenerPrimeros10();
        System.out.println("GOLA");
        System.out.println(pepe.size());
        for (RankingScheme s : pepe) {
            System.out.println(s.getNombre() + " " + s.getPartidasGanadas());
        }

    }
}
