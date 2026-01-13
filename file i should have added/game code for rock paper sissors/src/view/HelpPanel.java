package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * HelpPanel - The panel for the help screen with interactive demo.
 * Displays game rules, instructions, and an interactive demo of choices.
 * 
 * <p>This panel shows:</p>
 * <ul>
 *   <li>"HOW TO PLAY" title</li>
 *   <li>Game rules with shape examples</li>
 *   <li>Step-by-step instructions for playing</li>
 *   <li>Interactive demo where clicking a choice shows what it beats</li>
 *   <li>"Back to Menu" button</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class HelpPanel extends JPanel {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** Button for Rock choice in interactive demo */
    private JButton btnRockDemo;
    
    /** Button for Paper choice in interactive demo */
    private JButton btnPaperDemo;
    
    /** Button for Scissors choice in interactive demo */
    private JButton btnScissorsDemo;
    
    /** Label for displaying demo results */
    private JLabel lblDemoResult;
    
    /** Button for returning to main menu */
    private JButton btnBack;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Title text displayed at the top */
    private static final String TITLE = "HOW TO PLAY";
    
    /** Text for the Rock demo button */
    private static final String ROCK_TEXT = "ROCK";
    
    /** Text for the Paper demo button */
    private static final String PAPER_TEXT = "PAPER";
    
    /** Text for the Scissors demo button */
    private static final String SCISSORS_TEXT = "SCISSORS";
    
    /** Text for the back button */
    private static final String BACK_TEXT = "Back to Menu";
    
    /** Default demo result text */
    private static final String DEFAULT_DEMO_RESULT = "Click a choice to see what it beats";
    
    /** Demo result for Rock */
    private static final String ROCK_BEATS = "ROCK (○) beats SCISSORS (▲)";
    
    /** Demo result for Paper */
    private static final String PAPER_BEATS = "PAPER (■) beats ROCK (○)";
    
    /** Demo result for Scissors */
    private static final String SCISSORS_BEATS = "SCISSORS (▲) beats PAPER (■)";
    
    /** Font size for the main title */
    private static final int TITLE_FONT_SIZE = 56;
    
    /** Font size for section headers */
    private static final int HEADER_FONT_SIZE = 28;
    
    /** Font size for body text */
    private static final int BODY_FONT_SIZE = 22;
    
    /** Font size for demo button text */
    private static final int BUTTON_FONT_SIZE = 24;
    
    /** Font size for demo result text */
    private static final int DEMO_RESULT_FONT_SIZE = 32;
    
    /** Size of shape icons in pixels */
    private static final int SHAPE_SIZE = 40;
    
    /** Width of demo buttons in pixels */
    private static final int BUTTON_WIDTH = 180;
    
    /** Height of demo buttons in pixels */
    private static final int BUTTON_HEIGHT = 120;
    
    /** Width of the back button in pixels */
    private static final int BACK_BUTTON_WIDTH = 250;
    
    /** Height of the back button in pixels */
    private static final int BACK_BUTTON_HEIGHT = 60;
    
    /** Spacing between demo buttons */
    private static final int BUTTON_SPACING = 30;
    
    /** Background color for the panel (dark gray) */
    private static final Color BACKGROUND_COLOR = new Color(44, 44, 44);
    
    /** Text color for labels (white) */
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    
    /** Color for Rock button (gray) */
    private static final Color ROCK_COLOR = new Color(100, 100, 100);
    
    /** Color for Paper button (gray) */
    private static final Color PAPER_COLOR = new Color(100, 100, 100);
    
    /** Color for Scissors button (gray) */
    private static final Color SCISSORS_COLOR = new Color(100, 100, 100);
    
    /** Color for back button (neutral) */
    private static final Color BACK_BUTTON_COLOR = new Color(128, 128, 128);
    
    /** Color for demo result text (yellow/gold) */
    private static final Color DEMO_RESULT_COLOR = new Color(255, 215, 0);
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for HelpPanel.
     * Initializes the panel with controller.
     * 
     * @param controller The action listener for handling button clicks
     */
    public HelpPanel(ActionListener controller) {
        // Set the layout to null for absolute positioning
        this.setLayout(null);
        
        // Set the background color
        this.setBackground(BACKGROUND_COLOR);
        
        // Initialize all buttons and labels
        initializeComponents(controller);
        
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(1280, 720));
        
        // Print message to console for debugging
        System.out.println("HelpPanel initialized");
    }
    
    // ========================
    // COMPONENT INITIALIZATION
    // ========================
    
    /**
     * Initializes all UI components with appropriate properties.
     * Creates demo buttons, result label, and back button.
     * 
     * @param controller The action listener for handling button clicks
     */
    private void initializeComponents(ActionListener controller) {
        // Calculate positions for components
        int panelWidth = 1280; // Panel width
        int panelHeight = 720; // Panel height
        
        // Calculate horizontal center
        int centerX = panelWidth / 2;
        
        // Create the demo result label
        this.lblDemoResult = new JLabel(DEFAULT_DEMO_RESULT, SwingConstants.CENTER);
        this.lblDemoResult.setBounds(0, panelHeight / 2 + 80, panelWidth, 50);
        this.lblDemoResult.setFont(new Font("Arial", Font.BOLD, DEMO_RESULT_FONT_SIZE));
        this.lblDemoResult.setForeground(DEMO_RESULT_COLOR);
        this.add(this.lblDemoResult);
        
        // Calculate positions for demo buttons (centered horizontally)
        int totalButtonWidth = (BUTTON_WIDTH * 3) + (BUTTON_SPACING * 2);
        int startX = centerX - totalButtonWidth / 2;
        int demoY = panelHeight / 2;
        
        // Create Rock demo button
        this.btnRockDemo = createDemoButton(
            ROCK_TEXT,
            ROCK_COLOR,
            "○", // Circle symbol
            startX, // x position
            demoY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "demo_rock" // Action command
        );
        
        // Create Paper demo button
        this.btnPaperDemo = createDemoButton(
            PAPER_TEXT,
            PAPER_COLOR,
            "■", // Square symbol
            startX + BUTTON_WIDTH + BUTTON_SPACING, // x position
            demoY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "demo_paper" // Action command
        );
        
        // Create Scissors demo button
        this.btnScissorsDemo = createDemoButton(
            SCISSORS_TEXT,
            SCISSORS_COLOR,
            "▲", // Triangle symbol
            startX + (BUTTON_WIDTH + BUTTON_SPACING) * 2, // x position
            demoY, // y position
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            controller,
            "demo_scissors" // Action command
        );
        
        // Create the back button (bottom of screen)
        this.btnBack = new JButton(BACK_TEXT);
        this.btnBack.setBounds(centerX - BACK_BUTTON_WIDTH / 2, panelHeight - 100,
                               BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        this.btnBack.setFont(new Font("Arial", Font.BOLD, 24));
        this.btnBack.setBackground(BACK_BUTTON_COLOR);
        this.btnBack.setForeground(Color.WHITE);
        this.btnBack.setFocusPainted(false);
        this.btnBack.setBorderPainted(false);
        this.btnBack.addActionListener(controller);
        this.btnBack.setActionCommand("back_to_menu");
        this.add(this.btnBack);
    }
    
    /**
     * Creates a demo button with text and shape symbol.
     * Helper method to reduce code duplication.
     * 
     * @param text The text for the button
     * @param color The background color of the button
     * @param symbol The shape symbol to display (○, ■, ▲)
     * @param x The x position of the button
     * @param y The y position of the button
     * @param width The width of the button
     * @param height The height of the button
     * @param controller The action listener for button clicks
     * @param actionCommand The command string for identifying the button
     * @return The created JButton
     */
    private JButton createDemoButton(String text, Color color, String symbol,
                                     int x, int y, int width, int height,
                                     ActionListener controller, String actionCommand) {
        // Create the button
        JButton button = new JButton(text + "\n" + symbol);
        
        // Set the position and size
        button.setBounds(x, y, width, height);
        
        // Set the font
        button.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        
        // Enable multi-line text
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        
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
     * Paints the panel with help information.
     * Draws title, rules, and instructions.
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
        
        // Draw the game rules section
        drawGameRules(g2d);
        
        // Draw the instructions section
        drawInstructions(g2d);
        
        // Draw the interactive demo section label
        drawDemoLabel(g2d);
    }
    
    /**
     * Draws the main title at the top of the screen.
     * Shows "HOW TO PLAY" prominently.
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
        int y = 70;
        
        // Draw the title text
        g2d.drawString(TITLE, x, y);
    }
    
    /**
     * Draws the game rules section.
     * Shows the Rock-Paper-Scissors rules with shape examples.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawGameRules(Graphics2D g2d) {
        // Set the font for the section header
        Font headerFont = new Font("Arial", Font.BOLD, HEADER_FONT_SIZE);
        g2d.setFont(headerFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Draw the section header
        String header = "Game Rules:";
        g2d.drawString(header, 50, 130);
        
        // Set the font for the rules
        Font bodyFont = new Font("Arial", Font.PLAIN, BODY_FONT_SIZE);
        g2d.setFont(bodyFont);
        
        // Draw each rule with shape examples
        String[] rules = {
            "• Rock beats Scissors  (○ beats ▲)",
            "• Scissors beats Paper   (▲ beats ■)",
            "• Paper beats Rock       (■ beats ○)"
        };
        
        // Draw each rule on a new line
        int startY = 170;
        for (int i = 0; i < rules.length; i++) {
            g2d.drawString(rules[i], 50, startY + (i * 35));
        }
    }
    
    /**
     * Draws the instructions section.
     * Shows step-by-step instructions for playing the game.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawInstructions(Graphics2D g2d) {
        // Set the font for the section header
        Font headerFont = new Font("Arial", Font.BOLD, HEADER_FONT_SIZE);
        g2d.setFont(headerFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Draw the section header
        String header = "How to Play:";
        g2d.drawString(header, 50, 280);
        
        // Set the font for the instructions
        Font bodyFont = new Font("Arial", Font.PLAIN, BODY_FONT_SIZE);
        g2d.setFont(bodyFont);
        
        // Draw each instruction on a new line
        String[] instructions = {
            "1. Red player creates game, gets code",
            "2. Blue player joins with code",
            "3. Countdown: 3, 2, 1 - Make your choice!",
            "4. See results after both players choose",
            "5. Play 3 rounds, highest score wins"
        };
        
        // Draw each instruction
        int startY = 320;
        for (int i = 0; i < instructions.length; i++) {
            g2d.drawString(instructions[i], 50, startY + (i * 35));
        }
    }
    
    /**
     * Draws the label for the interactive demo section.
     * Shows "Interactive Demo:" above the demo buttons.
     * 
     * @param g2d The graphics context for drawing
     */
    private void drawDemoLabel(Graphics2D g2d) {
        // Set the font for the section header
        Font headerFont = new Font("Arial", Font.BOLD, HEADER_FONT_SIZE);
        g2d.setFont(headerFont);
        
        // Set the text color
        g2d.setColor(TEXT_COLOR);
        
        // Draw the section header
        String header = "Interactive Demo:";
        
        // Get the font metrics to calculate text dimensions
        FontMetrics fm = g2d.getFontMetrics();
        
        // Calculate the x position to center the text
        int textWidth = fm.stringWidth(header);
        int x = (this.getWidth() - textWidth) / 2;
        
        // Set the y position (above the demo buttons)
        int y = this.getHeight() / 2 - 40;
        
        // Draw the section header
        g2d.drawString(header, x, y);
    }
    
    // ========================
    // PUBLIC METHODS
    // ========================
    
    /**
     * Updates the demo result label with the specified text.
     * Called when a demo button is clicked.
     * 
     * @param result The result text to display
     */
    public void updateDemoResult(String result) {
        // Update the label text
        this.lblDemoResult.setText(result);
        // Repaint to update the display
        this.repaint();
    }
    
    /**
     * Resets the demo result to the default text.
     * Called to clear the demo result.
     */
    public void resetDemoResult() {
        // Reset to default result text
        updateDemoResult(DEFAULT_DEMO_RESULT);
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the Rock demo button.
     * @return The JButton for Rock demo
     */
    public JButton getBtnRockDemo() {
        return this.btnRockDemo;
    }
    
    /**
     * Gets the Paper demo button.
     * @return The JButton for Paper demo
     */
    public JButton getBtnPaperDemo() {
        return this.btnPaperDemo;
    }
    
    /**
     * Gets the Scissors demo button.
     * @return The JButton for Scissors demo
     */
    public JButton getBtnScissorsDemo() {
        return this.btnScissorsDemo;
    }
    
    /**
     * Gets the demo result label.
     * @return The JLabel for displaying demo results
     */
    public JLabel getLblDemoResult() {
        return this.lblDemoResult;
    }
    
    /**
     * Gets the back button.
     * @return The JButton for returning to menu
     */
    public JButton getBtnBack() {
        return this.btnBack;
    }
}