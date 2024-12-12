package Cliente;

import Controlador.Controlador;
import Vista.Consola.ConsoleGUI;
import Vista.IVista;
import Vista.Menus.MenusCliente.MenuCliente;
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
        //String ip = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        ips.toArray(),
        //        null
        //);
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        //String ipServidor = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Seleccione la IP en la corre el servidor", "IP del servidor",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        null,
        //        null
        //);
        //String portServidor = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        null,
        //        8888
        //);
        String nombreJugador = (String) JOptionPane.showInputDialog(
                null,
                "Diga su nombre de jugador", "Nombre de jugador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "pito"
        );
        //String equipo = (String) JOptionPane.showInputDialog(
        //        null,
        //        "Diga el equipo en el que juega", "Numero de equipo (1 o 2)",
        //        JOptionPane.QUESTION_MESSAGE,
        //        null,
        //        null,
        //        "1"
        //);
        //Jugador jugador = new Jugador(nombreJugador);
        Controlador controlador = new Controlador();
        //IVista vista = new ConsoleGUI(controlador);
        //controlador.setVista(vista);
        System.out.println("127.0.0.1" + " " + Integer.parseInt(port) + " " + "127.0.0.1" + " " + 8888);
        Cliente cliente = new Cliente("127.0.0.1", Integer.parseInt(port), "127.0.0.1", 8888);
        //vista.iniciar(); // muestra la vista gráfica

        try {
            controlador.setJugador(nombreJugador);
            cliente.iniciar(controlador);
            MenuCliente menuCliente = new MenuCliente(controlador);
            menuCliente.iniciar();

            //controlador.setEquipo(Integer.parseInt(equipo));
             // enlaza el controlador con el modelo remoto
        } catch (RemoteException e) {
            // error de conexión
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // error al crear el objeto de acceso remoto del modelo o del controlador
            e.printStackTrace();
        }
    }
}
