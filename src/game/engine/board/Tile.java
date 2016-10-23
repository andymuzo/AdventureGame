package game.engine.board;

/**
 * This class stores the information about a single tile in the room
 * @author rogersa
 *
 */
public class Tile {
	private TileType type;
	
	/**
	 * create a new tile with the TileType in the argument
	 * @param type
	 */
	public Tile(TileType type) {
		this.type = type;
	}
	
	public TileType getTileType() {
		return type;
	}
	
	public boolean isTilePassable() {
		if (type == TileType.WALL) return false; // add in all of the types that can't be passed here
		else return true;
	}
}
