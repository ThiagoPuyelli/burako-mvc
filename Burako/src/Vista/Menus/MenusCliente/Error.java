package Vista.Menus.MenusCliente;

import javax.swing.*;

public class Error extends JFrame {
    private JLabel label;

    public Error(String mensaje) {
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        label = new JLabel("           " + mensaje);
    }

    public void iniciar () {
        setVisible(true);
    }
}
