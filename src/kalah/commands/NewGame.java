package kalah.commands;

import kalah.Game;
import kalah.memento.GameCaretaker;

public class NewGame implements GameOperation {
    private final Game game;
    private final GameCaretaker gameCaretaker;

    public NewGame(Game game, GameCaretaker gameCaretaker) {
        this.game = game;
        this.gameCaretaker = gameCaretaker;
    }

    @Override
    public void execute() {
        this.gameCaretaker.clearGameMemento();
        this.game.reset();
    }
}
