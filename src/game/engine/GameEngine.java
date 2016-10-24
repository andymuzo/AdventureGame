package game.engine;

import game.GUI.GUI;
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
	GUI gui;
	/**
	 * use this one!
	 */
	public GameEngine(GUI gui) {
		this.gui = gui;

		System.out.println("in game engine");

		// create and apply settings

		// create the game board
		boardFactory = new BoardFactory();
		gameBoard = boardFactory.getNewGameBoard();
	}

	/**
	 * This method is the main game loop.
	 * @param gameBoard
	 */
	public void runGame() {
		// draw the inital game state
		gui.update(gameBoard);

		// main game loop
		boolean gameRunning = true;
		while (gameRunning) {
			// wait for input then update game state
			/* This line waits for an input, if a valid one is given it tries to
			 * perform that action. If the action is not valid then it waits for
			 * the next input, if it is then it performs it and moves on.
			 *
			 * This may need re-writing as more complex input happens.
			 */
			while (!gameBoard.getPlayer().move(gameBoard, gui.getNextAction()));
			gui.update(gameBoard);
			// here is where all the enemy actions will occour

		}
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
}