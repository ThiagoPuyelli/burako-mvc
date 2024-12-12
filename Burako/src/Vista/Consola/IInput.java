package Vista.Consola;

import Controlador.Controlador;

public abstract class IInput {
    public abstract void ejecutarComando (String inputText);
    protected IInput siguiente;
    protected ConsoleGUI console;
    protected Controlador controlador;

    public IInput (ConsoleGUI console, Controlador controlador) {
        this.console = console;
        this.controlador = controlador;
    }

    public void setSiguiente (IInput siguiente) {
        this.siguiente = siguiente;
    }
}
