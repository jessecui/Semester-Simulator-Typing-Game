/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Obstacle extends GameObj {
    public static final int SIZE = 120;
    public Color color = Color.ORANGE;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters
     */
    public Obstacle(int initX, int initY, int courtWidth, int courtHeight) {
        super(0, 0, initX, initY, SIZE, SIZE, courtWidth,courtHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(pos_x, pos_y, width, height);
    }
    
    /**Draws the name of the object at the top
     * of the square during the game.
     * Implementation was aided with StackOverFlow.
     */
    public void drawName(Graphics g, String text, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = ((width - metrics.stringWidth(text)) / 2) + pos_x;
        // Determine the Y coordinate for the text.
        int y = ((height - metrics.stringWidth(text)) / 2) + pos_y + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Set the color
        g.setColor(Color.BLACK);
        // Draw the String
        g.drawString(text, x, y);
    }
}
