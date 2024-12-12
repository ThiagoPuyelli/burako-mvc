package Vista.Consola;

import Controlador.Controlador;

import java.awt.*;

public class InputInvalido extends IInput {

    public InputInvalido(ConsoleGUI console, Controlador controlador) {
        super(console, controlador);
    }

    @Override
    public void ejecutarComando(String inputText) {
        console.agregarTexto("\n El input es invalido", Color.BLACK);
    }
}
