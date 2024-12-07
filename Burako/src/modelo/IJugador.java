package modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IJugador {
  int getId() throws RemoteException;
  String getNombre() throws RemoteException;
  void setFichas (ArrayList<IFicha> fichas) throws RemoteException;
  int getEstadoTurno () throws RemoteException;
  void setEstadoTurno(int estadoTurno) throws RemoteException;
  ArrayList<IFicha> getFichas () throws RemoteException;
  IFicha soltarFicha (int f) throws RemoteException;
  ICombinacion combinacion (ArrayList<Integer> posiciones) throws RemoteException;
}
