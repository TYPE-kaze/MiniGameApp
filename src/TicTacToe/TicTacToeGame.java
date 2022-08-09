package TicTacToe;

import javax.swing.*;

public class TicTacToeGame {

    // Game Configuration:
    protected static final int NUM_OF_PLAYERS = 2;
    protected final Player[] players = new Player[NUM_OF_PLAYERS];

    // The game grid:
    protected final TicTacToeGrid grid = new TicTacToeGrid();
    private final int gameMode;

    public TicTacToeGame(int gameMode) {
        this.gameMode = gameMode;
        playTicTacToe();
    }

    protected void playTicTacToe() {
        if (gameMode == 1) {
            String player1Name = JOptionPane.showInputDialog("Enter player 1's name:");
            String player2Name = JOptionPane.showInputDialog("Enter player 2's name:");

            players[0] = new HumanPlayer(player1Name);
            players[1] = new HumanPlayer(player2Name);
        } else if (gameMode == 2) {
            String player1Name = JOptionPane.showInputDialog("Enter player 1's name:");
            AIDifficulty player2difficulty = inputAiDifficulty(2);

            players[0] = new HumanPlayer(player1Name);
            players[1] = new AITicTacToePlayer("AI Player 2", player2difficulty, grid);
        } else if (gameMode == 3) {
            AIDifficulty player1difficulty = inputAiDifficulty(1);
            String player2Name = JOptionPane.showInputDialog("Enter Player 2's name:");

            players[0] = new AITicTacToePlayer("AI Player 1", player1difficulty, grid);
            players[1] = new HumanPlayer(player2Name);
        } else if (gameMode == 4) {
            AIDifficulty player1difficulty = inputAiDifficulty(1);
            AIDifficulty player2difficulty = inputAiDifficulty(2);

            players[0] = new AITicTacToePlayer("AI Player 1", player1difficulty, grid);
            players[1] = new AITicTacToePlayer("AI Player 2", player2difficulty, grid);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid game mode.");
        }
    }

    /**
     * Gets the grid the game is being played on.
     *
     * @return the grid the game is being played on.
     */
    public TicTacToeGrid getGrid() {
        return grid;
    }

    private AIDifficulty inputAiDifficulty(int aiPlayerNumber) {
        while (true) {
            String[] difficultyArray = { "Easy", "Medium", "Hard" };
            String input = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a difficulty of AI PLayer " + aiPlayerNumber + ":",
                    "AI Difficulty",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    difficultyArray,
                    difficultyArray[0]
            );
            try {
                return validateAiDifficultyInput(input);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public GameStatus handleInput(int gridPosition) throws IllegalArgumentException {
        grid.markGrid(gridPosition);
        grid.incrementPlayer();
        return checkWinTie();
    }

    private GameStatus checkWinTie() {
        if (grid.getNumberOfMarks() >= 5) {
            if (grid.isWin()) { //Win
                return GameStatus.WIN;
            } else if (getGrid().isMaxMovesMade()) { //Tie
                return GameStatus.TIE;
            }
        }
        return GameStatus.GAME_TO_CONTINUE;
    }

    public int getGameMode() {
        return gameMode;
    }


    /**
     * Validates AI difficulty input.
     *
     * @param input the string Ai difficulty input from the player.
     * @return the AI difficulty that corresponds to the difficulty entered by
     * the player.
     * @throws IllegalArgumentException when an invalid difficulty is entered.
     */
    protected AIDifficulty validateAiDifficultyInput(String input) throws IllegalArgumentException {
        if (input.equalsIgnoreCase("easy")) {
            return AIDifficulty.EASY;
        } else if (input.equalsIgnoreCase("medium")) {
            return AIDifficulty.MEDIUM;
        } else if (input.equalsIgnoreCase("hard")) {
            return AIDifficulty.HARD;
        } else {
            throw new IllegalArgumentException("Invalid AI difficulty entered (" + input + ")!");
        }
    }
}
