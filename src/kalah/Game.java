package kalah;

import kalah.memento.GameMemento;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Configuration configuration;
    private Board board;
    private List<Player> players;
    private Player currentPlayer;
    private boolean gameFinished;
    private final GameIO gameIO;
    private final GameState gameState;
    private boolean isQuit;

    public Game(GameIO gameIO, Configuration configuration, boolean vertical, boolean bmf) {
        this.configuration = configuration;
        this.board = new Board(configuration);
        this.gameIO = gameIO;
        this.gameState = new GameState();
        this.players = new ArrayList<>();
        setUpPlayers(bmf);
        this.currentPlayer = players.get(0);
        this.gameFinished = false;
        this.isQuit = false;
    }

    public void reset() {
        this.board = new Board(configuration);
        this.players = new ArrayList<>();
        setUpPlayers(false);
        this.currentPlayer = players.get(0);
        this.gameFinished = false;
        this.isQuit = false;
    }

    public GameMemento createGameMemento() {
        Board clonedBoard = new Board(this.board);
        Player clonedCurrentPlayer = new Player(this.currentPlayer, clonedBoard);
        List<Player> clonedPlayers = new ArrayList<>();

        int currentPlayerId = this.currentPlayer.getPlayerId();

        Player player1 = (currentPlayerId == 1) ? clonedCurrentPlayer : new Player(1, clonedBoard);
        Player player2 = (currentPlayerId == 2) ? clonedCurrentPlayer : new Player(2, clonedBoard);

        clonedPlayers.add(player1);
        clonedPlayers.add(player2);

        return new GameMemento(clonedBoard, clonedCurrentPlayer, clonedPlayers);
    }

    public void restore(GameMemento gameMemento) {
        if (gameMemento != null) {
            this.board = gameMemento.getBoard();
            this.currentPlayer = gameMemento.getCurrentPlayer();
            this.players = gameMemento.getPlayers();
        } else {
            this.gameIO.printNoSavedGame();
        }
    }

    /**
     * Plays a single turn in the game.
     *
     * This method manages the sowing and capturing of seeds and checking the condition to end the game.
     *
     * @param chosenHouseNumber The house number chosen by the current player.
     */
    protected void playTurn (int chosenHouseNumber) {
        int currentPlayerId = this.currentPlayer.getPlayerId();

        int seeds = this.board.takeSeeds(chosenHouseNumber, currentPlayerId);

        if (seeds == 0) {
            this.gameIO.printHouseEmpty();
            return;
        }

        Pit lastSownPit = sowSeeds(currentPlayerId, seeds, chosenHouseNumber - 1);

        attemptCapture(currentPlayerId, lastSownPit);

        if (this.gameState.checkPlayerHousesEmpty(this.currentPlayer.getPlayerId(), this.board)) {
            this.gameIO.printState(this.board);
            this.gameFinished = true;
        }
    }

    protected void printFinalGameState() {
        this.gameIO.printGameOver();
        this.gameIO.printState(this.board);
    }

    /**
     * Determines the final result of the game.
     *
     * This method calculates and prints the final scores of all players
     * and determines if there is a winner or if the game is a tie.
     */
    protected void determineFinalResult() {
        List<Integer> scores = this.gameState.calculateScores(this.players);

        Player winner = this.gameState.determineWinner(scores, this.players);

        for (int i = 0; i < scores.size(); i++) {
            int score = scores.get(i);
            this.gameIO.printScore(i + 1, score);
        }

        if (winner != null) {
            this.gameIO.printWinner(winner.getPlayerId());
        } else {
            this.gameIO.printTie();
        }
    }

    private Player createSecondPlayer(boolean bmf) {
        return bmf ? new RobotPlayer(2, this.board, this.gameIO, this.gameState) : new Player(2, this.board);
    }

    private void setUpPlayers(boolean bmf) {
        this.players.add(new Player(1, this.board));
        this.players.add(createSecondPlayer(bmf));
    }

    /**
     * Sows seeds starting with the house following the one chosen.
     *
     * This method starts sowing from the house following the chosen house. It continues sowing seeds
     * into next pits as long as they are eligible to receive seeds. The player gets another turn
     * if the last seed is sown in their own store. If not, the turn is switched to the other player.
     *
     * @param playerId          ID of the player who is sowing the seeds.
     * @param seeds             Number of seeds to be sown.
     * @param chosenHouseIndex  Index of the house chosen by the player.
     * @return                  The pit where the last seed was sown.
     */
    private Pit sowSeeds(int playerId, int seeds, int chosenHouseIndex) {
        List<House> playerHouses = this.board.getPlayerHouses(playerId);

        Pit currentPit = playerHouses.get(chosenHouseIndex);

        while (seeds > 0) {
            currentPit = currentPit.getNextPit();

            if (currentPit.canBeSown(playerId)) {
                currentPit.sow(1);
                seeds--;
            }
        }

        if (!this.gameState.isPlayerOwnStore(playerId, currentPit)) {
            switchPlayer();
        }

        return currentPit;
    }

    /**
     * Attempts to capture seeds from an opponent's house.
     *
     * This method checks if the given player can capture seeds according to the capture rule.
     * If eligible, the capture move is performed, and the captured seeds are sown in the player's store.
     *
     * @param playerId    ID of the player who might capture seeds.
     * @param lastSownPit The pit where the last seed was sown in the current turn.
     */
    private void attemptCapture(int playerId, Pit lastSownPit) {
        if (this.gameState.canCapture(playerId, lastSownPit)) {
            House lastSownHouse = (House) lastSownPit;
            int capturedSeeds = lastSownHouse.capture();
            this.board.getStore(playerId - 1).sow(capturedSeeds);
        }
    }

    private void switchPlayer() {
        int currentPlayerIndex = this.players.indexOf(this.currentPlayer);

        // if current player is the last player, switch to the first player
        if (currentPlayerIndex == this.players.size() - 1) {
            this.currentPlayer = this.players.get(0);
        } else {
            // Otherwise, switch to the next player in the list
            this.currentPlayer = this.players.get(currentPlayerIndex + 1);
        }
    }

    protected boolean getGameFinished() {
        return this.gameFinished;
    }

    protected Board getBoard() {
        return this.board;
    }

    protected Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    protected boolean isQuit() {
        return this.isQuit;
    }

    protected void setGameFinished() {
        this.gameFinished = true;
    }

    protected void setQuit() {
        this.isQuit = true;
    }
}
