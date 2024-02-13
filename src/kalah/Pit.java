package kalah;

public abstract class Pit {
    protected int numSeeds;
    private Pit nextPit;
    protected int ownerId;

    public Pit(int ownerId, int numSeeds) {
        this.numSeeds = numSeeds;
        this.ownerId = ownerId;
    }

    protected int getNumSeeds() {
        return this.numSeeds;
    }

    protected void sow(int numSeedsSown) {
        this.numSeeds += numSeedsSown;
    }

    protected void setNextPit(Pit nextPit) {
        this.nextPit = nextPit;
    }

    protected Pit getNextPit() {
        return this.nextPit;
    }

    protected int getOwnerId() {
        return this.ownerId;
    }

    protected abstract boolean canBeSown(int playerId);
}
