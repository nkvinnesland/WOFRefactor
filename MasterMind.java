import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The MasterMind class represents a game where players try to guess a randomly generated sequence of colors.
 * This class extends the GuessingGame superclass, which provides shared game functionality. The player has
 * a limited number of attempts to guess the sequence correctly.
 */
public class MasterMind extends GuessingGame {

    private static final String[] COLORS = {"R", "G", "B", "Y"}; // Available colors for guessing
    private List<String> secretCode;
    private final int maxAttempts; // Store the maximum number of attempts

    /**
     * Constructs a MasterMind game with a specified number of maximum attempts.
     *
     * @param maxAttempts The maximum number of attempts allowed.
     */
    public MasterMind(int maxAttempts) {
        super(maxAttempts);
        this.maxAttempts = maxAttempts; // Set the original maximum attempts
    }

    /**
     * Generates a random secret code sequence using the available colors.
     */
    @Override
    public void generateSecretCode() {
        Random random = new Random();
        secretCode = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // Generating a 4-color code
            secretCode.add(COLORS[random.nextInt(COLORS.length)]);
        }
    }

    /**
     * Initiates and plays a single game of MasterMind, allowing the player to make guesses until
     * they either guess the code correctly or run out of attempts.
     *
     * @return GameRecord containing the player's final score and identifier.
     */
    @Override
    public GameRecord play() {
        generateSecretCode(); // Generate a new secret code at the start of each game
        this.attempts = maxAttempts; // Reset attempts to the maximum for a new game

        System.out.println("Starting MasterMind. Try to guess the color sequence!");

        while (!isGameOver()) {
            List<String> guess = getGuess(); // Prompt player for a guess

            if (isWinningGuess(guess)) {
                System.out.println("Congratulations! You've guessed the code correctly.");
                break;
            }

            String feedback = getFeedback(guess);
            System.out.println(feedback);
            System.out.println("Debug: Remaining attempts = " + attempts);
        }

        int score = attempts > 0 ? attempts : 0; // Score based on remaining attempts
        System.out.println("Game over! The correct code was: " + secretCode);
        return new GameRecord(score, "Player");
    }

    /**
     * Determines if the game has ended due to either a successful guess or exhausting all attempts.
     *
     * @return true if the game is over, otherwise false.
     */
    @Override
    public boolean isGameOver() {
        return attempts <= 0;
    }

    /**
     * Checks if the given guess matches the secret code.
     *
     * @param guess The player's guess represented as a list of color strings.
     * @return true if the guess matches the secret code, otherwise false.
     */
    @Override
    public boolean isWinningGuess(List<String> guess) {
        return guess.equals(secretCode);
    }

    /**
     * Prompts the player to enter a guess and validates the input. The guess should be
     * a sequence of color letters.
     *
     * @return The player's guess as a List of color strings.
     */
    @Override
    protected List<String> getGuess() {
        List<String> guess = new ArrayList<>();
        System.out.println("Enter your guess (4 colors from R, G, B, Y):");
        String input = GuessingGame.scanner.nextLine().trim().toUpperCase();

        if (input.length() == 4 && isValidColors(input)) {
            for (char c : input.toCharArray()) {
                guess.add(String.valueOf(c));
            }
        } else {
            System.out.println("Invalid input. Please enter exactly 4 colors as single letters from R, G, B, Y.");
            return getGuess(); // Recursive call to prompt again
        }
        return guess;
    }

    /**
     * Generates feedback for the player's guess by calculating the number of exact and partial matches.
     *
     * @param guess The player's guess as a list of color strings.
     * @return A feedback message indicating the number of exact and partial matches.
     */
    @Override
    public String getFeedback(List<String> guess) {
        int exactMatches = 0;   // Correct color in correct position
        int partialMatches = 0; // Correct color in wrong position

        List<String> unmatchedCode = new ArrayList<>(secretCode);
        List<String> unmatchedGuess = new ArrayList<>();

        // First pass: Find exact matches
        for (int i = 0; i < guess.size(); i++) {
            if (guess.get(i).equals(secretCode.get(i))) {
                exactMatches++;
                unmatchedCode.set(i, null); // Mark as matched
            } else {
                unmatchedGuess.add(guess.get(i)); // Add to unmatched guess colors
            }
        }

        // Second pass: Find partial matches
        for (String color : unmatchedGuess) {
            if (unmatchedCode.contains(color)) {
                partialMatches++;
                unmatchedCode.set(unmatchedCode.indexOf(color), null); // Mark color as matched
            }
        }

        attempts--; // Decrement attempts only after valid input and feedback calculation
        return String.format("Feedback: %d exact, %d partial. Attempts left: %d", exactMatches, partialMatches, attempts);
    }

    /**
     * Validates the input string to ensure it contains only valid colors.
     *
     * @param input The input string to validate.
     * @return true if the input only contains valid color letters, otherwise false.
     */
    private boolean isValidColors(String input) {
        for (char c : input.toCharArray()) {
            boolean valid = false;
            for (String color : COLORS) {
                if (color.equalsIgnoreCase(String.valueOf(c))) {
                    valid = true;
                    break;
                }
            }
            if (!valid) return false;
        }
        return true;
    }

    /**
     * Provides a string representation of the MasterMind game.
     *
     * @return String representation of the current game state, including secret code and maximum attempts.
     */
    @Override
    public String toString() {
        return "MasterMind{" +
                "secretCode=" + secretCode +
                ", maxAttempts=" + maxAttempts +
                '}';
    }

    /**
     * Checks if this MasterMind instance is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MasterMind that = (MasterMind) o;
        return maxAttempts == that.maxAttempts && Objects.equals(secretCode, that.secretCode);
    }

    /**
     * Returns a hash code for this MasterMind instance.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), secretCode, maxAttempts);
    }

    /**
     * Main method to start and continuously play the MasterMind game until the player decides to stop.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        MasterMind game = new MasterMind(10); // Initialize game with 10 attempts
        AllGamesRecord allGamesRecord = new AllGamesRecord(); // Create a record for all games
        game.playAll(allGamesRecord); // Play all games until the player decides to stop

        // Print all game results at the end
        System.out.println("\nAll Games Results:");
        System.out.println("Average Score: " + allGamesRecord.average());
        System.out.println("Top Scores:");
        allGamesRecord.highGameList(5).forEach(System.out::println);
    }
}