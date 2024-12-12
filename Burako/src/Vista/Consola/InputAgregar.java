package Vista.Consola;

import Controlador.Controlador;

import java.awt.*;

public class InputAgregar extends IInput {

    public InputAgregar(ConsoleGUI console, Controlador controlador) {
        super(console, controlador);
    }

    @Override
    public void ejecutarComando(String inputText) {
        if (inputText.startsWith("agregar")) {
            try {
                if (controlador.getEstadoTurno() == 1) {
                    String sub = "agregar ";
                    String input = inputText.substring(sub.length());
                    String[] inputSplit = input.split(" ");
                    int c = Integer.parseInt(inputSplit[0]);
                    String f = inputSplit[1];
                    int posicion = console.posicionInputFicha(f, null);
                    if (c >= controlador.getCombinaciones().size() || posicion == -1) {
                        console.agregarTexto("\nUna de las posiciones es invalida", Color.BLACK);
                    }
                    controlador.agregarFichaComb(c - 1, posicion);
                } else {
                    console.agregarTexto("\nNo podes hacer un agregar en este momento", Color.BLACK);
                }
            } catch (Exception e) {
                console.agregarTexto("\nEl input no es valido", Color.BLACK);
            }

        } else {
            siguiente.ejecutarComando(inputText);
        }
    }
}
