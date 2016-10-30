package game.agents.actors.enemies;

import game.agents.actors.Actor;
import game.agents.actors.ActorID;
import game.agents.actors.AI.AI;
import game.agents.actors.AI.ApproachPlayer;
import game.agents.actors.AI.WanderingAimlessly;
import game.engine.board.GameBoard;

public class Cow implements Actor {

	int[] coords;
	int roomNumber;
	int health;
	AI ai;
	ActorID actorID;
	int actorLevel;

	public Cow(int[] coords, int roomNumber) {
		// set at the start
		this.coords = coords;
		this.roomNumber = roomNumber;
		// Set these depending on the actor created
		health = 1;
		actorID = ActorID.COW;
		actorLevel = 0; // negative for friendly combat unit, 0 for neutral, 1 for easy
		ai = new ApproachPlayer();
	}


	@Override
	public void update(GameBoard gameBoard) {
		if (gameBoard.getTools().getDistanceBetweenCoords(coords, gameBoard.getPlayer().getCoords()) < 2) {
			ai = new WanderingAimlessly();
		}
		ai.update(this, gameBoard);
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