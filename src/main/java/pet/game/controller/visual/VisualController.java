package pet.game.controller.visual;

import pet.game.controller.events.GameEventsManager;
import pet.game.controller.events.GameKeyListener;
import pet.game.model.Field;
import pet.game.model.Figure;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class VisualController {
    private final GameField gameField = new GameField();
    private JFrame jFrame;

    public void init() {
        jFrame = new JFrame("Tetris");
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setSize(430, 575);

        gameField.setLayout(null);
        jFrame.add(gameField);

        JTextArea textArea = new JTextArea(" Press LEFT, RIGHT, DOWN to move, UP to rotate.\n Try to press Ctrl+LEFT or Ctrl+RIGHT.");
        textArea.setBounds(0, gameField.getBounds().y + gameField.getBounds().height + 2, 300, 50);
        jFrame.add(textArea);

        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initGameControls(GameEventsManager gameEventsManager) {
        gameField.addKeyListener(new GameKeyListener(gameEventsManager));
    }

    public void drawField(Figure figure, Figure nextFigure, Field field, String status) {
        gameField.drawField(figure, nextFigure, field, status);
    }

    public void endGame() {
        gameField.endGame();
    }

    public void setVisible(boolean b) {
        jFrame.setVisible(b);
    }
}
