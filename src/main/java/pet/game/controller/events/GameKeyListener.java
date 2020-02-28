package pet.game.controller.events;

import pet.game.model.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameKeyListener extends KeyAdapter {
    private final GameEventsManager gameEventsManager;

    public GameKeyListener(GameEventsManager gameEventsManager) {
        this.gameEventsManager = gameEventsManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37:
                if (e.isControlDown())
                    tryMoveField(Direction.LEFT);
                else
                    tryMoveFigure(Direction.LEFT);
                break;
            case 39:
                if (e.isControlDown())
                    tryMoveField(Direction.RIGHT);
                else
                    tryMoveFigure(Direction.RIGHT);
                break;
            case 38:
                tryRotateFigure();
                break;
            case 32:
            case 40:
                tryMoveFigure(Direction.DOWN);
                break;
            default:break;

        }
    }

    public void tryMoveField(Direction direction) {
        gameEventsManager.tryMoveField(direction);
    }

    public void tryMoveFigure(Direction direction) {
        gameEventsManager.tryMoveFigure(direction);
    }

    public void tryRotateFigure() {
        gameEventsManager.tryRotateFigure();
    }
}
