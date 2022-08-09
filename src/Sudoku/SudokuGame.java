package Sudoku;

import Sudoku.model.Grid;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Sudoku Game (MODEL) Manager.
 */

public class SudokuGame {

    // Sudoku App Colors
    public static final Color BKGD_DARK_GRAY = new Color(42, 54, 63);
    public static final Color BKGD_LIGHT_GRAY = new Color(62, 74, 83);
    public static final Color APP_GREEN = new Color(146, 208, 80);

    // Sudoku Model Attributes
    private Grid puzzle;
    private int hintsUsed;
    private Timer timer;

    /**
     * Default Sudoku Model Constructor.
     */
    public SudokuGame() {
    }

    /**
     * @return the puzzle
     */
    public Grid getPuzzle() {
        return puzzle;
    }

    /**
     * @param puzzle the puzzle to set
     */
    public void setPuzzle(Grid puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * @return the number of hints used
     */
    public int getHintsUsed() {
        return hintsUsed;
    }

    /**
     * @param hintsUsed the number of hints used
     */
    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    /**
     * @return a string-formatted version of hints used
     */
    public String getStringHintsUsed() {
        return this.getHintsUsed() + "/" + this.getPuzzle().getDifficulty().getMaxHints();
    }

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
