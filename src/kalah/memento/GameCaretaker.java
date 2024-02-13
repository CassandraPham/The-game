package kalah.memento;

public class GameCaretaker {
    private GameMemento gameMemento;

    public void saveGameMemento(GameMemento memento) {
        this.gameMemento = memento;
    }

    public GameMemento getGameMemento() {
        if (this.gameMemento != null) {
            return this.gameMemento;
        } else {
            return null;
        }
    }

    public void clearGameMemento() {
        this.gameMemento = null;
    }
}
