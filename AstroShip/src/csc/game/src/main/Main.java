package csc.game.src.main;

import javax.swing.*;

/**
 * Class that calls the main method and creates the gui.
 */
public class Main {

	public final static int WIDTH = 800, HEIGHT = 800;

	public static void main(String[] args) {
		JFrame frame = new JFrame("AstroShip Shooter");

		frame.add(new Canvas());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	/**
	 * Returns width of frame.
	 */
	public static int getWidth() {
		return WIDTH;
	}

	/**
	 * Returns height of frame.
	 */
	public static int getHeight() {
		return HEIGHT;
	}
}
