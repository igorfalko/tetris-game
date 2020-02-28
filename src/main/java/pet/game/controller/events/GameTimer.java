package pet.game.controller.events;

import pet.game.model.Direction;

import java.util.TimerTask;

public class GameTimer extends TimerTask {
    private final GameEventsManager gameEventsManager;

    public GameTimer(GameEventsManager gameEventsManager) {
        this.gameEventsManager = gameEventsManager;
    }

    @Override
    public void run() {
        gameEventsManager.tryMoveFigure(Direction.DOWN);
    }
}
