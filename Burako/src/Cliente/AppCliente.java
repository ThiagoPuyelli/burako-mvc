package Cliente;

import Controlador.Controlador;
import Vista.Consola.ConsoleGUI;
import Vista.IVista;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import modelo.Equipo;
import modelo.Jugador;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppCliente {
    public static void main (String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        String nombreJugador = (String) JOptionPane.showInputDialog(
                null,
                "Diga su nombre de jugador", "Nombre de jugador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "pito"
        );
        String equipo = (String) JOptionPane.showInputDialog(
                null,
                "Diga el equipo en el que juega", "Numero de equipo (1 o 2)",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "1"
        );
        //Jugador jugador = new Jugador(nombreJugador);
        Controlador controlador = new Controlador();
        IVista vista = new ConsoleGUI(controlador);
        controlador.setVista(vista);
        System.out.println(ip + " " + Integer.parseInt(port) + " " + ipServidor + " " + Integer.parseInt(portServidor));
        Cliente cliente = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        vista.iniciar(); // muestra la vista gráfica
        try {
            controlador.setJugador(nombreJugador, Integer.parseInt(equipo));
            cliente.iniciar(controlador); // enlaza el controlador con el modelo remoto
        } catch (RemoteException e) {
            // error de conexión
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // error al crear el objeto de acceso remoto del modelo o del controlador
            e.printStackTrace();
        }
    }
}
