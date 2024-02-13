package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.commands.GameOperation;
import kalah.commands.LoadGame;
import kalah.commands.NewGame;
import kalah.commands.SaveGame;
import kalah.memento.GameCaretaker;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
	private Game game;
	private GameIO gameIO;
	private GameCaretaker gameCaretaker;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		initialiseGame(io);
		runGameLoop();
	}

	private void initialiseGame(IO io) {
		Configuration configuration = new Configuration(2, 4, 6);
		this.gameIO = new HorizontalBoardIO(io, configuration);
		this.gameCaretaker = new GameCaretaker();
		this.game = new Game(this.gameIO, configuration, false, false);
	}

	private void runGameLoop() {
		while (!game.getGameFinished()) {
			this.gameIO.printState(game.getBoard());

			String userCommand = this.gameIO.promptUserInput(game.getCurrentPlayer().getPlayerId());
			handleUserCommand(userCommand.toLowerCase());

			if (this.game.getGameFinished()) {
				finaliseGame();
			}
		}
	}

	private void finaliseGame() {
		this.game.printFinalGameState();
		if (!this.game.isQuit()) {
			this.game.determineFinalResult();
		}
	}

	private void handleUserCommand(String command) {
		if (this.gameIO.isInputNumeric(command)) {
			handleNumericCommand(Integer.parseInt(command));
		} else if (this.gameIO.isValidCommand(command)) {
			handleGameOperationCommand(command);
		} else {
			this.gameIO.printInvalidCommand();
		}
	}

	private void handleNumericCommand(int chosenHouseNumber) {
		if (this.gameIO.isValidHouseNumber(chosenHouseNumber)) {
			this.game.playTurn(chosenHouseNumber);
		} else {
			this.gameIO.printInvalidNumber();
		}
	}

	private void handleGameOperationCommand(String command) {
		GameOperation gameOperation = determineGameOperation(command);

		if (gameOperation == null) {
			this.game.setGameFinished();
			this.game.setQuit();
		} else {
			gameOperation.execute();
		}
	}

	private GameOperation determineGameOperation(String command) {
		GameOperation gameOperation = null;

		switch (command) {
			case "n":
				gameOperation = new NewGame(this.game, this.gameCaretaker);
				break;
			case "s":
				gameOperation = new SaveGame(this.game, this.gameCaretaker);
				break;
			case "l":
				gameOperation = new LoadGame(this.game, this.gameCaretaker);
				break;
		}

		return gameOperation;
	}

	public void play(IO io, boolean vertical, boolean bmf) {
		// DO NOT CHANGE. Only here for backwards compatibility
		play(io);
	}
}
