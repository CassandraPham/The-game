package kalah.commands;

import kalah.Game;
import kalah.memento.GameCaretaker;

public class LoadGame implements GameOperation {
    private final Game game;

    private final GameCaretaker gameCaretaker;

    public LoadGame(Game game, GameCaretaker gameCaretaker) {
        this.game = game;
        this.gameCaretaker = gameCaretaker;
    }

    @Override
    public void execute() {
        this.game.restore(this.gameCaretaker.getGameMemento());
    }
}
