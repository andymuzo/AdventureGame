package game.engine.board;

import java.util.List;


/**
 * container class for all of the tiles, enemies and items within the room.
 * @author rogersa
 *
 */
public class Room {
	private List<List<Tile>> tiles;
	private int[] entranceCoords;
	private int[] exitCoords;

	public Room(List<List<Tile>> tiles, int[] entranceCoords, int[] exitCoords) {
		this.tiles = tiles;
		this.entranceCoords = entranceCoords;
		this.exitCoords = exitCoords;
	}

	/**
	 * Try to avoid using this method for getting the tiles and manipulating them elsewhere,
	 * much better to put all the Tile manipulation methods in this class so they can be
	 * used throughout by many classes.
	 * @return
	 */
	public List<List<Tile>> getTiles() {
		return tiles;
	}

	/**
	 * height is the size of the max y coordinate + 1
	 * @return
	 */
	public int getHeight() {
		return tiles.size();
	}

	/**
	 * width is the size of the max x coordinate + 1
	 * @return
	 */
	public int getWidth() {
		return tiles.get(0).size();
	}

	/**
	 * returns the tile at the given coords, useful for rendering.
	 * Use isTileAtCoordsPassable(x,y) for collision detection.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTileAtCoords(int x, int y) {
		// remember, outer list is y coords, inner list is x coords
		return tiles.get(y).get(x);
	}

	public void setTileAtCoords(Tile tile, int x, int y) {
		tiles.get(y).set(x, tile);
	}

	/**
	 * This takes care of the collision detection for walls and other impassable
	 * Tiles.
	 * @param xPos x coordinate for testing
	 * @param yPos y coordinate for testing
	 * @return
	 */
	public boolean isTileAtCoordsPassable(int xPos, int yPos) {
		return getTileAtCoords(xPos, yPos).isTilePassable();
	}

	/**
	 * returns the location of the exit door
	 * @return
	 */
	public int[] getExitCoords() {
		return exitCoords;
	}

	/**
	 * returns the location of the entrance door
	 * @return
	 */
	public int[] getEntranceCoords() {
		return entranceCoords;
	}
}
