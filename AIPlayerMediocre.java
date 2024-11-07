import java.util.*;

/**
 * The AIPlayerMediocre class represents an intermediate AI player in the Wheel of Fortune game.
 * This AI player prioritizes guessing common English letters to increase its chances of correctly
 * guessing letters in the phrase. If no common letters remain, it falls back on random guessing.
 */
public class AIPlayerMediocre implements WheelOfFortunePlayer {

    private String playerId;
    private StringBuilder usedLetters; // Track letters already guessed by this AI player
    private Random random;
    private int score;

    /**
     * Constructs an AIPlayerMediocre with a specified player ID.
     *
     * @param playerId A unique identifier for this AI player.
     */
    public AIPlayerMediocre(String playerId) {
        this.playerId = playerId;
        this.usedLetters = new StringBuilder();
        this.random = new Random();
        this.score = 0;
    }

    /**
     * Increments the score of this AI player by one.
     */
    @Override
    public void incrementScore() {
        score++;
    }

    /**
     * Returns the current score of this AI player.
     *
     * @return The score of the player.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of this AI player to a specified value.
     *
     * @param score The score to set for this player.
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Determines the next letter to guess by prioritizing common English letters.
     * If all common letters have been used, a random unused letter is chosen as a fallback.
     *
     * @return The next guessed letter.
     */
    @Override
    public char nextGuess() {
        // Common letters prioritized for guessing
        List<Character> commonLetters = Arrays.asList('e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r');

        char guess = 0;
        boolean validGuess = false;

        // Attempt to guess from common letters
        for (char letter : commonLetters) {
            if (usedLetters.indexOf(String.valueOf(letter)) == -1) {
                guess = letter;
                validGuess = true;
                break;
            }
        }

        // If no common letter is found, choose a random letter as a fallback
        if (!validGuess) {
            do {
                guess = (char) (random.nextInt(26) + 'a'); // Random letter from 'a' to 'z'
            } while (usedLetters.indexOf(String.valueOf(guess)) != -1); // Ensure the letter hasn't been guessed
        }

        // Mark this guess as used
        usedLetters.append(guess);
        return guess;
    }

    /**
     * Returns the unique player ID of this AI player.
     *
     * @return The player ID.
     */
    @Override
    public String playerId() {
        return playerId;
    }

    /**
     * Resets the state of this AI player for a new game, clearing guessed letters
     * and resetting the score to zero.
     */
    @Override
    public void reset() {
        usedLetters.setLength(0); // Clear the used letters for a new game
        score = 0;  // Reset the score for a new game
    }

    /**
     * Provides a string representation of the AIPlayerMediocre instance, including its player ID,
     * guessed letters, and current score.
     *
     * @return String representation of the AI player's state.
     */
    @Override
    public String toString() {
        return "AIPlayerMediocre{" +
                "playerId='" + playerId + '\'' +
                ", usedLetters=" + usedLetters +
                ", random=" + random +
                ", score=" + score +
                '}';
    }

    /**
     * Compares this AIPlayerMediocre instance to another object for equality based on
     * the player ID, guessed letters, and score.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIPlayerMediocre that = (AIPlayerMediocre) o;
        return score == that.score && Objects.equals(playerId, that.playerId) && Objects.equals(usedLetters, that.usedLetters) && Objects.equals(random, that.random);
    }

    /**
     * Returns a hash code for this AIPlayerMediocre instance.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerId, usedLetters, random, score);
    }
}