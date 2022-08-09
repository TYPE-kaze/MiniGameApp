package Sudoku.model;

import javax.swing.*;

/**
 * Information about an individual cell in the grid
 */
public class Cell extends JTextField {

    // Cell Attributes
    protected int userValue;
    protected int solutionValue;
    protected int provisionalValue;
    private boolean locked;
    private final CellPosition position;
    private static final String COLOUR_RED = "\u001B[31m";
    private static final String COLOUR_RESET = "\u001B[0m";

    /**
     * Constructs a Cell object at row and column
     *
     * @param row the row of the cell position
     * @param column the column of the cell position
     */
    public Cell(int row, int column) {
        this.position = new CellPosition(row, column);
    }

    /**
     * @return the position of this cell
     */
    public CellPosition getPosition() {
        return this.position;
    }

    /**
     * @return true if cell is locked, else false
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param locked locks / unlocks the cell to prevent editing
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @return true if cell is empty (ie. 0), false if cell isn't empty (ie. not
     * 0)
     */
    public boolean isEmpty() {
        return getUserValue() == 0;
    }

    /**
     * @return the user chosen value for this cell
     */
    public int getUserValue() {
        return this.userValue;
    }

    /**
     * @param userValue the user's chosen value for this cell
     */
    public void setUserValue(int userValue) {
        this.userValue = userValue;
    }

    /**
     * @return the cell's true (solution) value
     */
    public int getSolutionValue() {
        return this.solutionValue;
    }

    /**
     * Set the solution value of the cell to userValue
     */
    public void setSolutionValue() {
        this.solutionValue = userValue;
    }

    /**
     * Set provisional value of the cell to user's entry
     */
    public void storeProvisionalValue() {
        this.provisionalValue = this.userValue;
    }

    /**
     * Gets the provisional value of the cell
     */
    public void fetchProvisionalValue() {
        this.userValue = this.provisionalValue;
    }

}
