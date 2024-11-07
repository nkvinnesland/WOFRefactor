import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * The GuessingGame class represents a generic guessing game that can be extended
 * to create different types of guessing games. It contains basic functionality
 * for handling attempts, generating a secret code, providing feedback, and controlling
 * the game's play flow.
 */
public abstract class GuessingGame extends Game {
    protected int attempts;                       // Number of attempts allowed for the game
    protected List<String> secretCode;            // Secret code or phrase to be guessed
    protected String feedbackMessage;             // Feedback message for the player's guess
    protected static final Scanner scanner = new Scanner(System.in); // Shared Scanner instance

    /**
     * Constructs a GuessingGame with a specified number of attempts.
     *
     * @param attempts The maximum number of attempts allowed in the game.
     */
    public GuessingGame(int attempts) {
        this.attempts = attempts;
    }

    /**
     * Provides a string representation of the GuessingGame instance, including
     * attempts, the secret code, and feedback message.
     *
     * @return String representation of the GuessingGame.
     */
    @Override
    public String toString() {
        return "GuessingGame{" +
                "attempts=" + attempts +
                ", secretCode=" + secretCode +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                '}';
    }

    /**
     * Compares this GuessingGame instance to another object for equality based on
     * attempts, the secret code, and feedback message.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GuessingGame that = (GuessingGame) o;
        return attempts == that.attempts && Objects.equals(secretCode, that.secretCode) && Objects.equals(feedbackMessage, that.feedbackMessage);
    }

    /**
     * Returns a hash code for this GuessingGame instance based on attempts, the
     * secret code, and feedback message.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attempts, secretCode, feedbackMessage);
    }

    /**
     * Abstract method to generate the secret code. Each game should implement its
     * own logic for generating the secret code.
     */
    public abstract void generateSecretCode();

    /**
     * Abstract method to provide feedback based on the player's guess.
     *
     * @param guess The player's guess as a list of strings.
     * @return A feedback message specific to the game.
     */
    public abstract String getFeedback(List<String> guess);

    /**
     * Plays multiple games in a row, recording each game's results, and continues
     * until the player decides to stop.
     *
     * @param record An AllGamesRecord to store the results of all games played.
     * @return The updated AllGamesRecord with all game records.
     */
    public AllGamesRecord playAll(AllGamesRecord record) {
        do {
            GameRecord gameRecord = play();  // Play each game
            record.add(gameRecord);          // Store the record after each game
        } while (playNext());                // Ask if they want to play again
        return record;
    }

    /**
     * Prompts the player to decide if they want to play another game.
     *
     * @return true if the player wants to play again, otherwise false.
     */
    public boolean playNext() {
        String response;
        do {
            System.out.print("Do you want to play another game? (yes/no): ");
            response = scanner.nextLine().trim().toLowerCase(); // Use the shared scanner
            if (response.equals("yes") || response.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        } while (true);

        return response.equals("yes");
    }

    /**
     * Abstract method to get the player's guess. Each game should implement its
     * own logic for capturing and returning the player's guess.
     *
     * @return The player's guess as a list of strings.
     */
    protected abstract List<String> getGuess();

    /**
     * Abstract method to determine if the game is over.
     *
     * @return true if the game is over, otherwise false.
     */
    protected abstract boolean isGameOver();

    /**
     * Abstract method to check if the player's guess is a winning guess.
     *
     * @param guess The player's guess as a list of strings.
     * @return true if the guess wins the game, otherwise false.
     */
    protected abstract boolean isWinningGuess(List<String> guess);
}