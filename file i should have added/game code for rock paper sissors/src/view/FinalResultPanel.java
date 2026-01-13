package view;

import model.GameModel;
import model.GameModel.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * FinalResultPanel - The panel for displaying final game results.
 * Shows the overall winner of the game and provides Finish/Keep Going options.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>"GAME OVER" title</li>
 *   <li>"FINAL RESULT" subtitle</li>
 *   <li>Overall winner announcement (Red wins, Blue wins, or Tie)</li>
 *   <li>Final scores display</li>
 *   <li>"Finish" button to exit the program</li>
 *   <li>"Keep Going" button to continue playing</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class FinalResultPanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state */
    private GameModel gameModel;
    
    /** Button for finishing the game (exits program) */
    private JButton btnFinish;
    
    /** Button for continuing to play another game */
    private JButton btnKeepGoing;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Title text displayed at the top */
    private static final String TITLE = "GAME OVER";
    
    /** Subtitle text below title */
    private static final String SUBTITLE = "FINAL RESULT";
    
    /** Text for the finish button */
    private static final String FINISH_TEXT = "FINISH";
    
    /** Text for the keep going button */
    private static final String KEEP_GOING_TEXT = "KEEP GOING";
    
    /** Text for game tie */
    private static final String TIE_TEXT = "GAME ENDED IN A TIE!";
    
    /** Text for Red player winning */
    private static final String RED_WINS_TEXT = "RED WINS THE GAME!";
    
    /** Text for Blue player winning */
    private static final String BLUE_WINS_TEXT = "BLUE WINS THE GAME!";
    
    /** Font size for the main title */
    private static final int TITLE_FONT_SIZE = 64;
    
    /** Font size for the subtitle */
    private static final int SUBTITLE_FONT_SIZE = 48;
    
    /** Font size for the winner announcement */
    private static final int WINNER_FONT_SIZE = 56;
    
    /** Font size for the final score */
    private static final int SCORE_FONT_SIZE = 40;
    
    /** Font size for button text */
    private static final int BUTTON_FONT_SIZE = 32;
    
    /** Width of the buttons in pixels */
    private static final int BUTTON_WIDTH = 300;
    
    /** Height of the buttons in pixels */
    private static final int BUTTON_HEIGHT = 100;
    
    /** Spacing between buttons */
    private static final int BUTTON_SPACING = 30;
    
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
    
    /** Color for finish button (red/orange warning) */
    private static final Color FINISH_BUTTON_COLOR = new Color(255, 100, 100);
    
    /** Color for keep going button (green/positive) */
    private static final int KEEP_GOING_BUTTON_COLOR = 68;
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for FinalResultPanel.
     * Initializes the panel with game model and controller.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling button clicks
     */
    public FinalResultPanel(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize all buttons
        initializeButtons(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("FinalResultPanel initialized");
    }
    
    // ========================
    // BUTTON INITIALIZATION
    // ========================
    
    /**
     * Initializes all buttons with appropriate properties and listeners.
     * Creates Finish and Keep Going buttons.
     * 
     * @param controller The action listener for handling button clicks
     */
    private void initializeButtons(ActionListener controller) {
        // Calculate positions to center buttons
        int panelWidth = 1280; // Panel width
        int panelHeight = 720; // Panel height
        
        // Calculate horizontal center
        int centerX = panelWidth / 2;
        
        // Calculate vertical position for buttons (lower half of screen)
        int startY = panelHeight / 2 + 150;
        
        // Create Finish button (red/orange warning color)
        this.btnFinish = createButton(
            FINISH_TEXT,
            FINISH_BUTTON_COLOR,
            centerX - BUTTON_WIDTH - BUTTON_SPACING / 2, // x position (left of center)
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "finish_game" // Action command
        );
        
        // Create Keep Going button (green positive color)
        this.btnKeepGoing = createButton(
            KEEP_GOING_TEXT,
            new Color(KEEP_GOING_BUTTON_COLOR, 255, KEEP_GOING_BUTTON_COLOR),
            centerX + BUTTON_SPACING / 2, // x position (right of center)
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "keep_going" // Action command
        );
    }
    
    /**
     * Creates a custom button with text.
     * Helper method to reduce code duplication.
     * 
     * @param text The text for the button
     * @param color The background color of the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param controller The action listener for button clicks
     * @param actionCommand The command string for identifying the button
     * @return The created JButton
     */
    private JButton createButton(String text, Color color, 
                                 int x, int y, int width, int height,
                                 ActionListener controller, String actionCommand) {
        // Create the button
        JButton button = new JButton(text);
        
        // Set the position and size
        button.setBounds(x, y, width, height);
        
        // Set the font
        button.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        
        // Set the button colors
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        
        // Remove default button styling
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
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
     * Paints the panel with final result information.
     * Draws title, subtitle, winner announcement, and final scores.
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
        
        // Draw the main title
        drawTitle(g2d);
        
        // Draw the subtitle
        drawSubtitle(g2d);
        
        // Draw the winner announcement
        drawWinner(g2d);
        
        // Draw the final scores
        drawFinalScores(g2d);
    }
    
    /**
     * Draws the main title at the top of the screen.
     * Shows "GAME OVER" prominently.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawTitle(Graphics2D g2d) {
        // Set the font for the title
        Font titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
        g2d.setFont(titleFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(TITLE);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (top of screen with padding)
        int y = 100;
        
        // Draw the title text
        g2d.drawString(TITLE, x, y);
    }
    
    /**
     * Draws the subtitle below the main title.
     * Shows "FINAL RESULT".
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawSubtitle(Graphics2D g2d) {
        // Set the font for the subtitle
        Font subtitleFont = new Font("Arial", Font.BOLD, SUBTITLE_FONT_SIZE);
        g2d.setFont(subtitleFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(SUBTITLE);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (below the title)
        int y = 170;
        
        // Draw the subtitle text
        g2d.drawString(SUBTITLE, x, y);
    }
    
    /**
     * Draws the winner announcement in the center of the screen.
     * Shows which player won the game or if it was a tie.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawWinner(Graphics2D g2d) {
        // Get the game winner and tie status from the game model
        Player winner = gameModel.getGameWinner();
        boolean isTie = gameModel.isGameTie();
        
        // Create the winner text based on the game result
        String winnerText;
        Color winnerColor;
        
        // Check the game result
        if (isTie) {
            // It's a tie
            winnerText = TIE_TEXT;
            winnerColor = TIE_COLOR;
        } else if (winner == Player.RED) {
            // Red wins the game
            winnerText = RED_WINS_TEXT;
            winnerColor = RED_COLOR;
        } else if (winner == Player.BLUE) {
            // Blue wins the game
            winnerText = BLUE_WINS_TEXT;
            winnerColor = BLUE_COLOR;
        } else {
            // Should not happen, but handle gracefully
            winnerText = "GAME COMPLETE";
            winnerColor = TEXT_COLOR;
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
        
        // Set the y position (center of screen)
        int y = this.getHeight() / 2 - 20;
        
        // Draw the winner text
        g2d.drawString(winnerText, x, y);
    }
    
    /**
     * Draws the final scores below the winner announcement.
     * Shows the final score in "Red X - Blue Y" format.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawFinalScores(Graphics2D g2d) {
        // Get the final scores from the game model
        int redScore = gameModel.getRedScore();
        int blueScore = gameModel.getBlueScore();
        
        // Create the score text
        String scoreText = "Final Score: Red " + redScore + " - Blue " + blueScore;
        
        // Set the font for the score text
        Font scoreFont = new Font("Arial", Font.BOLD, SCORE_FONT_SIZE);
        g2d.setFont(scoreFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(scoreText);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (below the winner text)
        int y = this.getHeight() / 2 + 60;
        
        // Draw the score text
        g2d.drawString(scoreText, x, y);
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the finish button.
     * @return The JButton for finishing the game
     */
    public JButton getBtnFinish() {
        return this.btnFinish;
    }
    
    /**
     * Gets the keep going button.
     * @return The JButton for continuing to play
     */
    public JButton getBtnKeepGoing() {
        return this.btnKeepGoing;
    }
    
    /**
     * Gets the game model.
     * @return The GameModel instance
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
}