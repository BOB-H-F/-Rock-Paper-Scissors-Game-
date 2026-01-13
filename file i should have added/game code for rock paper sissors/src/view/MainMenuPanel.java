package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * MainMenuPanel - The panel for the main menu screen.
 * Displays the game title and provides buttons for creating a game,
 * joining a game, and accessing help.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>Game title "Rock Paper Scissors - Multiplayer"</li>
 *   <li>Create New Game button (for Red player)</li>
 *   <li>Join Game button (for Blue player)</li>
 *   <li>Help button for interactive demo</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class MainMenuPanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** Button for creating a new game (Red player) */
    private JButton btnCreateGame;
    
    /** Button for joining an existing game (Blue player) */
    private JButton btnJoinGame;
    
    /** Button for accessing the help screen */
    private JButton btnHelp;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Title text displayed at the top of the screen */
    private static final String TITLE = "Rock Paper Scissors - Multiplayer";
    
    /** Text for the create game button */
    private static final String CREATE_GAME_TEXT = "Create New Game";
    
    /** Subtext for create game button showing player role */
    private static final String CREATE_GAME_SUBTEXT = "(Play as Red)";
    
    /** Text for the join game button */
    private static final String JOIN_GAME_TEXT = "Join Game";
    
    /** Subtext for join game button showing player role */
    private static final String JOIN_GAME_SUBTEXT = "(Play as Blue)";
    
    /** Text for the help button */
    private static final String HELP_TEXT = "Help";
    
    /** Subtext for help button describing the demo */
    private static final String HELP_SUBTEXT = "(Interactive Demo)";
    
    /** Font size for the main title */
    private static final int TITLE_FONT_SIZE = 64;
    
    /** Font size for button text */
    private static final int BUTTON_FONT_SIZE = 28;
    
    /** Font size for button subtext */
    private static final int SUBTEXT_FONT_SIZE = 20;
    
    /** Width of the buttons in pixels */
    private static final int BUTTON_WIDTH = 300;
    
    /** Height of the buttons in pixels */
    private static final int BUTTON_HEIGHT = 100;
    
    /** Vertical spacing between buttons in pixels */
    private static final int BUTTON_SPACING = 20;
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for the title and buttons (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for Red player button (red) */
    private static final Color RED_BUTTON_COLOR = new Color(255, 68, 68);
    
    /** Color for Blue player button (blue) */
    private static final Color BLUE_BUTTON_COLOR = new Color(68, 68, 255);
    
    /** Color for help button (neutral gray) */
    private static final Color HELP_BUTTON_COLOR = new Color(128, 128, 128);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for MainMenuPanel.
     * Initializes the panel with all UI components.
     * 
     * @param controller The action listener for handling button clicks
     */
    public MainMenuPanel(ActionListener controller) {
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize all buttons
        initializeButtons(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("MainMenuPanel initialized");
    }
    
    // ========================
    // BUTTON INITIALIZATION
    // ========================
    
    /**
     * Initializes all buttons with appropriate properties and listeners.
     * Creates and configures the create game, join game, and help buttons.
     * 
     * @param controller The action listener for handling button clicks
     */
    private void initializeButtons(ActionListener controller) {
        // Calculate vertical position to center buttons
        int panelHeight = 720; // Panel height
        int totalButtonHeight = (BUTTON_HEIGHT * 3) + (BUTTON_SPACING * 2);
        int startY = (panelHeight - totalButtonHeight) / 2;
        
        // Create Create Game button (Red player)
        this.btnCreateGame = createButton(
            CREATE_GAME_TEXT,
            CREATE_GAME_SUBTEXT,
            RED_BUTTON_COLOR,
            490, // x position (centered: 1280/2 - 300/2 = 490)
            startY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "create_game" // Action command
        );
        
        // Create Join Game button (Blue player)
        this.btnJoinGame = createButton(
            JOIN_GAME_TEXT,
            JOIN_GAME_SUBTEXT,
            BLUE_BUTTON_COLOR,
            490, // x position
            startY + BUTTON_HEIGHT + BUTTON_SPACING, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "join_game" // Action command
        );
        
        // Create Help button
        this.btnHelp = createButton(
            HELP_TEXT,
            HELP_SUBTEXT,
            HELP_BUTTON_COLOR,
            490, // x position
            startY + (BUTTON_HEIGHT + BUTTON_SPACING) * 2, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "show_help" // Action command
        );
    }
    
    /**
     * Creates a custom button with text and subtext.
     * Helper method to reduce code duplication.
     * 
     * @param text The main text for the button
     * @param subtext The subtext description
     * @param color The background color of the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param controller The action listener for button clicks
     * @param actionCommand The command string for identifying the button
     * @return The created JButton
     */
    private JButton createButton(String text, String subtext, Color color, 
                                 int x, int y, int width, int height,
                                 ActionListener controller, String actionCommand) {
        // Create a custom button with text and subtext
        CustomButton button = new CustomButton(text, subtext, color);
        
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
     * Paints the panel including the title.
     * Draws the main title at the top of the screen.
     * 
     * @param g The graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Call the parent class's paintComponent method
        super.paintComponent(g);
        
        // Enable anti-aliasing for smoother text rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw the main title
        drawTitle(g2d);
    }
    
    /**
     * Draws the main title at the top of the screen.
     * Centers the title horizontally and positions it near the top.
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
        
        // Set the y position (top of screen with some padding)
        int y = 100;
        
        // Draw the title text
        g2d.drawString(TITLE, x, y);
    }
    
    // ========================
    // INNER CLASS: CUSTOM BUTTON
    // ========================
    
    /**
     * CustomButton - A custom button class that displays text and subtext.
     * Provides a more visually appealing button with two lines of text.
     */
    private class CustomButton extends JButton {
        
        /** The main text displayed on the button */
        private String mainText;
        
        /** The subtext displayed below the main text */
        private String subText;
        
        /** The background color of the button */
        private Color buttonColor;
        
        /**
         * Constructor for CustomButton.
         * Creates a button with main text and subtext.
         * 
         * @param mainText The main text for the button
         * @param subText The subtext for the button
         * @param buttonColor The background color of the button
         */
        public CustomButton(String mainText, String subText, Color buttonColor) {
            // Store the text and color
            this.mainText = mainText;
            this.subText = subText;
            this.buttonColor = buttonColor;
            
            // Set up button properties
            this.setContentAreaFilled(false); // Don't fill background automatically
            this.setFocusPainted(false); // Remove focus border
            this.setBorderPainted(false); // Remove border
            this.setOpaque(false); // Make transparent
        }
        
        /**
         * Paints the custom button.
         * Draws the button background, main text, and subtext.
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
                Color lighterColor = lightenColor(buttonColor, 20);
                g2d.setColor(lighterColor);
            } else {
                // Use the normal color when not hovering
                g2d.setColor(buttonColor);
            }
            
            // Draw the button background (rounded rectangle)
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            
            // Draw the main text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
            FontMetrics fm = g2d.getFontMetrics();
            int mainTextWidth = fm.stringWidth(mainText);
            int mainTextX = (getWidth() - mainTextWidth) / 2;
            int mainTextY = getHeight() / 2 - 10;
            g2d.drawString(mainText, mainTextX, mainTextY);
            
            // Draw the subtext
            g2d.setFont(new Font("Arial", Font.PLAIN, SUBTEXT_FONT_SIZE));
            fm = g2d.getFontMetrics();
            int subTextWidth = fm.stringWidth(subText);
            int subTextX = (getWidth() - subTextWidth) / 2;
            int subTextY = mainTextY + 30;
            g2d.drawString(subText, subTextX, subTextY);
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
     * Gets the create game button.
     * @return The JButton for creating a game
     */
    public JButton getBtnCreateGame() {
        return this.btnCreateGame;
    }
    
    /**
     * Gets the join game button.
     * @return The JButton for joining a game
     */
    public JButton getBtnJoinGame() {
        return this.btnJoinGame;
    }
    
    /**
     * Gets the help button.
     * @return The JButton for showing help
     */
    public JButton getBtnHelp() {
        return this.btnHelp;
    }
}