package pet.game.model;

import java.awt.*;

public class Field {
    private Cell[][] field;

    public Field(int fieldWidth, int fieldHeight) {
        field = new Cell[fieldWidth][fieldHeight];
        for (int i = 0; i < fieldWidth; i++)
            for (int j = 0; j < fieldHeight; j++) {
                field[i][j] = new Cell(true, Color.BLACK);
            }
    }

    public Cell getCellCopy(int x, int y) {
        return new Cell(field[x][y].isEmpty(), field[x][y].getColor());
    }

    public void setCellState(int x, int y, Cell cell) {
        field[x][y].setState(cell.isEmpty(), cell.getColor());
    }

    public void setCellState(int x, int y, boolean isEmpty, Color color) {
        field[x][y].setState(isEmpty, color);
    }

    public Field getCopy() {
        int fieldWidth = field.length;
        int fieldHeight = field[fieldWidth - 1].length;
        Field copy = new Field(fieldWidth, fieldHeight);
        for (int i = 0; i < fieldWidth; i++)
            for (int j = 0; j < fieldHeight; j++) {
                copy.setCellState(i, j, field[i][j]);
            }
        return copy;
    }

    public boolean isCellEmpty(int x, int y) {
        return field[x][y].isEmpty();
    }
    public Color getCellColor(int x, int y) {
        return field[x][y].getColor();
    }

}
