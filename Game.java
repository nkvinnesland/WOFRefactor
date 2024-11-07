import java.util.Objects;

/**
 * The Game class serves as an abstract base class for games that can be played in multiple rounds.
 * Each game maintains a record of all games played and allows for the addition of specific game logic
 * through abstract methods to be implemented by subclasses.
 */
public abstract class Game {
    AllGamesRecord allGamesRecord = new AllGamesRecord();

    /**
     * Provides a string representation of the Game instance, including the record of all games played.
     *
     * @return String representation of the game state.
     */
    public String toString() {
        return "Game{" +
                "allGamesRecord=" + allGamesRecord +
                '}';
    }

    /**
     * Compares this Game instance to another object for equality based on their game records.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(allGamesRecord, game.allGamesRecord);
    }

    /**
     * Returns a hash code for this Game instance.
     *
     * @return The hash code for this instance.
     */
    public int hashCode() {
        return Objects.hashCode(allGamesRecord);
    }

    /**
     * Plays multiple rounds of the game, recording results in an AllGamesRecord instance.
     *
     * @param record The record to store results of all games played.
     * @return The updated AllGamesRecord containing the results of all games.
     */
    public abstract AllGamesRecord playAll(AllGamesRecord record);

    /**
     * Plays a single round of the game, tracking the results in a GameRecord.
     *
     * @return A GameRecord containing the result of the game round.
     */
    public abstract GameRecord play();

    /**
     * Determines whether another game round should be played.
     *
     * @return true if another round should be played, otherwise false.
     */
    public abstract boolean playNext();
}
