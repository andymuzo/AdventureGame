package game;

import game.GUI.FXGUI.FXGUI;

public class Launch {

	public static void main(String[] args) {
		// This is where the program first launches to,
		// we only use this as a way of starting up the program.

		// At first this will launch the first level directly but
		// as we progress it will launch options and menus instead

		// ***Launch Program Here***
		// TODO: Remove debug println
		System.out.println("start of game");
		// create the GUI (the whole game runs from inside it)
		// when new interfaces are made just change the below
		// statement to the new type
		new FXGUI();
		System.out.println("exiting game");
	}

}
