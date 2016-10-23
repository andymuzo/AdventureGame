package game.rendering;

import game.engine.board.GameBoard;
import game.engine.board.Room;

public class AsciiRenderer implements Renderer {
	
	/**
	 * This should be called every time the game board needs drawing on screen
	 */
	@Override
	public void render(GameBoard gameBoard) {
		
		// create a 2d array of chars for the room tiles
		// get the right room
		Room room = gameBoard.getPlayerRoom();
		
		// create the array of the right size
		int height = room.getHeight();
		int width = room.getWidth();
		char[][] asciiRoom = new char[height][width];
		
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
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				switch (room.getTileAtCoords(x, y).getTileType()) {
					case FLOOR:
						asciiRoom[y][x] = '-';
						break;
					case WALL:
						asciiRoom[y][x] = 'X';
						break;
					case ENTRANCE_DOOR:
					case EXIT_DOOR:
						asciiRoom[y][x] = '|';
						break;
					default:
						break;
				}
			}
		}
		
		// draw the actors in their correct positions in the array
		// draw items
		
		// draw enemies
		
		// draw player
		asciiRoom[gameBoard.getPlayer().getYPos()][gameBoard.getPlayer().getXPos()] = '@';
		
		// convert to a single string
		
		// clear the last frame
		System.out.println("\n\n\n\n\n\n\n\n\n\n");
		// testing
		System.out.println("" + gameBoard.getPlayer().getXPos() + "," + gameBoard.getPlayer().getYPos());
		// print the string
		System.out.println(charArrayToString(asciiRoom));
	}
	
	private String charArrayToString(char[][] asciiArray) {
		StringBuilder builder = new StringBuilder();
	    for(int i = 0; i < asciiArray.length; i++)
	    {
	        for(int j = 0; j < asciiArray[0].length; j++)
	        {
	            builder.append(asciiArray[i][j]);
	            builder.append(' ');
	        }
	        builder.append("\n");
	    }    
	    return builder.toString();
	}

}
