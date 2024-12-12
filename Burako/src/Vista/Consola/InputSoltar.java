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
                    int f = Integer.parseInt(input) - 1;
                    if (f >= controlador.getFichas().size()) {
                        console.agregarTexto("\nFicha invalida", Color.BLACK);
                    } else {
                        controlador.soltarFicha(f);
                    }
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
