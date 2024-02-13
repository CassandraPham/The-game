package kalah.commands;

import kalah.Game;
import kalah.memento.GameCaretaker;
import kalah.memento.GameMemento;

public class SaveGame implements GameOperation {
    private final Game game;
    private final GameCaretaker gameCaretaker;

    public SaveGame(Game game, GameCaretaker gameCaretaker) {
        this.game = game;
        this.gameCaretaker = gameCaretaker;
    }

    @Override
    public void execute() {
        GameMemento memento = this.game.createGameMemento();
        this.gameCaretaker.saveGameMemento(memento);
    }
}
