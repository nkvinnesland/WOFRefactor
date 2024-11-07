import java.util.Objects;
import java.util.Scanner;

/**
 * The UserPlayer class represents a human player in the Wheel of Fortune game.
 * This player interacts with the game by entering guesses through the console.
 */
public class UserPlayer implements WheelOfFortunePlayer {

    private String playerId;       // Unique identifier for the player
    private Scanner scanner;       // Scanner for reading user input
    private int score;             // Score of the player

    /**
     * Constructs a UserPlayer with a specified player ID.
     *
     * @param playerId A unique identifier for this player.
     */
    public UserPlayer(String playerId) {
        this.playerId = playerId;
        this.scanner = new Scanner(System.in);
        this.score = 0;
    }

    /**
     * Increments the score of this player by one.
     */
    @Override
    public void incrementScore() {
        score++;
    }

    /**
     * Returns the current score of this player.
     *
     * @return The score of the player.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Provides a string representation of the UserPlayer instance, including the
     * player ID, scanner, and score.
     *
     * @return String representation of the UserPlayer.
     */
    @Override
    public String toString() {
        return "UserPlayer{" +
                "playerId='" + playerId + '\'' +
                ", scanner=" + scanner +
                ", score=" + score +
                '}';
    }

    /**
     * Compares this UserPlayer instance to another object for equality based on
     * the player ID, scanner, and score.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlayer that = (UserPlayer) o;
        return score == that.score && Objects.equals(playerId, that.playerId) && Objects.equals(scanner, that.scanner);
    }

    /**
     * Returns a hash code for this UserPlayer instance based on the player ID, scanner, and score.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerId, scanner, score);
    }

    /**
     * Sets the score of this player to a specified value.
     *
     * @param score The score to set for this player.
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Prompts the player to enter their next letter guess and reads the input.
     *
     * @return The next guessed letter entered by the player.
     */
    @Override
    public char nextGuess() {
        System.out.print("Please enter a guess (example A or a, case does not matter): ");
        return scanner.next().charAt(0);
    }

    /**
     * Returns the unique player ID of this player.
     *
     * @return The player ID.
     */
    @Override
    public String playerId() {
        return playerId;
    }

    /**
     * Resets the state of this player for a new game by setting the score to zero.
     */
    @Override
    public void reset() {
        score = 0;  // Reset the score for a new game
    }
}
