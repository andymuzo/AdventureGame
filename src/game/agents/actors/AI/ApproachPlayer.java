package game.agents.actors.AI;

import game.GUI.Action;
import game.agents.actors.Actor;
import game.engine.board.BoardTools;
import game.engine.board.GameBoard;
import game.engine.board.TileType;

public class ApproachPlayer implements AI {

	@Override
	public void update(Actor actor, GameBoard gameBoard) {
		// keep it in the one room
		TileType type = gameBoard.getRoom(actor.getRoomNumber()).getTileAtCoords(actor.getCoords()).getTileType();
		if (type == TileType.ENTRANCE_DOOR) {
			doAction(actor, gameBoard, Action.RIGHT);
		} else if (type == TileType.EXIT_DOOR) {
			doAction(actor, gameBoard, Action.LEFT);
		}

		// use the board tools to work out the direction to the player
		BoardTools tools = gameBoard.getTools();
		doAction(actor,
				gameBoard,
				tools.getDirectionFromAtoB(actor.getCoords(), gameBoard.getPlayer().getCoords()));
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
