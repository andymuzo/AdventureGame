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
	ActorFactory actorFactory;
	/**
	 * create a blank factory.
	 */
	public BoardFactory() {
		// empty constructor to start with. Settings should be passed as a Settings class.
		// TODO: add a Settings class and a method to receive them here
		 rand = new Random(System.currentTimeMillis());
		 actorFactory = new ActorFactory();
	}

	/**
	 * uses the current Settings to create a new game board from scratch.
	 * @return the new GameBoard with the first 3 rooms generated
	 */
	public GameBoard getNewGameBoard() {
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(getNewFirstRoom());

		// create a player at coords 1,1 in room 0, at level 1
		Player player = new Player(2, 3, 0, 1);
		GameBoard gameBoard = new GameBoard(this, rooms, player);
		addNewRoom(gameBoard);
		addNewRoom(gameBoard);


		return gameBoard;
	}

	/**
	 * appends a new room of appropriate level to the end of the rooms list
	 * fills it with actors of the appropriate level
	 * @param rooms
	 * @param playerLevel
	 */
	public void addNewRoom(GameBoard gameBoard) {
		Room room = getNewRandomRoom();
		gameBoard.getRooms().add(room);
		// add the actors to the gameBoard
		int level = gameBoard.getPlayer().getLevel();
		int roomNumber = gameBoard.getRooms().size() - 1;
		gameBoard.addActors(actorFactory.getActorsForRoom(room, level, roomNumber));
	}

	//******************************************************************
	// private factory helper methods here

	/**
	 * returns a default first room with a door on the right and none on the left
	 * @return
	 */
	private Room getNewFirstRoom() {
		int roomHeight = 7;
		int roomWidth = 7;
		int entranceRow = -1; // use -1 as a value that wont break the method but will never come up
		int exitRow = 3;
		return getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
	}

	private Room getNewRandomRoom() {
		Room room;
//		switch (0) { // testing top room
		switch (rand.nextInt(7) + 1) {
		case 0:
			// use this one for testing, won't be called in the game
			room = getRandomSwissCheeseRoom();
			break;
		case 1:
			room = getRandomBasicSmallRoom();
			break;
		case 2:
			room = getRandomBasicLargeRoom();
			break;
		case 3:
			room = getRandomCorridor();
			break;
		case 4:
			room = getRandomDonutRoom();
			break;
		case 5:
			room = getRandomLRoom();
			break;
		case 6:
			room = getRandomChunkedRoom();
			break;
		case 7:
			room = getRandomSwissCheeseRoom();
			break;
		default:
			room = getRandomBasicSmallRoom();
			break;
		}
		return room;
	}

	/**
	 * calls the getNewBasicRoom method with randomised inputs
	 * @return
	 */
	private Room getRandomBasicSmallRoom() {
		int minRoomSpan = 4;
		int roomHeight = rand.nextInt(5) + minRoomSpan;
		int roomWidth = rand.nextInt(5) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		return getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
	}

	/**
	 * calls the getNewBasicRoom method with randomised inputs
	 * @return
	 */
	private Room getRandomBasicLargeRoom() {
		int minRoomSpan = 6;
		int roomHeight = rand.nextInt(8) + minRoomSpan;
		int roomWidth = rand.nextInt(8) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		return getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
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
	private Room getNewBasicRoom(int height, int width, int entranceRow, int exitRow) {
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
	 * Create a corridor room type
	 * @param isHorizontal vertical corridor if false, will randomly have entrance at top or bot
	 * @param length
	 * @return
	 */
	private Room getRandomCorridor() {
		boolean isHorizontal = rand.nextBoolean();
		boolean isTopToBot = rand.nextBoolean();
		int roomHeight = isHorizontal ? 3 : rand.nextInt(12) + 5;
		int roomWidth = isHorizontal ? rand.nextInt(12) + 5 : 3;
		int entranceRow = isHorizontal ? 1 : (isTopToBot ? 1 : roomHeight - 2);
		int exitRow = isHorizontal ? 1 : (isTopToBot ? roomHeight - 2 : 1);
		return getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
	}

	/**
	 * a large room with a large central pillar or void
	 * @return
	 */
	private Room getRandomDonutRoom() {
		// get a large room
		int minRoomSpan = 7;
		int roomHeight = rand.nextInt(7) + minRoomSpan;
		int roomWidth = rand.nextInt(7) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		Room room = getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
		// add the pillar
		// min width of pillar is 3 tiles
		// min top position is (2, 2)
		int[] topLeft = {rand.nextInt(roomWidth - 6) + 2, rand.nextInt(roomHeight - 6) + 2};
		int[] size = {rand.nextInt(roomWidth - topLeft[0] - 4) + 3, rand.nextInt(roomHeight - topLeft[1] - 4) + 3};
		addPillarToRoom(room, topLeft, size);
		return room;
	}

	private Room getRandomSwissCheeseRoom() {
		// get a large room
				int minRoomSpan = 9;
				int roomHeight = rand.nextInt(6) + minRoomSpan;
				int roomWidth = rand.nextInt(6) + minRoomSpan;
				int entranceRow = rand.nextInt(roomHeight - 2) + 1;
				int exitRow = rand.nextInt(roomHeight - 2) + 1;
				Room room = getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
				// add the pillar
				// min width of pillar is 3 tiles
				// min top position is (2, 2)
				int[] topLeft = {rand.nextInt(roomWidth - 6) + 2, rand.nextInt(roomHeight - 6) + 2};
				int[] size = {rand.nextInt(roomWidth - topLeft[0] - 4) + 3, rand.nextInt(roomHeight - topLeft[1] - 4) + 3};
				addPillarToRoom(room, topLeft, size);
				topLeft = new int[] {rand.nextInt(roomWidth - 6) + 2, rand.nextInt(roomHeight - 6) + 2};
				size = new int[] {rand.nextInt(roomWidth - topLeft[0] - 4) + 3, rand.nextInt(roomHeight - topLeft[1] - 4) + 3};
				addPillarToRoom(room, topLeft, size);
				topLeft = new int[] {rand.nextInt(roomWidth - 6) + 2, rand.nextInt(roomHeight - 6) + 2};
				size = new int[] {rand.nextInt(roomWidth - topLeft[0] - 4) + 3, rand.nextInt(roomHeight - topLeft[1] - 4) + 3};
				addPillarToRoom(room, topLeft, size);
				return room;
	}

	private void addPillarToRoom(Room room, int[] topLeft, int[] size) {
		// draw the top wall of the pillar
		for (int i = 0; i < size[0]; i++) {
			room.setTileAtCoords(new Tile(TileType.WALL), topLeft[0] + i, topLeft[1]);
		}

		// draw the middle walls
		for (int i = 1; i < size[1] - 1; i++) {
			room.setTileAtCoords(new Tile(TileType.WALL), topLeft[0], topLeft[1] + i);
		}
		for (int i = 1; i < size[1] - 1; i++) {
			room.setTileAtCoords(new Tile(TileType.WALL), topLeft[0] + size[0] - 1, topLeft[1] + i);
		}

		// draw the bottom wall of the pillar
		for (int i = 0; i < size[0]; i++) {
			room.setTileAtCoords(new Tile(TileType.WALL), topLeft[0] + i, topLeft[1] + size[1] - 1);
		}

		// draw the void
		if (size[0] > 2 && size[1] > 2) {
			// requires a middle void
			for (int i = 1; i < size[0] - 1; i++) {
				for (int j = 1; j < size[1] - 1; j++) {
					room.setTileAtCoords(new Tile(TileType.EMPTY), topLeft[0] + i, topLeft[1] + j);
				}
			}
		}
	}

	private Room getRandomLRoom() {
		// get a random size room
		int minRoomSpan = 5;
		int roomHeight = rand.nextInt(10) + minRoomSpan;
		int roomWidth = rand.nextInt(10) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		Room room = getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
		// remove a chunk
		removeCornerFromRoom(room);
		return room;
	}

	private void removeCornerFromRoom(Room room) {
		// figure out which corner to take
		// toss of a coin for left or right
		boolean isLeft = rand.nextBoolean();
		if (isLeft) {
			// now check for door position, take the chunk from the bigger side
			if (room.getEntranceCoords()[1] > (room.getHeight() / 2)) {
				// take top left
				int[] topLeft = {0, 0};
				int[] botRight = {rand.nextInt(room.getWidth() - 3) + 2, (room.getEntranceCoords()[1] - 1 - rand.nextInt(room.getEntranceCoords()[1] - 2))};
				// draw walls
				addTileTypeToArea(room, TileType.WALL, topLeft, botRight);
				addTileTypeToArea(room, TileType.EMPTY, topLeft, new int[] {botRight[0] - 1, botRight[1] -1});
			} else {
				// take bot left
				int[] topLeft = {0, (room.getHeight() - rand.nextInt(room.getHeight() - room.getEntranceCoords()[1] - 2) - 2)};
				int[] botRight = {rand.nextInt(room.getWidth() - 3) + 2, room.getHeight()};
				// draw walls
				addTileTypeToArea(room, TileType.WALL, topLeft, botRight);
				addTileTypeToArea(room, TileType.EMPTY, new int[] {topLeft[0], topLeft[1] + 1}, new int[] {botRight[0] - 1, botRight[1]});
			}
		} else {
			// now check for door position, take the chunk from the bigger side
			if (room.getExitCoords()[1] > (room.getHeight() / 2)) {
				// take top right
				int[] topLeft = { rand.nextInt(room.getWidth() - 3) + 2, 0 };
				int[] botRight = { room.getWidth(),
						(room.getExitCoords()[1] - 1 - rand.nextInt(room.getExitCoords()[1] - 2)) };
				// draw walls
				addTileTypeToArea(room, TileType.WALL, topLeft, botRight);
				addTileTypeToArea(room, TileType.EMPTY, new int[] { topLeft[0] + 1, topLeft[1] }, new int[] { botRight[0], botRight[1] - 1 });
			} else {
				// take bot right
				int[] topLeft = { rand.nextInt(room.getWidth() - 3) + 2,
						(room.getHeight() - rand.nextInt(room.getHeight() - room.getExitCoords()[1] - 2) - 2) };
				int[] botRight = { room.getWidth(), room.getHeight() };
				// draw walls
				addTileTypeToArea(room, TileType.WALL, topLeft, botRight);
				addTileTypeToArea(room, TileType.EMPTY, new int[] { topLeft[0] + 1, topLeft[1] + 1 },
						botRight);
			}
		}
	}

	private Room getRandomChunkedRoom() {
		// get a large room
		int minRoomSpan = 7;
		int roomHeight = rand.nextInt(7) + minRoomSpan;
		int roomWidth = rand.nextInt(7) + minRoomSpan;
		int entranceRow = rand.nextInt(roomHeight - 2) + 1;
		int exitRow = rand.nextInt(roomHeight - 2) + 1;
		Room room = getNewBasicRoom(roomHeight, roomWidth, entranceRow, exitRow);
		// add the pillar
		// min width of pillar is 3 tiles
		// min top position is (2, 2)
		int[] topLeft = { rand.nextInt(roomWidth - 6) + 2, rand.nextInt(roomHeight - 6) + 2 };
		int[] size = { rand.nextInt(roomWidth - topLeft[0] - 4) + 3, rand.nextInt(roomHeight - topLeft[1] - 4) + 3 };
		addPillarToRoom(room, topLeft, size);
		removeCornerFromRoom(room);
		return room;
	}

	/**
	 * use this for adding a rectangular area (or line) of a given tile type to a room
	 * @param room
	 * @param type
	 * @param topLeft
	 * @param botRight
	 */
	private void addTileTypeToArea(Room room, TileType type, int[] topLeft, int[] botRight) {
		for (int i = 0; i < botRight[0] - topLeft[0]; i++) {
			for (int j = 0; j < botRight[1] - topLeft[1]; j++) {
				room.setTileAtCoords(new Tile(type), topLeft[0] + i, topLeft[1] + j);
			}
		}
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
