package kalah;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Store> allStores;
    private List<List<House>> allHouses;
    private final Configuration configuration;

    public Board(Configuration configuration) {
        this.allHouses = new ArrayList<>();
        this.allStores = new ArrayList<>();
        this.configuration = configuration;
        setUpBoard();
    }

    public Board(Board other) {
        this.configuration = other.configuration;

        this.allStores = new ArrayList<>();
        for (Store store : other.allStores) {
            this.allStores.add(new Store(store));
        }

        this.allHouses = new ArrayList<>();
        for (List<House> houses : other.allHouses) {
            List<House> newHouses = new ArrayList<>();

            for (int i = 0; i < this.configuration.getNumHousesPerPlayer(); i++) {
                House originalHouse = houses.get(i);
                House clonedHouse = new House(originalHouse);

                newHouses.add(clonedHouse);

                if (i > 0) {
                    newHouses.get(i - 1).setNextPit(clonedHouse);
                }
            }
            this.allHouses.add(newHouses);
        }
        connectHousesAndStores();
        setOppositeHousePairs();
    }

    /**
     * Empties a specified house of a specified player and returns the number of seeds taken.
     *
     * @param houseNumber  The house number from which the seeds are taken.
     * @param playerId     The ID of the player who owns the house from which seeds are taken.
     * @return             The number of seeds removed from the specified house.
     */
    protected int takeSeeds(int houseNumber, int playerId) {
        int houseIndex = houseNumber - 1;
        int playerIndex = playerId - 1;

        List<House> houses = this.allHouses.get(playerIndex);
        return houses.get(houseIndex).emptyHouse();
    }

    /**
     * Returns a list of all houses owned by a specific player.
     *
     * @param playerId The ID of the player whose houses are to be retrieved.
     * @return         A list of House objects owned by the player with the given ID.
     */
    protected List<House> getPlayerHouses(int playerId) {
        return this.allHouses.get(playerId - 1);
    }

    protected int getNumHousesPerPlayer() {
        return this.configuration.getNumHousesPerPlayer();
    }

    protected List<Store> getAllStores() {
        return this.allStores;
    }

    protected Store getStore(int storeIndex) {
        return this.allStores.get(storeIndex);
    }

    protected int getNumPits() {
        return this.configuration.getNumHousesPerPlayer() * this.configuration.getNumberOfPlayers() + this.allStores.size();
    }

    /**
     * Sets up the board for the game.
     *
     * This method creates and initialises houses and stores for each player.
     * It then connects the houses and stores to form a continuous path for the game board.
     * and sets opposite pairs of houses to cater for the capture rule.
     */
    private void setUpBoard() {
        this.allHouses = new ArrayList<>();

        for (int i = 1; i <= this.configuration.getNumberOfPlayers(); i++) {
            this.allHouses.add(createHouses(i));
        }
        this.allStores = createStores();
        connectHousesAndStores();
        setOppositeHousePairs();
    }

    /**
     * Creates and initialises a list of houses for a specified player.
     *
     * The number of houses and seeds per house depend on the configuration.
     * Each house keeps a reference to the next house in the list.
     *
     * @param playerId The ID of the player whose houses are being created.
     * @return         A list of House objects that belong to the specified player.
     */
    private List<House> createHouses(int playerId) {
        ArrayList<House> houses = new ArrayList<>();

        for (int i = 0; i < this.configuration.getNumHousesPerPlayer(); i++) {
            House house = new House(playerId, this.configuration.getNumSeedsPerHouse());
            houses.add(house);
            if (i > 0) {
                houses.get(i - 1).setNextPit(house);
            }
        }

        return houses;
    }

    /**
     * Creates and initialises a list of stores for each player.
     *
     * Each store is initialised with a player ID indicating the owner of the store, and 0 seed.
     *
     * @return A list of Store objects.
     */
    private List<Store> createStores() {
        ArrayList<Store> stores = new ArrayList<>();

        for (int i = 1; i <= this.configuration.getNumberOfPlayers(); i++) {
            Store store = new Store(i, 0);
            stores.add(store);
        }

        return stores;
    }

    /**
     * Connects the houses and stores of all players to create a circular path for the board.
     *
     * This method connects each player's last house to their store,
     * and their store to the next player's first house,
     * and last player's store to the first player's first house.
     */
    private void connectHousesAndStores() {
        int numberOfPlayers = this.allHouses.size();

        // Loop through each player's list of houses
        for (int i = 1; i <= numberOfPlayers; i++) {
            List<House> currentHouses = getPlayerHouses(i);
            Store currentStore = this.allStores.get(i - 1);

            House lastHouse = currentHouses.get(this.configuration.getNumHousesPerPlayer() - 1);

            House nextFirstHouse;

            if (i < numberOfPlayers) {
                // If current player is not the last player, get the next player's first house
                nextFirstHouse = getPlayerHouses(i + 1).get(0);
            } else {
                // If current player is the last player, get the first player's first house
                nextFirstHouse = getPlayerHouses(1).get(0);
            }

            lastHouse.setNextPit(currentStore);
            currentStore.setNextPit(nextFirstHouse);
        }
    }

    /**
     * Sets the opposite house for each house to link pairs of opposite houses.
     *
     * This method determines the opposite house on the next player's side and assign it.
     * For the last player, the opposite houses are from the first player's list of houses.
     */
    private void setOppositeHousePairs() {
        int numberOfPlayers = this.allHouses.size();

        // Loop through each player's list of houses
        for (int i = 1; i <= numberOfPlayers; i++) {
            List<House> currentPlayerHouses = getPlayerHouses(i);

            List<House> nextPlayerHouses;
            if (i < numberOfPlayers) {
                // If the current player is not the last player, get the next player's houses
                nextPlayerHouses = getPlayerHouses(i + 1);
            } else {
                // If the current player is the last player, get the first player's houses
                nextPlayerHouses = getPlayerHouses(1);
            }

            // Set the opposite house for each of the current player's houses
            for (int j = 0; j < this.configuration.getNumHousesPerPlayer(); j++) {
                House currentHouse = currentPlayerHouses.get(j);

                int oppositeHouseIndex = this.configuration.getNumHousesPerPlayer() - j - 1;
                House oppositeHouse = nextPlayerHouses.get(oppositeHouseIndex);

                currentHouse.setOppositeHouse(oppositeHouse);
                oppositeHouse.setOppositeHouse(currentHouse);
            }
        }
    }
}
