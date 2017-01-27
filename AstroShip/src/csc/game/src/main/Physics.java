package csc.game.src.main;

import java.util.ArrayList;

/**
 * Class that checks collisions between game objects.
 */
public class Physics {
	
	/**
	 * Collisions involving obstacles.
	 */
	public static void obstacleCollision() {
		ArrayList obstacles = Canvas.getObstacles();

		// Iterate through all existing obstacles
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle o = (Obstacle) obstacles.get(i);

			// Check if the ship has collided with the obstacle
			if (Canvas.ship.getBounds().intersects(o.getBounds())) {
				if (Canvas.getStatus() == Canvas.STATUS.STARTED)
					Canvas.ship.life -= o.w;
				if (Canvas.ship.life < 0)
					Canvas.ship.life = 0;
				obstacles.remove(o);
				Sound.play("shipHit.wav");
			}
			ArrayList bullets = Canvas.ship.getBullets();
			// Iterate through all existing bullets
			for (int w = 0; w < bullets.size(); w++) {
				Bullet b = (Bullet) bullets.get(w);

				// Check if the obstacle has collided with the bullet
				if (b.getBounds().intersects(o.getBounds())) {
					// Remove both of them
					bullets.remove(b);
					obstacles.remove(o);
					Canvas.extraScore += 250;
					Sound.play("obstacleHit.wav");
				}
			}
		}
	}
	
	/**
	 * Collisions involving enemies.
	 */
	public static void enemyCollision() {
		ArrayList enemies = Canvas.getEnemies();

		// Iterate through all existing obstacles
		for (int i = 0; i < enemies.size(); i++) {
			Enemy en = (Enemy) enemies.get(i);
			ArrayList enemyBullets = Canvas.getEnemyBullets();
			for (int w = 0; w < enemyBullets.size(); w++) {
				Bullet eb = (Bullet) enemyBullets.get(w);

				if (eb.getBounds().intersects(Canvas.ship.getBounds())) {
					enemyBullets.remove(eb);
					Canvas.ship.life -= (5+10*Canvas.difficulty);
					Sound.play("shipHit.wav");
					if (Canvas.ship.life < 0)
						// Life can't go below 0
						Canvas.ship.life = 0;
				}
			}
			// Check if the ship has collided with the obstacle
			if (Canvas.ship.getBounds().intersects(en.getBounds())
					|| Canvas.ship.getBounds().intersects(en.getBounds2())) {
				if (Canvas.getStatus() == Canvas.STATUS.STARTED) {
					// Decrease life, dependent on obstacle size
					Canvas.visibleHP = 0;
					Canvas.ship.life = 0;
				}
			}
			ArrayList bullets = Canvas.ship.getBullets();
			// Iterate through all existing bullets
			for (int w = 0; w < bullets.size(); w++) {
				Bullet b = (Bullet) bullets.get(w);

				// Check if the obstacle has collided with the bullet
				if (b.getBounds().intersects(en.getBounds())
						|| b.getBounds().intersects(en.getBounds2())) {
					bullets.remove(b);
					en.life -= 50;
					Sound.play("enemyHit.wav");
				}
			}
		}
	}
	
	/**
	 * Collisions involving pick ups.
	 */
	public static void pickUpCollision() {
		ArrayList pickUps = Canvas.getPickUps();

		for (int i = 0; i < pickUps.size(); i++) {
			PickUp pu = (PickUp) pickUps.get(i);

			// Check if the pick up has collided with the ship
			if (Canvas.ship.getBounds().intersects(pu.getBounds())) {
				// Check pick up type
				if (pu.getType() == 1) {
					Canvas.ship.life += 25;
					Sound.play("extraLife.wav");
					int d = 0;
					// Check if HP is overflowing
					if (Canvas.ship.life > 250) {
						d = Canvas.ship.life - 250;
						// Set life back to 250
						Canvas.ship.life = 250;
						// Transform overflow into score
						Canvas.extraScore = d*500;
					}
				}
				if (pu.getType() == 2) {
					Canvas.ship.ammo += 5 + 5*Canvas.enemyC;
					Sound.play("extraAmmo.wav");
				}
				pickUps.remove(pu);
			}
		}
	}
}
