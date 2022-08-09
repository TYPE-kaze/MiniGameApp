package Sudoku.panels;

import Sudoku.AppJButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static Sudoku.SudokuGame.*;

/**
 * Home Panel View of Sudoku Game
 */

public class HomePanel extends JPanel {
    
    // Home Panel Attributes
    private final JButton newGameBtn;
    private final JButton viewRulesBtn;
    private DefaultTableModel tableModel;
    private DefaultComboBoxModel levelSelectionModel = new DefaultComboBoxModel();
    private final JComboBox levelSelector;
    private JLabel nameLabel;
    
    /**
     * Constructs a Home Panel View.
     */
    public HomePanel() {
        this.setLayout(new BorderLayout());

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
        game.setLayout(new GridLayout(2,0));
        game.setLocation(100, 50);
        game.setSize(300, 120);
        game.setBackground(BKGD_LIGHT_GRAY);
        // New Game Button
        newGameBtn = new AppJButton("START A NEW GAME", 20, BKGD_LIGHT_GRAY, APP_GREEN);
        game.add(newGameBtn);
        // Difficulty Level
        levelSelector = new JComboBox();
        levelSelector.setModel(levelSelectionModel);
        levelSelector.setFont(new Font("Avenir", Font.PLAIN, 20));
        game.add(levelSelector);
        left.add(game);

        game = new JPanel();
        game.setLayout(new GridLayout());
        game.setLocation(100, 200);
        game.setSize(300, 120);
        viewRulesBtn = new AppJButton("SHOW ME THE RULES", 20, BKGD_LIGHT_GRAY, APP_GREEN);
        game.add(viewRulesBtn);
        left.add(game);

        main.add(left);


        // Right Options
        JPanel right = new JPanel();
        right.setBackground(BKGD_DARK_GRAY);
        right.setLayout(null);

        main.add(right);

        this.add(main);
    }


    public JButton getNewGameBtn() {
        return newGameBtn;
    }

    public JComboBox getLevelSelector() {
        return levelSelector;
    }

    public JButton getViewRulesBtn() {
        return viewRulesBtn;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public DefaultComboBoxModel getLevelSelectionModel() {
        return levelSelectionModel;
    }
}