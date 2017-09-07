/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Enumeration;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes immutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Wreck my GPA!");
		frame.setLocation(300, 300);
		frame.setPreferredSize(new Dimension(1300, 850));
		
		CardLayout c1 = new CardLayout();
		//Create the card layout
		final JPanel cards = new JPanel(c1);
		//Create the instructions panel
		final JPanel instructions = new JPanel();
		instructions.setBackground(Color.LIGHT_GRAY);
		instructions.setLayout(new BoxLayout(instructions, BoxLayout.PAGE_AXIS));
		//Create the input panel
		final JPanel inputScreen = new JPanel();
		inputScreen.setLayout(new BoxLayout(inputScreen, BoxLayout.PAGE_AXIS));
		inputScreen.setBackground(Color.GREEN);
		//Create the home panel
		final JPanel main = new JPanel();	
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.setBackground(Color.CYAN);
	    //Create the game panel
        final JPanel game = new JPanel();
        game.setBackground(new Color(255, 120, 120));
		
		//Create the start button.
		final JButton startButton = new JButton("Start");
		//Create the instructions button.
		final JButton instructionButton = new JButton("Instructions");
	    //Create the return home button.
		final JButton mainButton1 = new JButton("Return to Main");
		//Create another return home button.
		final JButton mainButton2 = new JButton("Return to Main");
		//Create the Done button.
		final JButton doneButton = new JButton("Start Game");
		//Create the Quit button
		final JButton quitButton = new JButton("Quit");
		
		//User Interaction panel
        final JPanel user_interface = new JPanel();
        user_interface.setBackground(new Color(160, 160, 255));
        game.add(user_interface, BorderLayout.PAGE_START);
        
        //GPA indicator
        final JLabel score = new JLabel();
        score.setFont(new Font("Serif", Font.BOLD, 24));
        user_interface.add(score);

		// Main playing area
		final GameCourt court = new GameCourt(score);
		court.setBackground(Color.BLACK);
		game.add(court, BorderLayout.CENTER);
		
        // Type Box
        final JTextField textbox = new JTextField(20);
        
        // Add an action listener for when the code is entered
        textbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textbox.getText();
                court.deleteObject(input);
                textbox.setText("");
            }
        });
        
        //Add the textbox to the JPanel.
        user_interface.add(textbox);

		// Reset button
		final JPanel control_panel = new JPanel();
		control_panel.setBackground(new Color(255, 120, 120));
		game.add(control_panel, BorderLayout.PAGE_END);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    court.reset();
                } catch (Exception e2) {        
                    throw new RuntimeException(e2);
                }
				textbox.requestFocus();
			}
		});
		control_panel.add(reset);
		control_panel.add(quitButton);
		
		//Create the input screen.
		JPanel inputMenu = new JPanel();
		inputMenu.add(mainButton2);
		//Add an option to clear the radio buttons.
		JButton clearButton = new JButton("Clear All");
		inputMenu.add(clearButton);
        inputMenu.add(doneButton);
        inputScreen.add(inputMenu);
        
        JPanel gpaMenu = new JPanel();
        JLabel gpaAsk = new JLabel("What is your current GPA?");
        gpaAsk.setFont(new Font("Serif", Font.BOLD, 20));
        gpaMenu.add(gpaAsk);
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        JFormattedTextField gpaResponse = new JFormattedTextField(amountFormat);
        gpaResponse.setColumns(15);
        //amountField.addPropertyChangeListener("value", this);
        gpaMenu.add(gpaResponse);
        inputScreen.add(gpaMenu);
        
        
        JLabel workText = new JLabel("Type in any upcoming assignments or projects below and choose its "
                                   + "significance to your grade.");
        workText.setFont(new Font("Serif", Font.BOLD, 20));
        inputScreen.add(workText);
		JPanel row1 = new JPanel();
        JLabel label1 = new JLabel("Upcoming Assignment:");
        row1.add(label1);
	    JTextField text1 = new JTextField(15);
	    text1.setDocument (new JTextFieldLimit (15));
	    row1.add(text1);
	    inputScreen.add(row1);
	    
	    //Add the buttons.
	    JPanel buttons1 = new JPanel();
	    row1.add(buttons1);
	    JRadioButton a1, a2, a3, a4;
	    ButtonGroup group1 = new ButtonGroup();
	    
	    a1 = new JRadioButton("1");
	    a2 = new JRadioButton("2");
	    a3 = new JRadioButton("3");
	    a4 = new JRadioButton("4");
	    group1.add(a1);
	    group1.add(a2);
	    group1.add(a3);
	    group1.add(a4);
	    buttons1.add(a1);
	    buttons1.add(a2);
	    buttons1.add(a3);
	    buttons1.add(a4);
	    
	    JPanel row2 = new JPanel();
        JLabel label2 = new JLabel("Upcoming Assignment:");
        row2.add(label2);
        JTextField text2 = new JTextField(15);
        text2.setDocument (new JTextFieldLimit (15));
        row2.add(text2);
        inputScreen.add(row2);
        
        JPanel buttons2 = new JPanel();
        row2.add(buttons2);
        JRadioButton b1, b2, b3, b4;
        ButtonGroup group2 = new ButtonGroup();
        
        b1 = new JRadioButton("1");
        b2 = new JRadioButton("2");
        b3 = new JRadioButton("3");
        b4 = new JRadioButton("4");
        group2.add(b1);
        group2.add(b2);
        group2.add(b3);
        group2.add(b4);
        buttons2.add(b1);
        buttons2.add(b2);
        buttons2.add(b3);
        buttons2.add(b4);
        
        
        JPanel row3 = new JPanel();
        JLabel label3 = new JLabel("Upcoming Assignment:");
        row3.add(label3);
        JTextField text3 = new JTextField(15);
        text3.setDocument (new JTextFieldLimit (15));
        row3.add(text3);
        inputScreen.add(row3);
        
        JPanel buttons3 = new JPanel();
        row3.add(buttons3);
        JRadioButton cc1, c2, c3, c4;
        ButtonGroup group3 = new ButtonGroup();
        
        cc1 = new JRadioButton("1");
        c2 = new JRadioButton("2");
        c3 = new JRadioButton("3");
        c4 = new JRadioButton("4");
        group3.add(cc1);
        group3.add(c2);
        group3.add(c3);
        group3.add(c4);
        buttons3.add(cc1);
        buttons3.add(c2);
        buttons3.add(c3);
        buttons3.add(c4);
        
        JPanel row4 = new JPanel();
        JLabel label4 = new JLabel("Upcoming Assignment:");
        row4.add(label4);
        JTextField text4 = new JTextField(15);
        text4.setDocument (new JTextFieldLimit (15));
        row4.add(text4);
        inputScreen.add(row4);
        
        JPanel buttons4 = new JPanel();
        row4.add(buttons4);
        JRadioButton d1, d2, d3, d4;
        ButtonGroup group4 = new ButtonGroup();
        
        d1 = new JRadioButton("1");
        d2 = new JRadioButton("2");
        d3 = new JRadioButton("3");
        d4 = new JRadioButton("4");
        group4.add(d1);
        group4.add(d2);
        group4.add(d3);
        group4.add(d4);
        buttons4.add(d1);
        buttons4.add(d2);
        buttons4.add(d3);
        buttons4.add(d4);
        
        JPanel row5 = new JPanel();
        JLabel label5 = new JLabel("Upcoming Assignment:");
        row5.add(label5);
        JTextField text5 = new JTextField(15);
        text5.setDocument (new JTextFieldLimit (15));
        row5.add(text5);
        inputScreen.add(row5);
        
        JPanel buttons5 = new JPanel();
        row5.add(buttons5);
        JRadioButton e1, e2, e3, e4;
        ButtonGroup group5 = new ButtonGroup();
        
        e1 = new JRadioButton("1");
        e2 = new JRadioButton("2");
        e3 = new JRadioButton("3");
        e4 = new JRadioButton("4");
        group5.add(e1);
        group5.add(e2);
        group5.add(e3);
        group5.add(e4);
        buttons5.add(e1);
        buttons5.add(e2);
        buttons5.add(e3);
        buttons5.add(e4);
        
        JPanel row6 = new JPanel();
        JLabel label6 = new JLabel("Upcoming Assignment:");
        row6.add(label6);
        JTextField text6 = new JTextField(15);
        text6.setDocument (new JTextFieldLimit (15));
        row6.add(text6);
        inputScreen.add(row6);
        
        JPanel buttons6 = new JPanel();
        row6.add(buttons6);
        JRadioButton f1, f2, f3, f4;
        ButtonGroup group6 = new ButtonGroup();
        
        f1 = new JRadioButton("1");
        f2 = new JRadioButton("2");
        f3 = new JRadioButton("3");
        f4 = new JRadioButton("4");
        group6.add(f1);
        group6.add(f2);
        group6.add(f3);
        group6.add(f4);
        buttons6.add(f1);
        buttons6.add(f2);
        buttons6.add(f3);
        buttons6.add(f4);
        
        JPanel row7 = new JPanel();
        JLabel label7 = new JLabel("Upcoming Assignment:");
        row7.add(label7);
        JTextField text7 = new JTextField(15);
        text7.setDocument (new JTextFieldLimit (15));
        row7.add(text7);
        inputScreen.add(row7);
        
        JPanel buttons7 = new JPanel();
        row7.add(buttons7);
        JRadioButton g1, g2, g3, g4;
        ButtonGroup group7 = new ButtonGroup();
        
        g1 = new JRadioButton("1");
        g2 = new JRadioButton("2");
        g3 = new JRadioButton("3");
        g4 = new JRadioButton("4");
        group7.add(g1);
        group7.add(g2);
        group7.add(g3);
        group7.add(g4);
        buttons7.add(g1);
        buttons7.add(g2);
        buttons7.add(g3);
        buttons7.add(g4);
        
        JPanel row8 = new JPanel();
        JLabel label8 = new JLabel("Upcoming Assignment:");
        row8.add(label8);
        JTextField text8 = new JTextField(15);
        text8.setDocument (new JTextFieldLimit (15));
        row8.add(text8);
        inputScreen.add(row8);
        
        JPanel buttons8 = new JPanel();
        row8.add(buttons8);
        JRadioButton h1, h2, h3, h4;
        ButtonGroup group8 = new ButtonGroup();
        
        h1 = new JRadioButton("1");
        h2 = new JRadioButton("2");
        h3 = new JRadioButton("3");
        h4 = new JRadioButton("4");
        group8.add(h1);
        group8.add(h2);
        group8.add(h3);
        group8.add(h4);
        buttons8.add(h1);
        buttons8.add(h2);
        buttons8.add(h3);
        buttons8.add(h4);
        
        JPanel row9 = new JPanel();
        JLabel label9 = new JLabel("Upcoming Assignment:");
        row9.add(label9);
        JTextField text9 = new JTextField(15);
        text9.setDocument (new JTextFieldLimit (15));
        row9.add(text9);
        inputScreen.add(row9);
        
        JPanel buttons9 = new JPanel();
        row9.add(buttons9);
        JRadioButton i1, i2, i3, i4;
        ButtonGroup group9 = new ButtonGroup();
        
        i1 = new JRadioButton("1");
        i2 = new JRadioButton("2");
        i3 = new JRadioButton("3");
        i4 = new JRadioButton("4");
        group9.add(i1);
        group9.add(i2);
        group9.add(i3);
        group9.add(i4);
        buttons9.add(i1);
        buttons9.add(i2);
        buttons9.add(i3);
        buttons9.add(i4);
        
        JPanel row10 = new JPanel();
        JLabel label10 = new JLabel("Upcoming Assignment:");
        row10.add(label10);
        JTextField text10 = new JTextField(15);
        text10.setDocument (new JTextFieldLimit (15));
        row10.add(text10);
        inputScreen.add(row10);
        
        JPanel buttons10 = new JPanel();
        row10.add(buttons10);
        JRadioButton j1, j2, j3, j4;
        ButtonGroup group10 = new ButtonGroup();
        
        j1 = new JRadioButton("1");
        j2 = new JRadioButton("2");
        j3 = new JRadioButton("3");
        j4 = new JRadioButton("4");
        group10.add(j1);
        group10.add(j2);
        group10.add(j3);
        group10.add(j4);
        buttons10.add(j1);
        buttons10.add(j2);
        buttons10.add(j3);
        buttons10.add(j4);
        
        JPanel row11 = new JPanel();
        JLabel label11 = new JLabel("Upcoming Assignment:");
        row11.add(label11);
        JTextField text11 = new JTextField(15);
        text11.setDocument (new JTextFieldLimit (15));
        row11.add(text11);
        inputScreen.add(row11);
        
        JPanel buttons11 = new JPanel();
        row11.add(buttons11);
        JRadioButton k1, k2, k3, k4;
        ButtonGroup group11 = new ButtonGroup();
        
        k1 = new JRadioButton("1");
        k2 = new JRadioButton("2");
        k3 = new JRadioButton("3");
        k4 = new JRadioButton("4");
        group11.add(k1);
        group11.add(k2);
        group11.add(k3);
        group11.add(k4);
        buttons11.add(k1);
        buttons11.add(k2);
        buttons11.add(k3);
        buttons11.add(k4);
        
        JLabel distractText = new JLabel("Type in any distractions or bad habits you have that hinder your academic performance.");
        distractText.setFont(new Font("Serif", Font.BOLD, 20));
        inputScreen.add(distractText);
        
        JPanel row12 = new JPanel();
        JLabel label12 = new JLabel("Distraction:");
        row12.add(label12);
        JTextField text12 = new JTextField(15);
        text12.setDocument (new JTextFieldLimit (15));
        row12.add(text12);
        inputScreen.add(row12);
        
        JPanel row13 = new JPanel();
        JLabel label13 = new JLabel("Distraction:");
        row13.add(label13);
        JTextField text13 = new JTextField(15);
        text13.setDocument (new JTextFieldLimit (15));
        row13.add(text13);
        inputScreen.add(row13);
        
        JPanel row14 = new JPanel();
        JLabel label14 = new JLabel("Distraction:");
        row14.add(label14);
        JTextField text14 = new JTextField(15);
        text14.setDocument (new JTextFieldLimit (15));
        row14.add(text14);
        inputScreen.add(row14);
        
        JPanel row15 = new JPanel();
        JLabel label15 = new JLabel("Distraction:");
        row15.add(label15);
        JTextField text15 = new JTextField(15);
        text15.setDocument (new JTextFieldLimit (15));
        row15.add(text15);
        inputScreen.add(row15);
	   
        JPanel row16 = new JPanel();
        JLabel label16 = new JLabel("Distraction:");
        row16.add(label16);
        JTextField text16 = new JTextField(15);
        text16.setDocument (new JTextFieldLimit (15));
        row16.add(text16);
        inputScreen.add(row16);
        
		//Add all the buttons and panels to their appropriate containers
        JPanel homeMenu = new JPanel();
        homeMenu.setBackground(Color.GREEN);
		homeMenu.add(startButton);
		homeMenu.add(instructionButton);
		main.add(homeMenu);
		instructions.add(mainButton1);
		
	    //Create the home screen.
        Font titleFont = new Font("Serif", Font.BOLD, 150);
        JLabel title1 = new JLabel("WRECK", SwingConstants.CENTER);
        title1.setFont(titleFont);
        title1.setBackground(Color.CYAN);
        
        JLabel title2 = new JLabel("MY", SwingConstants.CENTER);
        title2.setFont(titleFont);
        title2.setBackground(Color.CYAN);
        
        JLabel title3 = new JLabel("GPA!", SwingConstants.CENTER);
        title3.setFont(titleFont);
        title3.setBackground(Color.CYAN);
        
        main.add(title1);
        main.add(title2);
        main.add(title3);
        
        //Create the instruction screen.
        JLabel heading = new JLabel("INSTRUCTIONS");
        heading.setFont(new Font("HELVETICA", Font.BOLD, 30));
        
        JTextArea body1 = new JTextArea("Welcome to Wreck My GPA! The goal of this game is to hopefully "
                + "not wreck your GPA. The goal of this game is to model your real experience "
                + "here at college. In the game, there will be squares that represent assignments "
                + "in your classes. Each will have a grade on them, and to complete the assignment"
                + " you will have to type in the code associated with each square. But beware, "
                + "take too long on typing an assignment and its final grade will wreck your "
                + "GPA! (It's sort of like procrastinating.)");
        
        JTextArea body2 = new JTextArea("When you start the game, you will be promted into an input "
                + "screen. Here, you will type in your GPA, followed by any upcoming assignments "
                + "you may have. Also check the corresponding box for the difficulty or "
                + "signigicance of the project on your GPA. Also, at the bottom type in any "
                + "distractions that have been hindering your academic performace, such as "
                + "Netflix or Parties. Feel free to fill in as many boxes as you want, "
                + "and you do not need to fill out all the prompts, but make sure to properly "
                + "check the corresponding number for any assignment you enter.");
        
        JTextArea body3 = new JTextArea("When the game loads after you finish entering in your "
                + "information, you will see your GPA and a text box at the top of the "
                + "screen. In the game itself, you will notice many flying squares. Each one "
                + "of these squares represents an upcoming academic assignment you entered. "
                + "To complete each assignment and collect the grade, just type in the code "
                + "you see on the square into the text box and hit enter. However, wait too "
                + "long and if a square that represents a grade hits another square, the grade "
                + "will drop. Longer codes will indicate a higher impact on your GPA, which "
                + "will update as the game continues. If square a D- and collides with another, "
                + "it will delete itself from the game and you will get an F factored into "
                + "your GPA! Also, you will notice bigger orange squares These represent "
                + "distractions, and you can get rid of them just like the others. Good luck,"
                + "and don't fail this semester!");
        
        Font bodyFont = new Font("Serif", Font.PLAIN, 25);
        
        body1.setFont(bodyFont);
        body2.setFont(bodyFont);
        body3.setFont(bodyFont);
        body1.setBackground(new Color(200, 200, 200));
        body2.setBackground(new Color(200, 200, 200));
        body3.setBackground(new Color(200, 200, 200));
        
        body1.setLineWrap(true);
        body1.setWrapStyleWord(true);
        body2.setLineWrap(true);
        body2.setWrapStyleWord(true);
        body3.setLineWrap(true);
        body3.setWrapStyleWord(true);
        
        instructions.add(heading);
        instructions.add(body1);
        instructions.add(body2);
        instructions.add(body3);
		
		//Add the panels to the card layout.
		cards.add(main, "Main");
		cards.add(instructions, "Instructions");
		cards.add(inputScreen, "Input");
		cards.add(game, "Game");
		c1.show(cards, "Main");
		
		//Add functionality to the buttons.
		mainButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(cards, "Main");
            }
        });
		
		mainButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(cards, "Main");
            }
        });
		
		instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(cards, "Instructions");
            }
        });
		
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(cards, "Input");
            }
        });
		
		//Add functionality to clear all user inputs.
		clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gpaResponse.setText("");
                group1.clearSelection();
                group2.clearSelection();
                group3.clearSelection();
                group4.clearSelection();
                group5.clearSelection();
                group6.clearSelection();
                group7.clearSelection();
                group8.clearSelection();
                group9.clearSelection();
                group10.clearSelection();
                group11.clearSelection();
                text1.setText("");
                text2.setText("");
                text3.setText("");
                text4.setText("");
                text5.setText("");
                text6.setText("");
                text7.setText("");
                text8.setText("");
                text9.setText("");
                text10.setText("");
                text11.setText("");
                text12.setText("");
                text13.setText("");
                text14.setText("");
                text15.setText("");
                text16.setText("");
            }
        });
		
		doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Save the data with a file writer. //Aided with StackOverFlow
                String desktopPath = System.getProperty("user.home") + "/Desktop/";
                FileWriter writer = null;
                try {
                   writer = new FileWriter(desktopPath + "jc1_gpa_and_class_info.txt");
                } catch (IOException ex) {
                    System.out.println("Error : " + ex);
                }
                PrintWriter printer = new PrintWriter(writer);
                printer.println(gpaResponse.getText());
                printer.println(text1.getText() + " , " + getSelectedButtonText(group1));
                printer.println(text2.getText() + " , " + getSelectedButtonText(group2));
                printer.println(text3.getText() + " , " + getSelectedButtonText(group3));
                printer.println(text4.getText() + " , " + getSelectedButtonText(group4));
                printer.println(text5.getText() + " , " + getSelectedButtonText(group5));
                printer.println(text6.getText() + " , " + getSelectedButtonText(group6));
                printer.println(text7.getText() + " , " + getSelectedButtonText(group7));
                printer.println(text8.getText() + " , " + getSelectedButtonText(group8));
                printer.println(text9.getText() + " , " + getSelectedButtonText(group9));
                printer.println(text10.getText() + " , " + getSelectedButtonText(group10));
                printer.println(text11.getText() + " , " + getSelectedButtonText(group11));
                printer.println(text12.getText());
                printer.println(text13.getText());
                printer.println(text14.getText());
                printer.println(text15.getText());
                printer.println(text16.getText());
                
                if (printer != null) {
                    printer.close();
                }
                c1.show(cards, "Game");
                try {
                    court.reset();
                } catch (Exception e1) {
                    throw new RuntimeException(e1);
                }
                textbox.requestFocus();
            }
        });
		
		quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c1.show(cards, "Main");
            }
        });
        
		//Add the card layout to the frame.
		frame.add(cards);
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		try {
            court.reset();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
	
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "";
    }
}
