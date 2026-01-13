package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JoinGamePanel - The panel for the join game screen (Blue player).
 * Displays an input field for entering the game code to join Red player's game.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>"JOIN A GAME" title</li>
 *   <li>"ENTER CODE BELOW" instruction</li>
 *   <li>Text field for entering the 5-digit game code</li>
 *   <li>"Connect to Game" button to submit the code</li>
 *   <li>Error message area for invalid codes</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class JoinGamePanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state */
    private GameModel gameModel;
    
    /** Text field for entering the game code */
    private JTextField txtGameCode;
    
    /** Button for connecting to the game */
    private JButton btnConnect;
    
    /** Label for displaying error messages */
    private JLabel lblError;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Title text displayed at the top */
    private static final String TITLE = "JOIN A GAME";
    
    /** Instruction text for code entry */
    private static final String INSTRUCTION = "ENTER CODE BELOW";
    
    /** Placeholder text for the code text field */
    private static final String CODE_PLACEHOLDER = "Enter Game Code";
    
    /** Text for the connect button */
    private static final String CONNECT_TEXT = "Connect to Game";
    
    /** Default error message (empty) */
    private static final String DEFAULT_ERROR = "";
    
    /** Error message for invalid code */
    private static final String INVALID_CODE_ERROR = "Invalid game code. Please try again.";
    
    /** Error message for connection failure */
    private static final String CONNECTION_ERROR = "Could not connect to game. Please check the code.";
    
    /** Font size for the main title */
    private static final int TITLE_FONT_SIZE = 64;
    
    /** Font size for instruction text */
    private static final int INSTRUCTION_FONT_SIZE = 36;
    
    /** Font size for text field */
    private static final int TEXT_FIELD_FONT_SIZE = 28;
    
    /** Font size for button text */
    private static final int BUTTON_FONT_SIZE = 32;
    
    /** Font size for error messages */
    private static final int ERROR_FONT_SIZE = 24;
    
    /** Width of the text field and button in pixels */
    private static final int COMPONENT_WIDTH = 400;
    
    /** Height of the text field in pixels */
    private static final int TEXT_FIELD_HEIGHT = 60;
    
    /** Height of the button in pixels */
    private static final int BUTTON_HEIGHT = 80;
    
    /** Vertical spacing between components */
    private static final int COMPONENT_SPACING = 30;
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for labels (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for text field background (lighter gray) */
    private static final Color TEXT_FIELD_BG = new Color(70, 70, 70);
    
    /** Color for connect button (blue for Blue player) */
    private static final Color CONNECT_BUTTON_COLOR = new Color(68, 68, 255);
    
    /** Color for error messages (red) */
    private static final Color ERROR_COLOR = new Color(255, 100, 100);
    
    /** Color for text field text (white) */
    private static final Color TEXT_FIELD_TEXT = new Color(255, 255, 255);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for JoinGamePanel.
     * Initializes the panel with game model and controller.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling button clicks
     */
    public JoinGamePanel(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize all UI components
        initializeComponents(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("JoinGamePanel initialized");
    }
    
    // ========================
    // COMPONENT INITIALIZATION
    // ========================
    
    /**
     * Initializes all UI components with appropriate properties.
     * Creates text field, button, and error label.
     * 
     * @param controller The action listener for handling button clicks
     */
    private void initializeComponents(ActionListener controller) {
        // Calculate positions to center components
        int panelWidth = 1280; // Panel width
        int panelHeight = 720; // Panel height
        
        // Calculate horizontal center
        int centerX = panelWidth / 2;
        int componentX = centerX - COMPONENT_WIDTH / 2;
        
        // Calculate vertical positions (center of screen)
        int startY = panelHeight / 2 - 80;
        
        // Create the instruction label
        JLabel lblInstruction = new JLabel(INSTRUCTION, SwingConstants.CENTER);
        lblInstruction.setBounds(0, startY - 80, panelWidth, 50);
        lblInstruction.setFont(new Font("Arial", Font.PLAIN, INSTRUCTION_FONT_SIZE));
        lblInstruction.setForeground(TEXT_COLOR);
        this.add(lblInstruction);
        
        // Create the game code text field
        this.txtGameCode = new JTextField(CODE_PLACEHOLDER);
        this.txtGameCode.setBounds(componentX, startY, COMPONENT_WIDTH, TEXT_FIELD_HEIGHT);
        this.txtGameCode.setFont(new Font("Courier New", Font.PLAIN, TEXT_FIELD_FONT_SIZE));
        this.txtGameCode.setBackground(TEXT_FIELD_BG);
        this.txtGameCode.setForeground(TEXT_FIELD_TEXT);
        this.txtGameCode.setCaretColor(TEXT_FIELD_TEXT);
        this.txtGameCode.setHorizontalAlignment(JTextField.CENTER);
        this.txtGameCode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.txtGameCode.addActionListener(controller); // Allow Enter key to submit
        this.txtGameCode.setActionCommand("connect_game");
        this.add(this.txtGameCode);
        
        // Create the connect button
        this.btnConnect = new JButton(CONNECT_TEXT);
        this.btnConnect.setBounds(componentX, startY + TEXT_FIELD_HEIGHT + COMPONENT_SPACING, 
                                  COMPONENT_WIDTH, BUTTON_HEIGHT);
        this.btnConnect.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        this.btnConnect.setBackground(CONNECT_BUTTON_COLOR);
        this.btnConnect.setForeground(Color.WHITE);
        this.btnConnect.setFocusPainted(false);
        this.btnConnect.setBorderPainted(false);
        this.btnConnect.addActionListener(controller);
        this.btnConnect.setActionCommand("connect_game");
        this.add(this.btnConnect);
        
        // Create the error label (initially empty)
        this.lblError = new JLabel(DEFAULT_ERROR, SwingConstants.CENTER);
        this.lblError.setBounds(0, startY + TEXT_FIELD_HEIGHT + COMPONENT_SPACING + BUTTON_HEIGHT + 20,
                                panelWidth, 40);
        this.lblError.setFont(new Font("Arial", Font.PLAIN, ERROR_FONT_SIZE));
        this.lblError.setForeground(ERROR_COLOR);
        this.lblError.setVisible(false); // Hide initially
        this.add(this.lblError);
    }
    
    // ========================
    // PAINT METHOD
    // ========================
    
    /**
     * Paints the panel with the title.
     * Draws the main title at the top of the screen.
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
        int y = 100;
        
        // Draw the title text
        g2d.drawString(TITLE, x, y);
    }
    
    // ========================
    // PUBLIC METHODS
    // ========================
    
    /**
     * Gets the game code entered by the user.
     * 
     * @return The game code as a string, or empty string if no code entered
     */
    public String getEnteredCode() {
        // Get the text from the text field
        String code = this.txtGameCode.getText();
        
        // Return the code, or empty string if null
        return code != null ? code : "";
    }
    
    /**
     * Clears the text field and removes focus.
     * Called after a connection attempt.
     */
    public void clearCodeField() {
        // Clear the text field
        this.txtGameCode.setText("");
        // Remove focus from the text field
        this.txtGameCode.transferFocus();
    }
    
    /**
     * Shows an error message to the user.
     * Displays the error label with the specified message.
     * 
     * @param error The error message to display
     */
    public void showError(String error) {
        // Set the error text
        this.lblError.setText(error);
        // Make the error label visible
        this.lblError.setVisible(true);
        // Repaint to update the display
        this.repaint();
    }
    
    /**
     * Hides the error message.
     * Called when clearing errors or starting a new connection attempt.
     */
    public void hideError() {
        // Hide the error label
        this.lblError.setVisible(false);
        // Clear the error text
        this.lblError.setText(DEFAULT_ERROR);
        // Repaint to update the display
        this.repaint();
    }
    
    /**
     * Shows the invalid code error message.
     * Convenience method for common error case.
     */
    public void showInvalidCodeError() {
        // Show the invalid code error
        showError(INVALID_CODE_ERROR);
    }
    
    /**
     * Shows the connection error message.
     * Convenience method for connection failure.
     */
    public void showConnectionError() {
        // Show the connection error
        showError(CONNECTION_ERROR);
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the game code text field.
     * @return The JTextField for entering game code
     */
    public JTextField getTxtGameCode() {
        return this.txtGameCode;
    }
    
    /**
     * Gets the connect button.
     * @return The JButton for connecting to game
     */
    public JButton getBtnConnect() {
        return this.btnConnect;
    }
    
    /**
     * Gets the error label.
     * @return The JLabel for displaying error messages
     */
    public JLabel getLblError() {
        return this.lblError;
    }
    
    /**
     * Gets the game model.
     * @return The GameModel instance
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
}