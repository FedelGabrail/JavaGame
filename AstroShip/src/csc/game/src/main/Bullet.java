package csc.game.src.main;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class that describes bullets.
 */
public class Bullet {

	int x, y, w, h, speed;
	boolean visible;

	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		w = 12;
		h = 5;
		visible = true;
		speed = 4;
	}

	/**
	 * Returns true if the bullet is within bounds.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Method creates a rectangle for collision detection.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	/**
	 * Method makes the bullet move by increasing its x-position and also checks
	 * if it's out of bounds or not.
	 */
	public void move() {
		x += speed;
		if (x > Main.getWidth())
			visible = false;
	}
	
	/**
	 * Method makes the enemy bullet move by decreasing its x-position and also checks
	 * if it's out of bounds or not.
	 */
	public void enemyMove() {
		x -= speed*5/2;
		if (x < 0)
			visible = false;
	}

}