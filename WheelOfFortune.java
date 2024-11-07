import java.util.*;

/**
 * WheelOfFortune represents a game where a player attempts to guess a hidden phrase.
 * It extends the core functionality provided by GuessingGame.
 */
public abstract class WheelOfFortune extends GuessingGame {

    protected List<String> phrases; // List of possible phrases for the game
    protected String phrase; // The current phrase to guess
    protected StringBuilder hiddenPhrase; // The masked version of the phrase with unrevealed letters as '*'
    protected StringBuilder usedLetters; // Keeps track of letters guessed by the player
    protected int numGuesses; // Number of guesses remaining for the player

    /**
     * Constructor for initializing a WheelOfFortune game with a list of phrases and a specific number of attempts.
     *
     * @param phrases List of phrases to use in the game.
     * @param attempts Number of attempts allowed in the game.
     */
    public WheelOfFortune(List<String> phrases, int attempts) {
        super(attempts); // Pass attempts to the GuessingGame constructor
        this.phrases = new ArrayList<>(phrases); // Initialize the list of phrases
        this.numGuesses = attempts; // Set the number of guesses
        this.usedLetters = new StringBuilder(); // Initialize used letters
        generateSecretCode(); // Generate the initial secret code (phrase)
    }

    /**
     * Generates the secret code for the game, which is a random phrase from the list.
     * It also sets up the hidden version of the phrase.
     */
    @Override
    public void generateSecretCode() {
        Random random = new Random();
        this.phrase = phrases.get(random.nextInt(phrases.size())); // Select a random phrase
        this.hiddenPhrase = generateHiddenPhrase(this.phrase); // Generate hidden version of the phrase
    }

    /**
     * Generates the hidden version of the given phrase with letters replaced by '*'.
     *
     * @param phrase The phrase to hide.
     * @return A StringBuilder with letters hidden by '*'.
     */
    protected StringBuilder generateHiddenPhrase(String phrase) {
        StringBuilder hidden = new StringBuilder();
        for (char c : phrase.toCharArray()) {
            if (Character.isLetter(c)) {
                hidden.append('*'); // Replace letters with '*'
            } else {
                hidden.append(c); // Keep non-letters as is (spaces, punctuation)
            }
        }
        return hidden;
    }

    /**
     * Checks if the player's guess is a winning guess by verifying if the phrase is fully revealed.
     *
     * @param guess The player's guess as a List of Strings.
     * @return true if the hidden phrase is fully revealed, otherwise false.
     */
    @Override
    public boolean isWinningGuess(List<String> guess) {
        // Check if there are any unrevealed letters left
        return hiddenPhrase.indexOf("*") == -1;
    }

    @Override
    public String toString() {
        return "WheelOfFortune{" +
                "phrases=" + phrases +
                ", phrase='" + phrase + '\'' +
                ", hiddenPhrase=" + hiddenPhrase +
                ", usedLetters=" + usedLetters +
                ", numGuesses=" + numGuesses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WheelOfFortune that = (WheelOfFortune) o;
        return numGuesses == that.numGuesses && Objects.equals(phrases, that.phrases) && Objects.equals(phrase, that.phrase) && Objects.equals(hiddenPhrase, that.hiddenPhrase) && Objects.equals(usedLetters, that.usedLetters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phrases, phrase, hiddenPhrase, usedLetters, numGuesses);
    }

    /**
     * Prompts the player to enter their guess, which should be a single letter.
     *
     * @return The player's valid guess as a List containing a single character string.
     */
    @Override
    protected abstract List<String> getGuess();

    /**
     * Provides feedback by updating the hidden phrase if the guessed letter is in the phrase.
     *
     * @param guess The player's guess as a List of Strings.
     * @return Feedback message showing the current state of the hidden phrase.
     */
    @Override
    public String getFeedback(List<String> guess) {
        String guessedLetter = guess.get(0);
        boolean found = false;

        for (int i = 0; i < phrase.length(); i++) {
            if (String.valueOf(phrase.charAt(i)).equalsIgnoreCase(guessedLetter)) {
                hiddenPhrase.setCharAt(i, phrase.charAt(i));
                found = true;
            }
        }

        if (found) {
            return "Good guess! Current phrase: " + hiddenPhrase;
        } else {
            numGuesses--;
            return "Sorry, that letter is not in the phrase. Guesses remaining: " + numGuesses;
        }
    }

    /**
     * Plays a single game of Wheel of Fortune.
     *
     * @return GameRecord of the game with the player's final score and ID.
     */
    @Override
    public GameRecord play() {
        generateSecretCode(); // Generate a new secret code (phrase) for the game
        while (numGuesses > 0 && !isWinningGuess(null)) {
            List<String> guess = getGuess();
            String feedback = getFeedback(guess);
            System.out.println(feedback);

            if (isWinningGuess(guess)) {
                System.out.println("Congratulations! You've guessed the full phrase: " + phrase);
                return new GameRecord(numGuesses, "Player");
            }
        }

        System.out.println("Game over! The phrase was: " + phrase);
        return new GameRecord(0, "Player"); // If player fails, return a score of 0
    }

    @Override
    public boolean isGameOver() {
        return isWinningGuess(null) || numGuesses <= 0;
    }

    /**
     * Asks the player if they want to play another game.
     *
     * @return true if the player wants to play again, otherwise false.
     */
    @Override
    public boolean playNext() {
        Scanner scanner = new Scanner(System.in);
        String response;
        do {
            System.out.print("Do you want to play another game? (yes/no): ");
            response = scanner.nextLine().trim();

            if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        } while (true);

        return response.equalsIgnoreCase("yes");
    }
}