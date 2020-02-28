package pet.game.controller.events;

import pet.game.model.Direction;

public interface GameEventsManager {
    void tryMoveField(Direction direction);
    void tryMoveFigure(Direction direction);
    void tryRotateFigure();
}
