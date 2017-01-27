package csc.game.src.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

import javax.swing.*;

/**
 * Class that updates and renders all events.
 */
public class Canvas extends JPanel implements ActionListener {
	Menu menu;
	static Enemy enemy;
	Settings settings;
	static Ship ship;
	Image img;
	static ImageIcon shp;
	ImageIcon obs;
	ImageIcon bg;
	ImageIcon health;
	ImageIcon ammo;
	ImageIcon bullet;
	Image bllt;
	Timer time;
	public static ArrayList obstacles;
	public static ArrayList pickUps;
	public static ArrayList enemyBullets;
	public static ArrayList enemies;
	private int highScore;
	private int r;
	private int imgWidth;
	private static int shootC;
	public static int extraScore, dx, score, c, visibleHP, boomC, puC, obsC,
			speedC, enemyC, fightC, minObstacles, difficulty, diffObsC, diffPuC, enemyDPS, enemyTimer;
	private Font f1, f2, f3;
	private static boolean enemyAlive;
	private int n;

	public static enum STATUS {
		MENU, STARTED, PAUSED, GAMEOVER, HELP, SETTINGS
	};

	public static STATUS Status = STATUS.MENU;
	static STATUS selectedStatus = STATUS.STARTED;
	public static STATUS previousStatus;

	public Canvas() {
		menu = new Menu();
		settings = new Settings();
		addKeyListener(new AL());
		ship = new Ship();
		setFocusable(true);
		bg = new ImageIcon(Main.class.getResource("/background.png"));
		imgWidth = bg.getIconWidth();
		shp = new ImageIcon(Main.class.getResource("/heavyfreighter_40x40.png"));
		health = new ImageIcon(Main.class.getResource("/heart.png"));
		ammo = new ImageIcon(
				Main.class.getResource("/ammobox.png"));
		img = bg.getImage();
		time = new Timer(10, this);
		time.start();
		obstacles = new ArrayList();
		pickUps = new ArrayList();
		enemyBullets = new ArrayList();
		enemies = new ArrayList();
		minObstacles = 6;
		puC = 1;
		obsC = 1;
		speedC = 1;
		enemyC = 1;
		shootC = 1;
		fightC = 1;
		menu.n = 1;
		difficulty = 2;
		Settings.n = 1;
		visibleHP = ship.life;
		f1 = new Font("Arial", 1, 30);
		f2 = new Font("Arial", 1, 100);
		f3 = new Font("Arial", 1, 15);
		bullet = new ImageIcon(Main.class.getResource("/enemybullet.png"));
		bllt = bullet.getImage();
	}

	/**
	 * Goes into start status and creates a new game if current state is game over.
	 */
	public static void start() {
		// Resets a lot of things in the if statement.
		if (getStatus() == STATUS.GAMEOVER) {
			ship = new Ship();
			obstacles.clear();
			ship.leftSpeed = 0;
			ship.rightSpeed = 0;
			ship.upSpeed = 0;
			ship.downSpeed = 0;
			enemyBullets.clear();
			enemies.clear();
			enemyAlive = false;
			fightC = 1;
			enemyC = 1;
			shootC = 1;
			score = 0;
			dx = 0;
			enemyTimer = 0;
			c = 0;
			Sound.setFrame(0);
			minObstacles = 6;
			Obstacle.baseSpeed = 4;
			Sound.stop();
			shp = new ImageIcon(
					Main.class.getResource("/heavyfreighter_40x40.png"));
			visibleHP = ship.life;
			puC = 1;
			obsC = 1;
			speedC = 1;
			boomC = 0;
			extraScore = 0;
			pickUps.clear();
		}
		// Goes into started status and starts music
		if (getStatus() != STATUS.STARTED) {
			// Only checks difficulty if it's a new game
			if (getStatus() != STATUS.PAUSED)
				checkDifficulty();
			setStatus(STATUS.STARTED);
			Sound.loop("spaceTheme2.wav");
		}
	}

	/**
	 * Goes into pause status and pauses the music.
	 */
	public void pause() {
		if (getStatus() != STATUS.PAUSED) {
			setStatus(STATUS.PAUSED);
			Sound.stopLoop();
		}
	}
	
	/**
	 * Goes into game over status.
	 */
	public void gameOver() {
		if (getStatus() != STATUS.GAMEOVER) {
			setStatus(STATUS.GAMEOVER);
			fightC = 1;
			enemyC = 1;
			Sound.stopLoop();
			Sound.play("gameOver.wav");
			shp = new ImageIcon(Main.class.getResource("/explosion.png"));
		}
	}
	
	/**
	 * Getter for Status.
	 */
	public static STATUS getStatus() {
		return Status;
	}
	
	/**
	 * Setter for status.
	 */
	public static void setStatus(STATUS state) {
		Status = state;
	}
	
	/**
	 * Returns the enemies array list.
	 */
	public static ArrayList getEnemies() {
		return enemies;
	}

	/**
	 * Returns the obstacles array list.
	 */
	public static ArrayList getObstacles() {
		return obstacles;
	}

	/**
	 * Returns the pickUps array list.
	 */
	public static ArrayList getPickUps() {
		return pickUps;
	}
	
	/**
	 * Returns the enemyBullets array list.
	 */
	public static ArrayList getEnemyBullets() {
		return enemyBullets;
	}

	/**
	 * Method creates a new obstacle.
	 */
	public void addObstacle() {
		Obstacle o = new Obstacle();
		obstacles.add(o);
	}
	
	/**
	 * Creates a new pick up.
	 */
	public void addPickUp(int n) {
		PickUp pu = new PickUp(n);
		pickUps.add(pu);
	}

	/**
	 * Updates the visible HP according to the hidden (real) HP.
	 */
	public void updateLife() {
		// visible HP decreases faster if the real HP is much less
		if (ship.life < visibleHP) {
			visibleHP--;
			if (ship.life < visibleHP*3/4) {
				visibleHP--;
				if (ship.life < visibleHP*1/2)
					visibleHP--;
			}		
		}
		// If extra HP has been picked up
		else if (ship.life > visibleHP)
			visibleHP++;
		if (ship.life < 0)
			visibleHP -= 3;
	}
	
	/**
	 * Checks if any extra score should be added to the score and then adds it.
	 */
	public void checkExtraScore() {
		if (extraScore > score)
			score++;
		else
			extraScore = score;
	}
	
	/**
	 * Checks which difficulty the game is set on, it's 2 on default.
	 */
	public static void checkDifficulty() {
		if (difficulty == 1) {
			diffObsC = 1;
			enemyDPS = 210;
			diffPuC = 2;
			Enemy.lifeC = 1;
		}
		if (difficulty == 2) {
			diffObsC = 2;
			enemyDPS = 180;
			diffPuC = 3;
			Enemy.lifeC = 2;
		}
		if (difficulty == 3) {
			diffObsC = 3;
			enemyDPS = 150;
			diffPuC = 4;
			Enemy.lifeC = 3;
		}
	}
	
	/**
	 * Checks if enemies should be spawned and then spawns them. Also adds pick ups to help the player.
	 */
	public void spawnEnemy() {
		// enemyC keeps track of which enemy should spawn
		if (score > 5000 && enemyC == 1) {
			Enemy en = new Enemy(enemyC);
			enemies.add(en);
			enemyC++;
			enemyAlive = true;
			addPickUp(2);
			// Only add extra HP if the player needs it
			if (visibleHP < 250)
				addPickUp(1);
		}
		if (score > 15000 && enemyC == 2) {
			Enemy en = new Enemy(enemyC);
			enemies.add(en);
			enemyC++;
			enemyAlive = true;
			addPickUp(2);
			if (visibleHP < 250)
				addPickUp(1);
		}
		if (score > 30000 && enemyC == 3) {
			Enemy en = new Enemy(enemyC);
			enemies.add(en);
			enemyC++;
			enemyAlive = true;
			addPickUp(2);
			if (visibleHP < 250)
				addPickUp(1);
		}
	}
	
	/**
	 * Movement (and some other stuff) for unfriendly objects.
	 */
	public void unfriendlyMovement() {
		// Movement for obstacles
		ArrayList obstacles = getObstacles();
		// Minimum amount of obstacles
		if (obstacles.size() <= minObstacles && !enemyAlive)
			// Maintain minimum amount of obstacles
			addObstacle();
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle o = (Obstacle) obstacles.get(i);
			if (o.isVisible() == true)
				o.move();
			else if (!enemyAlive) {
				// Respawns the obstacle with new properties
				o.newPath();
				o.newPosition();
				o.visible = true;
			}
			// Remove all obstacles completely during enemy encounters
			else
				obstacles.remove(i);
		}
		
		// Movement for enemies
		ArrayList enemies = getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy en = (Enemy) enemies.get(i);
			// Checks if enemy is in position
			if (!en.ready) {
				// Move it
				en.spawn();
			}
			if (enemyAlive)
				enemyTimer++;
			// Enemies are too cool to act until a certain time
			if (enemyTimer >= 200) {
				en.move();
				// Shoot stuff
				if(enemyTimer >= 200 + enemyDPS/enemyC * shootC) {
					en.fire();
					shootC++;
				}
			}
			// Checks if dead
			if (en.life < 1) {
				// Remove enemy
				enemies.remove(en);
				// Reset shit
				enemyAlive = false;
				enemyTimer = 0;
				fightC = 1;
				shootC = 1;
				extraScore += 1000;
				// Celebratory pick ups
				addPickUp(2);
				addPickUp(1);
			}
		}
		
		// Movement for hostile bullets
		ArrayList enemyBullets = getEnemyBullets();
		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet eb = (Bullet) enemyBullets.get(i);

			if (eb.isVisible() == true)
				eb.enemyMove();
			else {
				enemyBullets.remove(i);
			}
		}
	}
	
	/**
	 * Movement for pick ups.
	 */
	public void pickUpMovement() {
		ArrayList pickUps = getPickUps();
		for (int i = 0; i < pickUps.size(); i++) {
			PickUp pu = (PickUp) pickUps.get(i);

			if (pu.isVisible() == true)
				pu.move();
			else
				pickUps.remove(i);
		}
	}

	/**
	 * Gets called every 10 milliseconds and does a lot of dank stuff! It updates pretty much every event and action.
	 */
	public void actionPerformed(ActionEvent e) {
		if (getStatus() == STATUS.STARTED) {

			// Call collision methods
			Physics.obstacleCollision();
			Physics.pickUpCollision();
			Physics.enemyCollision();

			updateLife();

			if (visibleHP < 1) {
				// Dead
				gameOver();
			}
			
			// Increases background coordinate
			dx += 2;

			checkExtraScore();
			
			// Score stops increasing during enemy encounters.
			if(!enemyAlive)
				score++;
			
			// Increases difficulty as time goes on
			if (score > 6000/diffObsC * obsC) {
				minObstacles++;
				obsC++;
			}
			
			// Adds random extra life or ammo as time goes on
			if (score > 1000 * diffPuC * puC) {
				Random rnd = new Random();
				n = 1 + rnd.nextInt(2);
				addPickUp(n);
				puC++;
			}
			
			spawnEnemy();
			
			// Exclusive pick up spawns for enemy encounters
			if (enemyTimer == 500 * diffPuC * fightC) {
				addPickUp(2);
				if (visibleHP <= 150/enemyC)
					addPickUp(1);
				fightC++;
			}

			// Movement for friendly bullets
			ship.moveBullets();
			
			// Movement for all hostile objects.
			unfriendlyMovement();

			ArrayList pickUps = getPickUps();
			for (int i = 0; i < pickUps.size(); i++) {
				PickUp pu = (PickUp) pickUps.get(i);

				if (pu.isVisible() == true)
					pu.move();
				else
					pickUps.remove(i);
			}

			// Updates ship position
			if (ship.y > 0)
				ship.up();
			if (ship.y < Main.getHeight() - shp.getIconHeight() * 5 / 3)
				ship.down();
			if (ship.x > 0)
				ship.left();
			if (ship.x < Main.getWidth() - shp.getIconWidth())
				ship.right();
		}
		
		// Calls ship movement a last time and locks it
		if (getStatus() == STATUS.GAMEOVER) {
			ship.up();
			ship.down();
			ship.left();
			ship.right();
			// Increase the variable that counts the ship explosion
			boomC++;
			// Check if there's a new high score 
			if (score > highScore)
				highScore = score;
		}

		// Draw all visuals
		repaint();
	}

	/**
	 * Draws the moving and looping background by always drawing two of them (one is mostly out of bounds).
	 */
	public void drawBackground(Graphics g) {
		// Always draw 2 backgrounds concurrently
		g.drawImage(img, -(dx - imgWidth * c), 0, null);
		// This image begins from the end of current firstImg
		g.drawImage(img, -(dx - imgWidth * (c + 1)), 0, null);
		// Check if it has reached the end of firstImg
		if (dx > imgWidth * (c + 1))
			// Increase counter
			c++;
	}

	/**
	 * Draws all existing unfriendly objects.
	 */
	public void drawUnfriendlies(Graphics g) {
		ArrayList obstacles = getObstacles();
		// Draw all existing obstacles
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle o = (Obstacle) obstacles.get(i);
			o.getImage().paintIcon(this, g, o.x, o.y);
		}
		
		ArrayList enemies = getEnemies();
		// Draw all existing enemies
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = (Enemy) enemies.get(i);
			e.getImage().paintIcon(this, g, e.x, e.y);
		}
		
		ArrayList enemyBullets = getEnemyBullets();
		// Draw all existing hostile bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet eb = (Bullet) enemyBullets.get(i);

			g.drawImage(bllt, eb.x, eb.y, null);
		}
	}

	/**
	 * Draws all existing pick ups.
	 */
	public void drawPickUps(Graphics g) {
		ArrayList pickUps = getPickUps();

		for (int i = 0; i < pickUps.size(); i++) {
			PickUp pu = (PickUp) pickUps.get(i);
			if (pu.getType() == 1)
				health.paintIcon(this, g, pu.x, pu.y);
			if (pu.getType() == 2)
				ammo.paintIcon(this, g, pu.x, pu.y);
		}
	}

	/**
	 * Draws a HUD that contains game info.
	 */
	public void drawHUD(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.white);
		g.setFont(f1);

		g.drawString("Score: " + score / 10, WIDTH / 2, 60);
		g.drawString("Ammo: " + ship.getAmmo(), WIDTH / 2, 30);
		g.setColor(Color.black);
		g2d.setStroke(new BasicStroke(4));
		g2d.drawRect(500, 10, 253, 40);
		g.setColor(Color.green);
		g.fillRect(502, 12, visibleHP, 36);
		g.setColor(Color.red);
		g.setFont(new Font("Arial", 1, 20));
		g.drawString(visibleHP + "/250", 503, 37);
	}

	/**
	 * Draws the game over text.
	 */
	public void gameOverText(Graphics g) {
		g.setColor(Color.white);
		g.setFont(f1);

		g.drawString("High Score: " + highScore / 10, 315, 200);

		g.setFont(f2);

		g.drawString("Game Over", 130, 300);
	}
	
	/**
	 * Draws the help page.
	 */
	public void drawHelp(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.white);
		g.setFont(f1);

		g.drawString("Move with WASD", 283, 400);
		g.drawString("Shoot with Spacebar", 256, 440);
		g.drawString("Pause with Escape", 270, 480);

		g.drawString("Back", 370, 550);
		g2d.fillOval(350, 535, 10, 10);
		g2d.fillOval(450, 535, 10, 10);

		// Draw the HUD to point out things in it
		drawHUD(g);

		g.setFont(f1);
		g.setColor(Color.white);
		g.drawString("Health", 503, 80);

		g.setFont(f3);
		health.paintIcon(this, g, 300, 150);
		g.drawString("Hearts give health or more", 220, 180);
		g.drawString("score if health is full", 240, 200);

		ammo.paintIcon(this, g, 500, 145);
		g.drawString("These bullets refill", 450, 180);
		g.drawString("your ammo", 480, 200);
	}

	/**
	 * Draws all visuals-
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// Draw background
		drawBackground(g);
		
		// Checks if a game is ongoing
		if (getStatus() == STATUS.STARTED) {
			// Draw ship
			shp.paintIcon(this, g, ship.x, ship.y);
			ship.drawBullets(g);
			
			// Draw existing hostiles
			drawUnfriendlies(g);

			// Draw existing pick ups
			drawPickUps(g);
		}

		// Fancy metatext
		// Checks if any game has been started.
		if (getStatus() == STATUS.STARTED || getStatus() == STATUS.PAUSED
				|| getStatus() == STATUS.GAMEOVER)
			drawHUD(g);

		// Checks if the game is over
		if (getStatus() == STATUS.GAMEOVER) {
			gameOverText(g);
			// Ship explodes
			if(boomC < 7)
				shp.paintIcon(this, g, ship.x, ship.y);
		}

		// Checks if menu is up
		if (getStatus() == STATUS.MENU || getStatus() == STATUS.PAUSED)
			menu.paint(g);
		if (getStatus() == STATUS.PAUSED) {
			g.setFont(f2);
			g.drawString("Paused", 230, 200);
		}

		// Checks if control page is up
		if (getStatus() == STATUS.HELP)
			drawHelp(g);

		// Checks if settings page is up
		if (getStatus() == STATUS.SETTINGS)
			Settings.paint(g);
	}

	/** 
	 * Private Action Listener class that receives all player interaction, which is only by keyboard.
	 *
	 */
	private class AL extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			if (getStatus() == STATUS.STARTED) {
				ship.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					pause();
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					pause();
			} else if (getStatus() == STATUS.MENU
					|| getStatus() == STATUS.PAUSED) {
				menu.keyPressed(e);
			} else if (getStatus() == STATUS.HELP) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					setStatus(previousStatus);
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					setStatus(previousStatus);
			} else if (getStatus() == STATUS.SETTINGS) {
				settings.keyPressed(e);
			} else if (getStatus() == STATUS.GAMEOVER) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					start();
			}
		}

		public void keyReleased(KeyEvent e) {
			if (getStatus() != STATUS.GAMEOVER)
				ship.keyReleased(e);
		}
	}
}