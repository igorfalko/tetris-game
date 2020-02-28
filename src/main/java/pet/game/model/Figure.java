package pet.game.model;

import java.awt.*;
import java.util.Random;

public class Figure {
    private int[][] cells = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
    private int num;
    private int angle;
    private int offsetX;
    private int offsetY;
    private Color color;
    private int minx;
    private int maxx;
    private int miny;
    private int maxy;
    public Figure() {
        init();
    }

    public Figure(Figure figure) {
        init();
        this.copyFigure(figure);
    }

    public int getX(int i) {
        return cells[i][0];
    }

    public int getY(int i) {
        return cells[i][1];
    }

    public void setX(int i, int value) {
        cells[i][0] = value;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return maxx - minx;
    }

    public int getHeight() {
        return maxy - miny;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void init() {
        num = new Random().nextInt(7);
        color = Figures.getColor(num);
        offsetX = 4;
        offsetY = 0;
        angle = 0;
        minx = 0;
        maxx = 0;
        miny = 0;
        maxy = 0;
        for (int i = 0; i < 4; i++) {
            cells[i][0] = Figures.getFigure(num, angle)[i][0] + offsetX;
            cells[i][1] = Figures.getFigure(num, angle)[i][1] + offsetY;
            if (cells[i][0] - offsetX < minx)
                minx = cells[i][0] - offsetX;
            if (cells[i][0] - offsetX > maxx)
                maxx = cells[i][0] - offsetX;
            if (cells[i][1] - offsetY < miny)
                miny = cells[i][1] - offsetY;
            if (cells[i][1] - offsetY > maxy)
                maxy = cells[i][1] - offsetY;
        }
    }

    public void rotate() {
        angle++;
        if (angle == 4)
            angle = 0;
        for (int i = 0; i < 4; i++) {
            cells[i][0] = Figures.getFigure(num, angle)[i][0] + offsetX;
            cells[i][1] = Figures.getFigure(num, angle)[i][1] + offsetY;
        }

    }

    public void addX(int dx) {
        offsetX += dx;
        for (int i = 0; i < 4; i++) {
            cells[i][0] = cells[i][0] + dx;
        }
    }

    public void addY(int dy) {
        offsetY += dy;
        for (int i = 0; i < 4; i++) {
            cells[i][1] = cells[i][1] + dy;
        }
    }

    public void copyFigure(Figure source) {
        for (int i = 0; i < 4; i++) {
            this.cells[i][0] = source.cells[i][0];
            this.cells[i][1] = source.cells[i][1];
        }
        this.num = source.num;
        this.angle = source.angle;
        this.offsetX = source.offsetX;
        this.offsetY = source.offsetY;
        this.color = source.color;
        this.maxx = source.maxx;
        this.minx = source.minx;
        this.maxy = source.maxy;
        this.miny = source.miny;
    }

    public Figure getCopy() {
        return new Figure(this);
    }
}
