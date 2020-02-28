package pet.game.model;

import java.awt.*;

public class Cell {
    private boolean isEmpty;
    private Color color;

    public Cell(boolean isEmpty, Color color) {
        this.isEmpty = isEmpty;
        this.color = color;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setState(boolean isEmpty, Color color) {
        this.isEmpty = isEmpty;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
