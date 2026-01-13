package controller;

import lib.SuperSocketMaster;

import view.MainView;
import view.MainMenuPanel;
import view.GamePanel;
import view.ResultPanel;
import view.FinalResultPanel;
import view.CreateGamePanel;
import view.JoinGamePanel;
import view.HelpPanel;

import model.GameModel;
import model.GameModel.GamePhase;
import model.GameModel.Player;
import model.GameModel.Choice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 * GameController - The main controller class for the Rock-Paper-Scissors game.
 * This class connects the Model and View, handling all user interactions,
 * game logic, and network communication.
 * 
 * <p>This class is responsible for:</p>
 * <ul>
 *   <li>Handling button clicks and user input</li>
 *   <li>Managing game state transitions</li>
 *   <li>Implementing countdown timer logic</li>
 *   <li>Processing player choices</li>
 *   <li>Managing network communication via SuperSocketMaster</li>
 *   <li>Coordinating between Red (server) and Blue (client) players</li>
 *   <li>Handling round resolution and scoring</li>
 *   <li>Managing game completion and restart</li>
 * </ul>
 * 
 * @author ICS4U1 Student
 * @version 1.0
 * @since 2024
 */
public class GameController implements ActionListener {
    
    // ========================
    // INSTANCE VARIABLES
    // ========================
    
    /** The game model containing all game state and logic */
    private GameModel gameModel;
    
    /** The main view handling all UI components and panel swapping */
    private MainView mainView;
    
    /** SuperSocketMaster instance for network communication (server mode for Red) */
    private SuperSocketMaster socketMaster;
    
    /** Timer for handling the countdown during gameplay */
    private Timer countdownTimer;
    
    /** Timer for handling round transitions */
    private Timer roundTimer;
    
    /** Flag indicating if this player is the Red player (server) */
    private boolean isRedPlayer;
    
    /** Flag indicating if connected to opponent */
    private boolean isConnected;
    
    // ========================
    // CONSTANTS
    // ========================
    
    /** Delay for countdown timer ticks in milliseconds (1 second) */
    private static final int COUNTDOWN_DELAY = 1000;
    
    /** Delay for round transition in milliseconds (3 seconds to show results) */
    private static final int ROUND_DELAY = 3000;
    
    /** Action command for create game button */
    private static final String ACTION_CREATE_GAME = "create_game";
    
    /** Action command for join game button */
    private static final String ACTION_JOIN_GAME = "join_game";
    
    /** Action command for connect button */
    private static final String ACTION_CONNECT = "connect_game";
    
    /** Action command for show help button */
    private static final String ACTION_SHOW_HELP = "show_help";
    
    /** Action command for back to menu button */
    private static final String ACTION_BACK_TO_MENU = "back_to_menu";
    
    /** Action command for Rock choice button */
    private static final String ACTION_CHOOSE_ROCK = "choose_rock";
    
    /** Action command for Paper choice button */
    private static final String ACTION_CHOOSE_PAPER = "choose_paper";
    
    /** Action command for Scissors choice button */
    private static final String ACTION_CHOOSE_SCISSORS = "choose_scissors";
    
    /** Action command for next round timer */
    private static final String ACTION_NEXT_ROUND = "next_round";
    
    /** Action command for finish game button */
    private static final String ACTION_FINISH_GAME = "finish_game";
    
    /** Action command for keep going button */
    private static final String ACTION_KEEP_GOING = "keep_going";
    
    /** Action command for Rock demo button in help */
    private static final String ACTION_DEMO_ROCK = "demo_rock";
    
    /** Action command for Paper demo button in help */
    private static final String ACTION_DEMO_PAPER = "demo_paper";
    
    /** Action command for Scissors demo button in help */
    private static final String ACTION_DEMO_SCISSORS = "demo_scissors";
    
    /** Action command for network message events */
    private static final String ACTION_NETWORK_MESSAGE = "Network Message";
    
    // Network message prefixes
    /** Prefix for join request messages */
    private static final String MSG_JOIN_REQUEST = "JOIN_REQUEST:";
    
    /** Prefix for join accepted messages */
    private static final String MSG_JOIN_ACCEPTED = "JOIN_ACCEPTED:";
    
    /** Prefix for game start messages */
    private static final String MSG_GAME_START = "GAME_START:";
    
    /** Prefix for countdown messages */
    private static final String MSG_COUNTDOWN = "COUNTDOWN:";
    
    /** Prefix for player choice messages */
    private static final String MSG_PLAYER_CHOICE = "PLAYER_CHOICE:";
    
    /** Prefix for round result messages */
    private static final String MSG_ROUND_RESULT = "ROUND_RESULT:";
    
    /** Prefix for game over messages */
    private static final String MSG_GAME_OVER = "GAME_OVER:";
    
    /** Prefix for player quit messages */
    private static final String MSG_PLAYER_QUIT = "PLAYER_QUIT:";
    
    // ========================
    // CONSTRUCTOR
    // ========================
    
    /**
     * Constructor for GameController.
     * Initializes the controller with model and view references.
     * Sets up timers and prepares for user interaction.
     * 
     * @param gameModel The game model containing game state
     * @param mainView The main view handling UI components
     */
    public GameController(GameModel gameModel, MainView mainView) {
        // Store the game model reference
        this.gameModel = gameModel;
        
        // Store the main view reference
        this.mainView = mainView;
        
        // Initialize the connected flag to false
        this.isConnected = false;
        
        // Initialize timers
        initializeTimers();
        
        // Print message to console for debugging
        System.out.println("GameController initialized successfully");
    }
    
    // ========================
    // TIMER INITIALIZATION
    // ========================
    
    /**
     * Initializes all timers used by the controller.
     * Creates countdown timer and round transition timer.
     */
    private void initializeTimers() {
        // Create countdown timer (fires every second)
        this.countdownTimer = new Timer(COUNTDOWN_DELAY, this);
        this.countdownTimer.setActionCommand("countdown_tick");
        
        // Create round transition timer
        this.roundTimer = new Timer(ROUND_DELAY, this);
        this.roundTimer.setRepeats(false); // Only fire once
        this.roundTimer.setActionCommand("round_transition");
        
        // Print message to console
        System.out.println("Timers initialized successfully");
    }
    
    // ========================
    // ACTION LISTENER METHOD
    // ========================
    
    /**
     * Handles all action events from buttons, timers, and network messages.
     * This is the main event handler for the entire game.
     * 
     * @param e The action event to handle
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the action command to identify the source
        String command = e.getActionCommand();
        
        // Handle button clicks from main menu
        if (command.equals(ACTION_CREATE_GAME)) {
            // Handle create game button click
            handleCreateGame();
        } else if (command.equals(ACTION_JOIN_GAME)) {
            // Handle join game button click
            handleJoinGame();
        } else if (command.equals(ACTION_SHOW_HELP)) {
            // Handle show help button click
            handleShowHelp();
        } else if (command.equals(ACTION_BACK_TO_MENU)) {
            // Handle back to menu button click
            handleBackToMenu();
        }
        
        // Handle connect button click (Blue player joining)
        else if (command.equals(ACTION_CONNECT)) {
            // Handle connect button click
            handleConnect();
        }
        
        // Handle player choice buttons
        else if (command.equals(ACTION_CHOOSE_ROCK)) {
            // Handle Rock choice
            handlePlayerChoice(Choice.ROCK);
        } else if (command.equals(ACTION_CHOOSE_PAPER)) {
            // Handle Paper choice
            handlePlayerChoice(Choice.PAPER);
        } else if (command.equals(ACTION_CHOOSE_SCISSORS)) {
            // Handle Scissors choice
            handlePlayerChoice(Choice.SCISSORS);
        }
        
        // Handle timer events
        else if (command.equals("countdown_tick")) {
            // Handle countdown timer tick
            handleCountdownTick();
        } else if (command.equals("round_transition")) {
            // Handle round transition timer
            handleRoundTransition();
        } else if (command.equals(ACTION_NEXT_ROUND)) {
            // Handle next round from result panel timer
            handleNextRound();
        }
        
        // Handle game end buttons
        else if (command.equals(ACTION_FINISH_GAME)) {
            // Handle finish game button click
            handleFinishGame();
        } else if (command.equals(ACTION_KEEP_GOING)) {
            // Handle keep going button click
            handleKeepGoing();
        }
        
        // Handle help demo buttons
        else if (command.equals(ACTION_DEMO_ROCK)) {
            // Handle Rock demo button click
            handleDemoRock();
        } else if (command.equals(ACTION_DEMO_PAPER)) {
            // Handle Paper demo button click
            handleDemoPaper();
        } else if (command.equals(ACTION_DEMO_SCISSORS)) {
            // Handle Scissors demo button click
            handleDemoScissors();
        }
        
        // Handle network messages
        else if (command.equals(ACTION_NETWORK_MESSAGE)) {
            // Handle incoming network message
            handleNetworkMessage();
        }
    }
    
    // ========================
    // MAIN MENU HANDLERS
    // ========================
    
    /**
     * Handles the "Create Game" button click.
     * Sets up this player as Red (server) and shows create game screen.
     */
    private void handleCreateGame() {
        // Set this player as Red
        this.gameModel.setMyPlayer(Player.RED);
        this.isRedPlayer = true;
        
        // Show the create game panel
        this.mainView.showCreateGame();
        
        // Initialize the server socket
        initializeServer();
        
        // Print message to console
        System.out.println("Red player created game with code: " + gameModel.getGameCode());
    }
    
    /**
     * Handles the "Join Game" button click.
     * Sets up this player as Blue (client) and shows join game screen.
     */
    private void handleJoinGame() {
        // Set this player as Blue
        this.gameModel.setMyPlayer(Player.BLUE);
        this.isRedPlayer = false;
        
        // Show the join game panel
        this.mainView.showJoinGame();
        
        // Print message to console
        System.out.println("Blue player joining game");
    }
    
    /**
     * Handles the "Show Help" button click.
     * Displays the help screen with interactive demo.
     */
    private void handleShowHelp() {
        // Show the help panel
        this.mainView.showHelp();
        
        // Print message to console
        System.out.println("Showing help screen");
    }
    
    /**
     * Handles the "Back to Menu" button click.
     * Returns to the main menu from help screen.
     */
    private void handleBackToMenu() {
        // Show the main menu panel
        this.mainView.showMainMenu();
        
        // Print message to console
        System.out.println("Returning to main menu");
    }
    
    // ========================
    // CONNECTION HANDLERS
    // ========================
    
    /**
     * Initializes the server socket for Red player.
     * Creates a SuperSocketMaster instance in server mode.
     */
    private void initializeServer() {
        try {
            // Create SuperSocketMaster in server mode
            this.socketMaster = new SuperSocketMaster(gameModel.getPort(), this);
            
            // Connect to start listening for clients
            boolean connected = this.socketMaster.connect();
            
            // Check if connection was successful
            if (connected) {
                // Print success message
                System.out.println("Server initialized on port: " + gameModel.getPort());
            } else {
                // Print error message
                System.err.println("Failed to initialize server");
            }
        } catch (Exception e) {
            // Print error message
            System.err.println("Error initializing server: " + e.getMessage());
        }
    }
    
    /**
     * Handles the "Connect" button click for Blue player.
     * Connects to the Red player's server using the entered game code.
     */
    private void handleConnect() {
        // Get the entered game code from the join panel
        String code = mainView.getJoinGamePanel().getEnteredCode();
        
        // Validate the game code (should be 5 digits)
        if (code == null || code.length() != 5 || !code.matches("\\d+")) {
            // Show error message for invalid code
            mainView.getJoinGamePanel().showInvalidCodeError();
            // Print error message to console
            System.err.println("Invalid game code entered: " + code);
            return; // Exit method early
        }
        
        // Set the game code in the model
        this.gameModel.setGameCode(code);
        
        // Hide any previous error messages
        mainView.getJoinGamePanel().hideError();
        
        // Connect to the server (localhost for now, in production would use IP)
        try {
            // Create SuperSocketMaster in client mode
            this.socketMaster = new SuperSocketMaster("localhost", gameModel.getPort(), this);
            
            // Attempt to connect
            boolean connected = this.socketMaster.connect();
            
            // Check if connection was successful
            if (connected) {
                // Send join request to server
                sendJoinRequest();
                // Print success message
                System.out.println("Connected to server with code: " + code);
            } else {
                // Show connection error
                mainView.getJoinGamePanel().showConnectionError();
                // Print error message
                System.err.println("Failed to connect to server");
            }
        } catch (Exception e) {
            // Show connection error
            mainView.getJoinGamePanel().showConnectionError();
            // Print error message
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
    
    /**
     * Sends a join request message to the server.
     * Called by Blue player when connecting to Red player's game.
     */
    private void sendJoinRequest() {
        // Create the join request message
        String message = MSG_JOIN_REQUEST + "player_name=Blue";
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (sent) {
            // Print success message
            System.out.println("Join request sent");
        } else {
            // Print error message
            System.err.println("Failed to send join request");
        }
    }
    
    // ========================
    // GAMEPLAY HANDLERS
    // ========================
    
    /**
     * Handles a player's choice (Rock, Paper, or Scissors).
     * Records the choice and checks if both players have chosen.
     * 
     * @param choice The player's choice
     */
    private void handlePlayerChoice(Choice choice) {
        // Check if game is in choosing phase
        if (gameModel.getCurrentPhase() != GamePhase.CHOOSING) {
            // Ignore choice if not in choosing phase
            return;
        }
        
        // Get this player's role
        Player myPlayer = gameModel.getMyPlayer();
        
        // Record the choice in the game model
        gameModel.makeChoice(myPlayer, choice);
        
        // Send the choice to the opponent
        sendPlayerChoice(myPlayer, choice);
        
        // Print message to console
        System.out.println(myPlayer + " player chose: " + choice);
        
        // Check if both players have chosen
        if (gameModel.getCurrentPhase() == GamePhase.RESULTS) {
            // Both players have chosen, show results
            showRoundResults();
        }
    }
    
    /**
     * Sends the player's choice to the opponent.
     * Broadcasts the choice over the network.
     * 
     * @param player The player making the choice
     * @param choice The choice made
     */
    private void sendPlayerChoice(Player player, Choice choice) {
        // Create the player choice message
        String message = MSG_PLAYER_CHOICE + "player=" + player.name() + ",choice=" + choice.name();
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (!sent) {
            // Print error message
            System.err.println("Failed to send player choice");
        }
    }
    
    /**
     * Starts the game countdown.
     * Begins the countdown timer for the current round.
     */
    private void startCountdown() {
        // Start the countdown in the game model
        gameModel.startCountdown();
        
        // Start the countdown timer
        this.countdownTimer.start();
        
        // Print message to console
        System.out.println("Countdown started");
    }
    
    /**
     * Handles the countdown timer tick.
     * Decrements the countdown value and checks if countdown is complete.
     */
    private void handleCountdownTick() {
        // Decrement the countdown
        int remaining = gameModel.decrementCountdown();
        
        // Send countdown update to opponent
        sendCountdown(remaining);
        
        // Check if countdown has ended
        if (remaining <= 0) {
            // Stop the countdown timer
            this.countdownTimer.stop();
            
            // Print message to console
            System.out.println("Countdown ended - players can now choose");
        }
    }
    
    /**
     * Sends the countdown value to the opponent.
     * Synchronizes the countdown between players.
     * 
     * @param seconds The remaining seconds in the countdown
     */
    private void sendCountdown(int seconds) {
        // Only Red player (server) needs to send countdown
        if (!isRedPlayer) {
            return; // Blue player doesn't send countdown
        }
        
        // Create the countdown message
        String message = MSG_COUNTDOWN + "seconds_remaining=" + seconds;
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (!sent) {
            // Print error message
            System.err.println("Failed to send countdown update");
        }
    }
    
    /**
     * Shows the round results to both players.
     * Displays who won the round and updates scores.
     */
    private void showRoundResults() {
        // Get the round winner
        Player winner = gameModel.getRoundWinner();
        
        // If this is Red player, send round results to Blue
        if (isRedPlayer) {
            // Send round result message
            sendRoundResult();
        }
        
        // Show the result panel
        this.mainView.showResult();
        
        // Start the transition timer to show results for a few seconds
        this.mainView.getResultPanel().startTransitionTimer();
        
        // Print message to console
        System.out.println("Showing round results. Winner: " + winner);
    }
    
    /**
     * Sends the round result to the opponent.
     * Broadcasts the round outcome and updated scores.
     */
    private void sendRoundResult() {
        // Create the round result message
        String message = MSG_ROUND_RESULT + 
                        "round_number=" + gameModel.getCurrentRound() +
                        ",winner=" + (gameModel.getRoundWinner() != null ? gameModel.getRoundWinner().name() : "Tie") +
                        ",red_choice=" + (gameModel.getRedChoice() != null ? gameModel.getRedChoice().name() : "None") +
                        ",blue_choice=" + (gameModel.getBlueChoice() != null ? gameModel.getBlueChoice().name() : "None") +
                        ",red_score=" + gameModel.getRedScore() +
                        ",blue_score=" + gameModel.getBlueScore();
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (!sent) {
            // Print error message
            System.err.println("Failed to send round result");
        }
    }
    
    /**
     * Handles the transition to the next round.
     * Called when the result panel timer fires.
     */
    private void handleNextRound() {
        // Stop the result panel timer
        this.mainView.getResultPanel().stopTransitionTimer();
        
        // Check if there are more rounds to play
        if (gameModel.getCurrentRound() < gameModel.getMaxRounds()) {
            // Advance to next round
            boolean gameContinues = gameModel.nextRound();
            
            // If game continues, start next round
            if (gameContinues) {
                // Show game panel
                this.mainView.showGame();
                // Start countdown for next round
                startCountdown();
                
                // Print message to console
                System.out.println("Starting round " + gameModel.getCurrentRound());
            } else {
                // Game is over, show final results
                showFinalResults();
            }
        } else {
            // All rounds complete, show final results
            showFinalResults();
        }
    }
    
    /**
     * Shows the final game results.
     * Displays the overall winner and finish/continue options.
     */
    private void showFinalResults() {
        // If this is Red player, send game over message
        if (isRedPlayer) {
            // Send game over message
            sendGameOver();
        }
        
        // Show the final result panel
        this.mainView.showFinalResult();
        
        // Print message to console
        System.out.println("Game over. Final scores - Red: " + gameModel.getRedScore() + 
                          ", Blue: " + gameModel.getBlueScore());
    }
    
    /**
     * Sends the game over message to the opponent.
     * Broadcasts the final game results.
     */
    private void sendGameOver() {
        // Create the game over message
        String message = MSG_GAME_OVER + 
                        "final_red_score=" + gameModel.getRedScore() +
                        ",final_blue_score=" + gameModel.getBlueScore() +
                        ",winner=" + (gameModel.getGameWinner() != null ? gameModel.getGameWinner().name() : "Tie");
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (!sent) {
            // Print error message
            System.err.println("Failed to send game over message");
        }
    }
    
    // ========================
    // GAME END HANDLERS
    // ========================
    
    /**
     * Handles the "Finish" button click.
     * Exits the application completely.
     */
    private void handleFinishGame() {
        // Send quit message to opponent
        sendPlayerQuit();
        
        // Disconnect from network
        if (this.socketMaster != null) {
            this.socketMaster.disconnect();
        }
        
        // Exit the application
        System.exit(0);
    }
    
    /**
     * Handles the "Keep Going" button click.
     * Restarts the game with current scores maintained.
     */
    private void handleKeepGoing() {
        // Reset the game for new session (scores maintained)
        gameModel.resetForNewGame();
        
        // Show main menu to start new game
        this.mainView.showMainMenu();
        
        // Print message to console
        System.out.println("Starting new game session - scores maintained");
    }
    
    /**
     * Sends a quit message to the opponent.
     * Notifies the opponent that this player is leaving.
     */
    private void sendPlayerQuit() {
        // Check if socket is connected
        if (this.socketMaster == null) {
            return; // No socket to send through
        }
        
        // Create the player quit message
        String message = MSG_PLAYER_QUIT + "player=" + gameModel.getMyPlayer().name();
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (sent) {
            // Print success message
            System.out.println("Quit message sent");
        }
    }
    
    // ========================
    // HELP DEMO HANDLERS
    // ========================
    
    /**
     * Handles the Rock demo button click in help screen.
     * Shows what Rock beats in the demo result.
     */
    private void handleDemoRock() {
        // Update the demo result to show what Rock beats
        mainView.getHelpPanel().updateDemoResult("ROCK (○) beats SCISSORS (▲)");
        
        // Print message to console
        System.out.println("Rock demo shown");
    }
    
    /**
     * Handles the Paper demo button click in help screen.
     * Shows what Paper beats in the demo result.
     */
    private void handleDemoPaper() {
        // Update the demo result to show what Paper beats
        mainView.getHelpPanel().updateDemoResult("PAPER (■) beats ROCK (○)");
        
        // Print message to console
        System.out.println("Paper demo shown");
    }
    
    /**
     * Handles the Scissors demo button click in help screen.
     * Shows what Scissors beats in the demo result.
     */
    private void handleDemoScissors() {
        // Update the demo result to show what Scissors beats
        mainView.getHelpPanel().updateDemoResult("SCISSORS (▲) beats PAPER (■)");
        
        // Print message to console
        System.out.println("Scissors demo shown");
    }
    
    // ========================
    // NETWORK MESSAGE HANDLER
    // ========================
    
    /**
     * Handles incoming network messages.
     * Processes messages from the opponent and updates game state accordingly.
     */
    private void handleNetworkMessage() {
        // Read the incoming message from the socket
        String message = this.socketMaster.readText();
        
        // Check if message is empty
        if (message == null || message.isEmpty()) {
            // Print error message
            System.err.println("Received empty network message");
            return; // Exit method early
        }
        
        // Print received message to console for debugging
        System.out.println("Received network message: " + message);
        
        // Parse the message based on its type
        if (message.startsWith(MSG_JOIN_REQUEST)) {
            // Handle join request from Blue player
            handleJoinRequest(message);
        } else if (message.startsWith(MSG_JOIN_ACCEPTED)) {
            // Handle join accepted from Red player
            handleJoinAccepted(message);
        } else if (message.startsWith(MSG_GAME_START)) {
            // Handle game start message
            handleGameStart(message);
        } else if (message.startsWith(MSG_COUNTDOWN)) {
            // Handle countdown update
            handleCountdownUpdate(message);
        } else if (message.startsWith(MSG_PLAYER_CHOICE)) {
            // Handle opponent's choice
            handleOpponentChoice(message);
        } else if (message.startsWith(MSG_ROUND_RESULT)) {
            // Handle round result
            handleRoundResult(message);
        } else if (message.startsWith(MSG_GAME_OVER)) {
            // Handle game over
            handleGameOver(message);
        } else if (message.startsWith(MSG_PLAYER_QUIT)) {
            // Handle opponent quit
            handleOpponentQuit(message);
        } else {
            // Unknown message type
            System.err.println("Unknown message type: " + message);
        }
    }
    
    /**
     * Handles a join request from Blue player (Red player only).
     * Accepts the join request and starts the game.
     * 
     * @param message The join request message
     */
    private void handleJoinRequest(String message) {
        // Only Red player handles join requests
        if (!isRedPlayer) {
            return; // Blue player ignores join requests
        }
        
        // Send join accepted message to Blue player
        String acceptMessage = MSG_JOIN_ACCEPTED + "red_player_name=Red";
        boolean sent = this.socketMaster.sendText(acceptMessage);
        
        // Check if message was sent successfully
        if (sent) {
            // Mark as connected
            this.isConnected = true;
            
            // Send game start message
            sendGameStart();
            
            // Print message to console
            System.out.println("Blue player joined - starting game");
        } else {
            // Print error message
            System.err.println("Failed to send join accepted");
        }
    }
    
    /**
     * Handles join accepted from Red player (Blue player only).
     * Confirms connection and prepares to start game.
     * 
     * @param message The join accepted message
     */
    private void handleJoinAccepted(String message) {
        // Only Blue player handles join accepted
        if (isRedPlayer) {
            return; // Red player ignores join accepted
        }
        
        // Mark as connected
        this.isConnected = true;
        
        // Print message to console
        System.out.println("Join accepted by Red player");
    }
    
    /**
     * Handles game start message.
     * Transitions from waiting to game screen and starts countdown.
     * 
     * @param message The game start message
     */
    private void handleGameStart(String message) {
        // Show the game panel
        this.mainView.showGame();
        
        // Start the countdown
        startCountdown();
        
        // Print message to console
        System.out.println("Game started - countdown begun");
    }
    
    /**
     * Sends the game start message (Red player only).
     * Initiates the game after Blue player joins.
     */
    private void sendGameStart() {
        // Only Red player sends game start
        if (!isRedPlayer) {
            return;
        }
        
        // Create the game start message
        String message = MSG_GAME_START + "round_number=1";
        
        // Send the message via socket
        boolean sent = this.socketMaster.sendText(message);
        
        // Check if message was sent successfully
        if (sent) {
            // Print success message
            System.out.println("Game start message sent");
        } else {
            // Print error message
            System.err.println("Failed to send game start message");
        }
    }
    
    /**
     * Handles countdown update from Red player (Blue player only).
     * Updates the countdown value to stay synchronized.
     * 
     * @param message The countdown update message
     */
    private void handleCountdownUpdate(String message) {
        // Only Blue player needs to sync countdown
        if (isRedPlayer) {
            return; // Red player sends countdown, doesn't receive
        }
        
        // Parse the countdown value from the message
        try {
            // Extract the value after "seconds_remaining="
            String valueStr = message.substring(message.indexOf("=") + 1);
            int seconds = Integer.parseInt(valueStr);
            
            // Update the countdown in the game model
            gameModel.decrementCountdown();
            
            // Check if countdown has ended
            if (seconds <= 0) {
                // Print message to console
                System.out.println("Countdown ended - players can now choose");
            }
        } catch (Exception e) {
            // Print error message
            System.err.println("Error parsing countdown update: " + e.getMessage());
        }
    }
    
    /**
     * Handles opponent's choice message.
     * Records the opponent's choice in the game model.
     * 
     * @param message The player choice message
     */
    private void handleOpponentChoice(String message) {
        // Parse the player and choice from the message
        try {
            // Extract player name
            String playerStr = message.substring(message.indexOf("player=") + 7, 
                                                  message.indexOf(","));
            // Extract choice
            String choiceStr = message.substring(message.indexOf("choice=") + 7);
            
            // Convert to enum values
            Player player = Player.valueOf(playerStr);
            Choice choice = Choice.valueOf(choiceStr);
            
            // Record the opponent's choice in the game model
            gameModel.makeChoice(player, choice);
            
            // Check if both players have chosen
            if (gameModel.getCurrentPhase() == GamePhase.RESULTS) {
                // Both players have chosen, show results
                showRoundResults();
            }
            
            // Print message to console
            System.out.println("Received choice from " + player + ": " + choice);
        } catch (Exception e) {
            // Print error message
            System.err.println("Error parsing opponent choice: " + e.getMessage());
        }
    }
    
    /**
     * Handles round result message from Red player (Blue player only).
     * Updates scores and shows round results.
     * 
     * @param message The round result message
     */
    private void handleRoundResult(String message) {
        // Only Blue player needs to receive round results
        if (isRedPlayer) {
            return; // Red player sends results, doesn't receive
        }
        
        // Parse the round result from the message
        try {
            // Extract values from message
            // Note: In a full implementation, we would parse all values
            // For now, the game model already has the correct state
            
            // Show the result panel
            this.mainView.showResult();
            
            // Start the transition timer
            this.mainView.getResultPanel().startTransitionTimer();
            
            // Print message to console
            System.out.println("Received round result");
        } catch (Exception e) {
            // Print error message
            System.err.println("Error parsing round result: " + e.getMessage());
        }
    }
    
    /**
     * Handles game over message from Red player (Blue player only).
     * Shows final results and game end options.
     * 
     * @param message The game over message
     */
    private void handleGameOver(String message) {
        // Only Blue player needs to receive game over
        if (isRedPlayer) {
            return; // Red player sends game over, doesn't receive
        }
        
        // Show the final result panel
        this.mainView.showFinalResult();
        
        // Print message to console
        System.out.println("Received game over message");
    }
    
    /**
     * Handles opponent quit message.
     * Shows a message and returns to main menu.
     * 
     * @param message The player quit message
     */
    private void handleOpponentQuit(String message) {
        // Disconnect from network
        if (this.socketMaster != null) {
            this.socketMaster.disconnect();
        }
        
        // Show message to user (using JOptionPane for simplicity)
        JOptionPane.showMessageDialog(this.mainView, 
                                      "Opponent has disconnected.", 
                                      "Game Ended", 
                                      JOptionPane.INFORMATION_MESSAGE);
        
        // Return to main menu
        this.mainView.showMainMenu();
        
        // Print message to console
        System.out.println("Opponent quit - returned to main menu");
    }
    
    // ========================
    // ROUND TRANSITION HANDLER
    // ========================
    
    /**
     * Handles the round transition timer.
     * Moves to the next round or shows final results.
     */
    private void handleRoundTransition() {
        // Stop the round transition timer
        this.roundTimer.stop();
        
        // Check if there are more rounds
        if (gameModel.getCurrentRound() < gameModel.getMaxRounds()) {
            // Advance to next round
            boolean gameContinues = gameModel.nextRound();
            
            // If game continues, start next round
            if (gameContinues) {
                // Show game panel
                this.mainView.showGame();
                // Start countdown for next round
                startCountdown();
                
                // Print message to console
                System.out.println("Starting round " + gameModel.getCurrentRound());
            } else {
                // Game is over, show final results
                showFinalResults();
            }
        } else {
            // All rounds complete, show final results
            showFinalResults();
        }
    }
}