package kalah;

public class House extends Pit {
    private House oppositeHouse;

    public House(int ownerId, int numSeeds) {
        super(ownerId, numSeeds);
    }

    public House(House other) {
        super(other.getOwnerId(), other.getNumSeeds());
        this.oppositeHouse = null;
    }

    @Override
    protected boolean canBeSown(int playerId) {
        return true;
    }

    protected int emptyHouse() {
        int seeds = this.numSeeds;
        this.numSeeds = 0;
        return seeds;
    }

    protected int capture() {
        House oppositeHouse = this.getOppositeHouse();
        if (oppositeHouse.getNumSeeds() >= 1) {
            int totalSeeds;
            totalSeeds = oppositeHouse.emptyHouse() + this.emptyHouse();
            return totalSeeds;
        } else {
            return 0;
        }
    }

    protected void setOppositeHouse(House oppositeHouse) {
        this.oppositeHouse = oppositeHouse;
    }

    protected House getOppositeHouse() {
        return this.oppositeHouse;
    }
}
