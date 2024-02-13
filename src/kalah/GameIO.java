package kalah;

import com.qualitascorpus.testsupport.IO;

public abstract class GameIO {
    protected final IO io;
    protected final Configuration configuration;

    public GameIO(IO io, Configuration configuration) {
        this.io = io;
        this.configuration = configuration;
    }
    protected abstract void printState(Board board);

    /**
     * Prompts the user for their input, either to specify the house number they want to play from, or to quit the game.
     *
     * This method requests the player to input a house number. The player can also choose to quit the game by inputting "q".
     * The house numbers should be within the valid range, which is from 1 to the number of houses per player.
     *
     * @param playerId  The ID of the player whose turn it is.
     * @return          The house number provided by the user, or -1 if the user quits.
     */
    protected String promptUserInput(int playerId) {
        this.io.println("Player P" + playerId);
        this.io.println("    (1-" + this.configuration.getNumHousesPerPlayer() + ") - house number for move");
        this.io.println("    N - New game");
        this.io.println("    S - Save game");
        this.io.println("    L - Load game");
        this.io.println("    q - Quit");
        return this.io.readFromKeyboard("Choice:");
    }

    protected void printGameOver() {
        this.io.println("Game over");
    }

    protected void printHouseEmpty() {
        this.io.println("House is empty. Move again.");
    }

    protected void printScore(int playerNum, int score) {
        this.io.println(String.format("\tplayer %d:%d", playerNum, score));
    }

    protected void printTie() {
        this.io.println("A tie!");
    }

    protected void printWinner(int winnerNum) {
        this.io.println(String.format("Player %d wins!", winnerNum));
    }

    protected void printRobotChoice(int playerId, int houseNumber, String reason) {
        this.io.println(String.format("Player P%d (Robot) chooses house #%d because %s", playerId, houseNumber, reason));
    }

    protected boolean isInputNumeric(String input) {
        try {
            int chosenHouseNumber = Integer.parseInt(input);
            return isValidHouseNumber(chosenHouseNumber);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean isValidCommand(String command) {
        return command.equals("n") || command.equals("s") || command.equals("l") || command.equals("q");
    }

    protected boolean isValidHouseNumber(int houseNumber) {
        return houseNumber >= 1 && houseNumber <= this.configuration.getNumHousesPerPlayer();
    }

    protected void printNoSavedGame() {
        this.io.println("No saved game");
    }

    protected void printInvalidNumber() {
        String formattedString = String.format("Invalid input! Please choose a house number between 1 and %d.", this.configuration.getNumHousesPerPlayer());
        this.io.println(formattedString);
    }

    protected void printInvalidCommand() {
        this.io.println("Invalid command! Please choose a valid one.");
    }
}
