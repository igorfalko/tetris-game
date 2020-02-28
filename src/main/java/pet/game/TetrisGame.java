package pet.game;

import pet.game.controller.GameManager;
import pet.game.controller.visual.VisualController;

public class TetrisGame {

    public static void main(String[] args) {
        new GameManager(new VisualController()).startGame();
    }
}