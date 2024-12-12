package Vista.Consola;

import Controlador.Controlador;

import java.awt.*;
import java.util.ArrayList;

public class InputCombinacion extends IInput {

    public InputCombinacion(ConsoleGUI console, Controlador controlador) {
        super(console, controlador);
    }

    @Override
    public void ejecutarComando(String inputText) {
        if (inputText.startsWith("combinacion")) {
            try {
                if (controlador.getEstadoTurno() == 1) {
                    String sub = "combinacion ";
                    String input = inputText.substring(sub.length());
                    String[] inputSplit = input.split(" ");
                    ArrayList<Integer> posiciones = new ArrayList<>();
                    boolean verify = false;
                    int posicion;
                    for (String s : inputSplit) {
                        posicion = console.posicionInputFicha(s, posiciones);
                        verify = posicion == -1;
                        if (verify) break;
                        posiciones.add(posicion);
                    }
                    if (verify) {
                        console.agregarTexto("\nUna posicion es invalida", Color.BLACK);
                    } else {
                        controlador.combinacion(posiciones);
                    }
                } else {
                    console.agregarTexto("\nNo podes hacer una combinacion en este momento", Color.BLACK);
                }
            } catch (Exception e) {
                console.agregarTexto("\nEl input no es valido", Color.BLACK);
            }
        } else {
            siguiente.ejecutarComando(inputText);
        }
    }
}
