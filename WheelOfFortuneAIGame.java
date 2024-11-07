import java.util.*;

/**
 * The WheelOfFortuneAIGame class represents an AI-driven version of the Wheel of Fortune game.
 * In this game, multiple AI players take turns attempting to guess letters in hidden phrases,
 * aiming to reveal the full phrase or achieve the highest score possible. This class extends
 * WheelOfFortune and includes functionality specific to AI gameplay.
 */
public class WheelOfFortuneAIGame extends WheelOfFortune {

    private List<WheelOfFortunePlayer> aiPlayers; // List of AI players
    private List<String> remainingPhrases;        // Copy of phrases to track phrases for each game
    private WheelOfFortunePlayer currentPlayer;   // Field to store the current AI player
    private Set<Character> guessedLetters;        // Track guessed letters

    /**
     * Constructs a WheelOfFortuneAIGame with a specified list of AI players and game phrases.
     *
     * @param players List of AI players.
     * @param phrases List of phrases used in the game.
     */
    public WheelOfFortuneAIGame(List<WheelOfFortunePlayer> players, List<String> phrases) {
        super(new ArrayList<>(phrases), 10);
        this.aiPlayers = players;
        this.remainingPhrases = new ArrayList<>(phrases);
        this.guessedLetters = new HashSet<>(); // Initialize set for guessed letters
    }

    /**
     * Plays all games, with each AI player playing each phrase once and recording the results.
     *
     * @param record The AllGamesRecord to store all game records.
     * @return The updated AllGamesRecord after all games have been played.
     */
    @Override
    public AllGamesRecord playAll(AllGamesRecord record) {
        for (String phrase : phrases) {
            for (WheelOfFortunePlayer aiPlayer : aiPlayers) {
                this.currentPlayer = aiPlayer;               // Set the current AI player
                this.phrase = phrase;                         // Set the current phrase
                this.hiddenPhrase = generateHiddenPhrase(phrase); // Set the hidden version of the phrase
                GameRecord gameRecord = play();               // Play a game with the current AI player and current phrase
                record.add(gameRecord);                       // Add the game record to AllGamesRecord
                resetGameState();                             // Reset game state for the next game
            }
        }
        return record; // Return the record of all games played
    }

    /**
     * Plays a single game of Wheel of Fortune for the current AI player, allowing them to
     * make guesses until the game ends, then records the AI player's score.
     *
     * @return The final game record with the AI player's score and ID.
     */
    public GameRecord play() {
        System.out.println("Starting a new game for AI player: " + currentPlayer.playerId() + " with phrase: " + phrase);
        guessedLetters.clear(); // Clear guessed letters for each new game

        while (!isGameOver()) {
            List<String> guess = getGuess(); // Get an informed guess
            processGuess(guess.get(0), currentPlayer);
        }

        int score = currentPlayer.getScore();
        System.out.println("Game over for AI player: " + currentPlayer.playerId() + ". Score: " + score);

        GameRecord gameRecord = new GameRecord(score, currentPlayer.playerId());
        currentPlayer.setScore(0); // Reset AI player's score for the next game
        return gameRecord;
    }

    /**
     * Processes the AI player's guess, updating the hidden phrase and game state based on
     * whether the guess is correct or incorrect.
     *
     * @param guess The guessed letter.
     * @param currentPlayer The AI player making the guess.
     */
    private void processGuess(String guess, WheelOfFortunePlayer currentPlayer) {
        char guessedLetter = guess.charAt(0);
        boolean found = false;

        for (int i = 0; i < phrase.length(); i++) {
            if (Character.toLowerCase(phrase.charAt(i)) == Character.toLowerCase(guessedLetter)) {
                hiddenPhrase.setCharAt(i, phrase.charAt(i)); // Reveal the letter
                found = true;
            }
        }

        if (found) {
            currentPlayer.incrementScore();
            System.out.println("AI guess '" + guessedLetter + "' is correct! Current phrase: " + hiddenPhrase);
        } else {
            numGuesses--;
            System.out.println("AI guess '" + guessedLetter + "' is incorrect. Guesses remaining: " + numGuesses);
        }
    }

    /**
     * Resets the game state to prepare for the next round without reloading phrases, clearing
     * the guessed letters and resetting scores and state for each AI player.
     */
    private void resetGameState() {
        this.numGuesses = 10;                 // Reset the number of guesses for the new game
        this.usedLetters.setLength(0);         // Clear used letters in WheelOfFortuneAIGame
        this.hiddenPhrase = generateHiddenPhrase(this.phrase); // Reset hidden phrase based on the new phrase

        // Call reset on each AI player to clear their state
        for (WheelOfFortunePlayer player : aiPlayers) {
            player.reset();   // Use the existing reset method to clear used letters and reset score
        }
    }

    /**
     * Provides a string representation of the WheelOfFortuneAIGame instance, including
     * information about AI players, remaining phrases, and the guessed letters.
     *
     * @return String representation of the game state.
     */
    @Override
    public String toString() {
        return "WheelOfFortuneAIGame{" +
                "aiPlayers=" + aiPlayers +
                ", remainingPhrases=" + remainingPhrases +
                ", currentPlayer=" + currentPlayer +
                ", guessedLetters=" + guessedLetters +
                '}';
    }

    /**
     * Compares this WheelOfFortuneAIGame instance to another object for equality based on
     * the AI players, remaining phrases, and guessed letters.
     *
     * @param o The object to compare with.
     * @return true if this instance is equal to the specified object, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WheelOfFortuneAIGame that = (WheelOfFortuneAIGame) o;
        return Objects.equals(aiPlayers, that.aiPlayers) && Objects.equals(remainingPhrases, that.remainingPhrases) && Objects.equals(currentPlayer, that.currentPlayer) && Objects.equals(guessedLetters, that.guessedLetters);
    }

    /**
     * Returns a hash code for this WheelOfFortuneAIGame instance.
     *
     * @return The hash code for this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aiPlayers, remainingPhrases, currentPlayer, guessedLetters);
    }

    /**
     * Gets the current AI player's guess by calling their nextGuess method, converting
     * the result to a List<String> format to match the method signature.
     *
     * @return The guess as a List containing a single letter string.
     */
    @Override
    protected List<String> getGuess() {
        if (currentPlayer == null) {
            throw new IllegalStateException("Current player is not set.");
        }

        // Use the AI player's nextGuess method to get the next guess
        char guessChar = currentPlayer.nextGuess();

        // Convert the char to a List<String> format required by the method
        List<String> guessList = new ArrayList<>();
        guessList.add(String.valueOf(guessChar));

        return guessList;
    }

    /**
     * Main method to initialize the Wheel of Fortune game for AI players and play multiple rounds.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        List<String> phrases = new ArrayList<>();
        phrases.add("OpenAI is amazing");
        phrases.add("Java programming");
        phrases.add("Artificial intelligence");

        WheelOfFortunePlayer ai1 = new AIPlayer("AI Smart");
        WheelOfFortunePlayer ai2 = new AIPlayerMediocre("AI Mediocre");
        WheelOfFortunePlayer ai3 = new AIPlayerDumb("AI Dumb");

        List<WheelOfFortunePlayer> aiPlayers = new ArrayList<>();
        aiPlayers.add(ai1);
        aiPlayers.add(ai2);
        aiPlayers.add(ai3);

        WheelOfFortuneAIGame wofAIGame = new WheelOfFortuneAIGame(aiPlayers, phrases);
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        wofAIGame.playAll(allGamesRecord); // Start the games for all AI players and store records

        // Print all game results
        System.out.println("\nAll Games Results:");
        System.out.println("Average Score: " + allGamesRecord.average());
        System.out.println("Top Scores:");
        allGamesRecord.highGameList(5).forEach(System.out::println);
    }
}