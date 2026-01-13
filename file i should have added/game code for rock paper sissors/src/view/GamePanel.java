package view;

import model.GameModel;
import model.GameModel.GamePhase;
import model.GameModel.Player;
import model.GameModel.Choice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GamePanel - The main game panel showing countdown, choices, and scores.
 * This panel displays the actual gameplay where players make their choices.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>Countdown timer (3, 2, 1) during countdown phase</li>
 *   <li>Three choice buttons: Rock (circle), Paper (square), Scissors (triangle)</li>
 *   <li>Scores displayed in corners (Red and Blue)</li>
 *   <li>Current round indicator</li>
 *   <li>Player choice visualization</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class GamePanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state */
    private GameModel gameModel;
    
    /** Button for Rock choice (circle shape) */
    private JButton btnRock;
    
    /** Button for Paper choice (square shape) */
    private JButton btnPaper;
    
    /** Button for Scissors choice (triangle shape) */
    private JButton btnScissors;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Text for the Rock button */
    private static final String ROCK_TEXT = "ROCK";
    
    /** Text for the Paper button */
    private static final String PAPER_TEXT = "PAPER";
    
    /** Text for the Scissors button */
    private static final String SCISSORS_TEXT = "SCISSORS";
    
    /** Font size for the countdown timer */
    private static final int COUNTDOWN_FONT_SIZE = 200;
    
    /** Font size for score display */
    private static final int SCORE_FONT_SIZE = 36;
    
    /** Font size for round indicator */
    private static final int ROUND_FONT_SIZE = 28;
    
    /** Font size for choice button text */
    private static final int BUTTON_FONT_SIZE = 24;
    
    /** Width of choice buttons in pixels */
    private static final int BUTTON_WIDTH = 200;
    
    /** Height of choice buttons in pixels */
    private static final int BUTTON_HEIGHT = 150;
    
    /** Size of shape icons in pixels */
    private static final int SHAPE_SIZE = 60;
    
    /** Spacing between choice buttons */
    private static final int BUTTON_SPACING = 30;
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for labels (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for Red player elements (bright red) */
    private static final Color RED_COLOR = new Color(255, 68, 68);
    
    /** Color for Blue player elements (bright blue) */
    private static final Color BLUE_COLOR = new Color(68, 68, 255);
    
    /** Color for Rock button (neutral gray) */
    private static final Color ROCK_BUTTON_COLOR = new Color(100, 100, 100);
    
    /** Color for Paper button (neutral gray) */
    private static final Color PAPER_BUTTON_COLOR = new Color(100, 100, 100);
    
    /** Color for Scissors button (neutral gray) */
    private static final Color SCISSORS_BUTTON_COLOR = new Color(100, 100, 100);
    
    /** Color for countdown timer (yellow) */
    private static final Color COUNTDOWN_COLOR = new Color(255, 215, 0);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for GamePanel.
     * Initializes the panel with game model and controller.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling button clicks
     */
    public GamePanel(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize all choice buttons
        initializeButtons(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("GamePanel initialized");
    }
    
    // ========================
    // BUTTON INITIALIZATION
    // ========================
    
    /**
     * Initializes all choice buttons with appropriate properties and listeners.
     * Creates Rock, Paper, and Scissors buttons with shape icons.
     * 
     * @param controller The action listener for handling button clicks
     */
    private void initializeButtons(ActionListener controller) {
        // Calculate horizontal position to center buttons
        int panelWidth = 1280; // Panel width
        int totalButtonWidth = (BUTTON_WIDTH * 3) + (BUTTON_SPACING * 2);
        int startX = (panelWidth - totalButtonWidth) / 2;
        
        // Calculate vertical position (lower half of screen)
        int panelHeight = 720;
        int startY = panelHeight / 2 + 50;
        
        // Create Rock button (circle shape)
        this.btnRock = createChoiceButton(
            ROCK_TEXT,
            ROCK_BUTTON_COLOR,
            Choice.ROCK,
            startX, // x position
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "choose_rock" // Action command
        );
        
        // Create Paper button (square shape)
        this.btnPaper = createChoiceButton(
            PAPER_TEXT,
            PAPER_BUTTON_COLOR,
            Choice.PAPER,
            startX + BUTTON_WIDTH + BUTTON_SPACING, // x position
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "choose_paper" // Action command
        );
        
        // Create Scissors button (triangle shape)
        this.btnScissors = createChoiceButton(
            SCISSORS_TEXT,
            SCISSORS_BUTTON_COLOR,
            Choice.SCISSORS,
            startX + (BUTTON_WIDTH + BUTTON_SPACING) * 2, // x position
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "choose_scissors" // Action command
        );
    }
    
    /**
     * Creates a choice button with text and shape icon.
     * Helper method to reduce code duplication.
     * 
     * @param text The text for the button
     * @param color The background color of the button
     * @param choice The game choice associated with this button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param controller The action listener for button clicks
     * @param actionCommand The command string for identifying the button
     * @return The created JButton
     */
    private JButton createChoiceButton(String text, Color color, Choice choice,
                                       int x, int y, int width, int height,
                                       ActionListener controller, String actionCommand) {
        // Create a custom choice button with shape icon
        ChoiceButton button = new ChoiceButton(text, color, choice);
        
        // Set the position and size
        button.setBounds(x, y, width, height);
        
        // Add the action listener
        button.addActionListener(controller);
        
        // Set the action command for identification
        button.setActionCommand(actionCommand);
        
        // Add the button to the panel
        this.add(button);
        
        // Return the created button
        return button;
    }
    
    // ========================
    // PAINT METHOD
    // ========================
    
    /**
     * Paints the panel including countdown, scores, and choices.
     * Draws all game elements based on the current game phase.
     * 
     * @param g The graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent class's paintComponent method
        super.paintComponent(g);
        
        // Create Graphics2D object for better drawing capabilities
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the scores in the corners
        drawScores(g2d);
        
        // Draw the current round indicator
        drawRoundIndicator(g2d);
        
        // Check the current game phase
        GamePhase phase = gameModel.getCurrentPhase();
        
        // Draw appropriate content based on game phase
        if (phase == GamePhase.COUNTDOWN) {
            // Draw the countdown timer
            drawCountdown(g2d);
        } else if (phase == GamePhase.CHOOSING) {
            // Draw player's current choice if made
            drawPlayerChoice(g2d);
        }
    }
    
    /**
     * Draws the scores in the corners of the screen.
     * Red score in top-left, Blue score in top-right.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawScores(Graphics2D g2d) {
        // Set the font for scores
        Font scoreFont = new Font("Arial", Font.BOLD, SCORE_FONT_SIZE);
        g2d.setFont(scoreFont);
        
        // Draw Red score in top-left corner
        g2d.setColor(RED_COLOR);
        String redScoreText = "RED SCORE: " + gameModel.getRedScore();
        g2d.drawString(redScoreText, 20, 50);
        
        // Draw Blue score in top-right corner
        g2d.setColor(BLUE_COLOR);
        String blueScoreText = "BLUE SCORE: " + gameModel.getBlueScore();
        // Calculate x position to align text to right
        FontMetrics fm = g2d.getFontMetrics();
        int blueScoreX = this.getWidth() - fm.stringWidth(blueScoreText) - 20;
        g2d.drawString(blueScoreText, blueScoreX, 50);
    }
    
    /**
     * Draws the current round indicator at the bottom of the screen.
     * Shows "Round X of 3" format.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawRoundIndicator(Graphics2D g2d) {
        // Set the font for round indicator
        Font roundFont = new Font("Arial", Font.PLAIN, ROUND_FONT_SIZE);
        g2d.setFont(roundFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Create the round text
        String roundText = "Round: " + gameModel.getCurrentRound() + " of " + gameModel.getMaxRounds();
        
        // Calculate the x position to center the text
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(roundText);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (bottom of screen)
        int y = this.getHeight() - 30;
        
        // Draw the round text
        g2d.drawString(roundText, x, y);
    }
    
    /**
     * Draws the countdown timer in the center of the screen.
     * Shows large numbers: 3, 2, 1.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawCountdown(Graphics2D g2d) {
        // Get the current countdown value
        int countdown = gameModel.getCountdownValue();
        
        // Only draw if countdown is greater than 0
        if (countdown > 0) {
            // Set the font for countdown
            Font countdownFont = new Font("Arial", Font.BOLD, COUNTDOWN_FONT_SIZE);
            g2d.setFont(countdownFont);
            
            // Set the text color
            g2d.setColor(COUNTDOWN_COLOR);
            
            // Convert countdown to string
            String countdownText = String.valueOf(countdown);
            
            // Calculate the x position to center the text
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(countdownText);
            int x = (this.getWidth() - textWidth) / 2;
            
            // Calculate the y position (center of screen)
            int y = this.getHeight() / 2 + 60; // +60 for vertical centering
            
            // Draw the countdown number
            g2d.drawString(countdownText, x, y);
        }
    }
    
    /**
     * Draws the player's current choice if they have made one.
     * Shows the shape icon for the chosen option.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawPlayerChoice(Graphics2D g2d) {
        // Get the player's choice based on their role
        Player myPlayer = gameModel.getMyPlayer();
        Choice choice = null;
        Color color = null;
        
        // Check which player this instance represents
        if (myPlayer == Player.RED) {
            // Get Red player's choice
            choice = gameModel.getRedChoice();
            color = RED_COLOR;
        } else if (myPlayer == Player.BLUE) {
            // Get Blue player's choice
            choice = gameModel.getBlueChoice();
            color = BLUE_COLOR;
        }
        
        // Only draw if the player has made a choice
        if (choice != null) {
            // Calculate position (center of upper half of screen)
            int x = this.getWidth() / 2 - SHAPE_SIZE / 2;
            int y = this.getHeight() / 4 - SHAPE_SIZE / 2;
            
            // Set the color
            g2d.setColor(color);
            
            // Draw the shape based on the choice
            if (choice == Choice.ROCK) {
                // Draw a circle for Rock
                g2d.fillOval(x, y, SHAPE_SIZE, SHAPE_SIZE);
            } else if (choice == Choice.PAPER) {
                // Draw a square for Paper
                g2d.fillRect(x, y, SHAPE_SIZE, SHAPE_SIZE);
            } else if (choice == Choice.SCISSORS) {
                // Draw a triangle for Scissors
                int[] xPoints = {x + SHAPE_SIZE / 2, x, x + SHAPE_SIZE};
                int[] yPoints = {y, y + SHAPE_SIZE, y + SHAPE_SIZE};
                g2d.fillPolygon(xPoints, yPoints, 3);
            }
        }
    }
    
    // ========================
    // INNER CLASS: CHOICE BUTTON
    // ========================
    
    /**
     * ChoiceButton - A custom button that displays text and a shape icon.
     * Shows the shape representation of each choice (circle, square, triangle).
     */
    private class ChoiceButton extends JButton {
        
        /** The text displayed on the button */
        private String buttonText;
        
        /** The background color of the button */
        private Color buttonColor;
        
        /** The game choice associated with this button */
        private Choice choice;
        
        /**
         * Constructor for ChoiceButton.
         * Creates a button with text and shape icon.
         * 
         * @param text The text for the button
         * @param buttonColor The background color of the button
         * @param choice The game choice associated with this button
         */
        public ChoiceButton(String text, Color buttonColor, Choice choice) {
            // Store the text, color, and choice
            this.buttonText = text;
            this.buttonColor = buttonColor;
            this.choice = choice;
            
            // Set up button properties
            this.setContentAreaFilled(false); // Don't fill background automatically
            this.setFocusPainted(false); // Remove focus border
            this.setBorderPainted(false); // Remove border
            this.setOpaque(false); // Make transparent
        }
        
        /**
         * Paints the custom choice button.
         * Draws the button background, shape icon, and text.
         * 
         * @param g The graphics context for painting
         */
        @Override
        protected void paintComponent(Graphics g) {
            // Create Graphics2D object for better drawing capabilities
            Graphics2D g2d = (Graphics2D) g;
            
            // Enable anti-aliasing for smoother rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Check if mouse is hovering over the button
            if (this.getModel().isRollover()) {
                // Lighten the color when hovering
                Color lighterColor = lightenColor(buttonColor, 30);
                g2d.setColor(lighterColor);
            } else {
                // Use the normal color when not hovering
                g2d.setColor(buttonColor);
            }
            
            // Draw the button background (rounded rectangle)
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            
            // Draw the shape icon
            drawShape(g2d);
            
            // Draw the button text below the shape
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(buttonText);
            int textX = (getWidth() - textWidth) / 2;
            int textY = getHeight() - 20;
            g2d.drawString(buttonText, textX, textY);
        }
        
        /**
         * Draws the shape icon based on the choice.
         * Circle for Rock, square for Paper, triangle for Scissors.
         * 
         * @param g2d The graphics context for drawing
         */
        private void drawShape(Graphics2D g2d) {
            // Calculate position for the shape (center of upper portion)
            int shapeX = (getWidth() - SHAPE_SIZE) / 2;
            int shapeY = 20; // Top padding
            
            // Set the shape color
            g2d.setColor(Color.WHITE);
            
            // Draw the appropriate shape based on the choice
            if (choice == Choice.ROCK) {
                // Draw a circle for Rock
                g2d.fillOval(shapeX, shapeY, SHAPE_SIZE, SHAPE_SIZE);
            } else if (choice == Choice.PAPER) {
                // Draw a square for Paper
                g2d.fillRect(shapeX, shapeY, SHAPE_SIZE, SHAPE_SIZE);
            } else if (choice == Choice.SCISSORS) {
                // Draw a triangle for Scissors
                int[] xPoints = {shapeX + SHAPE_SIZE / 2, shapeX, shapeX + SHAPE_SIZE};
                int[] yPoints = {shapeY, shapeY + SHAPE_SIZE, shapeY + SHAPE_SIZE};
                g2d.fillPolygon(xPoints, yPoints, 3);
            }
        }
        
        /**
         * Lightens a color by a specified amount.
         * Helper method for creating hover effects.
         * 
         * @param color The color to lighten
         * @param amount The amount to lighten (0-255)
         * @return The lightened color
         */
        private Color lightenColor(Color color, int amount) {
            // Get the RGB components
            int red = Math.min(255, color.getRed() + amount);
            int green = Math.min(255, color.getGreen() + amount);
            int blue = Math.min(255, color.getBlue() + amount);
            
            // Return new color with adjusted values
            return new Color(red, green, blue);
        }
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the Rock button.
     * @return The JButton for Rock choice
     */
    public JButton getBtnRock() {
        return this.btnRock;
    }
    
    /**
     * Gets the Paper button.
     * @return The JButton for Paper choice
     */
    public JButton getBtnPaper() {
        return this.btnPaper;
    }
    
    /**
     * Gets the Scissors button.
     * @return The JButton for Scissors choice
     */
    public JButton getBtnScissors() {
        return this.btnScissors;
    }
}