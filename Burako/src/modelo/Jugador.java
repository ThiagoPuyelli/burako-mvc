package modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class Jugador implements IJugador {
  private static int ID = 0;
  private int id;
  private String nombre;
  private ArrayList<Ficha> fichas;
  private int estadoTurno = 0;

  public Jugador (String nombre) {
    this.id = ID;
    ID++;
    this.nombre = nombre;
  }
  public void setFichas (ArrayList<Ficha> fichas) {
    this.fichas = fichas;
  }
  public int getEstadoTurno () {
    return estadoTurno;
  }
  public void setEstadoTurno(int estadoTurno) {
    this.estadoTurno = estadoTurno;
  }
  public int getId () { return id; };
  public String getNombre () { return nombre; }
  public ArrayList<Ficha> getFichas () { return this.fichas; };
  public Ficha soltarFicha (int f) {
    return fichas.remove(f);
  }

  public Combinacion combinacion (ArrayList<Integer> posiciones) {
    ArrayList<Ficha> fichasComb = new ArrayList<>();
    for (Integer p : posiciones) {
      fichasComb.add(fichas.get(p));
    }
    TipoCombinacion tipo = Combinacion.verificarCombinacion(fichasComb);

    if (tipo == null) {
      return null;
    }
    for (Ficha f : fichasComb) {
      Iterator<Ficha> iterador = fichas.iterator();

      while (iterador.hasNext()) {
        Ficha ficha = iterador.next();
        if (ficha.getId() == f.getId()) {
          iterador.remove();
        }
      }
    }
    return new Combinacion(fichasComb, tipo);
  }
}
