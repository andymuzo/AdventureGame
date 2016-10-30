package game.agents.actors.enemies;

import java.util.ArrayList;
import java.util.List;

import game.agents.actors.Actor;
import game.agents.actors.ActorID;
import game.agents.actors.AI.AI;
import game.agents.actors.AI.ApproachPlayer;
import game.agents.actors.AI.WanderingAimlessly;
import game.engine.board.GameBoard;

public class Rabbit implements Actor {

	int[] coords;
	int roomNumber;
	int health;
	List<AI> ais;
	ActorID actorID;
	int actorLevel;
	int counter;

	public Rabbit(int[] coords, int roomNumber) {
		// set at the start
		this.coords = coords;
		this.roomNumber = roomNumber;
		// Set these depending on the actor created
		health = 1;
		actorID = ActorID.COW;
		actorLevel = 0; // negative for friendly combat unit, 0 for neutral, 1 for easy
		ais = new ArrayList<>();
		ais.add(new ApproachPlayer());
		ais.add(new WanderingAimlessly());
		counter = 0;
	}

	@Override
	public void update(GameBoard gameBoard) {
		if (counter < 4) {
			ais.get(0).update(this, gameBoard);
		} else {
			ais.get(1).update(this, gameBoard);
		}

		counter++;
		if (counter > 10) {
			counter = 0;
		}
	}

	@Override
	public void hit() {
		// TODO Auto-generated method stub

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
