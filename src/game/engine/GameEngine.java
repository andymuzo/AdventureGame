package game.engine;

import game.engine.board.GameBoard;
import game.engine.input.Input;
import game.factories.BoardFactory;
import game.rendering.AsciiRenderer;
import game.rendering.Renderer;

/**
 * This class runs in it's constructor the main game loop
 * @author rogersa
 *
 */
public class GameEngine {
	BoardFactory boardFactory;
	GameBoard gameBoard;
	Renderer renderer;
	Input input;
	/**
	 * use this one!
	 */
	public GameEngine() {
		System.out.println("in game engine");
		
		// setup the game
		renderer = new AsciiRenderer();
		input = new Input();
		
		// create and apply settings
		
		// get the game board
		boardFactory = new BoardFactory();
		gameBoard = boardFactory.getNewGameBoard();
		
		// start the game loop
		runGame(gameBoard);
	}
	
	/**
	 * This method is the main game loop.
	 * @param gameBoard
	 */
	private void runGame(GameBoard gameBoard) {
		// draw the inital game state
		renderer.render(gameBoard);
		
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
			while (!gameBoard.getPlayer().move(gameBoard, input.getNextAction()));			
			renderer.render(gameBoard);
			// here is where all the enemy actions will occour
			
		}
	}
}