package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class Jugador implements IJugador, Serializable {
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
  public void setFichas (ArrayList<IFicha> fichas) throws RemoteException {
    this.fichas = fichas;
  }
  public int getEstadoTurno () throws RemoteException {
    return estadoTurno;
  }
  public void setEstadoTurno(int estadoTurno) throws RemoteException {
    this.estadoTurno = estadoTurno;
  }
  public int getId () throws RemoteException { return id; };
  public String getNombre () throws RemoteException { return nombre; }
  public ArrayList<IFicha> getFichas () throws RemoteException { return this.fichas; };
  public IFicha soltarFicha (int f) throws RemoteException {
    return fichas.remove(f);
  }

  public ICombinacion combinacion (ArrayList<Integer> posiciones) throws RemoteException {
    ArrayList<IFicha> fichasComb = new ArrayList<>();
    for (Integer p : posiciones) {
      fichasComb.add(fichas.get(p));
    }
    System.out.print("DALE GEY: ");
    for (IFicha f : fichasComb) {
      System.out.print(f.getNumero() + " ");
    }
    System.out.print("\n");
    ICombinacion comb = FabricaCombinacion.crearCombinacion(fichasComb);

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
