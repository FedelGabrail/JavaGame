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
public class Ship {
	int x = 400;
	int y = 400;
	int w = 40;
	int h = w;
	static int ammo;
	int speed = 3;
	public static Random rnd;
	int rightSpeed, leftSpeed, upSpeed, downSpeed, life;
	public static ArrayList bullets;
	Image bllt;
	ImageIcon bullet;
	boolean shot;
	

	public Ship() {
		bullets = new ArrayList();
		ammo = 20;
		life = 250;
		bullet = new ImageIcon(Main.class.getResource("/bullet.png"));
		bllt = bullet.getImage();
	}

	/**
     * Method returns a list of all the bullets.
     */
    public static ArrayList getBullets() {
            return bullets;
    }
   
    /**
     * Return the amount of ammo
     */
    public int getAmmo() {
            return ammo;
    }

    /**
     * Method changes the ship x-position.
     */
    public void right() {
            x += rightSpeed;
    }

    /**
     * Method changes the ship x-position.
     */
    public void left() {
            x += -leftSpeed;
    }

    /**
     * Method changes the ship y-position.
     */
    public void up() {
            y += -upSpeed;
    }

    /**
     * Method changes the ship y-position.
     */
    public void down() {
            y += downSpeed;
    }

    /**
     * Method creates a bullet, adds it to an arraylist and decreases ammo.
     */
    public void fire() {
            if (ammo > 0) {
                    ammo--;
                    int bulletX = x + 30;
                    int bulletY = y + 17;
                    Bullet b = new Bullet(bulletX, bulletY);
                    bullets.add(b);
            }
    }
   
    /**
     * Move the bullets that are visible
     */
    public void moveBullets() {
            ArrayList bullets = getBullets();

            for (int i = 0; i < bullets.size(); i++) {
                    Bullet b = (Bullet) bullets.get(i);

                    if (b.isVisible() == true)
                            b.move();
                    else
                            bullets.remove(i);
            }
    }

    /**
     * Method creates a rectangle for collision detection.
     */
    public Rectangle getBounds() {
            return new Rectangle(x - 3, y - 3, w, h);
    }
    public void drawBullets(Graphics g) {
            g.setColor(Color.black);
            ArrayList bullets = Ship.getBullets();

            for (int i = 0; i < bullets.size(); i++) {
                    Bullet b = (Bullet) bullets.get(i);
                    g.drawImage(bllt, b.x, b.y, null);
            }
    }

    /**
     * Registers the key/s that is/are being pressed.
     */
    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D)
                    rightSpeed = speed - 1;
            if (e.getKeyCode() == KeyEvent.VK_A)
                    leftSpeed = speed + 1;
            if (e.getKeyCode() == KeyEvent.VK_W)
                    upSpeed = speed;
            if (e.getKeyCode() == KeyEvent.VK_S)
                    downSpeed = speed;
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    fire();
    }

    /**
     * Registers the key/s that is/are being released.
     */
    public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A)
                    leftSpeed = 0;
            if (e.getKeyCode() == KeyEvent.VK_D)
                    rightSpeed = 0;
            if (e.getKeyCode() == KeyEvent.VK_W)
                    upSpeed = 0;
            if (e.getKeyCode() == KeyEvent.VK_S)
                    downSpeed = 0;
    }
}
