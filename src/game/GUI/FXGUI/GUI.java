package game.GUI.FXGUI;

import game.engine.board.GameBoard;

public interface GUI {

	/**
	 * called whenever there is new information to update (e.g. player or
	 * enemy moves, takes damage etc.)
	 */
	void update(GameBoard gameBoard);

}