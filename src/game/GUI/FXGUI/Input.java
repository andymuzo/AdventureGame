package game.GUI.FXGUI;

import java.util.Scanner;

import game.GUI.Action;

public class Input {
	Scanner scan;

	public Input() {
		scan = new Scanner(System.in);
	}

	/**
	 * use this method to get the next possible player action. It will effectively
	 * pause the game until a valid input is made (although it won't check if the action
	 * this input is attached to is possible).
	 * @return
	 */
	public Action getNextAction() {
		Action action = Action.WAIT; //sets a default value for the action
		boolean isInputValid = false;
		while (!isInputValid) {
			switch (scan.next()) {
			case "w":
			case "W":
				action = Action.UP;
				isInputValid = true;
				break;
			case "a":
			case "A":
				action = Action.LEFT;
				isInputValid = true;
				break;
			case "s":
			case "S":
				action = Action.DOWN;
				isInputValid = true;
				break;
			case "d":
			case "D":
				action = Action.RIGHT;
				isInputValid = true;
				break;
			}
		}
		return action;
	}
}
