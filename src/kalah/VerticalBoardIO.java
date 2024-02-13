package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class VerticalBoardIO extends GameIO {

    public VerticalBoardIO(IO io, Configuration configuration) {
        super(io, configuration);
    }

    @Override
    protected void printState(Board board) {
        printBorder();
        printStore("P2", board);
        printDivider();
        printHouses(board);
        printDivider();
        printStore("P1", board);
        printBorder();
    }

    private void printBorder() {
        this.io.println("+---------------+");
    }

    private void printStore(String playerLabel, Board board) {
        int p1SeedsInStore = board.getAllStores().get(0).getNumSeeds();
        int p2SeedsInStore = board.getAllStores().get(1).getNumSeeds();

        if (playerLabel.equals("P1")) {
            this.io.println(String.format("| %s%s%d |       |", playerLabel, p1SeedsInStore <= 9 ? "  " : " ", p1SeedsInStore));
        } else if (playerLabel.equals("P2")) {
            this.io.println(String.format("|       | %s%s%d |", playerLabel, p2SeedsInStore <= 9 ? "  " : " ", p2SeedsInStore));
        }
    }

    private void printDivider() {
        this.io.println("+-------+-------+");
    }

    private void printHouses(Board board) {
        int numHousesPerPlayer = board.getNumHousesPerPlayer();
        List<House> p1Houses = board.getPlayerHouses(1);
        List<House> p2Houses = board.getPlayerHouses(2);

        for (int i = 0; i < numHousesPerPlayer; i++) {
            int p1HouseNumber = i + 1;
            int p1SeedsInHouse = p1Houses.get(i).getNumSeeds();
            int p2HouseNumber = numHousesPerPlayer - i;
            int p2Index = numHousesPerPlayer - i - 1;
            int p2SeedsInHouse = p2Houses.get(p2Index).getNumSeeds();

            printHouseState(p1HouseNumber, p1SeedsInHouse);
            printHouseState(p2HouseNumber, p2SeedsInHouse);
            this.io.println("|");
        }
    }

    private void printHouseState(int houseNumber, int seedsInHouse) {
        this.io.print(String.format("| %d[%s%d] ", houseNumber, seedsInHouse <= 9 ? " " : "", seedsInHouse));
    }
}
