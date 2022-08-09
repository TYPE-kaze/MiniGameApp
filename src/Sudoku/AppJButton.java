package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Custom Button for Sudoku Panels
 */
public class AppJButton extends JButton {

    /**
     * Custom Button for Sudoku Panels
     *
     * @param buttonText the text of the button
     * @param fontSize the font size of the text
     * @param background the background color
     * @param foreground the foreground color
     */
    public AppJButton(String buttonText, int fontSize, Color background, Color foreground) {

        // Button Attributes
        this.setText(buttonText);
        this.setFont(new Font("Halvetica Neue", Font.PLAIN, fontSize));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(true);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect Background Color Change
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(72, 84, 93));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(background);
            }
        });
    }
}
