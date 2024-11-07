import java.util.Objects;

/**
 * The GameRecord class represents a record of a game, containing the player's ID and their score.
 * It supports comparison based on the score to facilitate sorting of game records.
 */
public class GameRecord implements Comparable<GameRecord> {

    private String playerId; // Unique identifier for the player
    private int score;       // Score of the player for this game record

    /**
     * Constructs a GameRecord with the specified score and player ID.
     *
     * @param score    The score of the player in this game.
     * @param playerId The unique identifier of the player.
     */
    public GameRecord(int score, String playerId){
        this.score = score;
        this.playerId = playerId;
    }

    /**
     * Returns the score of the player in this game.
     *
     * @return The score.
     */
    public int getScore(){
        return score;
    }

    /**
     * Increments the score by one and returns the updated score.
     *
     * @return The updated score after incrementing by one.
     */
    public int setScore(){
        score = score + 1;
        return score;
    }

    /**
     * Compares this GameRecord instance to another object for equality based on
     * the player ID and score.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord that = (GameRecord) o;
        return score == that.score && Objects.equals(playerId, that.playerId);
    }

    /**
     * Returns a hash code for this GameRecord instance based on the player ID and score.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerId, score);
    }

    /**
     * Returns the player ID associated with this game record.
     *
     * @return The player ID.
     */
    public String getPlayerId(){
        return playerId;
    }

    /**
     * Compares this GameRecord to another GameRecord based on score, to allow sorting
     * in ascending order by score.
     *
     * @param other The GameRecord to compare to.
     * @return A negative integer, zero, or a positive integer as this score is less than,
     *         equal to, or greater than the specified GameRecord's score.
     */
    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(this.score, other.score);
    }

    /**
     * Provides a string representation of the GameRecord instance, including the player ID and score.
     *
     * @return String representation of the game record.
     */
    @Override
    public String toString(){
        return "GameRecord: " + playerId + " - " + score;
    }
}
