package Services;

import java.util.ArrayList;
import java.util.Comparator;

public class RankingSerializador {
    private static Serializador serializadorRanking = new Serializador("ranking.dat");

    public static void cargarJugador (String nombreJugador, int partida) {
        ArrayList<RankingScheme> rankings = obtenerRanking();
        boolean actualizado = false;
        for (RankingScheme r : rankings) {
            if (r.getNombre().equals(nombreJugador)) {
                r.setPartidasGanadas(r.getPartidasGanadas() + partida);
                actualizado = true;
            }
        }
        if (!actualizado) {
            rankings.add(new RankingScheme(nombreJugador, partida));
        }

        guardarRanking(rankings);
    }

    public static void guardarRanking (ArrayList<RankingScheme> rankings) {
        if (rankings.size() >= 1) {
            serializadorRanking.writeOneObject(rankings.get(0));
            for(int x = 1; x < rankings.size(); x ++) {
                serializadorRanking.addOneObject(rankings.get(x));
            }
        }
    }

    public static ArrayList<RankingScheme> obtenerRanking () {
        ArrayList<RankingScheme> rankings = new ArrayList<>();
        Object[] recuperado = serializadorRanking.readObjects();
        if (recuperado != null) {
            for(int x = 0; x < recuperado.length; x ++) {
                rankings.add((RankingScheme) recuperado[x]);
            }
        }
        return rankings;
    }

    public static ArrayList<RankingScheme> obtenerPrimeros10 () {
        ArrayList<RankingScheme> ranking = obtenerRanking();
        ranking.sort(Comparator.comparingInt(RankingScheme::getPartidasGanadas).reversed());
        ArrayList<RankingScheme> primeros10 = new ArrayList<>();
        int i = 0;
        while (i < ranking.size() && i < 10) {
            primeros10.add(ranking.get(i));
            i++;
        }
        return primeros10;
    }
}
