package csc.game.src.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Class that describes the obstacles.
 */
public class Obstacle {

	int x, y, w, h;
	static int baseSpeed;
	boolean visible;
	private int rx, ry, n;
	ImageIcon obs;
	int a, ammo;

	public Obstacle() {
		Random rnd = new Random();
		x = Main.getWidth() + rnd.nextInt(200);
		y = w + rnd.nextInt(Main.getHeight() - w);
		int[] size = { 29, 42, 57, 70, 85 }; // Picturesizes 30x30, 40x40, 60x60
												// 75x75, 90x90.
		// Get a random size
		a = rnd.nextInt(5);
		w = size[a];
		// It's a square
		h = w;
		// Get an image
		check();
		
		baseSpeed = 4;
		visible = true;
		rx = rnd.nextInt(2);
		ry = rnd.nextInt(3);
		n = Math.random() < 0.5 ? -1 : 1;
	}

	/**
	 * Checks the width of the obstacle to determine which image to represent it with.
	 */
	private void check() {
		if (w == 29)
			obs = new ImageIcon(Main.class.getResource("/asteroid1.png"));
		if (w == 42)
			obs = new ImageIcon(Main.class.getResource("/asteroid2.png"));
		if (w == 57)
			obs = new ImageIcon(Main.class.getResource("/asteroid3.png"));
		if (w == 70)
			obs = new ImageIcon(Main.class.getResource("/asteroid4.png"));
		if (w == 85)
			obs = new ImageIcon(Main.class.getResource("/asteroid5.png"));
	}

	public ImageIcon getImage() {
		return obs;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * Gives the obstacle a new position so that it essentially respawns.
	 */
	public void newPosition() {
		Random rnd = new Random();
		x = Main.getWidth() + rnd.nextInt(200);
		y = w + rnd.nextInt(Main.getHeight() - w);
	}
	
	/**
	 * Gives the obstacle a new path and velocity so things won't get too repeating.
	 */
	public void newPath() {
		Random rnd = new Random();
		rx = rnd.nextInt(2);
		ry = rnd.nextInt(3);
	}
		

	/*
	 * Method creates a rectangle for collision detection.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	/*
	 * Method makes the obstacle move by decreasing its x-position and changing its y-position. It also
	 * checks if the object is out of bounds or not.
	 */
	public void move() {
		x -= (baseSpeed + rx);
		y += ry * n;
		if (x < (0 - w) || y < (0 - h) || y > Main.getHeight())
			visible = false;
	}

}