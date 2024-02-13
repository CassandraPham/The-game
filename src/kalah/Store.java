package kalah;

public class Store extends Pit {
    public Store(int ownerId, int numSeeds) {
        super(ownerId, numSeeds);
    }

    public Store(Store other) {
        super(other.getOwnerId(), other.getNumSeeds());
    }

    @Override
    protected boolean canBeSown(int playerId) {
        return this.ownerId == playerId;
    }
}
