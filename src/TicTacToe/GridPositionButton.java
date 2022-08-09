package TicTacToe;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * A JButton subclass that is used in a tic tac toe grid.
 */
class GridPositionButton extends JButton {
    private static final int BUTTON_TEXT_SIZE = 70;
    private final int gridPosition;

    /**
     * Instantiates a new grid position button object setting text to grid
     * position number, and setting the buttons font.
     *
     * @param gridPosition the grid position that the button represents.
     */
    GridPositionButton(Integer gridPosition) {
        this.gridPosition = gridPosition;
        setFont(new Font("Ariel", Font.PLAIN, BUTTON_TEXT_SIZE));
            setBackground(new Color(42, 54, 63));
            setBorder(new LineBorder(Color.BLACK, 0));
            setHorizontalAlignment(JTextField.CENTER);
    }

    public int getGridPosition() {
        return gridPosition;
    }
}
