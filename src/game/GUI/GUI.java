package game.GUI;

import game.engine.board.GameBoard;

/**
 * interface used by any user interface, it should be passed to the main
 * game loop and used for callbacks to update the visuals and take input.
 * @author Andrew
 *
 */
public interface GUI {
	public void update(GameBoard gameBoard);
	public Action getNextAction();
}
