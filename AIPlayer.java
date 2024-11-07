import java.util.*;

/**
 * The AIPlayer class represents a player in the Wheel of Fortune game controlled by AI.
 * This AI player prioritizes guessing common letters and letter combinations to improve
 * its chances of solving the phrase.
 */
public class AIPlayer implements WheelOfFortunePlayer {

    private String playerId;
    private StringBuilder usedLetters; // Track letters already guessed by this AI player
    private Random random;
    private int score;

    /**
     * Constructs an AIPlayer with a specified player ID.
     *
     * @param playerId A unique identifier for this AI player.
     */
    public AIPlayer(String playerId) {
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
     * Determines the next letter to guess based on prioritized common letters and letter combinations.
     * If all prioritized letters are used, a random unused letter is selected as a fallback.
     *
     * @return The next guessed letter.
     */
    @Override
    public char nextGuess() {
        // Common letters prioritized for guessing
        List<Character> commonLetters = Arrays.asList('e', 't', 'a', 'o');

        // Define common letter combinations for each letter
        Map<Character, List<Character>> commonCombinations = new HashMap<>();
        commonCombinations.put('a', Arrays.asList('n', 's', 't', 'r', 'l'));
        commonCombinations.put('t', Arrays.asList('h', 'r', 'o', 'i', 'a'));
        commonCombinations.put('e', Arrays.asList('r', 'n', 's', 'd', 'v'));
        commonCombinations.put('o', Arrays.asList('u', 'n', 'f', 'r', 't'));
        commonCombinations.put('i', Arrays.asList('n', 's', 't', 'e', 'o'));

        char guess = 0;
        boolean validGuess = false;

        // Loop until a valid guess is found
        while (!validGuess) {
            // First, prioritize common letters
            for (char letter : commonLetters) {
                if (usedLetters.indexOf(String.valueOf(letter)) == -1) {
                    guess = letter;
                    validGuess = true;
                    break;
                }
            }

            // If no common letter found, pick from common combinations
            if (!validGuess) {
                for (Map.Entry<Character, List<Character>> entry : commonCombinations.entrySet()) {
                    for (char combinationLetter : entry.getValue()) {
                        if (usedLetters.indexOf(String.valueOf(combinationLetter)) == -1) {
                            guess = combinationLetter;
                            validGuess = true;
                            break;
                        }
                    }
                    if (validGuess) break;
                }
            }

            // As a fallback, choose any random unused letter
            if (!validGuess) {
                char randomLetter;
                do {
                    randomLetter = (char) (random.nextInt(26) + 'a'); // Random letter from 'a' to 'z'
                } while (usedLetters.indexOf(String.valueOf(randomLetter)) != -1);
                guess = randomLetter;
                validGuess = true;
            }
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
     * Provides a string representation of the AIPlayer instance, including its player ID,
     * guessed letters, and current score.
     *
     * @return String representation of the AI player's state.
     */
    @Override
    public String toString() {
        return "AIPlayer{" +
                "playerId='" + playerId + '\'' +
                ", usedLetters=" + usedLetters +
                ", random=" + random +
                ", score=" + score +
                '}';
    }

    /**
     * Compares this AIPlayer instance to another object for equality based on
     * the player ID, guessed letters, and score.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIPlayer aiPlayer = (AIPlayer) o;
        return score == aiPlayer.score && Objects.equals(playerId, aiPlayer.playerId) && Objects.equals(usedLetters, aiPlayer.usedLetters) && Objects.equals(random, aiPlayer.random);
    }

    /**
     * Returns a hash code for this AIPlayer instance.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerId, usedLetters, random, score);
    }
}