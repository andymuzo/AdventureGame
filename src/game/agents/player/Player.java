package game.agents.player;

import game.GUI.Action;
import game.engine.board.GameBoard;

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

	/**
	 * create a new player at the specified coordinates in the numbered room
	 * @param xPos
	 * @param yPos
	 * @param roomPos
	 */
	public Player(int xPos, int yPos, int roomPos) {
		this.setXPos(xPos);
		this.setYPos(yPos);
		this.setRoomPos(roomPos);
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
		return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos - 1, yPos);
	}

	/**
	 * tests to see if the move can be made
	 * @param gameBoard
	 * @return
	 */
	private boolean canMoveRight(GameBoard gameBoard) {
		return gameBoard.getPlayerRoom().isTileAtCoordsPassable(xPos + 1, yPos);
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
			xPos--;
			return true;
		} else return false;
	}

	/**
	 * should return true if possible and update position
	 * @return
	 */
	private boolean moveRight(GameBoard gameBoard) {
		// right is positive x
		if (canMoveRight(gameBoard)) {
			xPos++;
			return true;
		} else return false;
	}
}