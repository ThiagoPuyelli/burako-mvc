package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class Jugador implements IJugador, Serializable, IJugadorProxy {
  @Serial
  private static final long serialVersionUID = 582287822910246580L;
  private String nombre;
  private ArrayList<IFicha> fichas;
  private int estadoTurno = 0;

  public Jugador (String nombre) {
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
  public String getNombre () throws RemoteException { return nombre; }

  @Override
  public int cantFichas() throws RemoteException {
    return fichas.size();
  }

  public ArrayList<IFicha> getFichas () throws RemoteException { return this.fichas; };
  public IFicha soltarFicha (int f) throws RemoteException {
    return fichas.remove(f);
  }

  public ICombinacion combinacion (ArrayList<Integer> posiciones) throws RemoteException {
    ArrayList<IFicha> fichasComb = new ArrayList<>();
    for (Integer p : posiciones) {
      fichasComb.add(fichas.get(p));
    }
    ICombinacion comb = FabricaCombinacion.crearCombinacion(fichasComb);

    if (comb == null) {
      return null;
    }
    for (IFicha f : fichasComb) {
      fichas.stream()
              .filter(fi -> fi.getNumero() == f.getNumero() && fi.getColor() == f.getColor())
              .findFirst()
              .ifPresent(fichas::remove);
    }
    return comb;
  }
}
