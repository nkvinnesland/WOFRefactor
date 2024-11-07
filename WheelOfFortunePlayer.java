public interface WheelOfFortunePlayer {

    // Method to get the next guess from the player
    char nextGuess();

    // Method to get the player's ID
    String playerId();

    // Method to reset the player to start a new game
    void reset();

    // Method to get the player's current score
    int getScore();

    // Method to set the player's score
    void setScore(int score);

    // Method to increment the player's score by 1
    void incrementScore();
}