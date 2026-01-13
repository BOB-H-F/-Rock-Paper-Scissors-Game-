package view;

import model.GameModel;
import model.GameModel.GamePhase;
import model.GameModel.Player;
import model.GameModel.Choice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainView - The main view class for the Rock-Paper-Scissors game.
 * This class manages all UI components and panel swapping for the game.
 * It follows the Model-View-Controller paradigm where this class handles
 * all visual elements and user interface interactions.
 * 
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Creating and managing the main game frame</li>
 *   <li>Managing panel swapping for different game states</li>
 *   <li>Creating all game panels (menu, game, results, etc.)</li>
 *   <li>Handling 60fps animation timer</li>
 *   <li>Drawing game elements (shapes, scores, countdown)</li>
 *   <li>Managing user interface components (buttons, text fields)</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class MainView extends JFrame implements ActionListener {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state and logic */
    private GameModel gameModel;
    
    /** Timer for 60fps animation updates */
    private Timer animationTimer;
    
    /** Panel for the main menu screen */
    private MainMenuPanel mainMenuPanel;
    
    /** Panel for the create game screen (Red player) */
    private CreateGamePanel createGamePanel;
    
    /** Panel for the join game screen (Blue player) */
    private JoinGamePanel joinGamePanel;
    
    /** Panel for the actual gameplay */
    private GamePanel gamePanel;
    
    /** Panel for displaying round results */
    private ResultPanel resultPanel;
    
    /** Panel for displaying final game results */
    private FinalResultPanel finalResultPanel;
    
    /** Panel for the help screen with interactive demo */
    private HelpPanel helpPanel;
    
    /** Action listener to handle button clicks and events */
    private ActionListener controller;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Animation refresh rate in milliseconds (1000/60 = ~16.67ms for 60fps) */
    private static final int ANIMATION_DELAY = 16;
    
    /** Default window title */
    private static final String WINDOW_TITLE = "Rock Paper Scissors - Multiplayer";
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for MainView.
     * Initializes the main game window, all panels, and the animation timer.
     * 
     * @param gameModel The game model containing game state
     * @param controller The action listener for handling UI events
     */
    public MainView(GameModel gameModel, ActionListener controller) {
        // Store the game model reference
        this.gameModel = gameModel;
        // Store the controller reference
        this.controller = controller;
        
        // Set up the main window properties
        setupWindow();
        
        // Initialize all game panels
        initializePanels();
        
        // Create and start the animation timer for 60fps updates
        setupAnimationTimer();
        
        // Display the main menu panel initially
        showMainMenu();
        
        // Print message to console for debugging
        System.out.println("MainView initialized successfully");
    }
    
    // ========================
    // WINDOW SETUP METHODS
    // ========================
    
    /**
     * Sets up the main window properties including size, title, and close operation.
     * Ensures the window is centered on the screen and properly configured.
     */
    private void setupWindow() {
        // Set the window title
        this.setTitle(WINDOW_TITLE);
        // Set the window to close when X button is clicked
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the window not resizable (fixed size)
        this.setResizable(false);
        
        // Get the panel dimensions from the game model
        int width = gameModel.getPanelWidth();
        int height = gameModel.getPanelHeight();
        
        // Set the preferred size of the content pane
        this.getContentPane().setPreferredSize(new Dimension(width, height));
        
        // Pack the window to fit the preferred size
        this.pack();
        
        // Center the window on the screen
        this.setLocationRelativeTo(null);
        
        // Print window dimensions to console
        System.out.println("Window set up: " + width + "x" + height);
    }
    
    // ========================
    // PANEL INITIALIZATION
    // ========================
    
    /**
     * Initializes all game panels.
     * Creates instances of each panel type with appropriate parameters.
     */
    private void initializePanels() {
        // Create main menu panel with controller reference
        this.mainMenuPanel = new MainMenuPanel(controller);
        
        // Create game panel with game model and controller
        this.gamePanel = new GamePanel(gameModel, controller);
        
        // Create result panel with game model and controller
        this.resultPanel = new ResultPanel(gameModel, controller);
        
        // Create final result panel with game model and controller
        this.finalResultPanel = new FinalResultPanel(gameModel, controller);
        
        // Create create game panel with controller
        this.createGamePanel = new CreateGamePanel(gameModel, controller);
        
        // Create join game panel with controller
        this.joinGamePanel = new JoinGamePanel(gameModel, controller);
        
        // Create help panel with controller
        this.helpPanel = new HelpPanel(controller);
        
        // Print message to console
        System.out.println("All panels initialized");
    }
    
    // ========================
    // ANIMATION TIMER SETUP
    // ========================
    
    /**
     * Sets up the animation timer for 60fps updates.
     * The timer triggers repaints to ensure smooth animations.
     */
    private void setupAnimationTimer() {
        // Create a timer that fires every 16ms (~60fps)
        this.animationTimer = new Timer(ANIMATION_DELAY, this);
        // Start the animation timer
        this.animationTimer.start();
        
        // Print message to console
        System.out.println("Animation timer started at 60fps");
    }
    
    // ========================
    // ACTION LISTENER METHODS
    // ========================
    
    /**
     * Handles action events from the animation timer.
     * Repaints the current panel to update animations.
     * 
     * @param e The action event from the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the event is from the animation timer
        if (e.getSource() == animationTimer) {
            // Repaint the current panel to update animations
            this.getContentPane().repaint();
        }
    }
    
    // ========================
    // PANEL DISPLAY METHODS
    // ========================
    
    /**
     * Displays the main menu panel.
     * Shows the initial screen with game options.
     */
    public void showMainMenu() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the main menu panel
        this.getContentPane().add(mainMenuPanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing main menu panel");
    }
    
    /**
     * Displays the create game panel (Red player).
     * Shows the waiting screen with game code.
     */
    public void showCreateGame() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the create game panel
        this.getContentPane().add(createGamePanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing create game panel (Red player)");
    }
    
    /**
     * Displays the join game panel (Blue player).
     * Shows the code entry screen.
     */
    public void showJoinGame() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the join game panel
        this.getContentPane().add(joinGamePanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing join game panel (Blue player)");
    }
    
    /**
     * Displays the game panel.
     * Shows the main gameplay interface with countdown and choices.
     */
    public void showGame() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the game panel
        this.getContentPane().add(gamePanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing game panel");
    }
    
    /**
     * Displays the result panel.
     * Shows round results and opponent's choice.
     */
    public void showResult() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the result panel
        this.getContentPane().add(resultPanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing result panel");
    }
    
    /**
     * Displays the final result panel.
     * Shows game winner and finish/continue options.
     */
    public void showFinalResult() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the final result panel
        this.getContentPane().add(finalResultPanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing final result panel");
    }
    
    /**
     * Displays the help panel.
     * Shows interactive demo and game instructions.
     */
    public void showHelp() {
        // Remove any existing panel
        this.getContentPane().removeAll();
        // Add the help panel
        this.getContentPane().add(helpPanel);
        // Revalidate and repaint to update the display
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        
        // Print message to console
        System.out.println("Showing help panel");
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the main menu panel.
     * @return The MainMenuPanel instance
     */
    public MainMenuPanel getMainMenuPanel() {
        return this.mainMenuPanel;
    }
    
    /**
     * Gets the create game panel.
     * @return The CreateGamePanel instance
     */
    public CreateGamePanel getCreateGamePanel() {
        return this.createGamePanel;
    }
    
    /**
     * Gets the join game panel.
     * @return The JoinGamePanel instance
     */
    public JoinGamePanel getJoinGamePanel() {
        return this.joinGamePanel;
    }
    
    /**
     * Gets the game panel.
     * @return The GamePanel instance
     */
    public GamePanel getGamePanel() {
        return this.gamePanel;
    }
    
    /**
     * Gets the result panel.
     * @return The ResultPanel instance
     */
    public ResultPanel getResultPanel() {
        return this.resultPanel;
    }
    
    /**
     * Gets the final result panel.
     * @return The FinalResultPanel instance
     */
    public FinalResultPanel getFinalResultPanel() {
        return this.finalResultPanel;
    }
    
    /**
     * Gets the help panel.
     * @return The HelpPanel instance
     */
    public HelpPanel getHelpPanel() {
        return this.helpPanel;
    }
    
    /**
     * Gets the game model.
     * @return The GameModel instance
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }
    
    /**
     * Sets the controller for this view.
     * Allows the view to forward events to the controller.
     * 
     * @param controller The controller to set
     */
    public void setController(ActionListener controller) {
        this.controller = controller;

        // Propagate the controller to existing panels so their buttons work
        if (this.mainMenuPanel != null && controller != null) {
            try {
                this.mainMenuPanel.getBtnCreateGame().addActionListener(controller);
                this.mainMenuPanel.getBtnJoinGame().addActionListener(controller);
                this.mainMenuPanel.getBtnHelp().addActionListener(controller);
            } catch (Exception ignored) {}
        }

        if (this.joinGamePanel != null && controller != null) {
            try {
                this.joinGamePanel.getBtnConnect().addActionListener(controller);
                this.joinGamePanel.getTxtGameCode().addActionListener(controller);
            } catch (Exception ignored) {}
        }

        if (this.gamePanel != null && controller != null) {
            try {
                this.gamePanel.getBtnRock().addActionListener(controller);
                this.gamePanel.getBtnPaper().addActionListener(controller);
                this.gamePanel.getBtnScissors().addActionListener(controller);
            } catch (Exception ignored) {}
        }

        if (this.finalResultPanel != null && controller != null) {
            try {
                this.finalResultPanel.getBtnFinish().addActionListener(controller);
                this.finalResultPanel.getBtnKeepGoing().addActionListener(controller);
            } catch (Exception ignored) {}
        }

        if (this.helpPanel != null && controller != null) {
            try {
                this.helpPanel.getBtnRockDemo().addActionListener(controller);
                this.helpPanel.getBtnPaperDemo().addActionListener(controller);
                this.helpPanel.getBtnScissorsDemo().addActionListener(controller);
                this.helpPanel.getBtnBack().addActionListener(controller);
            } catch (Exception ignored) {}
        }
    }
}