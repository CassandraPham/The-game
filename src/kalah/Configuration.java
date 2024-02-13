package kalah;

public class Configuration {
    private final int numPlayers;
    private final int numSeedsPerHouse;
    private final int numHousesPerPlayer;

    public Configuration(int numPlayers, int numSeedsPerHouse, int numHousesPerPlayer) {
        this.numPlayers = numPlayers;
        this.numSeedsPerHouse = numSeedsPerHouse;
        this.numHousesPerPlayer = numHousesPerPlayer;
    }

    public int getNumberOfPlayers() {
        return this.numPlayers;
    }

    public int getNumSeedsPerHouse() {
        return this.numSeedsPerHouse;
    }

    public int getNumHousesPerPlayer() {
        return this.numHousesPerPlayer;
    }
}
