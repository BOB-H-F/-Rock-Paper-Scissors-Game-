import controller.GameController;
import model.GameModel;
import view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * RockPaperScissorsGame - The main class for the Rock-Paper-Scissors multiplayer game.
 * This class serves as the entry point for the application and sets up the MVC architecture.
 * 
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Initializing the application on the Event Dispatch Thread</li>
 *   <li>Setting up the look and feel for consistent UI appearance</li>
 *   <li>Creating the Model, View, and Controller components</li>
 *   <li>Starting the game application</li>
 * </ul>
 * 
 * <p>The application follows the Model-View-Controller (MVC) paradigm:</p>
 * <ul>
 *   <li><b>Model</b>: GameModel - Contains all game state and logic</li>
 *   <li><b>View</b>: MainView and its panels - Handles all UI elements</li>
 *   <li><b>Controller</b>: GameController - Manages user input and game flow</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class RockPaperScissorsGame {
    
    // ========================
    // MAIN METHOD
    // ========================
    
    /**
     * The main entry point for the Rock-Paper-Scissors game application.
     * Launches the game on the Swing Event Dispatch Thread for thread safety.
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Use SwingUtilities to ensure UI creation happens on the Event Dispatch Thread
        // This is a best practice for Swing applications to ensure thread safety
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * The run method is called on the Event Dispatch Thread.
             * This is where we create and initialize all UI components.
             */
            @Override
            public void run() {
                // Set up the look and feel for consistent UI appearance across platforms
                setupLookAndFeel();
                
                // Create the Model component
                // The GameModel contains all game state, scores, and game logic
                GameModel gameModel = new GameModel();
                
                // Print message to console indicating model creation
                System.out.println("GameModel created successfully");
                
                // Create the View component
                // The MainView handles all UI panels and display management
                MainView mainView = new MainView(gameModel, null);
                
                // Print message to console indicating view creation
                System.out.println("MainView created successfully");
                
                // Create the Controller component
                // The GameController handles user input and coordinates between Model and View
                GameController gameController = new GameController(gameModel, mainView);
                
                // Print message to console indicating controller creation
                System.out.println("GameController created successfully");
                
                // Set the controller for the main view
                // This allows the view to forward events to the controller
                mainView.setController(gameController);
                
                // Print message to console indicating controller setup
                System.out.println("Controller set for MainView successfully");
                
                // Make the main window visible
                // This displays the game to the user
                mainView.setVisible(true);
                
                // Print message to console indicating application launch
                System.out.println("Rock-Paper-Scissors game launched successfully");
                System.out.println("===========================================");
                System.out.println("Game Instructions:");
                System.out.println("- Red player creates a game and gets a code");
                System.out.println("- Blue player joins using the code");
                System.out.println("- Make your choice during the countdown (3, 2, 1)");
                System.out.println("- Rock (circle) beats Scissors (triangle)");
                System.out.println("- Scissors (triangle) beats Paper (square)");
                System.out.println("- Paper (square) beats Rock (circle)");
                System.out.println("- Play 3 rounds, highest score wins!");
                System.out.println("===========================================");
            }
        });
    }
    
    // ========================
    // LOOK AND FEEL SETUP
    // ========================
    
    /**
     * Sets up the look and feel for the application.
     * Uses the system look and feel to match the native OS appearance.
     * This provides a more familiar and consistent user experience.
     */
    private static void setupLookAndFeel() {
        try {
            // Attempt to set the system look and feel
            // This makes the application look like a native application on each OS
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Print success message to console
            System.out.println("System look and feel set successfully");
        } catch (ClassNotFoundException e) {
            // Handle case where look and feel class is not found
            System.err.println("Look and Feel class not found: " + e.getMessage());
            System.err.println("Using default look and feel");
        } catch (InstantiationException e) {
            // Handle case where look and feel cannot be instantiated
            System.err.println("Could not instantiate Look and Feel: " + e.getMessage());
            System.err.println("Using default look and feel");
        } catch (IllegalAccessException e) {
            // Handle case where look and feel cannot be accessed
            System.err.println("Could not access Look and Feel: " + e.getMessage());
            System.err.println("Using default look and feel");
        } catch (UnsupportedLookAndFeelException e) {
            // Handle case where look and feel is not supported
            System.err.println("Look and Feel not supported: " + e.getMessage());
            System.err.println("Using default look and feel");
        }
    }
}