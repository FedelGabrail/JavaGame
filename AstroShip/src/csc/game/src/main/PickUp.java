package csc.game.src.main;
 
import java.awt.Rectangle;
import java.util.Random;

/**
 * Class that describes pick ups.
 */
public class PickUp {
       
        int x, y, w, h;
        boolean visible;
        private int type; // 1 is health, 2 is ammo.
        public Random rand;
       
        public PickUp(int n) {
                Random rnd = new Random();
                x = Main.getWidth() + 30;
                y = 25 + rnd.nextInt(Main.getHeight() - 50);
                w = 19;
                h = w;
                visible = true;
                type = n;
        }
       
        /**
         * Returns the type of the bullet, 1 for health and 2 for ammo.
         */
        public int getType() {
                return type;
        }
       
        /**
    	 * Returns true if the pick up is within bounds.
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
         * Method makes the pick up move by decreasing its x-position
         * and also checks if it's out of bounds or not.
         */
        public void move() {
                x -= 2;
                if(x < 0)
                        visible = false;
        }
}