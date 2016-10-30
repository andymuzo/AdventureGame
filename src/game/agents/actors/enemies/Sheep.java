package game.agents.actors.enemies;

import game.agents.actors.Actor;
import game.agents.actors.ActorID;
import game.agents.actors.AI.AI;
import game.agents.actors.AI.WanderingAimlessly;
import game.engine.board.GameBoard;

public class Sheep implements Actor {

	int[] coords;
	int roomNumber;
	int health;
	AI ai;
	ActorID actorID;
	int actorLevel;

	public Sheep(int[] coords, int roomNumber) {
		// set at the start
		this.coords = coords;
		this.roomNumber = roomNumber;
		// Set these depending on the actor created
		health = 1;
		actorID = ActorID.SHEEP;
		actorLevel = 0; // negative for friendly combat unit, 0 for neutral, 1 for easy
		ai = new WanderingAimlessly();
	}

	@Override
	public void update(GameBoard gameBoard) {
		/**
		 *  here is where you would change between different states by writing:
		 *  ai = new SomeAIStateClass();
		 *
		 *  In this case we just want it to wander aimlessly so it's fine to leave
		 *  it as a single state.
		 */
		ai.update(this, gameBoard);
	}

	@Override
	public void hit() {
		// This is where we will eventually apply armour and attack types etc.
		// blank for now until we implement combat
	}

	// The following will be the same in every Actor **********************************

	@Override
	public int getActorLevel() {
		return actorLevel;
	}

	@Override
	public int[] getCoords() {
		return coords;
	}

	@Override
	public int getRoomNumber() {
		return roomNumber;
	}

	@Override
	public ActorID getActorID() {
		return actorID;
	}

}
