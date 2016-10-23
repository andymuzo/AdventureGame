package game.engine.board;

import java.util.List;

import game.agents.player.Player;

/**
 * This class is for the main game board that contains all of the game's rooms, the
 * actors, the items, and the rules of play. It should be passed to each actor in turn
 * to manipulate using the public methods. It is also passed to the renderer to draw it.
 * @author rogersa
 *
 */
public class GameBoard {
	private List<Room> rooms;
	private Player player;
	
	/**
	 * use this constructor for putting a List of rooms into the gameboard
	 * @param rooms
	 * @param player
	 */
	public GameBoard(List<Room> rooms, Player player) {
		this.setRooms(rooms);
		this.setPlayer(player);
	}

	public List<Room> getRooms() {
		return rooms;
	}
	
	public Room getPlayerRoom() {
		return rooms.get(player.getRoomPos());
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
