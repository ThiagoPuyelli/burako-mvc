package Servidor;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import modelo.Tablero;

//import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppServidor {

    public static void main(String[] args) {
        //ArrayList<String> ips = Util.getIpDisponibles();
        //String ip = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Seleccione la IP en la que escuchar� peticiones el servidor", "IP del servidor",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        ips.toArray(),
        //        null
        //);
        //String port = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Seleccione el puerto en el que escuchar� peticiones el servidor", "Puerto del servidor",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        null,
        //        8888
        //);
        Tablero modelo = null;
        try {
            modelo = new Tablero();
            Servidor servidor = new Servidor("127.0.0.1", 8888);
            try {
                servidor.iniciar(modelo);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RMIMVCException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public static void iniciarServidor () {
        Tablero modelo = null;
        try {
            modelo = new Tablero();
            Servidor servidor = new Servidor("127.0.0.1", 8999);
            try {
                servidor.iniciar(modelo);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RMIMVCException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
