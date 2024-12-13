package Vista.Grafica;

import javax.swing.*;
import java.awt.*;

public class SurGrafica extends JPanel {
    JLabel score;
    JLabel scoreEnemigo;
    SetFichasGrafica fichas;
    private Image backgroundImage = new ImageIcon("src/Imagenes/fondoFichas.jpg").getImage();

    public SurGrafica(int score, int scoreEnemigo, SetFichasGrafica fichas) {
        this.score = new JLabel();
        this.scoreEnemigo = new JLabel();
        this.score.setForeground(Color.WHITE);
        this.scoreEnemigo.setForeground(Color.WHITE);
        this.setOpaque(false);
        this.setScore(score);
        this.setScoreEnemigo(scoreEnemigo);
        this.fichas = fichas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setScore(int score) {
        this.score.setText("Tu score: " + score);
    }

    public void setScoreEnemigo(int scoreEnemigo) {
        this.scoreEnemigo.setText("Score enemigo: " + scoreEnemigo);
    }

    public void actualizarDisenio () {
        this.removeAll();
        this.add(this.score);
        this.add(this.fichas);
        this.add(this.scoreEnemigo);
        this.score.revalidate();
        this.score.repaint();
        this.scoreEnemigo.revalidate();
        this.scoreEnemigo.repaint();
        fichas.actualizarDisenio();
        this.revalidate();
        this.repaint();
    }

    public void setFichas (SetFichasGrafica fichas) {
        this.fichas = fichas;
    }
}
