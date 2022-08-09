import Sudoku.AppJButton;
import Sudoku.SudokuGameApp;
import TicTacToe.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Sudoku.SudokuGame.*;
import static Sudoku.SudokuGame.BKGD_DARK_GRAY;

/**
 * Main Menu where player chooses games to play
 */

public class MainMenu extends JFrame {
    public static final Color BKGD_DARK_GRAY = new Color(42, 54, 63);
    public static final Color BKGD_LIGHT_GRAY = new Color(62, 74, 83);
    public static final Color APP_GREEN = new Color(146, 208, 80);

    // MainMenu attributes
    private JButton sudokuGameButton;
    private JButton ticTacToeGameButton;
    private JLabel nameLabel;
    /**
     * Constructs a Main Menu view
     */
    public MainMenu() {
        this.setLayout(new BorderLayout());
        setSize(1000, 650);
        setResizable(false);
        setTitle("Main Menu");

        // Window Action Listeners
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Exit", "Cancel"};
                int result = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to exit the application?", "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Banner
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.LINE_AXIS));
        banner.setBorder(BorderFactory.createEmptyBorder(0, 100, 0 ,0));
        banner.setPreferredSize(new Dimension(1000, 115));
        banner.setBackground(BKGD_DARK_GRAY);
        banner.setAlignmentX(CENTER_ALIGNMENT);

        // Dynamic Banner Content
        JPanel jP2 = new JPanel();
        jP2.setBackground(BKGD_DARK_GRAY);
        jP2.setPreferredSize(new Dimension(200, 100));
        jP2.setLayout(new GridLayout(2,0));
        JLabel welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("Avenir", Font.PLAIN, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setVerticalAlignment(JLabel.BOTTOM);
        jP2.add(welcomeLabel);

        nameLabel = new JLabel("Player");
        nameLabel.setFont(new Font("Avenir", Font.PLAIN, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setVerticalAlignment(JLabel.TOP);
        jP2.add(nameLabel);
        banner.add(jP2);

        this.add(banner, BorderLayout.NORTH);

        // Main Content
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(0,2));

        // Left Options
        JPanel left = new JPanel();
        left.setBackground(BKGD_DARK_GRAY);
        left.setLayout(null);

        JPanel game = new JPanel();
        game.setLayout(new GridLayout());
        game.setLocation(100, 50);
        game.setSize(300, 120);
        game.setBackground(BKGD_LIGHT_GRAY);
        // New Game Button
        sudokuGameButton = new AppJButton("Sudoku", 20, BKGD_LIGHT_GRAY, APP_GREEN);
        game.add(sudokuGameButton);
        left.add(game);

        game = new JPanel();
        game.setLayout(new GridLayout());
        game.setLocation(100, 200);
        game.setSize(300, 120);
        ticTacToeGameButton = new AppJButton("Tic Tac Toe", 20, BKGD_LIGHT_GRAY, APP_GREEN);
        game.add(ticTacToeGameButton);
        left.add(game);

        main.add(left);

        // Right Options
        JPanel right = new JPanel();
        right.setBackground(BKGD_DARK_GRAY);
        right.setLayout(null);

        main.add(right);

        this.add(main);

        /**
         * Sudoku game button action listener
         */
        sudokuGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SudokuGameApp sudokuGameApp = new SudokuGameApp("Sudoku Game");
                sudokuGameApp.setLocationRelativeTo(null);
                sudokuGameApp.setVisible(true);
                dispose();
            }
        });

        /**
         * TicTacToe game button action listener
         */
        ticTacToeGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TicTacToeMainMenu ticTacToeMainMenu = new TicTacToeMainMenu();
                ticTacToeMainMenu.setLocationRelativeTo(null);
                ticTacToeMainMenu.setVisible(true);
                dispose();
            }
        });
    }

}
