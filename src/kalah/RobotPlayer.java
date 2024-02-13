package kalah;

public class RobotPlayer extends Player {
    private final GameIO gameIO;
    private final GameState gameState;
    private final Simulator simulator;

    public RobotPlayer(int playerId, Board board, GameIO gameIO, GameState gameState) {
        super(playerId, board);
        this.gameIO = gameIO;
        this.gameState = gameState;
        this.simulator = new Simulator();
    }

    protected int chooseHouse() {
        for (int i = 1; i <= this.board.getNumHousesPerPlayer(); i++) {
            if (leadsToExtraMove(i)) {
                this.gameIO.printRobotChoice(this.playerId, i, "it leads to an extra move");
                return i;
            }
        }

        for (int i = 1; i <= this.board.getNumHousesPerPlayer(); i++) {
            if (leadsToCapture(i)) {
                this.gameIO.printRobotChoice(this.playerId, i, "it leads to a capture");
                return i;
            }
        }

        for (int i = 1; i <= this.board.getNumHousesPerPlayer(); i++) {
            if (hasLegalMove(i)) {
                this.gameIO.printRobotChoice(this.playerId, i, "it is the first legal move");
                return i;
            }
        }

        return -1;
    }

    private boolean leadsToExtraMove(int houseNumber) {
        int seeds = this.board.getPlayerHouses(this.playerId).get(houseNumber - 1).getNumSeeds();
        Pit lastPit = this.simulator.simulateSowing(this.board, this.playerId, seeds, houseNumber - 1);
        return this.gameState.isPlayerOwnStore(this.playerId, lastPit);
    }

    private boolean leadsToCapture(int houseNumber) {
        int seeds = this.board.getPlayerHouses(this.playerId).get(houseNumber - 1).getNumSeeds();
        Pit lastPit = this.simulator.simulateSowing(this.board, this.playerId, seeds, houseNumber - 1);
        int totalPits = this.board.getNumPits();

        if (seeds == 0) {
            return false;
        }

        if (houseNumber + seeds >= totalPits) {
            // If the sowing completes a full wrap, all opponent's houses will have been sown,
            // so no need to check if the opposite house has at least 1 seed,
            // only need to check if the last pit satisfies the capture rule according to sowing simulation logic.
            return this.gameState.canSimulateCapture(this.playerId, lastPit);
        } else {
            if (this.gameState.canSimulateCapture(this.playerId, lastPit)) {
                House oppositeHouse = ((House)lastPit).getOppositeHouse();
                return oppositeHouse.getNumSeeds() > 0;
            }
        }

        return false;
    }

    private boolean hasLegalMove(int houseNumber) {
        int seeds = this.board.getPlayerHouses(this.playerId).get(houseNumber - 1).getNumSeeds();
        return seeds > 0;
    }
}
