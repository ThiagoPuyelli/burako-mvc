package Vista.Grafica;

import Controlador.Controlador;
import Vista.IVista;
import Vista.VistaPlay;
import modelo.ColorFicha;
import modelo.ICombinacion;
import modelo.IFicha;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Grafica extends VistaPlay implements IVista, MouseListener {
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel() {
        private Image backgroundImage = new ImageIcon("src/Imagenes/fondoCentro.jpg").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

    private PozoGrafica pozoGrafica = new PozoGrafica(0, this);
    private JPanel panelPrincipal = new JPanel();
    private boolean inicioPartida = false;
    private boolean terminaPartida = false;
    private SetFichasGrafica setFichas = new SetFichasGrafica();
    private CombinacionesGrafica combinaciones = new CombinacionesGrafica("Tus combinaciones", this);
    private CombinacionesGrafica combinacionesContrario = new CombinacionesGrafica("Combinaciones en contra", this);
    private ArrayList<Integer> posiciones = new ArrayList<>();
    private ArrayList<IFicha> fichas;
    private ArrayList<ICombinacion> combinacionesJugador;
    private int score = 0;
    private int scoreEnemigo = 0;
    private SurGrafica sur = new SurGrafica(score, scoreEnemigo, setFichas);
    private JButton hacerCombinacion = new JButton();
    private JButton soltar = new JButton();
    private GraficaJugadores graficaJugadores;
    private PanelMensaje panelDesconexion = new PanelMensaje("Un jugador se desconecto, cuando vuelvan todos se sigue jugando");
    private PanelMensaje panelAIniciar = new PanelMensaje("La partida comenzara cuando todos se unan");
    private PanelMensaje panelTerminar = new PanelMensaje("Partida finalizada");
    private final CardLayout cardLayout = new CardLayout();

    public Grafica(Controlador controlador) {
        super(controlador);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 650);
        panelPrincipal.setLayout(cardLayout);
        Dimension dimension = new Dimension(frame.getWidth(), frame.getHeight());
        panelPrincipal.setPreferredSize(dimension);
        panelPrincipal.setMinimumSize(dimension);
        panelPrincipal.setMaximumSize(dimension);
        frame.add(panelPrincipal);
        panel.setLayout(new BorderLayout());

        hacerCombinacion.setText("Hacer combinacion");
        hacerCombinacion.setVisible(false);
        soltar.setText("Soltar ficha");
        JPanel botones = new JPanel();
        botones.add(hacerCombinacion);
        botones.add(soltar);
        botones.setOpaque(false);
        graficaJugadores = new GraficaJugadores(controlador, panel);

        panel.add(sur, BorderLayout.SOUTH);
        panel.add(combinaciones, BorderLayout.EAST);
        panel.add(combinacionesContrario, BorderLayout.WEST);
        panel.add(pozoGrafica, BorderLayout.CENTER);
        panel.add(botones,BorderLayout.NORTH);

        hacerCombinacion.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                if (controlador.getEstadoTurno() == 1) {
                    crearCombinacion();
                    resetPosiciones();
                }
            }
        });

        soltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controlador.getEstadoTurno() == 1) {
                    soltarFichaPozo();
                    resetPosiciones();
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlador.desconectarJugador();
                SwingUtilities.invokeLater(()-> System.exit(0));
            }
        });

        soltar.setVisible(false);
        panel.setVisible(true);
        panelDesconexion.setVisible(false);
        panelTerminar.setVisible(false);
        panelAIniciar.setVisible(true);
        panelPrincipal.add(panelAIniciar, "panelAIniciar");
        panelPrincipal.add(graficaJugadores, "graficaJugadores");
        panelPrincipal.add(panelDesconexion, "panelDesconexion");
        panelPrincipal.add(panelTerminar, "panelTerminar");

    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarTurno(String nombre) {
        panelAIniciar.setVisible(false);
        panelDesconexion.setVisible(false);
        cardLayout.show(panelPrincipal, "graficaJugadores");
        graficaJugadores.mostrarTurnos(nombre);
    }

    public ArrayList<Integer> getPosiciones () {
        return posiciones;
    }

    public void iniciarControlador () {
        controlador.iniciarPartida();
    }

    @Override
    public void setDesconexion() {
        if (!terminaPartida) {
            graficaJugadores.setVisible(false);
            cardLayout.show(panelPrincipal, "panelDesconexion");
        }
    }

    private void crearCombinacion () {
        if (controlador.getEstadoTurno() == 1) {
            controlador.combinacion(posiciones);
        }
    }

    public int getEstadoTurno () {
        return controlador.getEstadoTurno();
    }

    public void soltarFichaPozo () {
        controlador.soltarFicha(posiciones.get(0));
    }

    public void agregarPosicion (int posicion) {
        posiciones.add(posicion);
        if (posiciones.size() == 1) {
            soltar.setVisible(true);
        } else {
            soltar.setVisible(false);
        }
        if (posiciones.size() >= 3) {
            hacerCombinacion.setVisible(true);
        } else {
            hacerCombinacion.setVisible(false);
        }
        panel.revalidate();
        panel.repaint();
    }

    public void eliminarPosicion (int posicion) {
        posiciones.removeIf((p) -> p == posicion);
        if (posiciones.size() >= 3) {
            hacerCombinacion.setVisible(true);
            soltar.setVisible(false);
        } else if (posiciones.size() == 1) {
            soltar.setVisible(true);
            hacerCombinacion.setVisible(false);
        } else {
            soltar.setVisible(false);
            hacerCombinacion.setVisible(false);
        }
    }

    private void resetPosiciones () {
        posiciones.clear();
        hacerCombinacion.setVisible(false);
        soltar.setVisible(false);
        for (FichaGrafica f : setFichas.getFichas()) {
            FichaGraficaSet s = (FichaGraficaSet) f;
            s.reset();
        }
    }

    @Override
    public void mostrarFichas() {
        ArrayList<FichaGrafica> fichasGrafica = new ArrayList<>();
        fichas = controlador.getFichas();
        combinacionesJugador = controlador.getCombinaciones();
        ArrayList<ICombinacion> combinacionesContrario = controlador.getCombinacionesContrario();

        int i = 0;
        for (IFicha f : fichas) {
            fichasGrafica.add(new FichaGraficaSet(f.getNumero(), this.definirColor(f.getColor()), i, this));
            i++;
        }
        setFichas.setFichas(fichasGrafica);
        setFichas.actualizarDisenio();
        sur.setFichas(setFichas);
        sur.setScore(controlador.getScore());
        sur.setScoreEnemigo(controlador.getScoreContrario());
        sur.actualizarDisenio();

        SetFichasGrafica setPozo = new SetFichasGrafica();
        for (IFicha f : controlador.getPozo()) {
            setPozo.add(new FichaGrafica(f.getNumero(), this.definirColor(f.getColor())));
        }
        pozoGrafica.setPozo(setPozo);
        pozoGrafica.setCantFichas(controlador.cantidadMazo());
        this.combinaciones.setCombinaciones(generarSetCombinaciones(combinacionesJugador));
        this.combinaciones.actualizarDisenio();
        this.combinacionesContrario.setCombinaciones(generarSetCombinaciones(combinacionesContrario));
        this.combinacionesContrario.actualizarDisenio();
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    private ArrayList<SetFichasGrafica> generarSetCombinaciones (ArrayList<ICombinacion> combinaciones) {
        ArrayList<SetFichasGrafica> setCombinaciones = new ArrayList<>();
        for (ICombinacion c : combinaciones) {
            ArrayList<FichaGrafica> fichas = new ArrayList<>();
            for (IFicha f : c.getFichas()) {
                fichas.add(new FichaGrafica(f.getNumero(), this.definirColor(f.getColor())));
            }
            SetFichasGrafica set = new SetFichasGrafica();
            set.setFichas(fichas);
            set.actualizarDisenio();
            setCombinaciones.add(set);
        }
        return setCombinaciones;
    }

    public void agarrarDelMazo () {
        if (controlador.getEstadoTurno() == 2){
            controlador.agarrarMazo();
            mostrarFichas();
            mostrarTurno(controlador.getNombre());
        }
    }

    public void agarrarDelPozo () {
        if (controlador.getEstadoTurno() == 2) {
            controlador.agarrarPozo();
            mostrarFichas();
            mostrarTurno(controlador.getNombre());
        }
    }

    public void generarCombinacion (int pos) {
        if (posiciones.size() == 1) {
            controlador.agregarFichaComb(pos, posiciones.get(0));
            this.resetPosiciones();
        }
    }

    @Override
    public void setMuerto() {

    }

    @Override
    public void terminarPartida() {
        panel.setVisible(false);
        terminaPartida = true;
        panelTerminar.setTextoLabel("Partida finalizada ganador/es: " + controlador.getGanador());
        cardLayout.show(panelPrincipal, "panelTerminar");
        controlador.limpiarPartida();
    }

    @Override
    public void iniciarPartida () {
        inicioPartida = true;
    }

    private Color definirColor (ColorFicha colorFicha) {
        switch (colorFicha) {
            case azul -> {
                return Color.GREEN;
            }
            case rojo -> {
                return Color.RED;
            }
            case amarillo -> {
                return Color.GRAY;
            }
            default -> {
                return Color.BLACK;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
