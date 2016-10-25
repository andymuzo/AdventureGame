package game.agents.player;

import game.GUI.Action;
import game.engine.board.GameBoard;
import game.engine.board.TileType;

/**
 * The class containing everything to do with the Player, including movement,
 * stats, position etc.
 * @author rogersa
 *
 */
public class Player {
	private int xPos;
	private int yPos;
	private int roomPos;
	private int level;

	/**
	 * create a new player at the specified coordinates in the numbered room
	 * @param xPos
	 * @param yPos
	 * @param roomPos
	 */
	public Player(int xPos, int yPos, int roomPos, int level) {
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setRoomPos(roomPos);
		this.setLevel(level);
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public int getRoomPos() {
		return roomPos;
	}

	public void setRoomPos(int roomPos) {
		this.roomPos = roomPos;
	}

	/**
	 * tests to see if the given action is possible
	 * @param gameBoard
	 * @param action
	 * @return true if possible, false if not
	 */
	public boolean isActionPossible(GameBoard gameBoard, Action action) {
		boolean isSuccessful = false;
		switch (action) {
		case UP:
			isSuccessful = canMoveUp(gameBoard);
			break;
		case DOWN:
			isSuccessful = canMoveDown(gameBoard);
			break;
		case LEFT:
			isSuccessful = canMoveLeft(gameBoard);
			break;
		case RIGHT:
			isSuccessful = canMoveRight(gameBoard);
			break;
		case WAIT:
		default:
			isSuccessful = true;
		}
		return isSuccessful;
	}

	/**
	 * Takes an action command and does it if it is able to, if it can't then returns false
	 * @param gameBoard
	 * @param action
	 * @return true if completed, false if unable to
	 */
	public boolean runAction(GameBoard gameBoard, Action action) {
		/* NOTE: the game screen coords are like this:
		 * X 0 1 2 3 4 5
		 * 0 - - - - - -
		 * 1 - - - - - -
		 * 2 - - - - - -
		 * 3 - - - - - -
		 * 4 - - - - - -
		 * 5 - - - - - -
		 */

		boolean isSuccessful = false;
		switch (action) {
		case UP:
			isSuccessful = moveUp(gameBoard);
			break;
		case DOWN:
			isSuccessful = moveDown(gameBoard);
			break;
		case LEFT:
			isSuccessful = moveLeft(gameBoard);
			break;
		case RIGHT:
			isSuccessful = moveRight(gameBoard);
			break;
		case WAIT:
		default:
			isSuccessful = true;
		}
		return isSuccessful;
	}

	/**
	 * tests to see if the move can be made
	 * @param gameBoard
	 * @return
	 */
	private boolean canMoveUp(GameBoard gameBoard) {
		return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos, yPos - 1);
	}

	/**
	 * tests to see if the move can be made
	 * @param gameBoard
	 * @return
	 */
	private boolean canMoveDown(GameBoard gameBoard) {
		return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos, yPos + 1);
	}

	/**
	 * tests to see if the move can be made
	 * @param gameBoard
	 * @return
	 */
	private boolean canMoveLeft(GameBoard gameBoard) {
		// this must include moving to the previous room
		if (gameBoard.getPlayerRoom().getTileAtCoords(xPos, yPos).getTileType() == TileType.ENTRANCE_DOOR) {
			// must be in a doorway
			return true;
		} else {
			// if not then check for movement in room
			return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos - 1, yPos);
		}
	}

	/**
	 * tests to see if the move can be made
	 * @param gameBoard
	 * @return
	 */
	private boolean canMoveRight(GameBoard gameBoard) {
		// this must include moving to the next room
		if (gameBoard.getPlayerRoom().getTileAtCoords(xPos, yPos).getTileType() == TileType.EXIT_DOOR) {
			// must be in a doorway
			return true;
		} else {
			return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos + 1, yPos);
		}
	}

	/**
	 * should return true if possible and update position
	 * @return
	 */
	private boolean moveUp(GameBoard gameBoard) {
		// up is negative y
		if (canMoveUp(gameBoard)) {
			yPos--;
			return true;
		} else return false;
	}

	/**
	 * should return true if possible and update position
	 * @return
	 */
	private boolean moveDown(GameBoard gameBoard) {
		// down is positive y
		if (canMoveDown(gameBoard)) {
			yPos++;
			return true;
		} else return false;
	}

	/**
	 * should return true if possible and update position
	 * @return
	 */
	private boolean moveLeft(GameBoard gameBoard) {
		// left is negative x
		if (canMoveLeft(gameBoard)) {
			// check to see if need to move to the previous room
			if (gameBoard.getPlayerRoom().getTileAtCoords(xPos, yPos).getTileType() == TileType.ENTRANCE_DOOR) {
				// must be in a doorway
				return moveToPreviousRoom(gameBoard);
			} else {
				xPos--;
				return true;
			}
		} else return false;
	}

	/**
	 * should return true if possible and update position
	 * @return
	 */
	private boolean moveRight(GameBoard gameBoard) {
		// right is positive x
		if (canMoveRight(gameBoard)) {
			if (gameBoard.getPlayerRoom().getTileAtCoords(xPos, yPos).getTileType() == TileType.EXIT_DOOR) {
				// must be in a doorway
				return moveToNextRoom(gameBoard);
			} else {
				xPos++;
				return true;
			}
		} else return false;
	}

	/**
	 * if the current room isn't the starting room, move to the doorway of the previous one
	 * NOTE: there shouldn't be an entry door in the first room anyhow!
	 * @param gameBoard
	 * @return
	 */
	private boolean moveToPreviousRoom(GameBoard gameBoard) {
		if (getRoomPos() != 0) {
			// not in starting room
			roomPos--;
			int[] exitCoords = gameBoard.getPlayerRoom().getExitCoords();
			xPos = exitCoords[0];
			yPos = exitCoords[1];
			return true;
		} else return false;
	}

	/**
	 * moves to the next room and creates a new room in the list if there would be less than 2 rooms ahead of player
	 * @param gameBoard
	 * @return
	 */
	private boolean moveToNextRoom(GameBoard gameBoard) {
		roomPos++;
		// trigger the creation of a new room if needed (there should always be 2 rooms generated ahead of the player)
		gameBoard.createNextRoom();

		int[] entranceCoords = gameBoard.getPlayerRoom().getEntranceCoords();
		xPos = entranceCoords[0];
		yPos = entranceCoords[1];
		return true;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

