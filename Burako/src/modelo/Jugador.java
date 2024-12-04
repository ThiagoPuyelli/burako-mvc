package modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class Jugador implements IJugador {
  private static int ID = 0;
  private int id;
  private String nombre;
  private ArrayList<IFicha> fichas;
  private int estadoTurno = 0;

  public Jugador (String nombre) {
    this.id = ID;
    ID++;
    this.nombre = nombre;
  }
  public void setFichas (ArrayList<IFicha> fichas) {
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
  public ArrayList<IFicha> getFichas () { return this.fichas; };
  public IFicha soltarFicha (int f) {
    return fichas.remove(f);
  }

  public Combinacion combinacion (ArrayList<Integer> posiciones) {
    ArrayList<IFicha> fichasComb = new ArrayList<>();
    for (Integer p : posiciones) {
      fichasComb.add(fichas.get(p));
    }
    System.out.print("DALE GEY: ");
    for (IFicha f : fichasComb) {
      System.out.print(f.getNumero() + " ");
    }
    System.out.print("\n");
    Combinacion comb = FabricaCombinacion.crearCombinacion(fichasComb);

    if (comb == null) {
      return null;
    }
    for (IFicha f : fichasComb) {
      fichas.removeIf(fi -> fi.getId() == f.getId());
    }
    return comb;
    //for (Ficha f : fichasComb) {
    //  Iterator<Ficha> iterador = fichas.iterator();
//
    //  while (iterador.hasNext()) {
    //    Ficha ficha = iterador.next();
    //    if (ficha.getId() == f.getId()) {
    //      iterador.remove();
    //    }
    //  }
    //}
  }
}
