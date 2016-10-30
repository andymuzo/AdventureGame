package game.agents.actors.AI;


import java.util.Random;

import game.GUI.Action;
import game.agents.actors.Actor;
import game.engine.board.GameBoard;
import game.engine.board.TileType;

public class WanderingAimlessly implements AI {
	Random rand;

	public WanderingAimlessly() {
		rand = new Random(System.currentTimeMillis());
	}

	@Override
	public void update(Actor actor, GameBoard gameBoard) {
		// this AI just chooses a random direction and moves that way
		// if it can. If it can't it just waits.

		// keep it in the one room
		TileType type = gameBoard.getRoom(actor.getRoomNumber()).getTileAtCoords(actor.getCoords()).getTileType();
		if (type == TileType.ENTRANCE_DOOR) {
			doAction(actor, gameBoard, Action.RIGHT);
		} else if (type == TileType.EXIT_DOOR) {
			doAction(actor, gameBoard, Action.LEFT);
		}

		switch (rand.nextInt(4)) {
		case 0:
			doAction(actor, gameBoard, Action.UP);
			break;
		case 1:
			doAction(actor, gameBoard, Action.DOWN);
			break;
		case 2:
			doAction(actor, gameBoard, Action.LEFT);
			break;
		case 3:
			doAction(actor, gameBoard, Action.RIGHT);
			break;
		default:
			break;
		}
	}

	/**
	 * this is where the AI performs the actions it selected above
	 * @param actor
	 * @param gameBoard
	 * @param action
	 * @return
	 */
	private boolean doAction(Actor actor, GameBoard gameBoard, Action action) {
		boolean isSuccessful = false;
		switch (action) {
		case UP:
			if (gameBoard.isCoordsPassable(new int[] {actor.getCoords()[0], actor.getCoords()[1] - 1}, actor.getRoomNumber())) {
				actor.getCoords()[1]--;
				isSuccessful = true;
			}
			break;
		case DOWN:
			if (gameBoard.isCoordsPassable(new int[] {actor.getCoords()[0], actor.getCoords()[1] + 1}, actor.getRoomNumber())) {
				actor.getCoords()[1]++;
				isSuccessful = true;
			}
			break;
		case LEFT:
			// TODO: add code for changing room, at the moment it will crash if the sheep passes through a door!
			if (gameBoard.isCoordsPassable(new int[] {actor.getCoords()[0] - 1, actor.getCoords()[1]}, actor.getRoomNumber())) {
				actor.getCoords()[0]--;
				isSuccessful = true;
			}
			break;
		case RIGHT:
			// TODO: add code for changing room, at the moment it will crash if the sheep passes through a door!
			if (gameBoard.isCoordsPassable(new int[] {actor.getCoords()[0] + 1, actor.getCoords()[1]}, actor.getRoomNumber())) {
				actor.getCoords()[0]++;
				isSuccessful = true;
			}
			break;
		default:
			// do nothing
			break;
		}

		return isSuccessful; // returns true if the action was possible
	}
}
