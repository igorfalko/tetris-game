package pet.game.controller.visual;

import pet.game.model.Field;
import pet.game.model.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import static pet.game.controller.GameManager.FIELD_HEIGHT;
import static pet.game.controller.GameManager.FIELD_WIDTH;

public class GameField extends JPanel {
    public static final String GAME_OVER_STRING = "Game over";
    private static final int CELLSIZE = 25;
    private static final int H_OFFSET = 10;
    private static final int V_OFFSET = 10;
    transient Figure figure;
    transient Figure nextFigure;
    transient Field field;
    private JTextArea taScores;

    public GameField() {
        this.setBounds(0, 0, H_OFFSET + getDrawWidth() + 6 * CELLSIZE + 2, V_OFFSET + getDrawHeight() + 2);
        taScores = new JTextArea("Level: 0\nScore: 0\nLines: 0");
        taScores.setBounds(H_OFFSET + getDrawWidth() + CELLSIZE + 1, CELLSIZE * 6 + 1, 200, 60);
        this.add(taScores);
        setFocusable(true);
    }

    public void drawField(Figure figure, Figure nextFigure, Field field, String status) {
        this.figure = figure;
        this.nextFigure = nextFigure;
        this.field = field;
        taScores.setText(status);
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (field == null)
            return;

        drawField(g);
        drawCurrentFigure(g);
        drawNextFigure(g);
    }

    private void drawField(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(
                H_OFFSET - 1,
                V_OFFSET - 1,
                getDrawWidth() + 2,
                getDrawHeight() + 2);
        g.setColor(Color.BLACK);
        g.fillRect(
                H_OFFSET,
                V_OFFSET,
                getDrawWidth() + 1,
                getDrawHeight() + 1);

        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_HEIGHT; j++)
                if (!field.isCellEmpty(i, j)) {
                    g.setColor(field.getCellColor(i, j));
                    g.fillRect(
                            H_OFFSET + i * CELLSIZE + 1,
                            V_OFFSET + j * CELLSIZE + 1,
                            CELLSIZE - 1,
                            CELLSIZE - 1);
                }
    }

    private void drawCurrentFigure(Graphics g) {
        g.setColor(figure.getColor());
        for (int i = 0; i < 4; i++)
            if (figure.getY(i) >= 0)
                g.drawRect(
                        H_OFFSET + figure.getX(i) * CELLSIZE,
                        V_OFFSET + figure.getY(i) * CELLSIZE,
                        CELLSIZE,
                        CELLSIZE);
    }

    private void drawNextFigure(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(
                H_OFFSET + getDrawWidth() + CELLSIZE + 1,
                V_OFFSET - 1,
                CELLSIZE * 5 + 1,
                CELLSIZE * 5 + 1);
        g.setColor(nextFigure.getColor());
        int dx = CELLSIZE * (6 - (nextFigure.getWidth())) / 2;
        int dy = CELLSIZE * (6 - (nextFigure.getHeight())) / 2 - CELLSIZE;
        for (int i = 0; i < 4; i++)
            g.drawRect(
                    H_OFFSET + getDrawWidth() + 1 + dx + (nextFigure.getX(i) - nextFigure.getOffsetX()) * CELLSIZE,
                    V_OFFSET + dy + (nextFigure.getY(i) - nextFigure.getOffsetY()) * CELLSIZE,
                    CELLSIZE,
                    CELLSIZE);
    }

    public void endGame() {
        KeyListener[] keyListeners = getKeyListeners();
        for (KeyListener keyListener : keyListeners) removeKeyListener(keyListener);
        Graphics g = getGraphics();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString(GAME_OVER_STRING, H_OFFSET + CELLSIZE * 2, V_OFFSET + getDrawHeight() / 2);
        g.drawString(GAME_OVER_STRING, H_OFFSET + CELLSIZE * 2 + 2, V_OFFSET + getDrawHeight() / 2 + 2);
        g.setColor(Color.WHITE);
        g.drawString(GAME_OVER_STRING, H_OFFSET + CELLSIZE * 2 + 1, V_OFFSET + getDrawHeight() / 2 + 1);
    }

    private int getDrawWidth() {
        return FIELD_WIDTH * CELLSIZE;
    }

    private int getDrawHeight() {
        return FIELD_HEIGHT * CELLSIZE;
    }

}
