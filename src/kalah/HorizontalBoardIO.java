package kalah;

import com.qualitascorpus.testsupport.IO;

import java.util.List;

public class HorizontalBoardIO extends GameIO {

    public HorizontalBoardIO(IO io, Configuration configuration) {
        super(io, configuration);
    }

    @Override
    protected void printState(Board board) {
        List<House> p1Houses = board.getPlayerHouses(1);
        List<House> p2Houses = board.getPlayerHouses(2);
        int numHousesPerPlayer = board.getNumHousesPerPlayer();

        printBorder(numHousesPerPlayer);
        this.io.print("| P2 ");
        printHouses(p2Houses, true);
        printStore(board.getAllStores().get(0).getNumSeeds());
        this.io.println("|");
        printDivider(numHousesPerPlayer);
        printStore(board.getAllStores().get(1).getNumSeeds());
        printHouses(p1Houses, false);
        this.io.println("| P1 |");
        printBorder(numHousesPerPlayer);
    }

    private void printBorder(int numHousesPerPlayer) {
        this.io.print("+----");
        for (int i = 0; i < numHousesPerPlayer; i++) {
            this.io.print("+-------");
        }
        this.io.println("+----+");
    }

    /**
     * Prints the given houses in a specified order with their numbers of seeds.
     *
     * The houses can be printed in either ascending or descending order,
     * depending on the 'reverseOrder' parameter.
     *
     * @param houses       The list of houses to be printed.
     * @param reverseOrder A boolean flag to decide which order to print the houses.
     */
    private void printHouses(List<House> houses, boolean reverseOrder) {
        int numHouses = houses.size();

        // Start from the last house if reverseOrder is true, otherwise start from the first house
        int startIndex = reverseOrder ? numHouses - 1 : 0;

        // End at the first house if reverseOrder is true, otherwise end at the last house
        int endIndex = reverseOrder ? 0 : numHouses - 1;

        // Step backwards if reverseOrder is true, otherwise step forwards
        int step = reverseOrder ? -1 : 1;

        // Loop through the houses to print each house number along with their seed count
        for (int i = startIndex; reverseOrder ? i >= endIndex : i <= endIndex; i += step) {
            House house = houses.get(i);
            int houseNum = i + 1;
            int seeds = house.getNumSeeds();
            String formattedString = String.format("| %d[%s%d] ", houseNum, seeds <= 9 ? " " : "", seeds);
            this.io.print(formattedString);
        }
    }

    private void printStore(int seeds) {
        this.io.print(String.format("| %s%d ", seeds <= 9 ? " " : "", seeds));
    }

    private void printDivider(int numHousesPerPlayer) {
        this.io.print("|    |");
        for (int i = 0; i < numHousesPerPlayer; i++) {
            if (i == numHousesPerPlayer - 1) {
                this.io.print("-------");
            } else {
                this.io.print("-------+");
            }
        }
        this.io.println("|    |");
    }
}
