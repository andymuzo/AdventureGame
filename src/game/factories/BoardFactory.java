package game.factories;

import java.util.ArrayList;
import java.util.List;

import game.agents.player.Player;
import game.engine.board.GameBoard;
import game.engine.board.Room;
import game.engine.board.Tile;
import game.engine.board.TileType;

/**
 * contains methods for creating new games and for adding new rooms to the GameBoard
 * Should be passed game settings information before generating board
 * @author rogersa
 *
 */
public class BoardFactory {
	// This could be made as a static class but isn't in the interest of future flexibility
	// i.e. multiple factories with different settings
		
	/**
	 * create a blank factory.
	 */
	public BoardFactory() {
		// empty constructor to start with. Settings should be passed as a Settings class.
	}
	
	// TODO: add a Settings class and a method to recieve them here
	
	/**
	 * uses the current Settings to create a new game board from scratch.
	 * @return the new GameBoard with the first 3 rooms generated
	 */
	public GameBoard getNewGameBoard() {
		/*
		 *  TODO: for now it just generates a single test room with no doors.
		 *  This method should generate the first 3 rooms since we want to have 
		 *  2 rooms either side of the player active
		 */
		
		int level = 1;
		int roomHeight = 10;
		int roomWidth = 10;
		int entranceRow = 4;
		int exitRow = 6;
		
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(getNewBasicRoom(level, roomHeight, roomWidth, entranceRow, exitRow));
		
		// create a player at coords 1,1 in room 0
		Player player = new Player(1, 1, 0);
		
		return new GameBoard(rooms, player);		
	}
	
	//******************************************************************
	// private factory helper methods here
	
	/**
	 * Creates a new room of the appropriate level
	 * @param level this should be the player level, it will be used to set the room difficulty
	 * @param height height of the room in squares
	 * @param width width of the room in squares
	 * @param entranceRow which row should the entrance door be on, should be between 1 and height-1
	 * @param exitRow which row should the exit door be on, should be between 1 and height-1
	 * @return a new Room object
	 */
	private Room getNewBasicRoom(int level, int height, int width, int entranceRow, int exitRow) {
		// simple for now and ignores the player level
		// TODO: create much more interesting rooms!
		
		/* NOTE: contrary to regular coordinate systems, y is the outer list (i.e. the row number)
		 * and x is the inner list (i.e. the column number). This oddness should be abstracted away
		 * using the coordinate system for items and players in their classes, and in the Room class
		 * where requests for the tile at given coordinates takes it into account. The only times it 
		 * needs to be thought about is in making and rendering the rooms.
		 */
		List<List<Tile>> tiles = new ArrayList<>();
		tiles.add(getTopBotLine(width));
		for (int i = 1; i < height - 1; i++) {
			tiles.add(getMidLine(width, i == entranceRow, i == exitRow));
		}
		tiles.add(getTopBotLine(width));
		
		return new Room(tiles);
	}
	
	/**
	 * creates a top or bottom line of tiles (all wall) of the specified length
	 * @param length Must be at least 3
	 * @return
	 */
	private List<Tile> getTopBotLine(int length) {
		List<Tile> line = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			line.add(new Tile(TileType.WALL));
		}
		return line;
	}
	
	/**
	 * creates a line of tiles of the specified length
	 * @param length Must be at least 3
	 * @param entryDoor true if this line contains the room entrance door
	 * @param exitDoor true is this line contains the room exit door
	 * @return
	 */
	private List<Tile> getMidLine(int length, boolean entryDoor, boolean exitDoor) {
		List<Tile> line = new ArrayList<>(length);
		line.add(new Tile(entryDoor ? TileType.ENTRANCE_DOOR : TileType.WALL));
		for (int i = 1; i < length - 1; i++) {
			line.add(new Tile(TileType.FLOOR));
		}
		line.add(new Tile(exitDoor ? TileType.EXIT_DOOR : TileType.WALL));
		return line;
	}
}
