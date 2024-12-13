package Vista.Menus.MenusCliente;

import Controlador.Controlador;
import Services.RankingScheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsuarioRepetido extends JFrame {

    public UsuarioRepetido(Controlador controlador) {
        String nombreJugador = (String) JOptionPane.showInputDialog(
                null,
                "El nombre esta repetido, elija otro", "Nombre de jugador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "nombre"
        );
        if (nombreJugador == null) {
            setVisible(false);
            return;
        }
        controlador.setJugador(nombreJugador);
        if (!controlador.existeJugador()) {
            ElegirEquipo elegirEquipo = new ElegirEquipo(controlador);
            elegirEquipo.setVisible(true);
        } else {
            setVisible(false);
            new UsuarioRepetido(controlador);
        }
    }

    public void iniciar () {
        setVisible(true);
    }
}
