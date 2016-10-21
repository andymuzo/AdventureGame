package game;

import game.engine.GameEngine;

public class Launch {

	public static void main(String[] args) {
		// This is where the program first launches to,
		// we only use this as a way of starting up the program.
		
		// At first this will launch the first level directly but
		// as we progress it will launch options and menus instead
		
		// ***Launch Program Here***
		System.out.println("start of game");
		new GameEngine();
		System.out.println("exiting game");
	}

}
