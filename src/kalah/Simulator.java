package kalah;
import java.util.List;

public class Simulator {
    public Simulator() {}
    protected Pit simulateSowing(Board board, int playerId, int seeds, int chosenHouseIndex) {
        List<House> playerHouses = board.getPlayerHouses(playerId);
        Pit currentPit = playerHouses.get(chosenHouseIndex);

        while (seeds > 0) {
            currentPit = currentPit.getNextPit();
            if (currentPit.canBeSown(playerId)) {
                // As this is a simulation, the seeds are not actually being sown,
                // only the count is decremented.
                seeds--;
            }
        }

        return currentPit;
    }

}
