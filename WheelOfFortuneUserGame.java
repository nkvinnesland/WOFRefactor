import java.util.*;

/**
 * WheelOfFortuneUserGame represents a user-specific version of the Wheel of Fortune game.
 * It extends the core functionality provided by WheelOfFortune and includes additional
 * features for managing user interactions and tracking game records.
 */
public class WheelOfFortuneUserGame extends WheelOfFortune {

    private WheelOfFortunePlayer player;
    private AllGamesRecord allGamesRecord; // Record to store all games played

    /**
     * Constructor for initializing a user game with a list of phrases and a specific player.
     *
     * @param phrases List of phrases used in the game.
     * @param player  The player participating in the game.
     */
    public WheelOfFortuneUserGame(List<String> phrases, WheelOfFortunePlayer player) {
        super(phrases, 10); // Initialize with phrases and default attempts
        this.player = player;
        this.allGamesRecord = new AllGamesRecord();
    }

    /**
     * Generates the secret code (random phrase) for the game.
     */
    @Override
    public void generateSecretCode() {
        Random random = new Random();
        this.phrase = phrases.get(random.nextInt(phrases.size())); // Select a random phrase
        this.hiddenPhrase = generateHiddenPhrase(this.phrase); // Generate hidden version of the phrase
    }

    /**
     * Plays a single game of Wheel of Fortune.
     *
     * @return The final game record with the player's score and ID.
     */
    @Override
    public GameRecord play() {
        resetGame(); // Reset the game state for a new round
        generateSecretCode(); // Set up a new phrase (secret code) for the game

        while (!isGameOver()) {
            List<String> guess = getGuess();
            processGuess(guess.get(0), player);
        }

        int score = player.getScore();
        System.out.println("Game over! Your score: " + score);

        GameRecord gameRecord = new GameRecord(score, player.playerId());
        player.setScore(0); // Reset player score for next game
        return gameRecord;
    }
    private void resetGame() {
        this.numGuesses = 10; // Reset number of guesses for the new game
        this.usedLetters.setLength(0); // Clear used letters
        this.hiddenPhrase = generateHiddenPhrase(this.phrase); // Reset the hidden phrase display
    }
    /**
     * Plays multiple games, asking the user if they want to continue after each game,
     * and stores all game records in AllGamesRecord.
     */
    public void playAll() {
        do {
            GameRecord record = play(); // Play a single game
            allGamesRecord.add(record); // Add the game record to AllGamesRecord
        } while (playNext()); // Ask if the player wants to play another game

        // Print all game records when the user is done
        printAllGameResults();
    }

    /**
     * Checks if the player has guessed the entire phrase or run out of attempts.
     *
     * @return true if the game is over, otherwise false.
     */
    @Override
    public boolean isGameOver() {
        return isWinningGuess(null) || numGuesses <= 0;
    }

    /**
     * Processes the player's guess, updating the hidden phrase and game state.
     *
     * @param guess  The guessed letter.
     * @param player The player making the guess.
     */
    private void processGuess(String guess, WheelOfFortunePlayer player) {
        char guessedLetter = guess.charAt(0);
        boolean found = false;

        for (int i = 0; i < phrase.length(); i++) {
            if (Character.toLowerCase(phrase.charAt(i)) == Character.toLowerCase(guessedLetter)) {
                hiddenPhrase.setCharAt(i, phrase.charAt(i)); // Reveal the letter
                found = true;
            }
        }

        if (found) {
            player.incrementScore();
            System.out.println("Correct guess! Current phrase: " + hiddenPhrase);
        } else {
            numGuesses--;
            System.out.println("Incorrect guess. Guesses remaining: " + numGuesses);
        }
    }

    /**
     * Prompts the user to enter their guess.
     *
     * @return The player's guess as a List containing a single character string.
     */
    @Override
    protected List<String> getGuess() {
        Scanner scanner = new Scanner(System.in);
        String guess;
        do {
            System.out.print("Enter your guess (a single letter): ");
            guess = scanner.nextLine().trim().toUpperCase();
            if (guess.length() == 1 && Character.isLetter(guess.charAt(0)) && usedLetters.indexOf(guess) == -1) {
                usedLetters.append(guess);
                break;
            } else {
                System.out.println("Invalid input. Please enter a single unused letter.");
            }
        } while (true);

        List<String> guessList = new ArrayList<>();
        guessList.add(guess);
        return guessList;
    }

    /**
     * Checks if the player has guessed the entire phrase correctly.
     *
     * @param guess The player's guess (not used in this method).
     * @return true if there are no unrevealed letters, otherwise false.
     */
    @Override
    public boolean isWinningGuess(List<String> guess) {
        return hiddenPhrase.indexOf("*") == -1;
    }

    /**
     * Asks if the player wants to play another game.
     *
     * @return true if the player wants to continue, otherwise false.
     */

    /**
     * Prints all game records at the end of all games played.
     */
    private void printAllGameResults() {
        System.out.println("\nAll Games Results:");
        System.out.println("Average Score: " + allGamesRecord.average());
        System.out.println("Top Scores:");
        allGamesRecord.highGameList(5).forEach(System.out::println);
    }

    /**
     * Main method to initialize and start the Wheel of Fortune game for a single user.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        List<String> phrases = new ArrayList<>();
        phrases.add("OpenAI is amazing");
        phrases.add("Java programming");
        phrases.add("Artificial intelligence");

        WheelOfFortunePlayer player = new UserPlayer("User1");
        WheelOfFortuneUserGame game = new WheelOfFortuneUserGame(phrases, player);
        game.playAll(); // Start playing multiple games
    }

    @Override
    public String toString() {
        return "WheelOfFortuneUserGame{" +
                "player=" + player +
                ", allGamesRecord=" + allGamesRecord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WheelOfFortuneUserGame that = (WheelOfFortuneUserGame) o;
        return Objects.equals(player, that.player) && Objects.equals(allGamesRecord, that.allGamesRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, allGamesRecord);
    }
}