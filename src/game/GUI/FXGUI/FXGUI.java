package game.GUI.FXGUI;

import game.GUI.Action;
import game.GUI.GUI;
import game.engine.GameEngine;
import game.engine.board.GameBoard;

public class FXGUI implements GUI {

	GameEngine gameEngine;
	Input input;
	AsciiRenderer renderer;

	public FXGUI() {
		input = new Input();
		renderer = new AsciiRenderer();
		gameEngine = new GameEngine(this);
		gameEngine.runGame();
	}

	/**
	 * called from the game engine whenever there is new information
	 * to update (e.g. player or enemy moves, takes damage etc.)
	 */
	@Override
	public void update(GameBoard gameBoard) {
		System.out.println(renderer.getBoardAsString(gameBoard));
	}

	/**
	 * use this method to get the next possible player action. It will effectively
	 * pause the game until a valid input is made (although it won't check if the action
	 * this input is attached to is possible).
	 */
	@Override
	public Action getNextAction() {
		return input.getNextAction();
	}
}
