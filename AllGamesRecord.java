import java.util.*;

/**
 * The AllGamesRecord class maintains a record of all games played. It provides functionality
 * to calculate averages, retrieve high scores, and filter results by player.
 */
public class AllGamesRecord {

    private List<GameRecord> allGames; // List to store records of all games played

    /**
     * Constructs an AllGamesRecord with an empty list of game records.
     */
    public AllGamesRecord(){
        this.allGames = new ArrayList<>();
    }

    /**
     * Adds a new game record to the list of all games.
     *
     * @param gameRecord The GameRecord to add.
     */
    public void add(GameRecord gameRecord){
        allGames.add(gameRecord);
    }

    /**
     * Calculates the average score across all games.
     *
     * @return The average score, or 0 if there are no games recorded.
     */
    public int average(){
        int sum = 0;

        for (GameRecord rec : allGames){
            sum += rec.getScore();
        }
        return allGames.isEmpty() ? 0 : sum / allGames.size(); // Avoid division by zero
    }

    /**
     * Provides a string representation of the AllGamesRecord instance, including the list of all games.
     *
     * @return String representation of the AllGamesRecord.
     */
    @Override
    public String toString() {
        return "AllGamesRecord{" +
                "allGames=" + allGames +
                '}';
    }

    /**
     * Compares this AllGamesRecord instance to another object for equality based on the list of games.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllGamesRecord that = (AllGamesRecord) o;
        return Objects.equals(allGames, that.allGames);
    }

    /**
     * Returns a hash code for this AllGamesRecord instance.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(allGames);
    }

    /**
     * Calculates the average score for a specific player across all their games.
     *
     * @param playerId The ID of the player for whom the average is calculated.
     * @return The player's average score, or 0 if the player has no recorded games.
     */
    public int playerAverage(String playerId){
        int sum = 0;
        int count = 0;

        for (GameRecord rec : allGames){
            if (rec.getPlayerId().equals(playerId)){
                sum += rec.getScore();
                count++;
            }
        }
        return count == 0 ? 0 : sum / count; // Avoid division by zero
    }

    /**
     * Retrieves the list of top n game records based on score, sorted in descending order.
     *
     * @param n The number of top game records to retrieve.
     * @return A list of up to n highest-scoring GameRecords.
     */
    public List<GameRecord> highGameList(int n) {
        // Sort allGames by score in descending order
        Collections.sort(allGames, Comparator.comparingInt(GameRecord::getScore).reversed());

        // Get the top n scores or the whole list if n is larger than the size
        return allGames.subList(0, Math.min(n, allGames.size()));
    }

    /**
     * Retrieves the list of top n game records for a specific player, sorted in descending order of score.
     *
     * @param playerId The ID of the player.
     * @param n The number of top game records to retrieve.
     * @return A list of up to n highest-scoring GameRecords for the specified player.
     */
    public List<GameRecord> highGameList(String playerId, int n) {
        ArrayList<GameRecord> playerGames = new ArrayList<>();

        for (GameRecord rec : allGames) {
            if (rec.getPlayerId().equals(playerId)) {
                playerGames.add(rec);
            }
        }
        Collections.sort(playerGames, Comparator.comparingInt(GameRecord::getScore).reversed());

        return playerGames.subList(0, Math.min(n, playerGames.size()));
    }
}