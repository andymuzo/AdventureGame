package game.GUI.FXGUI;

import game.agents.actors.Actor;
import game.agents.actors.ActorID;
import game.engine.board.GameBoard;
import game.engine.board.Room;

public class AsciiRenderer {

	/**
	 * This should be called every time the game board needs drawing on screen
	 */

	public String getBoardAsString(GameBoard gameBoard) {

		// create a 2d array of chars for the room tiles
		// get the right room
		Room room = gameBoard.getPlayerRoom();

		// create the array of the right size
		int height = room.getHeight();
		int width = room.getWidth();
		char[][] asciiRoom = new char[width][height];

		// populate it with the tile icons

		/* NOTE: the game screen coords are like this:
		 * X 0 1 2 3 4 5
		 * 0 - - - - - -
		 * 1 - - - - - -
		 * 2 - - - - - -
		 * 3 - - - - - -
		 * 4 - - - - - -
		 * 5 - - - - - -
		 */

		// It really bugs me that I have to put the x and y coords the wrong way around here
		// so that getTileAtCoords returns the correct tile! Not sure there's much to be done about it though.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				switch (room.getTileAtCoords(x, y).getTileType()) {
					case FLOOR:
						asciiRoom[x][y] = '-';
						break;
					case WALL:
						asciiRoom[x][y] = 'X';
						break;
					case EMPTY:
						asciiRoom[x][y] = ' ';
						break;
					case ENTRANCE_DOOR:
					case EXIT_DOOR:
						asciiRoom[x][y] = '|';
						break;
					default:
						break;
				}
			}
		}

		// draw the actors in their correct positions in the array
		// draw items

		drawActors(asciiRoom, gameBoard);

		// draw player
		asciiRoom[gameBoard.getPlayer().getXPos()][gameBoard.getPlayer().getYPos()] = '@';

		// convert to a single string and return
		return charArrayToString(asciiRoom);
	}

	private String charArrayToString(char[][] asciiArray) {
		StringBuilder builder = new StringBuilder();
	    for(int i = 0; i < asciiArray[0].length; i++)
	    {
	        for(int j = 0; j < asciiArray.length; j++)
	        {
	            builder.append(asciiArray[j][i]);
	            builder.append(' ');
	        }
	        builder.append("\n");
	    }
	    return builder.toString();
	}

	private void drawActors(char[][] asciiRoom, GameBoard gameBoard) {
		for (Actor actor : gameBoard.getActors()) {
			if (actor.getRoomNumber() == gameBoard.getPlayer().getRoomNumber()) {
				// draw it!
				asciiRoom[actor.getCoords()[0]][actor.getCoords()[1]] = getActorChar(actor.getActorID());
			}
		}
	}

	private char getActorChar(ActorID actorID) {
		switch (actorID) {
		case SHEEP:
			return 's';
		case COW:
			return 'c';
		default:
			return 'e';
		}
	}
}
