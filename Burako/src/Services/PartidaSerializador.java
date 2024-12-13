package Services;

import modelo.Tablero;

import java.util.ArrayList;

public class PartidaSerializador {
    private static Serializador serializadorPartidas = new Serializador("partida.dat");

    public static void cargarPartida (TableroScheme tablero) {
        ArrayList<TableroScheme> tableros = new ArrayList<>();
        tableros.add(tablero);

        guardarPartidas(tableros);
    }

    public static void eliminarPartida (int i) {
        ArrayList<TableroScheme> tableros = obtenerTableros();
        tableros.remove(i);
        guardarPartidas(tableros);
    }

    public static void guardarPartidas (ArrayList<TableroScheme> tableros) {
        if (tableros.size() >= 1) {
            serializadorPartidas.writeOneObject(tableros.get(0));
            for(int x = 1; x < tableros.size(); x ++) {
                serializadorPartidas.addOneObject(tableros.get(x));
            }
        }
    }

    public static ArrayList<TableroScheme> obtenerTableros () {
        ArrayList<TableroScheme> tableros = new ArrayList<>();
        Object[] recuperado = serializadorPartidas.readObjects();
        if (recuperado != null) {
            for(int x = 0; x < recuperado.length; x ++) {
                tableros.add((TableroScheme) recuperado[x]);
            }
        }
        return tableros;
    }
}
