package csc.game.src.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import csc.game.src.main.Canvas.STATUS;

/*
 * Class that contains methods for the menu.
 */
public class Menu {
		
	public static int n;
	
	/**
	 * Draw the menu page. Ovals are used to "mark" one of the strings.
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.white);
		
		g.drawString("Play", 355, 300);
		g.drawString("Help", 350, 380);
		g.drawString("Settings", 307, 460);
		g.drawString("Exit Game", 283, 540);
		
		if(n == 1) {
			// Ovals mark current string
			g2d.fillOval(330, 278, 10, 10);
			g2d.fillOval(470, 278, 10, 10);
		}
		else if(n == 2) {
			g2d.fillOval(330, 358, 10, 10);
			g2d.fillOval(470, 358, 10, 10);
		}
		else if(n == 3) {
			g2d.fillOval(285, 438, 10, 10);
			g2d.fillOval(515, 438, 10, 10);
		}
		else if(n == 4) {
			g2d.fillOval(260, 518, 10, 10);
			g2d.fillOval(540, 518, 10, 10);
		}
	}
	
	/**
	 * Registers the key/s that is/are being pressed. The variable n is used to keep track of the selected string.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (Canvas.selectedStatus == STATUS.STARTED)
				// Start game
				Canvas.start();
			else if (n == 4)
				// Exit game
				System.exit(1);
			else {
				// Save current status and set a new one
				Canvas.previousStatus = Canvas.getStatus();
				Canvas.setStatus(Canvas.selectedStatus);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			n--;
			if (n == 0)
				// Jump to bottom
				n = 4;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			n++;
			if (n == 5)
				// Jump to start
				n = 1;
		}
		// Can unpause with the escape-button
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && Canvas.getStatus() == STATUS.PAUSED)
			Canvas.start();
		// Sets the current string as the selected status
		if (n == 1)
			Canvas.selectedStatus = STATUS.STARTED;
		if (n == 2)
			Canvas.selectedStatus = STATUS.HELP;
		if (n == 3)
			Canvas.selectedStatus = STATUS.SETTINGS;
	}
	
}
