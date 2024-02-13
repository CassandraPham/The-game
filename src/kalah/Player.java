package kalah;

public class Player {
    protected final int playerId;
    protected int score;
    protected final Board board;

    public Player(int playerId, Board board) {
        this.playerId = playerId;
        this.score = 0;
        this.board = board;
    }

    public Player(Player other, Board board) {
        this.playerId = other.playerId;
        this.score = other.score;
        this.board = board;
    }

    protected void calculateScore() {
        this.score = 0;
        for (House house : this.board.getPlayerHouses(this.playerId)) {
            this.score += house.getNumSeeds();
        }
        this.score += this.board.getStore(this.playerId - 1).getNumSeeds();
    }

    protected int getPlayerId() {
        return this.playerId;
    }

    protected int getScore() {
        return this.score;
    }
}
