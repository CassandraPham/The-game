package kalah;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public GameState() {}

    /**
     * Checks if the given pit is a store owned by a specified player.
     *
     * @param playerId The ID of the player.
     * @param pit      The Pit object to check.
     * @return         True if the pit is a store that is owned by the player with the given ID, false otherwise.
     */
    protected boolean isPlayerOwnStore(int playerId, Pit pit) {
        return pit instanceof Store && pit.getOwnerId() == playerId;
    }

    /**
     * Checks if the given pit satisfies the capture rule for the specified player.
     *
     * @param playerId The ID of the player.
     * @param pit      The Pit object to check.
     * @return         True if the pit is a house owned by the player with the given ID and contains 1 seed, false otherwise.
     */
    protected boolean canCapture(int playerId, Pit pit) {
        return pit instanceof House && pit.getOwnerId() == playerId && pit.getNumSeeds() == 1;
    }

    /**
     * Checks if all houses of the player with the given ID are empty.
     *
     * This method iterates through all houses of the specified player
     * and verifies whether each house is empty (i.e., contains 0 seeds).
     *
     * @param playerId The ID of the player whose house are checked.
     * @param board    The current game board.
     * @return         True if all houses of the player with the given ID are empty, false otherwise.
     */
    protected boolean checkPlayerHousesEmpty(int playerId, Board board) {
        boolean allEmpty = true;
        for (int i = 0; i < board.getNumHousesPerPlayer(); i++) {
            List<House> playerHouseList = board.getPlayerHouses(playerId);
            if (playerHouseList.get(i).getNumSeeds() != 0) {
                allEmpty = false;
                break;
            }
        }
        return allEmpty;
    }

    /**
     * Calculates the scores of all players and add them to a list of scores.
     *
     * @param players The list of all players.
     * @return        A list of all players' scores, in the same order as the given list of players.
     */
    protected List<Integer> calculateScores(List<Player> players) {
        List<Integer> scores = new ArrayList<>();

        for (Player player : players) {
            player.calculateScore();
            int score = player.getScore();
            scores.add(score);
        }
        return scores;
    }

    /**
     * Determines the player with the highest score from a given list of scores.
     *
     * This method iterates over a list of scores and keeps track of the highest score found and the player who has that score.
     * If two or more players have the same highest score, the winner is set to null, indicating a draw.
     *
     * @param scores   The list of scores of all players. Each score corresponds to a player in the players list by index.
     * @param players  The list of all players.
     * @return         The player with the highest score, or null if the game is a draw.
     */
    protected Player determineWinner(List<Integer> scores, List<Player> players) {
        int highestScore = 0;
        Player winner = null;

        for (int i = 0; i < scores.size(); i++) {
            int score = scores.get(i);

            if (score > highestScore) {
                highestScore = score;
                winner = players.get(i);
            } else if (score == highestScore) {
                winner = null;
            }
        }
        return winner;
    }

    /**
     * Checks if the given pit satisfies the capture rule for the specified player according to sowing simulation logic.
     *
     * @param playerId The ID of the player.
     * @param pit      The Pit object to check.
     * @return         True if the pit is a house owned by the player with the given ID and contains 0 seed, false otherwise.
     */
    protected boolean canSimulateCapture(int playerId, Pit pit) {
        return pit instanceof House && pit.getOwnerId() == playerId && pit.getNumSeeds() == 0;
    }
}
