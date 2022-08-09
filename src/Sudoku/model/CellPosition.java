package Sudoku.model;

/**
 * Information about a cell's position in the grid
 */
public class CellPosition {

    // Cell Position Attributes
    private final int row;
    private final int column;
    private final int subgrid;

    /**
     * Constructs a CellPosition object at row and column
     *
     * @param row the row of the cell position
     * @param column the column of the cell position
     */
    public CellPosition(int row, int column) {
        this.row = row;
        this.column = column;

        // Evaluate which sub-grid this cell associates itself
        int evaluate = this.row < 3 ? 0 : this.row < 6 ? 2 : 4;
        this.subgrid = (this.row / 3) + (this.column / 3) + evaluate;
    }

    /**
     * @return the row position
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return the column position
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * @return the sub-grid position (0-based)
     */
    public int getSubgrid() {
        return this.subgrid;
    }

}
