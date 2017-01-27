package csc.game.src.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Class that contains methods for the player controlled object, the ship.
 */
public class Enemy {
	int x, y, w, h;
	public static int lifeC;
	int speed = 3;
	public static Random rnd;
	int rightSpeed, leftSpeed, upSpeed, downSpeed, life, type;
	Image bllt;
	ImageIcon bullet;
	ImageIcon enmy;
	boolean ready;
	int enemyTimer;
	private int r1, r2;
	

	public Enemy(int n) {
		type = n;
		bullet = new ImageIcon(Main.class.getResource("/enemybullet.png"));
		bllt = bullet.getImage();
		if(type == 1) {
			enmy = new ImageIcon(Main.class.getResource("/enemy1.png"));
			life = 300 + 100*lifeC;
		}
		else if(type == 2) {
			enmy = new ImageIcon(Main.class.getResource("/enemy2.png"));
			life = 300 + 100*lifeC;
		}
		else if(type == 3) {
			enmy = new ImageIcon(Main.class.getResource("/enemy3.png"));
			life = 300 + 150*lifeC;
		}
		w = enmy.getIconWidth();
		h = enmy.getIconHeight();
		x = Main.getWidth();
		y = Main.getHeight()/2-h/2;
		Random rnd = new Random();
		if (type == 2) {
			r1 = rnd.nextInt(350);
			r2 = rnd.nextInt(350);
		}
    }
	
	/**
	 * Returns the ImageIcon for the enemy.
	 */
	public ImageIcon getImage() {
		return enmy;
	}

	/**
	 * Method creates bullets and adds them to an array list.
	 */
	public void fire() {
		int bulletX = x + w/3;
		int bullet2X = x + w/4*3;
		int bullet1Y = y;
		int bullet2Y = y + h;
		int bullet3Y = y + h/2 - 2;
		Bullet b1 = new Bullet(bulletX, bullet1Y);
		Bullet b2 = new Bullet(bulletX, bullet2Y);
		Canvas.enemyBullets.add(b1);
		Canvas.enemyBullets.add(b2);
		if(type == 2) {
			Bullet b3 = new Bullet(bullet2X, bullet3Y);
			Canvas.enemyBullets.add(b3);
		}
		if(type == 3) {
			Bullet b3 = new Bullet(bullet2X, bullet3Y-15);
			Bullet b4 = new Bullet(bullet2X, bullet3Y+15);
			Canvas.enemyBullets.add(b3);
			Canvas.enemyBullets.add(b4);
		}
		Sound.play("shot.wav");
	}
	
	/**
	 * Method returns true if enemy is in position.
	 */
	public boolean isReady() {
		return ready;
	}
	
	/**
	 * Method creates the initial movement for the enemy to move into start position.
	 */
	public void spawn() {
		x -= 2;
		if(x < Main.getWidth() - w*6/5)
			ready = true;
	}
	
	/**
	 * Method creates movement for the enemy.
	 */
	public void move() {
		Random rnd = new Random();
		if (type == 1) {	
			y += speed;
			if (y < 0 || y > Main.getHeight()-h)
				speed = -speed;
		}
		else if (type == 2) {
			y += speed;
			if (y < r1) {
				speed = -speed;
				r2 = rnd.nextInt(350);
			}
			if (y > Main.getHeight()-h-r2) {
				speed = -speed;
				r1 = rnd.nextInt(350);
			}
		}
		else if (type == 3) {
			if (y > Canvas.ship.y + 60) {
				y -= 1;
			}
			else if (y < Canvas.ship.y - 60) {
				y += 1;
			}
		}
	}
	
	/**
	 * Method creates a rectangle for collision detection.
	 */
	public Rectangle getBounds() {
		if (type == 1)
			return new Rectangle(x, y+40, 55, 26);
		if (type == 3)
			return new Rectangle(x, y+60, 55, 35);
		return new Rectangle(x, y, w, h);
	}
	
	/**
	 * A second method to create another rectangle for more accurate collision detection.
	 */
	public Rectangle getBounds2() {
		if (type == 1)
			return new Rectangle(x+55, y, w-55, h);
		if (type == 3)
			return new Rectangle(x+55, y, w-55, h);
		return new Rectangle(x, y, w, h);
	}
}

