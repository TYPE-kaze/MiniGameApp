package Sudoku;

import Launcher.MainMenu;
import Sudoku.model.Cell;
import Sudoku.model.CellPosition;
import Sudoku.model.Difficulty;
import Sudoku.model.Generator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

import static Sudoku.SudokuGame.*;

/**
 * This is the Sudoku Game (CONTROLLER).
 */
public class SudokuGameApp extends JFrame {

    // Local Model-View Link Variables
    private final SudokuGame model;
    private final SudokuGamePanel view;
    private String rulesCaller; // -> Tells us where the back button on the rules pane should redirect to based on its caller
    private final KeyListener cellKeyListener;
    private final MouseListener cellMouseListener;

    /**
     * Constructs the Sudoku Game Frame
     */
    public SudokuGameApp(String name) {
        super(name);
        this.model = new Sudoku.SudokuGame();
        this.view = new SudokuGamePanel();

        getContentPane().add(this.view);
        setSize(1000, 650);
        setResizable(false);

        // Fill Difficulty Selector
        for (Difficulty diff : Difficulty.values()) {
            view.getHomePanel().getLevelSelectionModel().addElement(diff);
        }

        // Window Action Listeners
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Exit Sudoku", "Cancel"};
                int result = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to exit the application?\nActive games will not be saved.", "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });


        // Action Listeners on Home Panel
        this.view.getHomePanel().getNewGameBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get Level for the Game
                Difficulty level = Difficulty.valueOf(view.getHomePanel().getLevelSelector().getSelectedItem().toString().toUpperCase());

                // Generate New Game
                Generator puzzle = new Generator();
                puzzle.generateGrid(level);
                model.setPuzzle(puzzle.getGrid());

                // Configure View
                view.getGamePanel().setViewCellList(model.getPuzzle().getCellList());
                view.getGamePanel().getLevelTitle().setText(String.valueOf(level));
                update();

                // Switch to Game Panel
                view.getCardLayoutManager().show(view.getContent(), "game");

                // Set up Game Timer & Start
                long start = Calendar.getInstance().getTimeInMillis() / 1000;
                model.setTimer(new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long secondsSinceInit = ((Calendar.getInstance().getTimeInMillis() / 1000) - start);
                        view.getGamePanel().getTimeLabel().setText(String.format("%02d:%02d", secondsSinceInit / 60 % 60, secondsSinceInit % 60));
                    }
                }));
                model.getTimer().setInitialDelay(0);
                model.getTimer().start();
            }
        });
        this.view.getHomePanel().getViewRulesBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rulesCaller = "home"; // -> Rules was called from the 'home' panel, so return to it when done
                view.getCardLayoutManager().show(view.getContent(), "rules");
            }
        });
        this.view.getHomePanel().getHomeBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                MainMenu mainMenu = new MainMenu();
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
                dispose();
            }
        });

        // Actions Listeners on Game & Rules Panel
        this.view.getGamePanel().getHintBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // User wants a hint, check if game has unused hints
                if (model.getHintsUsed() < model.getPuzzle().getDifficulty().getMaxHints()) {
                    model.getPuzzle().hint(false);
                    model.setHintsUsed(model.getHintsUsed() + 1);
                    update();
                    System.err.println("HINT USED: " + model.getStringHintsUsed());
                    if (model.getHintsUsed() == model.getPuzzle().getDifficulty().getMaxHints()) {
                        view.getGamePanel().getHintBtn().setEnabled(false);
                        JOptionPane.showOptionDialog(getParent(), "Let's not make it too easy!\nThat was the last hint for this game.\n\nDid you Know?\nSudokus can likely prevent Alzheimer's disease\nand Dementia, so don't make it too easy.", "Out of Hints", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    }
                    checkGridCompletion();
                }
            }
        });
        this.view.getGamePanel().getViewRulesBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Rules Panel
                rulesCaller = "game"; // -> Rules was called from the 'game' panel, so return to it when done
                view.getCardLayoutManager().show(view.getContent(), "rules");
            }
        });
        this.view.getRulesPanel().getBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCardLayoutManager().show(view.getContent(), rulesCaller); // -> Return to caller panel
            }
        });
        this.view.getGamePanel().getEndGameBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"That's it", "Cancel"};
                int result = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to end the game?\n\nThis Sudoku is best played in one sitting,\nand can't be continued later.", "Exit?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    view.getCardLayoutManager().show(view.getContent(), "home");
                    destroyGameInstance();
                }
            }
        });

        // Cell Listener Adapters
        this.cellKeyListener = new KeyAdapter() {
            /**
             * Validates the user input for the cell
             *
             * @param evt the key event trigger
             */
            @Override
            public void keyTyped(KeyEvent evt) {
                Cell cell = (Cell) evt.getSource();
                // Disregard entry if not 1-9 or text already exists
                if (!String.valueOf(evt.getKeyChar()).matches("^[1-9]$") || cell.getText().length() == 1) {
                    System.out.println("Input: " + evt.getKeyChar() + " was rejected.");
                    evt.consume();
                } else {
                    // Check if input meets contraints
                    if (!model.getPuzzle().meetsConstraints(cell, Integer.valueOf(String.valueOf(evt.getKeyChar()).trim()))) {
                        System.err.println("VALUE " + evt.getKeyChar() + " AT " + cell.getPosition() + " DOES NOT MEET SUDOKU CONTRAINTS");
                        cell.setText("");
                        cell.setUserValue(0);
                        evt.consume();
                    } else {
                        cell.setUserValue(Integer.valueOf(String.valueOf(evt.getKeyChar()).trim()));
                    }
                    checkGridCompletion();
                }
            }

        };
        this.cellMouseListener = new MouseAdapter() {
            // Cell Hover Attribute
            private Color preActionColor;

            /**
             * Event Handler for mouse button press
             *
             * @param evt the event trigger
             */
            @Override
            public void mousePressed(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();

                // On Right-Click, clear cell
                if (evt.getButton() == MouseEvent.BUTTON3) {
                    cell.setText("");
                    cell.setUserValue(0);
                }

                cell.selectAll();
            }

            /**
             * Highlights game constraints for the hovered cell
             *
             * @param evt the cell being hovered
             */
            @Override
            public void mouseEntered(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();
                preActionColor = cell.getBackground();

                // Highlight Valid Cells
                for (Cell aCell : view.getGamePanel().getViewCellList()) {
                    if (cell.getPosition().getRow() == aCell.getPosition().getRow()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                    if (cell.getPosition().getColumn() == aCell.getPosition().getColumn()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                    if (cell.getPosition().getSubgrid() == aCell.getPosition().getSubgrid()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                }

                cell.setBackground(APP_GREEN);
            }

            /**
             * Restores hover colors from hover event
             */
            @Override
            public void mouseExited(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();

                // Restore Color
                for (Cell aCell : view.getGamePanel().getViewCellList()) {
                    if (aCell.isLocked()) {
                        aCell.setBackground(BKGD_DARK_GRAY);
                    } else {
                        aCell.setBackground(BKGD_LIGHT_GRAY);
                    }
                }

                cell.setBackground(preActionColor);
            }

        };
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        JFrame frame = new SudokuGameApp("Sudoku Game");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * View update event handler.
     */
    private void update() {
        // Set for each cell
        for (Cell cell : this.view.getGamePanel().getViewCellList()) {
            cell.setBackground(BKGD_DARK_GRAY);
            cell.setForeground(Color.WHITE);
            cell.setFont(new Font("Halvetica Neue", Font.PLAIN, 36));
            cell.setBorder(new LineBorder(Color.BLACK, 0));
            cell.setHorizontalAlignment(JTextField.CENTER);
            cell.setCaretColor(new Color(32, 44, 53));
            cell.setDragEnabled(false);
            cell.setTransferHandler(null);

            // Add subgrid separators
            CellPosition pos = cell.getPosition();
            if (pos.getColumn() == 2 || pos.getColumn() == 5) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(146, 208, 80)));
            } else if (pos.getRow() == 2 || pos.getRow() == 5) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(146, 208, 80)));
            }
            if ((pos.getColumn() == 2 && pos.getRow() == 2) || (pos.getColumn() == 5 && pos.getRow() == 5)
                    || (pos.getColumn() == 2 && pos.getRow() == 5) || (pos.getColumn() == 5 && pos.getRow() == 2)) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(146, 208, 80)));
            }

            // Validate User's Cell Input + Mouse Listeners
            cell.removeKeyListener(cellKeyListener);
            cell.removeMouseListener(cellMouseListener);
            if (cell.isLocked()) {
                cell.setEditable(false);
                cell.setHighlighter(null);
            } else {
                cell.setBackground(BKGD_LIGHT_GRAY);
                cell.addMouseListener(cellMouseListener);
                cell.addKeyListener(cellKeyListener);
            }
            if (cell.isEmpty()) {
                cell.setText("");
            } else {
                cell.setText(String.valueOf(cell.getUserValue()));
            }

            // Adds cell to the view's grid
            this.view.getGamePanel().getGrid().add(cell);
        }

    }


    /**
     * Checks the player's current grid's completion
     */
    private void checkGridCompletion() {
        if (this.model.getPuzzle().isFilled()) {
            if (this.model.getPuzzle().isSolved()) {
                puzzleCompleted();
            }
        }
    }

    /**
     * Events which fire at completion of Sudoku grid
     */
    private void puzzleCompleted() {
        // Stop timer
        this.model.getTimer().stop();
        String gameTime = view.getGamePanel().getTimeLabel().getText();

        // Lock all cells to prevent editing
        for (Cell cell : this.model.getPuzzle().getCellList()) {
            cell.setLocked(true);
        }

        update();

        // Award Points
        Object[] options = {"Great!"};
        JOptionPane.showOptionDialog(this, "You have solved the Puzzle.\n\nGame Time: " + gameTime + "\nHints Used: " + this.model.getStringHintsUsed(), "Congratulations!", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        // Return Home
        view.getCardLayoutManager().show(view.getContent(), "home");

        // Destroy Game
        destroyGameInstance();
    }

    /**
     * Clears game settings after game
     */
    private void destroyGameInstance() {
        // Destroy Game
        this.model.setPuzzle(null);
        this.model.setHintsUsed(0);
        this.model.getTimer().stop();
        this.model.setTimer(null);
        view.getGamePanel().getHintBtn().setEnabled(true);
        for (Cell cell : this.view.getGamePanel().getViewCellList()) {
            this.view.getGamePanel().getGrid().remove(cell);
        }
    }
}
