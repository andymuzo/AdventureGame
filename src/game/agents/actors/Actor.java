package game.agents.actors;

import game.engine.board.GameBoard;

public interface Actor {
	public void update(GameBoard gameBoard);
	public void hit();
	public int getActorLevel();
	public int[] getCoords();
	public int getRoomNumber();	
	public ActorID getActorID();
}
