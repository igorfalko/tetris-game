package pet.game.model;

public class FigureFactory {

    private FigureFactory() {
    }

    public static Figure getRandomFigure() {
        return new Figure();
    }

    public static Figure getCopyOfFigure(Figure figure) {
        return new Figure(figure);
    }
}
