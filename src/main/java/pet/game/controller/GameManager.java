package pet.game.controller;

import pet.game.controller.events.GameEventsManager;
import pet.game.controller.events.GameTimer;
import pet.game.controller.visual.VisualController;
import pet.game.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameManager implements GameEventsManager {
    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 20;
    private long timerdelay = 1000;
    private long timerperiod = 1000;
    private int level = 1;
    private int score = 0;
    private int lines = 0;
    private Figure figure;
    private Figure nextFigure;
    private Field field;
    private VisualController visualController;
    private Timer timer;

    public GameManager(VisualController visualController) {
        this.visualController = visualController;
        this.visualController.init();
        field = new Field(FIELD_WIDTH, FIELD_HEIGHT);
        figure = generateFigure();
        nextFigure = generateFigure();
    }

    public void startGame() {
        visualController.initGameControls(this);
        visualController.setVisible(true);
        drawField();
        timer = new Timer();
        timer.schedule(new GameTimer(this), timerdelay, timerperiod);
    }

    public Figure generateFigure() {
        return FigureFactory.getRandomFigure();
    }

    public void tryMoveFigure(Direction dir) {
        Figure tempFigure = FigureFactory.getCopyOfFigure(figure);
        switch (dir) {
            case DOWN:
                tempFigure.addY(1);
                break;
            case LEFT:
                tempFigure.addX(-1);
                break;
            case RIGHT:
                tempFigure.addX(1);
                break;
        }
        normalizeFigurePosition(tempFigure);
        if (isCorrectPosition(tempFigure)) {
            figure.copyFigure(tempFigure);
            drawField();
        } else {
            if (dir == Direction.DOWN)
                addFigureToField();
        }
    }

    public void tryMoveField(Direction dir) {
        Figure tempFigure = FigureFactory.getCopyOfFigure(figure);
        switch (dir) {
            case LEFT:
                tempFigure.addX(1);
                break;
            case RIGHT:
                tempFigure.addX(-1);
                break;
            default:
                break;
        }
        normalizeFigurePosition(tempFigure);
        if (isCorrectPosition(tempFigure)) {
            moveField(dir);
            drawField();
        }
    }

    private void moveField(Direction dir) {
        Cell tmpCell;
        switch (dir) {
            case LEFT:
                for (int i = 0; i < FIELD_HEIGHT; i++) {
                    tmpCell = field.getCellCopy(0, i);
                    for (int j = 0; j < FIELD_WIDTH - 1; j++) {
                        field.setCellState(j, i, field.getCellCopy(j + 1, i));
                    }
                    field.setCellState(FIELD_WIDTH - 1, i, tmpCell);
                }
                break;
            case RIGHT:
                for (int i = 0; i < FIELD_HEIGHT; i++) {
                    tmpCell = field.getCellCopy(FIELD_WIDTH - 1, i);
                    for (int j = FIELD_WIDTH - 1; j > 0; j--) {
                        field.setCellState(j, i, field.getCellCopy(j - 1, i));
                    }
                    field.setCellState(0, i, tmpCell);
                }
                break;
            default:
                break;
        }
    }

    public void tryRotateFigure() {
        Figure tempFigure = FigureFactory.getCopyOfFigure(figure);
        tempFigure.rotate();
        normalizeFigurePosition(tempFigure);
        if (isCorrectPosition(tempFigure)) {
            figure.copyFigure(tempFigure);
            drawField();
        }
    }

    public boolean isCorrectPosition(Figure figure) {
        boolean goodpos = true;
        for (int i = 0; i < 4; i++) {
            if (figure.getX(i) < 0)
                figure.setX(i, FIELD_WIDTH - 1);
            if (figure.getX(i) == FIELD_WIDTH)
                figure.setX(i, 0);
            goodpos = (goodpos) &&
                    ((figure.getX(i) >= 0) && (figure.getX(i) < FIELD_WIDTH)) &&
                    (figure.getY(i) < FIELD_HEIGHT) &&
                    ((figure.getY(i) < 0) || (field.isCellEmpty(figure.getX(i), figure.getY(i))));
        }
        return goodpos;
    }

    public void removeRows() {
        java.util.List<Integer> rows = new ArrayList<>();
        int i = FIELD_HEIGHT - 1;
        while (i >= 0) {
            boolean full = true;
            for (int j = 0; j < FIELD_WIDTH; j++)
                full = (full) && (!field.isCellEmpty(j, i));
            if (full)
                rows.add(i);
            i--;
        }
        if (!rows.isEmpty()) {
            clearRows(rows);
            drawField();
        }
    }

    public void clearRows(List<Integer> rows) {
        int count = 0;
        for (Integer row : rows) {
            for (int i = row + count; i > 0; i--)
                for (int j = 0; j < FIELD_WIDTH; j++) {
                    field.setCellState(j, i, field.getCellCopy(j, i - 1));
                }
            for (int j = 0; j < FIELD_WIDTH; j++) {
                field.setCellState(j,0,true, Color.BLACK);
            }
            count++;
        }
        lines += count;
        int k = 10;
        while (count > 0) {
            score += k;
            k *= 2;
            count--;
        }
        tryLevelUp();
    }

    private void tryLevelUp() {
        if (score >= level * 100) {
            timerperiod = 1000 / level;
            timerdelay = 1000 / level;
            timer.cancel();
            timer = new Timer();
            timer.schedule(new GameTimer(this), timerdelay, timerperiod);
            level++;
        }
    }

    public void addFigureToField() {
        for (int i = 0; i < 4; i++) {
            field.setCellState(figure.getX(i), figure.getY(i),false, figure.getColor());
        }
        if (isCorrectPosition(nextFigure)) {
            figure.copyFigure(nextFigure);
            nextFigure.init();
            removeRows();
            drawField();
        } else {
            timer.cancel();
            visualController.endGame();
        }
    }

    public void normalizeFigurePosition(Figure figure) {
        for (int i = 0; i < 4; i++) {
            figure.setX(i, (figure.getX(i) + FIELD_WIDTH) % FIELD_WIDTH);
        }
        figure.setOffsetX((figure.getOffsetX() + FIELD_WIDTH) % FIELD_WIDTH);
    }

    private void drawField() {
        String status = String.format("Level: %1$d%nScore: %2$d%nLines: %3$d", level, score, lines);
        visualController.drawField(figure.getCopy(), nextFigure.getCopy(), field.getCopy(), status);
    }

}
