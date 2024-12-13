package Vista.Consola;

import Controlador.Controlador;

import java.awt.*;

public class InputSoltar extends IInput {
    public InputSoltar(ConsoleGUI console, Controlador controlador) {
        super(console, controlador);
    }

    @Override
    public void ejecutarComando(String inputText) {
        if (inputText.startsWith("soltar")) {
            try {
                if (controlador.getEstadoTurno() == 1) {
                    String sub = "soltar ";
                    String input = inputText.substring(sub.length());
                    String f = input;
                    int posicion = console.posicionInputFicha(f, null);
                    controlador.soltarFicha(posicion);
                } else {
                    console.agregarTexto("\nNo podes soltar una ficha en este momento", Color.BLACK);
                }
            } catch (Exception e) {
                console.agregarTexto("\nEl input no es valido", Color.BLACK);
            }
        } else {
            siguiente.ejecutarComando(inputText);
        }
    }
}
