package game.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	Random rand; // useful for getting random numbers throughout the class

	/**
	 * create a blank factory.
	 */
	public BoardFactory() {
		// empty constructor to start with. Settings should be passed as a Settings class.
		// TODO: add a Settings class and a method to recieve them here
		 rand = new Random(System.currentTimeMillis());
	}

	/**
	 * uses the current Settings to create a new game board from scratch.
	 * @return the new GameBoard with the first 3 rooms generated
	 */
	public GameBoard getNewGameBoard() {

		int level = 1;

		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(getNewFirstRoom());
		rooms.add(getNewRandomRoom(level));
		rooms.add(getNewRandomRoom(level));

		// create a player at coords 1,1 in room 0, at level 1
		Player player = new Player(2, 3, 0, 1);

		return new GameBoard(this, rooms, player);
	}

	/**
	 * appends a new room of appropriate level to the end of the rooms list
	 * @param rooms
	 * @param playerLevel
	 */
	public void addNewRoom(List<Room> rooms, int playerLevel) {
		rooms.add(getNewRandomRoom(playerLevel));
	}

	//******************************************************************
	// private factory helper methods here

	/**
	 * returns a default first room with a door on the right and none on the left
	 * @return
	 */
	private Room getNewFirstRoom() {
		int level = 1;
		int roomHeight = 7;
		int roomWidth = 7;
		int entranceRow = -1; // use -1 as a value that wont break the method but will never come up
		int exitRow = 3;
		return getNewBasicRoom(level, roomHeight, roomWidth, entranceRow, exitRow);
	}

	private Room getNewRandomRoom(int level) {
		// for now just returns rectangular rooms of random size. Could be developed to call a range of
		// different getRoom functions of various shapes for variety.
		int minRoomSpan = 3;

		int roomHeight = rand.nextInt(11) + minRoomSpan;
		int roomWidth = rand.nextInt(11) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		return getNewBasicRoom(level, roomHeight, roomWidth, entranceRow, exitRow);
	}

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

		int[] entranceCoords = {0, entranceRow};
		int[] exitCoords = {width - 1, exitRow};

		return new Room(tiles, entranceCoords, exitCoords);
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
