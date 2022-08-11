package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Launcher.MainMenu;

public class TicTacToeMainMenu extends JFrame {
    private final static int WIDTH = 1000;
    private final static int HEIGHT = 650;
    private int gameMode = 1;

    public TicTacToeMainMenu() {
        createComponents();
    }

    private void createComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());
        // Banner
        createBannerPanel();
        // Main Content
        createMainContent();

        setSize(WIDTH, HEIGHT);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Window Action Listeners
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Exit Tic Tac Toe", "Cancel"};
                int result = JOptionPane.showOptionDialog(getParent(), "Are you sure you want to exit the application?\nActive games will not be saved.", "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        setLocationRelativeTo(null);
        setTitle("Tic Tac Toe Game");

    }

    private void createMainContent() {
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(0,2));

        // Left Options
        JPanel left = new JPanel();
        left.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);
        left.setLayout(null);

        JPanel startGamePanel = new JPanel();
        startGamePanel.setLayout(new GridLayout(2,0));
        startGamePanel.setLocation(100, 50);
        startGamePanel.setSize(300, 120);
        startGamePanel.setBackground(TicTacToeGameGUI.BKGD_LIGHT_GRAY);
        // New Game Button
        JButton newGameBtn = new AppJButton("START A NEW GAME", 20, TicTacToeGameGUI.BKGD_LIGHT_GRAY, TicTacToeGameGUI.APP_GREEN);
        ActionListener startGameButtonListener = e -> {
            TicTacToeGameGUI newGameWindow = new TicTacToeGameGUI(gameMode, this);
            newGameWindow.setVisible(true);
        };
        newGameBtn.addActionListener(startGameButtonListener);
        startGamePanel.add(newGameBtn);

        // Difficulty Level
        String[] gameMode ={"Human vs Human","Human vs Ai","Ai vs Human","Ai vs Ai"};
        JComboBox<String> levelSelector = new JComboBox<>(gameMode);
        levelSelector.setFont(new Font("Avenir", Font.PLAIN, 20));
        ItemListener modeSelectListener = e -> setGameMode(levelSelector.getSelectedIndex() + 1);
        levelSelector.addItemListener(modeSelectListener);
        startGamePanel.add(levelSelector);
        left.add(startGamePanel);

        // View Rule Button
        JPanel showRulesPanel = new JPanel();
        showRulesPanel.setLayout(new GridLayout());
        showRulesPanel.setLocation(100, 200);
        showRulesPanel.setSize(300, 120);
        JButton viewRulesBtn = new AppJButton("SHOW ME THE RULES", 20, TicTacToeGameGUI.BKGD_LIGHT_GRAY, TicTacToeGameGUI.APP_GREEN);
        ActionListener viewRule = e -> (new RulesPanel(this)).setVisible(true);
        viewRulesBtn.addActionListener(viewRule);
        showRulesPanel.add(viewRulesBtn);
        left.add(showRulesPanel);

        // Return Button
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new GridLayout());
        returnPanel.setLocation(100, 350);
        returnPanel.setSize(300, 60);
        JButton returnBtn = new AppJButton("RETURN TO MAIN MENU", 20, TicTacToeGameGUI.BKGD_LIGHT_GRAY, TicTacToeGameGUI.APP_GREEN);
        ActionListener returnToMainMenu = e -> {
            setVisible(false);
            MainMenu mainMenu = new MainMenu();
            mainMenu.setLocationRelativeTo(null);
            mainMenu.setVisible(true);
            dispose();
        };
        returnBtn.addActionListener(returnToMainMenu);
        returnPanel.add(returnBtn);
        left.add(returnPanel);

        main.add(left);

        // Right Options
        JPanel right = new JPanel();
        right.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);
        right.setLayout(null);
        main.add(right);

        this.add(main);
    }

    //Banner
    private void createBannerPanel() {
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.LINE_AXIS));
        banner.setBorder(BorderFactory.createEmptyBorder(0, 100, 0 ,0));
        banner.setPreferredSize(new Dimension(1000, 115));
        banner.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);
        banner.setAlignmentX(CENTER_ALIGNMENT);

        // Dynamic Banner Content

        JPanel jP2 = new JPanel();
        jP2.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);
        jP2.setPreferredSize(new Dimension(200, 100));
        jP2.setLayout(new GridLayout(2,0));
        JLabel welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("Avenir", Font.PLAIN, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setVerticalAlignment(JLabel.BOTTOM);
        jP2.add(welcomeLabel);

        JLabel nameLabel = new JLabel("Player");
        nameLabel.setFont(new Font("Avenir", Font.PLAIN, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setVerticalAlignment(JLabel.TOP);
        jP2.add(nameLabel);
        banner.add(jP2);

        this.add(banner, BorderLayout.NORTH);
    }

    private void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

}
