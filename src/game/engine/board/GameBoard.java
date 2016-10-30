package game.engine.board;

import java.util.ArrayList;
import java.util.List;

import game.GUI.FXGUI.GUI;
import game.agents.actors.Actor;
import game.agents.player.Player;
import game.factories.BoardFactory;

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
	private BoardFactory factory;
	List<Actor> actors;
	BoardTools tools;

	/**
	 * use this constructor for putting a List of rooms into the gameboard
	 * @param rooms
	 * @param player
	 */
	public GameBoard(BoardFactory factory, List<Room> rooms, Player player) {
		this.factory = factory;
		this.setRooms(rooms);
		this.setPlayer(player);
		this.actors = new ArrayList<Actor>();
		this.tools = new BoardTools();
	}

	public void createNextRoom() {
		// check to see if we need to first
		if (player.getRoomPos() > rooms.size() - 2) {
			factory.addNewRoom(this);
		}
	}

	public BoardTools getTools() {
		return tools;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Room getPlayerRoom() {
		return rooms.get(player.getRoomPos());
	}

	public Room getRoom(int roomNumber) {
		return rooms.get(roomNumber);
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

	public void addActors(List<Actor> newActors) {
		actors.addAll(newActors);
	}

	/**
	 * when called it will update all the turns of the actors in the player's room and the 2 rooms on either side.
	 * @param gui Needs this to be able to tell the gui to update the display after each actor's turn
	 */
	public void updateActors(GUI gui) {
		// for now just update actors in the player's room!
		for (Actor actor : actors) {
			if (actor.getRoomNumber() == player.getRoomPos()) {
				actor.update(this);
				gui.update(this);
			}
		}
	}

	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * checks to see if there is the player or an actor in a certain location
	 * @param coords
	 * @param roomNumber
	 * @return
	 */
	public boolean isCoordsOccupied(int[] coords, int roomNumber) {
		boolean isEmpty = true;
		if (player.getRoomPos() == roomNumber && tools.areCoordsEqual(player.getCoords(), coords)) {
			isEmpty = false; // player is there
		} else {
			for (Actor actor : actors) {
				if (actor.getRoomNumber() == roomNumber && tools.areCoordsEqual(actor.getCoords(), coords)) {
					isEmpty = false;
				}
			}
		}
		return isEmpty;
	}

	/**
	 * true if there are no tile or actors at the coords that can't be occupied by an actor
	 * @param coords
	 * @return
	 */
	public boolean isCoordsPassable(int[] coords, int room) {
		boolean isPassable = getRoom(room).isTileAtCoordsPassable(coords);
		boolean isOccupied = !isCoordsOccupied(coords, room);
		return isPassable && !isOccupied;
	}

}