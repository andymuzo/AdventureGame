package game.agents.actors.AI;

import game.agents.actors.Actor;
import game.engine.board.GameBoard;

public interface AI {
	public void update(Actor actor, GameBoard gameBoard);
}
