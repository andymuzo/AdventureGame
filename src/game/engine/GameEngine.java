package game.engine;

import game.GUI.Action;
import game.GUI.FXGUI.GUI;
import game.engine.board.GameBoard;
import game.factories.BoardFactory;

/**
 * This class runs in it's constructor the main game loop
 * @author rogersa
 *
 */
public class GameEngine {
	BoardFactory boardFactory;
	GameBoard gameBoard;

	/**
	 * use this one!
	 */
	public GameEngine() {
		// create and apply settings

		// create the game board
		boardFactory = new BoardFactory();
		gameBoard = boardFactory.getNewGameBoard();
	}

	/**
	 * tests if a given action is possible
	 * @param action
	 * @return
	 */
	public boolean isActionPossible(Action action) {
		return gameBoard.getPlayer().isActionPossible(gameBoard, action);
	}

	public boolean runAction(Action action) {
		return gameBoard.getPlayer().runAction(gameBoard, action);
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	/**
	 * when called it will update all the turns of the actors in the player's room and the 2 rooms on either side.
	 */
	public void updateActors(GUI gui) {
		gameBoard.updateActors(gui);
	}
}