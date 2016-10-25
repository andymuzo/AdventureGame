package game.factories;

import java.util.List;

import game.agents.actors.Actor;
import game.engine.board.Room;

/**
 * a factory class that creates and returns Actor objects. Should be used by the BoardFactory class
 * @author Andrew
 *
 */
public class ActorFactory {

	/**
	 * places actors in the room of a suitable level
	 * @param room
	 * @param level
	 */
	public List<Actor> getActorsForRoom(Room room, int level) {
		// I need the room to be able to find the free tiles and position the actors as I make them
		// I also need to know the room's position number in the list even though it hasn't been added yet...




	}

}
