package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * CreateGamePanel - The panel for the create game screen (Red player).
 * Displays a waiting screen with the game code that Blue player needs to join.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>"Waiting for Opponent" message</li>
 *   <li>"YOU ARE: RED" player identification</li>
 *   <li>Game code for sharing with Blue player</li>
 *   <li>Status message indicating waiting for Blue player to join</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class CreateGamePanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state */
    private GameModel gameModel;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Title text displayed at the top */
    private static final String TITLE = "WAITING FOR OPPONENT";
    
    /** Player identification text */
    private static final String PLAYER_TEXT = "YOU ARE: RED";
    
    /** Game code label text */
    private static final String CODE_LABEL = "Game Code:";
    
    /** Instruction text */
    private static final String INSTRUCTION = "Share this code with your friend";
    
    /** Waiting status text */
    private static final String WAITING_TEXT = "Waiting for Blue player to join...";
    
    /** Font size for the main title */
    private static final int TITLE_FONT_SIZE = 48;
    
    /** Font size for player text */
    private static final int PLAYER_FONT_SIZE = 36;
    
    /** Font size for game code */
    private static final int CODE_FONT_SIZE = 72;
    
    /** Font size for instructions */
    private static final int INSTRUCTION_FONT_SIZE = 28;
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for labels (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for Red player elements (bright red) */
    private static final Color RED_COLOR = new Color(255, 68, 68);
    
    /** Color for game code (yellow/gold) */
    private static final Color CODE_COLOR = new Color(255, 215, 0);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for CreateGamePanel.
     * Initializes the panel with game model and controller.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling events
     */
    public CreateGamePanel(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("CreateGamePanel initialized with game code: " + gameModel.getGameCode());
    }
    
    // ========================
    // PAINT METHOD
    // ========================
    
    /**
     * Paints the panel with waiting screen information.
     * Draws title, player identification, game code, and waiting message.
     * 
     * @param g The graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent class's paintComponent method
        super.paintComponent(g);
        
        // Create Graphics2D object for better drawing capabilities
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother text rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the main title
        drawTitle(g2d);
        
        // Draw the player identification
        drawPlayerIdentification(g2d);
        
        // Draw the game code
        drawGameCode(g2d);
        
        // Draw the instruction text
        drawInstruction(g2d);
        
        // Draw the waiting status
        drawWaitingStatus(g2d);
    }
    
    /**
     * Draws the main title at the top of the screen.
     * Centers the title horizontally.
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
        int y = 80;
        
        // Draw the title text
        g2d.drawString(TITLE, x, y);
    }
    
    /**
     * Draws the player identification showing this is the Red player.
     * Centers the text horizontally below the title.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawPlayerIdentification(Graphics2D g2d) {
        // Set the font for player text
        Font playerFont = new Font("Arial", Font.BOLD, PLAYER_FONT_SIZE);
        g2d.setFont(playerFont);
        
        // Set the text color to red
        g2d.setColor(RED_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(PLAYER_TEXT);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (below title)
        int y = 150;
        
        // Draw the player identification text
        g2d.drawString(PLAYER_TEXT, x, y);
    }
    
    /**
     * Draws the game code in the center of the screen.
     * Displays the 5-digit code prominently.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawGameCode(Graphics2D g2d) {
        // Get the game code from the game model
        String gameCode = gameModel.getGameCode();
        
        // Set the font for game code (large)
        Font codeFont = new Font("Courier New", Font.BOLD, CODE_FONT_SIZE);
        g2d.setFont(codeFont);
        
        // Set the text color to gold/yellow
        g2d.setColor(CODE_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(gameCode);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (center of screen)
        int y = this.getHeight() / 2 + 40; // +40 for vertical centering
        
        // Draw the game code
        g2d.drawString(gameCode, x, y);
    }
    
    /**
     * Draws the instruction text above the game code.
     * Tells the player to share the code with their friend.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawInstruction(Graphics2D g2d) {
        // Set the font for instruction text
        Font instructionFont = new Font("Arial", Font.PLAIN, INSTRUCTION_FONT_SIZE);
        g2d.setFont(instructionFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(INSTRUCTION);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (above the game code)
        int y = this.getHeight() / 2 - 60;
        
        // Draw the instruction text
        g2d.drawString(INSTRUCTION, x, y);
    }
    
    /**
     * Draws the waiting status message at the bottom of the screen.
     * Indicates that the system is waiting for the Blue player to join.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawWaitingStatus(Graphics2D g2d) {
        // Set the font for waiting text
        Font waitingFont = new Font("Arial", Font.ITALIC, INSTRUCTION_FONT_SIZE);
        g2d.setFont(waitingFont);
        
        // Set the text color to white with slight transparency
        g2d.setColor(new Color(255, 255, 255, 200));
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(WAITING_TEXT);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (bottom of screen)
        int y = this.getHeight() - 100;
        
        // Draw the waiting status text
        g2d.drawString(WAITING_TEXT, x, y);
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the game model.
     * @return The GameModel instance
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
}