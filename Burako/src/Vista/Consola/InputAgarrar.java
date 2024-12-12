package Vista.Consola;

import Controlador.Controlador;

import java.awt.*;

public class InputAgarrar extends IInput {

    public InputAgarrar(ConsoleGUI console, Controlador controlador) {
        super(console, controlador);
    }

    @Override
    public void ejecutarComando(String inputText) {
        if (inputText.startsWith("agarrar")) {
            try {
                if (controlador.getEstadoTurno() == 2) {
                    String sub = "agarrar ";
                    String input = inputText.substring(sub.length());
                    if (input.startsWith("pozo")) {
                        if (controlador.getPozo().isEmpty()) {
                            console.agregarTexto("El pozo esta vacio\n", Color.BLACK);
                        } else {
                            controlador.agarrarPozo();
                        }
                    } else if (input.startsWith("mazo")) {
                        controlador.agarrarMazo();
                        console.mostrarTurno(controlador.getNombre());
                        console.mostrarFichas();
                    } else {
                        console.agregarTexto("\nComando equivocado", Color.BLACK);
                    }
                } else {
                    console.agregarTexto("\nNo podes agarrar una ficha en este momento", Color.BLACK);
                }
            } catch (Exception e) {
                console.agregarTexto("\nEl input no es valido", Color.BLACK);
            }
        } else {
            siguiente.ejecutarComando(inputText);
        }
    }
}
