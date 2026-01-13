package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameModel - The model class for the Rock-Paper-Scissors multiplayer game.
 * This class manages all game state, including scores, rounds, player choices,
 * and game configuration. It follows the Model-View-Controller paradigm.
 * 
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Tracking game state (waiting, countdown, playing, results, game over)</li>
 *   <li>Managing player scores (Red and Blue)</li>
 *   <li>Tracking current round number (1-3)</li>
 *   <li>Storing player choices for each round</li>
 *   <li>Loading game configuration from data file</li>
 *   <li>Determining round winners based on Rock-Paper-Scissors rules</li>
 *   <li>Determining overall game winner</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class GameModel {
    
    // ========================
    // ENUMERATIONS
    // ========================
    
    /**
     * GamePhase enumeration represents the different states/phases of the game.
     * Each phase determines what actions are available to players.
     */
    public enum GamePhase {
        /** Waiting for opponent to join the game */
        WAITING,
        /** Countdown timer is active (3, 2, 1) */
        COUNTDOWN,
        /** Players are making their choices */
        CHOOSING,
        /** Displaying round results */
        RESULTS,
        /** Game is complete, showing final results */
        GAME_OVER
    }
    
    /**
     * Player enumeration represents the two players in the game.
     * Red player creates the game, Blue player joins the game.
     */
    public enum Player {
        /** Red player - creates the game and acts as server */
        RED,
        /** Blue player - joins the game and acts as client */
        BLUE
    }
    
    /**
     * Choice enumeration represents the three possible choices in Rock-Paper-Scissors.
     * Each choice has a corresponding shape for visual representation.
     */
    public enum Choice {
        /** Rock choice - represented by a circle shape */
        ROCK("circle"),
        /** Paper choice - represented by a square shape */
        PAPER("square"),
        /** Scissors choice - represented by a triangle shape */
        SCISSORS("triangle");
        
        /** The shape associated with this choice */
        private final String shape;
        
        /**
         * Constructor for Choice enumeration.
         * @param shape The shape representation of this choice
         */
        Choice(String shape) {
            this.shape = shape;
        }
        
        /**
         * Gets the shape representation of this choice.
         * @return The shape as a string ("circle", "square", or "triangle")
         */
        public String getShape() {
            return this.shape;
        }
        
        /**
         * Determines if this choice beats another choice.
         * Rock beats Scissors, Scissors beats Paper, Paper beats Rock.
         * @param other The other choice to compare against
         * @return true if this choice beats the other choice, false otherwise
         */
        public boolean beats(Choice other) {
            // Rock beats Scissors
            if (this == ROCK && other == SCISSORS) {
                return true;
            }
            // Scissors beats Paper
            if (this == SCISSORS && other == PAPER) {
                return true;
            }
            // Paper beats Rock
            if (this == PAPER && other == ROCK) {
                return true;
            }
            // Otherwise, this choice does not beat the other
            return false;
        }
    }
    
    // ========================
    // GAME STATE PROPERTIES
    // ========================
    
    /** Current phase of the game (waiting, countdown, choosing, results, game over) */
    private GamePhase currentPhase;
    
    /** The player role of this instance (RED or BLUE) */
    private Player myPlayer;
    
    /** Current round number (starts at 1, goes up to max rounds) */
    private int currentRound;
    
    /** Maximum number of rounds in the game (default is 3) */
    private int maxRounds;
    
    /** Red player's score (increases when Red wins a round) */
    private int redScore;
    
    /** Blue player's score (increases when Blue wins a round) */
    private int blueScore;
    
    /** Countdown timer value in seconds (starts at 3, counts down to 0) */
    private int countdownValue;
    
    /** Flag indicating if Red player has made a choice for the current round */
    private boolean redChosen;
    
    /** Flag indicating if Blue player has made a choice for the current round */
    private boolean blueChosen;
    
    /** Red player's choice for the current round (null if not chosen yet) */
    private Choice redChoice;
    
    /** Blue player's choice for the current round (null if not chosen yet) */
    private Choice blueChoice;
    
    /** Winner of the current round (null if round not complete) */
    private Player roundWinner;
    
    /** Overall winner of the game (null if game not complete) */
    private Player gameWinner;
    
    /** Flag indicating if the game ended in a tie */
    private boolean gameTie;
    
    // ========================
    // CONFIGURATION PROPERTIES
    // ========================
    
    /** Map to store game configuration values loaded from data file */
    private Map<String, String> config;
    
    /** Network port number for socket connection */
    private int port;
    
    /** Game code for connecting players */
    private String gameCode;
    
    /** Panel width in pixels */
    private int panelWidth;
    
    /** Panel height in pixels */
    private int panelHeight;
    
    /** Animation refresh rate in frames per second */
    private int refreshRate;
    
    // ========================
    // CONSTRUCTORS
    // ========================
    
    /**
     * Default constructor for GameModel.
     * Initializes the game with default values and loads configuration from file.
     * Sets the current phase to WAITING and prepares the game for setup.
     */
    public GameModel() {
        // Initialize configuration map
        this.config = new HashMap<>();
        // Load game configuration from data file
        loadConfiguration();
        // Initialize game state with default values
        initializeGame();
    }
    
    /**
     * Parameterized constructor for GameModel that sets the player role.
     * Initializes the game with the specified player role and loads configuration.
     * 
     * @param player The player role (RED or BLUE) for this instance
     */
    public GameModel(Player player) {
        // Initialize configuration map
        this.config = new HashMap<>();
        // Load game configuration from data file
        loadConfiguration();
        // Set the player role
        this.myPlayer = player;
        // Initialize game state
        initializeGame();
    }
    
    // ========================
    // INITIALIZATION METHODS
    // ========================
    
    /**
     * Initializes the game state to default values.
     * Resets scores, rounds, choices, and sets phase to WAITING.
     */
    private void initializeGame() {
        // Set initial phase to waiting for opponent
        this.currentPhase = GamePhase.WAITING;
        // Set current round to 1 (first round)
        this.currentRound = 1;
        // Initialize max rounds from configuration or default to 3
        this.maxRounds = Integer.parseInt(config.getOrDefault("max_rounds", "3"));
        // Reset both players' scores to 0
        this.redScore = 0;
        this.blueScore = 0;
        // Reset countdown to default value (3 seconds)
        this.countdownValue = Integer.parseInt(config.getOrDefault("countdown_seconds", "3"));
        // Reset choice flags (no players have chosen yet)
        this.redChosen = false;
        this.blueChosen = false;
        // Reset choices (no choices made yet)
        this.redChoice = null;
        this.blueChoice = null;
        // Reset round winner
        this.roundWinner = null;
        // Reset game winner
        this.gameWinner = null;
        // Reset game tie flag
        this.gameTie = false;
        // Load panel dimensions from configuration
        this.panelWidth = Integer.parseInt(config.getOrDefault("panel_width", "1280"));
        this.panelHeight = Integer.parseInt(config.getOrDefault("panel_height", "720"));
        // Load refresh rate from configuration
        this.refreshRate = Integer.parseInt(config.getOrDefault("refresh_rate", "60"));
        // Load port number from configuration
        this.port = Integer.parseInt(config.getOrDefault("default_port", "1337"));
        // Generate random game code
        this.gameCode = generateGameCode();
    }
    
    /**
     * Loads game configuration from the gameconfig.csv data file.
     * Parses the file and stores key-value pairs in the configuration map.
     */
    private void loadConfiguration() {
        // Try to read the configuration file
        try (BufferedReader reader = new BufferedReader(new FileReader("data/gameconfig.csv"))) {
            String line; // Variable to hold each line from the file
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line by comma to get key and value
                String[] parts = line.split(",");
                // Check if the line has both key and value
                if (parts.length >= 2) {
                    // Store the key-value pair in the configuration map
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
            // Print success message when configuration loaded
            System.out.println("Game configuration loaded successfully from data file");
        } catch (IOException e) {
            // Print error message if file cannot be read
            System.err.println("Error loading game configuration: " + e.getMessage());
            // Set default values if configuration file cannot be loaded
            setDefaultConfiguration();
        }
    }
    
    /**
     * Sets default configuration values when configuration file cannot be loaded.
     * Ensures the game can still run with sensible defaults.
     */
    private void setDefaultConfiguration() {
        // Set default game name
        config.put("game_name", "Rock Paper Scissors");
        // Set default max rounds
        config.put("max_rounds", "3");
        // Set default countdown seconds
        config.put("countdown_seconds", "3");
        // Set default shapes
        config.put("rock_shape", "circle");
        config.put("paper_shape", "square");
        config.put("scissors_shape", "triangle");
        // Set default colors
        config.put("red_player_color", "255,0,0");
        config.put("blue_player_color", "0,0,255");
        // Set default panel dimensions
        config.put("panel_width", "1280");
        config.put("panel_height", "720");
        // Set default refresh rate
        config.put("refresh_rate", "60");
        // Set default port
        config.put("default_port", "1337");
        // Print message indicating defaults are being used
        System.out.println("Using default configuration values");
    }
    
    /**
     * Generates a random 5-digit game code for players to connect.
     * The code is numeric and should be shared between players.
     * 
     * @return A 5-digit numeric string representing the game code
     */
    private String generateGameCode() {
        // Generate random number between 10000 and 99999
        int code = (int)(Math.random() * 90000) + 10000;
        // Convert to string and return
        return String.valueOf(code);
    }
    
    // ========================
    // GAME STATE MANAGEMENT
    // ========================
    
    /**
     * Starts the countdown for the current round.
     * Changes the game phase to COUNTDOWN and sets the countdown value.
     */
    public void startCountdown() {
        // Change phase to countdown
        this.currentPhase = GamePhase.COUNTDOWN;
        // Reset countdown to starting value
        this.countdownValue = Integer.parseInt(config.getOrDefault("countdown_seconds", "3"));
        // Print message to console for debugging
        System.out.println("Countdown started for Round " + currentRound);
    }
    
    /**
     * Decrements the countdown timer by one second.
     * Should be called every second during the countdown phase.
     * 
     * @return The new countdown value
     */
    public int decrementCountdown() {
        // Decrease countdown value by 1
        this.countdownValue--;
        // Check if countdown has reached zero
        if (this.countdownValue <= 0) {
            // Transition to choosing phase
            this.currentPhase = GamePhase.CHOOSING;
            // Print message to console
            System.out.println("Countdown ended - players must choose now");
        }
        // Return the new countdown value
        return this.countdownValue;
    }
    
    /**
     * Records a player's choice for the current round.
     * Checks if both players have chosen and determines the round winner.
     * 
     * @param player The player making the choice (RED or BLUE)
     * @param choice The choice made (ROCK, PAPER, or SCISSORS)
     */
    public void makeChoice(Player player, Choice choice) {
        // Check if it's the Red player making a choice
        if (player == Player.RED) {
            // Store Red player's choice
            this.redChoice = choice;
            // Mark Red player as having chosen
            this.redChosen = true;
            // Print message to console
            System.out.println("Red player chose: " + choice);
        } else if (player == Player.BLUE) {
            // Store Blue player's choice
            this.blueChoice = choice;
            // Mark Blue player as having chosen
            this.blueChosen = true;
            // Print message to console
            System.out.println("Blue player chose: " + choice);
        }
        
        // Check if both players have made their choices
        if (this.redChosen && this.blueChosen) {
            // Determine the round winner
            determineRoundWinner();
            // Change phase to results
            this.currentPhase = GamePhase.RESULTS;
            // Print message to console
            System.out.println("Both players chosen - determining winner");
        }
    }
    
    /**
     * Determines the winner of the current round based on Rock-Paper-Scissors rules.
     * Updates scores if there is a winner (ties don't change scores).
     */
    private void determineRoundWinner() {
        // Check if both choices are the same (tie)
        if (this.redChoice == this.blueChoice) {
            // Set round winner to null (tie)
            this.roundWinner = null;
            // Print tie message to console
            System.out.println("Round " + currentRound + " ended in a tie");
            return; // Exit method early
        }
        
        // Check if Red's choice beats Blue's choice
        if (this.redChoice.beats(this.blueChoice)) {
            // Red wins the round
            this.roundWinner = Player.RED;
            // Increment Red's score
            this.redScore++;
            // Print winner message to console
            System.out.println("Red wins Round " + currentRound);
        } else {
            // Blue wins the round
            this.roundWinner = Player.BLUE;
            // Increment Blue's score
            this.blueScore++;
            // Print winner message to console
            System.out.println("Blue wins Round " + currentRound);
        }
    }
    
    /**
     * Advances to the next round or ends the game if all rounds are complete.
     * Resets choices for the new round and starts countdown.
     * 
     * @return true if game continues to next round, false if game is over
     */
    public boolean nextRound() {
        // Increment the current round number
        this.currentRound++;
        // Check if we've exceeded the maximum number of rounds
        if (this.currentRound > this.maxRounds) {
            // Determine the overall game winner
            determineGameWinner();
            // Change phase to game over
            this.currentPhase = GamePhase.GAME_OVER;
            // Print game over message to console
            System.out.println("Game over - determining overall winner");
            // Return false to indicate game is complete
            return false;
        }
        
        // Reset choices for the new round
        this.redChosen = false;
        this.blueChosen = false;
        this.redChoice = null;
        this.blueChoice = null;
        this.roundWinner = null;
        
        // Start countdown for the new round
        startCountdown();
        
        // Return true to indicate game continues
        return true;
    }
    
    /**
     * Determines the overall winner of the game based on final scores.
     * Sets gameWinner and gameTie flags appropriately.
     */
    private void determineGameWinner() {
        // Check if scores are tied
        if (this.redScore == this.blueScore) {
            // Set game tie flag to true
            this.gameTie = true;
            // Set game winner to null
            this.gameWinner = null;
            // Print tie message to console
            System.out.println("Game ended in a tie: " + redScore + " - " + blueScore);
        } else if (this.redScore > this.blueScore) {
            // Red has higher score
            this.gameWinner = Player.RED;
            // Set game tie flag to false
            this.gameTie = false;
            // Print winner message to console
            System.out.println("Red wins the game: " + redScore + " - " + blueScore);
        } else {
            // Blue has higher score
            this.gameWinner = Player.BLUE;
            // Set game tie flag to false
            this.gameTie = false;
            // Print winner message to console
            System.out.println("Blue wins the game: " + redScore + " - " + blueScore);
        }
    }
    
    /**
     * Resets the game for a new game session while keeping current scores.
     * Called when players choose "Keep Going" after game completion.
     */
    public void resetForNewGame() {
        // Reset current round to 1
        this.currentRound = 1;
        // Reset choices and flags
        this.redChosen = false;
        this.blueChosen = false;
        this.redChoice = null;
        this.blueChoice = null;
        this.roundWinner = null;
        this.gameWinner = null;
        this.gameTie = false;
        // Change phase to waiting
        this.currentPhase = GamePhase.WAITING;
        // Generate new game code
        this.gameCode = generateGameCode();
        // Print message to console
        System.out.println("Game reset for new session - scores maintained");
    }
    
    /**
     * Resets the entire game including scores.
     * Called when starting a completely fresh game.
     */
    public void fullReset() {
        // Reset scores to zero
        this.redScore = 0;
        this.blueScore = 0;
        // Call resetForNewGame to reset other game state
        resetForNewGame();
        // Print message to console
        System.out.println("Full game reset - all scores cleared");
    }
    
    // ========================
    // GETTER METHODS
    // ========================
    
    /**
     * Gets the current game phase.
     * @return The current GamePhase (WAITING, COUNTDOWN, CHOOSING, RESULTS, GAME_OVER)
     */
    public GamePhase getCurrentPhase() {
        return this.currentPhase;
    }
    
    /**
     * Gets the player role of this instance.
     * @return The Player (RED or BLUE)
     */
    public Player getMyPlayer() {
        return this.myPlayer;
    }
    
    /**
     * Sets the player role for this instance.
     * @param player The player role (RED or BLUE)
     */
    public void setMyPlayer(Player player) {
        this.myPlayer = player;
    }
    
    /**
     * Gets the current round number.
     * @return The current round number (1-3)
     */
    public int getCurrentRound() {
        return this.currentRound;
    }
    
    /**
     * Gets the maximum number of rounds.
     * @return The maximum rounds (default is 3)
     */
    public int getMaxRounds() {
        return this.maxRounds;
    }
    
    /**
     * Gets Red player's current score.
     * @return Red's score as an integer
     */
    public int getRedScore() {
        return this.redScore;
    }
    
    /**
     * Gets Blue player's current score.
     * @return Blue's score as an integer
     */
    public int getBlueScore() {
        return this.blueScore;
    }
    
    /**
     * Gets the current countdown value.
     * @return The countdown value in seconds (3, 2, 1, or 0)
     */
    public int getCountdownValue() {
        return this.countdownValue;
    }
    
    /**
     * Checks if Red player has made a choice.
     * @return true if Red has chosen, false otherwise
     */
    public boolean isRedChosen() {
        return this.redChosen;
    }
    
    /**
     * Checks if Blue player has made a choice.
     * @return true if Blue has chosen, false otherwise
     */
    public boolean isBlueChosen() {
        return this.blueChosen;
    }
    
    /**
     * Gets Red player's choice for the current round.
     * @return Red's Choice (ROCK, PAPER, or SCISSORS), or null if not chosen
     */
    public Choice getRedChoice() {
        return this.redChoice;
    }
    
    /**
     * Gets Blue player's choice for the current round.
     * @return Blue's Choice (ROCK, PAPER, or SCISSORS), or null if not chosen
     */
    public Choice getBlueChoice() {
        return this.blueChoice;
    }
    
    /**
     * Gets the winner of the current round.
     * @return The round winner (RED, BLUE, or null for tie)
     */
    public Player getRoundWinner() {
        return this.roundWinner;
    }
    
    /**
     * Gets the overall winner of the game.
     * @return The game winner (RED, BLUE, or null for tie)
     */
    public Player getGameWinner() {
        return this.gameWinner;
    }
    
    /**
     * Checks if the game ended in a tie.
     * @return true if game is tied, false otherwise
     */
    public boolean isGameTie() {
        return this.gameTie;
    }
    
    /**
     * Gets the game code for connecting players.
     * @return The 5-digit game code as a string
     */
    public String getGameCode() {
        return this.gameCode;
    }
    
    /**
     * Sets the game code (typically when joining an existing game).
     * @param code The game code to set
     */
    public void setGameCode(String code) {
        this.gameCode = code;
    }
    
    /**
     * Gets the panel width in pixels.
     * @return The panel width (default is 1280)
     */
    public int getPanelWidth() {
        return this.panelWidth;
    }
    
    /**
     * Gets the panel height in pixels.
     * @return The panel height (default is 720)
     */
    public int getPanelHeight() {
        return this.panelHeight;
    }
    
    /**
     * Gets the animation refresh rate in frames per second.
     * @return The refresh rate (default is 60)
     */
    public int getRefreshRate() {
        return this.refreshRate;
    }
    
    /**
     * Gets the network port number.
     * @return The port number for socket connection
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * Gets a configuration value by key.
     * @param key The configuration key
     * @return The configuration value, or null if not found
     */
    public String getConfigValue(String key) {
        return this.config.get(key);
    }
    
    /**
     * Gets the entire configuration map.
     * @return The map containing all configuration key-value pairs
     */
    public Map<String, String> getConfig() {
        return this.config;
    }
}