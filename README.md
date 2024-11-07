# WOFRefactor
Project 4 for OOP

Features

	•	Human and AI Players: Players can choose to play as a human or let different AI players attempt to guess the phrase.
	•	Multiple AI Strategies:
	•	Smart AI: Guesses letters based on common letter combinations.
	•	Mediocre AI: Guesses commonly used English letters.
	•	Dumb AI: Guesses letters randomly.
	•	Game Records: Track game records and scores for each player.
	•	Statistics and Analysis: View high scores, calculate averages, and more.
	•	Replayability: Players can choose to play multiple games in succession, with scores tracked across sessions.

Project Structure

The project is organized as follows:

	•	Game: Base class for all games.
	•	GuessingGame: Abstract class representing a general guessing game, extended by specific game types.
	•	WheelOfFortune: Main game class with logic specific to the Wheel of Fortune-style gameplay.
	•	Players:
	•	UserPlayer: Human player who inputs guesses through the console.
	•	AIPlayerDumb: Simple AI player with random guessing.
	•	AIPlayerMediocre: AI player with basic guessing based on common letters.
	•	AIPlayerSmart: AI player with advanced guessing based on letter patterns and common combinations.
	•	Game Records:
	•	GameRecord: Represents the results of a single game.
	•	AllGamesRecord: Holds all game records, allowing for analysis like high scores and averages.

Installation

git clone https://github.com/nkvinnesland/wheel-of-fortune-game.git
cd wheel-of-fortune-game
javac *.java
java WheelOfFortuneAIGame

Sample Usage

	1.	Starting the Game: Run the main class WheelOfFortuneAIGame, and let the AI players take turns.
	2.	Gameplay:
	•	For human players, enter guesses through the console.
	•	For AI players, watch as they make guesses based on their defined strategies.
	3.	Replay: After each game, you can choose to play another game or exit.

Classes and Methods

Core Classes

	•	GuessingGame: Abstract class providing core functionality for guessing games, including tracking attempts, generating feedback, and managing the gameplay loop.
	•	WheelOfFortuneAIGame: Main class implementing Wheel of Fortune-specific gameplay, managing AI players, and tracking game results.

Player Classes

	•	UserPlayer: Allows a human to participate by entering guesses.
	•	AIPlayerDumb, AIPlayerMediocre, AIPlayerSmart: Different AI players with varying levels of intelligence and guessing strategies.

Game Records

	•	GameRecord: Represents an individual game’s outcome with player ID and score.
	•	AllGamesRecord: Stores and processes multiple GameRecord instances for analysis.

Key Methods

	•	playAll: Runs multiple rounds of games, with the option to replay.
	•	playNext: Prompts the user to play again or stop.
	•	generateSecretCode: Generates a hidden phrase for guessing.
	•	getFeedback: Provides feedback for each guess, guiding players toward the correct answer.

Sample Gameplay

	1.	Run the game and select either human or AI players.
	2.	If playing as a human, guess letters to reveal the hidden phrase.
	3.	After each game, choose whether to play again.
	4.	View scores, averages, and other game statistics when finished.

 Example Code Usage

To initialize and start a game with different AI players, create WheelOfFortunePlayer instances and pass them to WheelOfFortuneAIGame:
public static void main(String[] args) {
    List<String> phrases = Arrays.asList("OpenAI is amazing", "Java programming", "Artificial intelligence");

    WheelOfFortunePlayer ai1 = new AIPlayerSmart("AI Smart");
    WheelOfFortunePlayer ai2 = new AIPlayerMediocre("AI Mediocre");
    WheelOfFortunePlayer ai3 = new AIPlayerDumb("AI Dumb");

    List<WheelOfFortunePlayer> aiPlayers = Arrays.asList(ai1, ai2, ai3);

    WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(aiPlayers, phrases);
    AllGamesRecord allGamesRecord = new AllGamesRecord();
    game.playAll(allGamesRecord);
}

