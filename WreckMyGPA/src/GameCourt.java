/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    /** A special purpose exception class to indicate errors when reading 
     *  the input for the FileCorrector.
     */
    public static class FormatException extends Exception {
      public FormatException(String msg) {
        super(msg);
      }
    }

	public boolean playing = false; // whether the game is running
	private JLabel score; // Current GPA score in the game.

	// Game constants
	public static final int COURT_WIDTH = 1200;
	public static final int COURT_HEIGHT = 700;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel score) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    tick();
                } catch (Exception except) {
                    throw new RuntimeException(except);
                } 
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		this.score = score;
	}
    
    //Creates new collection of codes to names of objects.
    Map<String, String> codes = new TreeMap<String, String>();
    //Creates another map from name to work square. This models the state of the game.
    Map<String, WorkSquare> works = new TreeMap<String, WorkSquare>();
    //Creates a list of potential obstacle objects names.
    ArrayList<String> obstacleNames = new ArrayList<>();
    //Creates a map from name to obstacle. This also models the state of the game.
    Map<String, Obstacle> obstacles = new TreeMap<String, Obstacle>();
    
    //Updating list of squares to delete.
    ArrayList<String> worksToDelete = new ArrayList<>();    
    
    //Updating list of obstacles to delete.
    ArrayList<String> obstaclesToDelete = new ArrayList<>();    
    
    //Current GPA.
    DecimalFormat twoPlaces = new DecimalFormat("#.00");
    public double gpa;
    
	/**
	 * (Re-)set the game to its initial state.
	 * @throws FormatException 
	 * @throws IOException 
	 * @throws InputConverter.FormatException 
	 */
	public void reset() throws IOException, InputConverter.FormatException {
	    //Clear the current maps and list.
        codes.clear();
        works.clear();
        obstacleNames.clear();
        obstacles.clear();
        
        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        InputConverter inputs = InputConverter.make(desktopPath + "jc1_gpa_and_class_info.txt");
	    
	    //Set the GPA to the starting GPA.
	    gpa = inputs.getGpa();
	    if (gpa > 4) gpa = 4;
	    score.setText("GPA: " + (twoPlaces.format(gpa)));
	    
	    //Add the obstacle names to the list.
	    obstacleNames = inputs.getObstacles();
	    
	    //Get the map of user work inputs.
	    Map<String, Integer> workInputs = inputs.getWorkInputs();
	    
	    //Get the list of the user work names.
	    ArrayList<String> workNames = new ArrayList<String>(workInputs.keySet());
	    
	    //Get the number of worknames the user has entered.
	    int size = workNames.size();
	    
	    //Generates a 2-D array to model the inital game state for spawning.
	    int[][] array = new int[4][4];
	    
	    //Generates a random list of integers from 1 to 16 to input into 2-D array.
	    ArrayList<Integer> list = new ArrayList<>(16);
	    for (int i = 1; i <= 16; i++){ 
	        list.add(i);
	    }
	    Collections.shuffle(list);
	    
	    //Inputs integers from 1 to 16 in a random order into the 2-D array.
	    int counter = 0;
	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 4; j++) {
	            array[i][j] = list.get(counter);
	            counter++;
	        }
	    }
	    
	    //Creates possible chars to use in code.
	    String possibleChars = "abcdefghijklmnopqrstuvwxyz1234567890";
	    
	    //When I/O is implemented create another map from variable name to object.
	    for (int n = 1; n <= size + obstacleNames.size(); n++) {
	        
	        int velX;
	        int velY;
	        
	        double randx = Math.random();
	        if (randx > 0.5) velX = 4;
	        else velX = -4;
	        
	        double randy = Math.random();
            if (randy > 0.5) velY = 4;
            else velY = -4;
            
            int row = -1;
            int col = -1;
            
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (array [i][j] == n) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            double ratioWorkX = (1 - ((double)(WorkSquare.SIZE)/(COURT_WIDTH / 4)));
            double ratioWorkY = (1 - ((double)(WorkSquare.SIZE)/(COURT_HEIGHT / 4)));
            double ratioObstacleX = (1 - ((double)(Obstacle.SIZE)/(COURT_WIDTH / 4)));
            double ratioObstacleY = (1 - ((double)(Obstacle.SIZE)/(COURT_HEIGHT / 4)));
            
            int posWorkX = (int) ((Math.random()*ratioWorkX + col) * (COURT_WIDTH / 4));
            int posWorkY = (int) ((Math.random()*ratioWorkY + row) * (COURT_HEIGHT / 4));
            int posObstacleX = (int) ((Math.random()*ratioObstacleX + col) * (COURT_WIDTH / 4));
            int posObstacleY = (int) ((Math.random()*ratioObstacleY + row) * (COURT_HEIGHT / 4));
	        
	        if (n <= size) {
                //Create the work square.
                WorkSquare work = new WorkSquare(velX, velY, posWorkX, posWorkY, 
                                                 COURT_WIDTH, COURT_HEIGHT);
                //Create the name to add in the mapping.
                String name = workNames.get(n-1);
                //Create the code for the specified object.
                String randomString = "";
                //Determine the number of characters for code from user input.
                Integer inputDigit = workInputs.get(name);
                
                int amount;
                if (inputDigit.equals(1)) amount = 3;
                else if (inputDigit.equals(2)) amount = 4;
                else if (inputDigit.equals(3)) amount = 5;
                else amount = 6;
                
                
                for (int i = 1; i <= size; i++) {
                    char[] randomChars = new char[amount];
                    for (int h = 0; h < amount; h++) {
                        randomChars[h] = possibleChars.charAt((int) (Math.random() * possibleChars.length()));
                    }
                    randomString = new String(randomChars);
                }
    	        //Set the code for the game object to be displayed on the square.
                work.setCode(randomString);
    	        //Add the variable name to works mapping.
                works.put(name, work);
    	        //Add the code to variable name mapping.
    	        codes.put(randomString, name);
    	        
	        } else {
	            //Create the code for the specified object.
	            String randomString = "";
	            for (int i = 1; i <= size; i++) {
	                char[] randomChars = new char[4];
	                for (int h = 0; h < 4; h++) {
	                    randomChars[h] = possibleChars.charAt((int) (Math.random() * possibleChars.length()));
	                }
	                randomString = new String(randomChars);
	            }
	            //Create the obstacle.
	            Obstacle obstacle = new Obstacle(posObstacleX, posObstacleY, 
	                                             COURT_WIDTH, COURT_HEIGHT);
	            //Set the code for the game object to be displayed on the square.
	            obstacle.setCode(randomString);
	            //Create the name to add in the mapping
	            String name = obstacleNames.get(n - size - 1);
	            //Add the variable name to the obstacles mapping.
	            obstacles.put(name, obstacle);
	            //Add the code to variable name mapping.
                codes.put(randomString, name);
	        }
	    }
		playing = true;
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 * @throws InputConverter.FormatException 
	 * @throws IOException 
	 */
	void tick() throws IOException, InputConverter.FormatException {
		if (playing) {
		    String desktopPath = System.getProperty("user.home") + "/Desktop/";
	        InputConverter inputs = InputConverter.make(desktopPath + "jc1_gpa_and_class_info.txt");
	        Map<String, Integer> workInputs = inputs.getWorkInputs();
	        
			// Advance the squares in their current direction.
		    for (Map.Entry<String, WorkSquare> entry : works.entrySet()) {
		        WorkSquare square = entry.getValue();
		        square.move();
		    }
	         
		    //Bounce and change states of squares that intersect with an object.
		    for (Map.Entry<String, WorkSquare> entry : works.entrySet()) {
                WorkSquare square = entry.getValue();
                
                //Check to see if the square will bounce off other squares.
	            for (Map.Entry<String, WorkSquare> potential : works.entrySet()) {
	                WorkSquare potentialSquare = potential.getValue();
	                
	                //Update the square if it will intersect.
	                if (square != potentialSquare && square.willIntersect(potentialSquare)) {
	                    if (square.currentGrade == 0.25) {
	                        worksToDelete.add(entry.getKey());    
	                    }
	                    square.update(square.currentGrade - 0.25); 
	                    //Bounce off other squares.
	                    square.bounce(square.hitObj(potentialSquare));
	                }    
	            }
	            
	            //Check to see if the square will bounce off an obstacle.
	            for (Map.Entry<String, Obstacle> potential : obstacles.entrySet()) {
	                Obstacle potentialObstacle = potential.getValue();
	                
	                //Update the square if it will intersect.
	                if(square.willIntersect(potentialObstacle)) {
	                    if (square.currentGrade == 0.25) {
                           worksToDelete.add(entry.getKey());    
                        }
                        square.update(square.currentGrade - 0.25); 
                        //Bounce off the obstacle.
                        square.bounce(square.hitObj(potentialObstacle));
	                }
	            }
            }
		    
		    //Bounce squares off the wall.
            for (Map.Entry<String, WorkSquare> entry : works.entrySet()) {
                WorkSquare square = entry.getValue();
    	        //Bounce off walls.
    	        square.bounce(square.hitWall());
            }
            
            //Delete all the works to be deleted and update GPA.
            for (String key : worksToDelete) {
                double gradeValue = works.get(key).currentGrade;
                //Determine the factor that the grade will affect GPA.
                Integer factor = workInputs.get(key);
                gpa = gpa + ((factor*(gradeValue - gpa)/30));
                if (gpa > 4) gpa = 4;
                if (gpa < 0) gpa = 0;
                score.setText("GPA: " + (twoPlaces.format(gpa)));
                works.remove(key);
                
                //Delete the code mapping from the codes map.
                for (Map.Entry<String, String> entry : codes.entrySet()) {
                    String value = entry.getValue();
                    if (value.equals(key)) {
                        codes.remove(entry.getKey());
                        break;
                    }
                }
            }
            
            //Delete all the obstacles to be deleted and update gpa.
            for (String key : obstaclesToDelete) {
                obstacles.remove(key);
                
                //Delete the code mapping from the codes map.
                for (Map.Entry<String, String> entry : codes.entrySet()) {
                    String value = entry.getValue();
                    if (value.equals(key)) {
                        codes.remove(entry.getKey());
                        break;
                    }
                }
            }
            
            //Clear the array of works to be deleted after deleting them
            worksToDelete.clear();
            
            //Clear the array of obstacles to be deleted after deleting them
            obstaclesToDelete.clear();
            
			// update the display
			repaint();
			
			//End the game when all pieces are gone.
			if (works.isEmpty()) {
			    playing = false;
			    obstacles.clear();
			    score.setText("You finished the semester with a " + (twoPlaces.format(gpa)) +
			                  ". Go back to studying!");
			}
		} 
	}
	
	//Delete an object given an input string from user.
	public void deleteObject(String input) {
	    Set<Map.Entry<String, String>> entries = codes.entrySet();
	    Iterator<Entry<String, String>> iter = entries.iterator();
	    
	    while(iter.hasNext()) {
    	    Map.Entry<String, String> entry = iter.next();
	        if (entry.getKey().equals(input)) {
	            String toDeleteName = entry.getValue();
	            if (works.containsKey(toDeleteName)) 
	                worksToDelete.add(toDeleteName); 
	            else if (obstacles.containsKey(toDeleteName))
	                obstaclesToDelete.add(toDeleteName);
	        }    
	    }
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Paint all the WorkSquares.
		for (Map.Entry<String, WorkSquare> entry : works.entrySet())
        {
            WorkSquare square = entry.getValue();
            String name = entry.getKey();
		    square.draw(g);
		    Font codeFont = new Font ("OPTIMA", Font.BOLD, 16);
		    square.drawCode(g, square.code, codeFont);
		    Font letterFont = new Font ("ROCKWELL", Font.BOLD, 32);
		    square.drawGrade(g, letterFont);
		    Font nameFont = new Font ("SANSSERIF", Font.BOLD, 10);
		    square.drawName(g, name, nameFont);
        }
		//Paint all the Obstacles.
        for (Map.Entry<String, Obstacle> entry : obstacles.entrySet())
        {
            String name = entry.getKey();
            Obstacle obstacle = entry.getValue();
            obstacle.draw(g);
            Font codeFont = new Font ("OPTIMA", Font.BOLD, 16);
            obstacle.drawCode(g, obstacle.code, codeFont);
            Font nameFont = new Font ("ROCKWELL", Font.BOLD, 20);
            obstacle.drawName(g, name, nameFont);
        }
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
