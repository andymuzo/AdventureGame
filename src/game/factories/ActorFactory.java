package game.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.agents.actors.Actor;
import game.agents.actors.enemies.Cow;
import game.agents.actors.enemies.Sheep;
import game.engine.board.Room;

/**
 * a factory class that creates and returns Actor objects. Should be used by the BoardFactory class
 * @author Andrew
 *
 */
public class ActorFactory {

	Random rand;

	public ActorFactory() {
		rand = new Random(System.currentTimeMillis());
	}

	/**
	 * places actors in the room of a suitable level
	 * @param room
	 * @param level
	 */
	public List<Actor> getActorsForRoom(Room room, int level, int roomNumber) {
		List<Actor> actors = new ArrayList<>();
		List<int[]> coords = room.getEmptyFloorTileCoords();

		// for now just place a single sheep in each room :)
		actors.add(new Sheep(coords.get(rand.nextInt(coords.size())), roomNumber));
		actors.add(new Cow(coords.get(rand.nextInt(coords.size())), roomNumber));
		return actors;
	}
}
