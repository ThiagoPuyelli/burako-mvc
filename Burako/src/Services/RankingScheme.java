package Services;

import java.io.Serializable;

public class RankingScheme implements Serializable {
    String nombre;
    int partidasGanadas;

    public RankingScheme(String nombre, int partidasGanadas) {
        this.nombre = nombre;
        this.partidasGanadas = partidasGanadas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
}
