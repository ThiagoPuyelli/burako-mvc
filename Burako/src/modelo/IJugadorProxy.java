package modelo;

import java.rmi.RemoteException;

public interface IJugadorProxy {
    String getNombre() throws RemoteException;
    int cantFichas () throws RemoteException;
}
