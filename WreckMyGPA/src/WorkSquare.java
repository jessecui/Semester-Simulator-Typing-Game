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
public class WorkSquare extends GameObj {
	public static final int SIZE = 90;
	public double currentGrade = 4.00;
	public Color color = Color.GREEN;

	/**
	 * Note that, because we don't need to do anything special when constructing
	 * a Square, we simply use the superclass constructor called with the
	 * correct parameters
	 */
	public WorkSquare(int initVelX, int initVelY, int initX, int initY, 
	                  int courtWidth, int courtHeight) {
		super(initVelX, initVelY, initX, initY, SIZE, SIZE, courtWidth,courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
	public void update (double newGrade) {
	    if (newGrade > 4)
	        newGrade = 4;
	    // Define the colors for each grade.
	    double decimal = 1 - ((newGrade - (int) newGrade) * 0.8);
	    if (decimal == 1) decimal = 0;
	    
 	    if (newGrade <= 4 && newGrade > 3)
	        color = new Color((int) (decimal * 255), 255, (int) (decimal * 255));
	    if (newGrade <= 3 && newGrade > 2)
	        color = new Color((int) (decimal * 255), (int) (decimal * 255), 255);
	    if (newGrade <= 2 && newGrade > 1)
	        color = new Color(255, 255, (int) (decimal * 255));
	    if (newGrade <= 1 && newGrade > 0)
	        color = new Color(255, (int) (decimal * 255), (int) (decimal * 255));
        
        currentGrade = newGrade;

        if (v_x > 0) {
            v_x = (int) (newGrade + 1 - 0.01);
        } else if (v_x < 0) {
            v_x = - (int) (newGrade + 1);
        }
        
        if (v_y > 0) {
            v_y = (int) (newGrade + 1 - 0.01);
        } else if (v_y < 0) {
            v_y = - (int) (newGrade + 1 - 0.01);
        }
	}
	
	//Draw the grade letter on the square. Implementation was aided with StackOverflow.
	public void drawGrade(Graphics g, Font font) {
        String letter;
        String ending;
        
        //Determine the type of grade i.e. + or -.
        double decimal = currentGrade - (int)(currentGrade);
        if (decimal == 0 || decimal > 0.8) ending = "+";
        else if (decimal <= 0.35 && decimal > 0) ending = "-";
        else ending = "";
        
        //Determine the grade letter.
        if (currentGrade > 3 && currentGrade <= 4) letter = "A";
        else if (currentGrade > 2 && currentGrade <= 3) letter = "B";
        else if (currentGrade > 1 && currentGrade <= 2) letter = "C";
        else letter = "D";
        
        String textToDisplay = letter + ending;
        
	    // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = ((width - metrics.stringWidth(textToDisplay)) / 2) + pos_x;
        // Determine the Y coordinate for the text.
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent() + pos_y;
        // Set the font
        g.setFont(font);
        // Set the color
        g.setColor(Color.BLACK);
        // Draw the String
        g.drawString(textToDisplay, x, y);
    }
}
