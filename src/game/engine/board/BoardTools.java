package game.engine.board;

import game.GUI.Action;

/**
 * this class should contain helper methods for calculations (e.g. distance between two
 * coordinates, tests for line of sight etc.)
 * @author Andrew
 *
 */
public class BoardTools {

	/**
	 * returns the distance between the two coords in number of moves to travel between them
	 * @param coordsA
	 * @param coordsB
	 * @return
	 */
	public int getDistanceBetweenCoords(int[] coordsA, int[] coordsB) {
		int[] vector = getVectorBetweenCoords(coordsA, coordsB);
		int distance = Math.abs(vector[0]) + Math.abs(vector[1]);
		return distance;
	}

	/**
	 * returns coordsB - coordsA
	 * @param coordsA
	 * @param coordsB
	 * @return
	 */
	public int[] getVectorBetweenCoords(int[] coordsA, int[] coordsB) {
		return new int[] {coordsB[0] - coordsA[0], coordsB[1] - coordsA[1]};
	}

	/**
	 * returns the as the crow flies direction to move from where point A is to where point B is.
	 * (useful for e.g. enemies moving towards the player)
	 * @param coordsA
	 * @param coordsB
	 * @return
	 */
	public Action getDirectionFromAtoB(int[] coordsA, int[] coordsB) {
		int vector[] = getVectorBetweenCoords(coordsA, coordsB);
		if (Math.abs(vector[0]) >= Math.abs(vector[1])) {
			// horizontal
			if (vector[0] >= 0) {
				return Action.RIGHT;
			} else {
				return Action.LEFT;
			}
		} else {
			// vertical
			if (vector[1] >= 0) {
				return Action.DOWN;
			} else {
				return Action.UP;
			}
		}
	}

	public boolean areCoordsEqual(int[] a, int[] b) {
		return a[0] == b[0] && a[1] == b[1];
	}
}
