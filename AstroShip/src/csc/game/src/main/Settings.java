package csc.game.src.main;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Class that contains methods for the settings page.
 */
public class Settings {
	public static int n; // Tracks current string
	
	/**
	 * Draws the settings page. Ovals are used to "mark" one of the strings.
	 */
	public static void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 30));
		
		g.drawString("Difficulty", 342, 360);
		g.drawString("Music", 363, 400);
		g.drawString("Sound Effects", 306, 440);
		g.drawString("Back", 370, 480);
		if (n == 1) {
			// Ovals mark current text
			g2d.fillOval(325, 345, 10, 10);
			g2d.fillOval(475, 345, 10, 10);
			
			// Current difficulty is written in red
			if (Canvas.difficulty == 1)
				g.setColor(Color.red);
			g.drawString("Easy", 253, 290);
			g.setColor(Color.white);
			if (Canvas.difficulty == 2)
				g.setColor(Color.red);
			g.drawString("Normal", 353, 290);
			g.setColor(Color.white);
			if (Canvas.difficulty == 3)
				g.setColor(Color.red);
			g.drawString("Hard", 485, 290);
		}
		if (n == 2) {
			g2d.fillOval(340, 385, 10, 10);
			g2d.fillOval(460, 385, 10, 10);
			// These rectangles represent volume
			if(Sound.d > -32)
				g2d.fillRect(300, 300, 20, -10);
			if(Sound.d > -28)
				g2d.fillRect(325, 300, 20, -20);
			if(Sound.d > -24)
				g2d.fillRect(350, 300, 20, -30);
			if(Sound.d > -20)
				g2d.fillRect(375, 300, 20, -40);
			if(Sound.d > -16)
				g2d.fillRect(400, 300, 20, -50);
			if(Sound.d > -12)
				g2d.fillRect(425, 300, 20, -60);
			if(Sound.d > -8)
				g2d.fillRect(450, 300, 20, -70);
			if(Sound.d > -4)
				g2d.fillRect(475, 300, 20, -80);
			if(Sound.d > 0)
				g2d.fillRect(500, 300, 20, -90);
			if(Sound.d > 4)
				g2d.fillRect(525, 300, 20, -100);
		}
		if (n == 3) {
			g2d.fillOval(285, 425, 10, 10);
			g2d.fillOval(515, 425, 10, 10);
			// These rectangles represent volume
			if(Sound.fxD > -32)
				g2d.fillRect(300, 300, 20, -10);
			if(Sound.fxD > -28)
				g2d.fillRect(325, 300, 20, -20);
			if(Sound.fxD > -24)
				g2d.fillRect(350, 300, 20, -30);
			if(Sound.fxD > -20)
				g2d.fillRect(375, 300, 20, -40);
			if(Sound.fxD > -16)
				g2d.fillRect(400, 300, 20, -50);
			if(Sound.fxD > -12)
				g2d.fillRect(425, 300, 20, -60);
			if(Sound.fxD > -8)
				g2d.fillRect(450, 300, 20, -70);
			if(Sound.fxD > -4)
				g2d.fillRect(475, 300, 20, -80);
			if(Sound.fxD > 0)
				g2d.fillRect(500, 300, 20, -90);
			if(Sound.fxD > 4)
				g2d.fillRect(525, 300, 20, -100);
		}
		if (n == 4) {
			g2d.fillOval(350, 465, 10, 10);
			g2d.fillOval(450, 465, 10, 10);
		}
	}
	
	/**
	 * Registers the key/s that is/are being pressed. The variable n is used to keep track of the selected string.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			n--;
			if (n == 0)
				// Jump to the bottom
				n = 4;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			n++;
			if (n == 5)
				// Jump to the top
				n = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if (n == 1) {
				Canvas.difficulty++;
				if (Canvas.difficulty == 4)
					// Jump to easy
					Canvas.difficulty = 1;
			}
			if (n == 2)
				Sound.increaseMusicVolume();
			if (n == 3)
				Sound.increaseFxVolume();
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (n == 1) {
				Canvas.difficulty--;
				if (Canvas.difficulty == 0)
					// Jump to hard
					Canvas.difficulty = 3;
			}
			if (n == 2)
				Sound.decreaseMusicVolume();
			if (n == 3)
				Sound.decreaseFxVolume();
		}
		// Return to previous status
		if (e.getKeyCode() == KeyEvent.VK_ENTER && n == 4)
			Canvas.setStatus(Canvas.previousStatus);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Canvas.setStatus(Canvas.previousStatus);
	}
}
