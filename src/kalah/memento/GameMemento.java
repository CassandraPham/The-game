package kalah.memento;

import kalah.Board;
import kalah.Player;
import java.util.List;

public class GameMemento {
    private final Board board;
    private final Player currentPlayer;
    private final List<Player> players;

    public GameMemento(Board board, Player currentPlayer, List<Player> players) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
