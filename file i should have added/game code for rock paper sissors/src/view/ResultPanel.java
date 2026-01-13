package view;

import model.GameModel;
import model.GameModel.Player;
import model.GameModel.Choice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * ResultPanel - The panel for displaying round results.
 * Shows the winner of the round, both players' choices, and updated scores.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>"ROUND X RESULT" title</li>
 *   <li>Round winner announcement (Red wins, Blue wins, or Tie)</li>
 *   <li>Both players' choices with shape icons</li>
 *   <li>Updated scores in corners</li>
 *   <li>Auto-transition to next round</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class ResultPanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state */
    private GameModel gameModel;
    
    /** Timer for auto-transitioning to next round */
    private Timer transitionTimer;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Font size for the result title */
    private static final int TITLE_FONT_SIZE = 48;
    
    /** Font size for the winner announcement */
    private static final int WINNER_FONT_SIZE = 56;
    
    /** Font size for choice labels */
    private static final int CHOICE_FONT_SIZE = 32;
    
    /** Font size for score display */
    private static final int SCORE_FONT_SIZE = 36;
    
    /** Size of shape icons in pixels */
    private static final int SHAPE_SIZE = 100;
    
    /** Delay before transitioning to next round (in milliseconds) */
    private static final int TRANSITION_DELAY = 3000; // 3 seconds
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for labels (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for Red player elements (bright red) */
    private static final Color RED_COLOR = new Color(255, 68, 68);
    
    /** Color for Blue player elements (bright blue) */
    private static final Color BLUE_COLOR = new Color(68, 68, 255);
    
    /** Color for tie message (yellow) */
    private static final Color TIE_COLOR = new Color(255, 215, 0);
    
    /** Color for winner text (green) */
    private static final Color WINNER_COLOR = new Color(68, 255, 68);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for ResultPanel.
     * Initializes the panel with game model and controller.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling events
     */
    public ResultPanel(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize the transition timer
        initializeTransitionTimer(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("ResultPanel initialized");
    }
    
    // ========================
    // TIMER INITIALIZATION
    // ========================
    
    /**
     * Initializes the transition timer for auto-advancing to next round.
     * Creates a timer that triggers after the specified delay.
     * 
     * @param controller The action listener for handling timer events
     */
    private void initializeTransitionTimer(ActionListener controller) {
        // Create a timer that fires after the transition delay
        this.transitionTimer = new Timer(TRANSITION_DELAY, controller);
        // Set the action command for identifying the timer event
        this.transitionTimer.setActionCommand("next_round");
        // Set timer to only fire once (not repeating)
        this.transitionTimer.setRepeats(false);
    }
    
    // ========================
    // PUBLIC METHODS
    // ========================
    
    /**
     * Starts the transition timer to auto-advance to next round.
     * Called when the panel is displayed.
     */
    public void startTransitionTimer() {
        // Start the transition timer
        this.transitionTimer.start();
        // Print message to console
        System.out.println("Transition timer started - will advance to next round in " + 
                          TRANSITION_DELAY / 1000 + " seconds");
    }
    
    /**
     * Stops the transition timer.
     * Called if user interacts or to cancel auto-transition.
     */
    public void stopTransitionTimer() {
        // Stop the transition timer if it's running
        if (this.transitionTimer.isRunning()) {
            this.transitionTimer.stop();
            // Print message to console
            System.out.println("Transition timer stopped");
        }
    }
    
    // ========================
    // PAINT METHOD
    // ========================
    
    /**
     * Paints the panel with round result information.
     * Draws title, winner announcement, player choices, and scores.
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
        
        // Draw the round title
        drawTitle(g2d);
        
        // Draw the winner announcement
        drawWinner(g2d);
        
        // Draw the player choices
        drawPlayerChoices(g2d);
        
        // Draw the scores in the corners
        drawScores(g2d);
    }
    
    /**
     * Draws the round title at the top of the screen.
     * Shows "ROUND X RESULT" format.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawTitle(Graphics2D g2d) {
        // Create the title text with current round number
        String titleText = "ROUND " + gameModel.getCurrentRound() + " RESULT";
        
        // Set the font for the title
        Font titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
        g2d.setFont(titleFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(titleText);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (top of screen with padding)
        int y = 80;
        
        // Draw the title text
        g2d.drawString(titleText, x, y);
    }
    
    /**
     * Draws the winner announcement in the center of the screen.
     * Shows which player won the round or if it was a tie.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawWinner(Graphics2D g2d) {
        // Get the round winner from the game model
        Player winner = gameModel.getRoundWinner();
        
        // Create the winner text based on the winner
        String winnerText;
        Color winnerColor;
        
        // Check who won the round
        if (winner == Player.RED) {
            // Red wins
            winnerText = "RED WINS THIS ROUND!";
            winnerColor = RED_COLOR;
        } else if (winner == Player.BLUE) {
            // Blue wins
            winnerText = "BLUE WINS THIS ROUND!";
            winnerColor = BLUE_COLOR;
        } else {
            // It's a tie
            winnerText = "IT'S A TIE!";
            winnerColor = TIE_COLOR;
        }
        
        // Set the font for the winner text
        Font winnerFont = new Font("Arial", Font.BOLD, WINNER_FONT_SIZE);
        g2d.setFont(winnerFont);
        
        // Set the text color based on the winner
        g2d.setColor(winnerColor);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(winnerText);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (upper center of screen)
        int y = this.getHeight() / 3 + 20;
        
        // Draw the winner text
        g2d.drawString(winnerText, x, y);
    }
    
    /**
     * Draws both players' choices with shape icons.
     * Shows what each player chose for this round.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawPlayerChoices(Graphics2D g2d) {
        // Get both players' choices
        Choice redChoice = gameModel.getRedChoice();
        Choice blueChoice = gameModel.getBlueChoice();
        
        // Calculate positions for the two choices (left and right sides)
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2 + 50;
        int spacing = 300; // Space between the two choices
        
        // Draw Red player's choice on the left
        if (redChoice != null) {
            drawChoice(g2d, redChoice, "Your Choice", RED_COLOR, 
                      centerX - spacing, centerY);
        }
        
        // Draw Blue player's choice on the right
        if (blueChoice != null) {
            drawChoice(g2d, blueChoice, "Opponent's Choice", BLUE_COLOR, 
                      centerX + spacing - SHAPE_SIZE, centerY);
        }
    }
    
    /**
     * Draws a single player's choice with label and shape.
     * Helper method to reduce code duplication.
     * 
     * @param g2d The graphics context for drawing
     * @param choice The player's choice
     * @param label The label text for this choice
     * @param color The color for this player
     * @param x The x position for the choice
     * @param y The y position for the choice
     */
    private void drawChoice(Graphics2D g2d, Choice choice, String label, 
                           Color color, int x, int y) {
        // Draw the label text
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.BOLD, CHOICE_FONT_SIZE));
        FontMetrics fm = g2d.getFontMetrics();
        int labelX = x + (SHAPE_SIZE - fm.stringWidth(label)) / 2;
        g2d.drawString(label, labelX, y - 20);
        
        // Draw the shape based on the choice
        g2d.setColor(color);
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
        
        // Draw the choice name below the shape
        String choiceName = choice.name();
        g2d.setFont(new Font("Arial", Font.PLAIN, CHOICE_FONT_SIZE));
        fm = g2d.getFontMetrics();
        int nameX = x + (SHAPE_SIZE - fm.stringWidth(choiceName)) / 2;
        g2d.drawString(choiceName, nameX, y + SHAPE_SIZE + 30);
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
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the transition timer.
     * @return The Timer for auto-transitioning to next round
     */
    public Timer getTransitionTimer() {
        return this.transitionTimer;
    }
    
    /**
     * Gets the game model.
     * @return The GameModel instance
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
}