package TicTacToe;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

class TicTacToeGameGUI extends JFrame {
    private final static int WIDTH = 1000;
    private final static int HEIGHT = 650;
    private final static Color X_MARK_COLOUR = Color.BLUE;
    private final static Color O_MARK_COLOUR = Color.RED;
    public final static Color BKGD_DARK_GRAY = new Color(42, 54, 63);
    public static final Color APP_GREEN = new Color(146, 208, 80);
    public static final Color BKGD_LIGHT_GRAY = new Color(62, 74, 83);

    private boolean isGridLocked = false;

    private TicTacToeGame game;

    private JFrame main;

    private final GridPositionButton[] gridPositionButtons =
            new GridPositionButton[TicTacToeGrid.NUMBER_OF_GRID_POSITIONS];

    TicTacToeGameGUI(int gameMode, JFrame main) {
        main.setVisible(false);
        this.main = main;
        game = new TicTacToeGame(gameMode);
        createComponents();
        setLocationRelativeTo(null);

        if (game.getGameMode() == 3) {
            aiMove(0);
        } else if (game.getGameMode() == 4) {
            initAiVsAiMode();
        }
    }

    private void initAiVsAiMode() {
        GameStatus status = GameStatus.GAME_TO_CONTINUE;

        Thread newThread = new Thread(() -> {
            while (!isGameToEnd(status)) {
                aiMove(1);
            }
        });
        newThread.start();
    }

    private void createComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe Game");

        //Banner
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.LINE_AXIS));
        banner.setPreferredSize(new Dimension(1000, 115));
        banner.setBackground(BKGD_DARK_GRAY);
        banner.setAlignmentX(CENTER_ALIGNMENT);

        // Dynamic Banner Content
        JPanel jP2 = new JPanel();
        jP2.setBackground(BKGD_DARK_GRAY);
        jP2.setPreferredSize(new Dimension(200, 100));
        jP2.setLayout(new GridLayout(2,0));

        JLabel gameLabel = new JLabel("Tic Toc Toe");
        gameLabel.setFont(new Font("Avenir", Font.BOLD, 36));
        gameLabel.setForeground(Color.WHITE);
        gameLabel.setVerticalAlignment(JLabel.BOTTOM);
        gameLabel.setHorizontalAlignment(JLabel.RIGHT);
        jP2.add(gameLabel);

        JLabel modeLabel = new JLabel(game.players[0].getName() + " vs " + game.players[1].getName());
        modeLabel.setFont(new Font("Avenir", Font.PLAIN, 24));
        modeLabel.setForeground(Color.WHITE);
        modeLabel.setVerticalAlignment(JLabel.TOP);
        modeLabel.setHorizontalAlignment(JLabel.RIGHT);
        jP2.add(modeLabel);

        banner.add(jP2);
        banner.add(Box.createRigidArea(new Dimension(15,0)));
        add(banner, BorderLayout.NORTH);

        //Main content
        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBackground(BKGD_DARK_GRAY);

        //Action
        JPanel actions = new JPanel();
        actions.setLayout(new GridLayout(2,1));
        actions.setSize(135, 90);
        actions.setLocation(0, 415 - actions.getHeight());

        // View Rules Button
        JButton viewRulesBtn = new AppJButton("VIEW RULES", 14, BKGD_LIGHT_GRAY, APP_GREEN);
        ActionListener viewRule = e -> (new RulesPanel(this)).setVisible(true);
        viewRulesBtn.addActionListener(viewRule);
        actions.add(viewRulesBtn);

        // Sign Out Button
        JButton endGameBtn = new AppJButton("END GAME", 14, BKGD_LIGHT_GRAY, APP_GREEN);
        ActionListener finishActionListener = e -> hideThisWindow();
        endGameBtn.addActionListener(finishActionListener);
        actions.add(endGameBtn);
        main.add(actions);


        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(3, 3));
        grid.setPreferredSize(new Dimension(120, 120));
        grid.setMaximumSize(new Dimension(433, 433));
        grid.setBorder(new LineBorder(APP_GREEN, 2));
        grid.setBackground(BKGD_DARK_GRAY.darker());
        grid.setForeground(Color.white);
        grid.setLocation(285, 0);
        grid.setSize(400, 400);
        main.add(grid);

        for (int i = 0; i < gridPositionButtons.length; i++) {
            gridPositionButtons[i] = new GridPositionButton(i + 1);
            int finalI = i;
            ActionListener actionListener = e -> gridButtonPressed(gridPositionButtons[finalI]);
            gridPositionButtons[i].addActionListener(actionListener);

            grid.add(gridPositionButtons[i]);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(main, BorderLayout.CENTER);
        add(panel);
    }

    private void gridButtonPressed(GridPositionButton gridPositionButton) {
        if (!(gridPositionButton.getText().equals(GridStatus.X_CLAIMED.toString()) ||
                gridPositionButton.getText().equals(GridStatus.O_CLAIMED.toString()))) {
            boolean isMoveMadeOrGameFinished;
            try {
                isMoveMadeOrGameFinished = playerMove(gridPositionButton);
            } catch (IllegalStateException e) {
                return;
            }

            if (isMoveMadeOrGameFinished && isHumanPlayingAgainstAi()) {
                Thread newThread = new Thread(() -> aiMove(1));
                newThread.start();
            }
        }
    }

    private boolean playerMove(GridPositionButton gridPositionButton) throws IllegalStateException {
        if (!isGridLocked) {
            int gridPositionButtonNumber = gridPositionButton.getGridPosition();
//            Color oldButtonTextColour = gridPositionButton.getForeground();

            try {
                gridPositionButton.setText(getPlayerMark());
                gridPositionButton.setForeground(getPlayerColour());
                GameStatus status = game.handleInput(gridPositionButtonNumber);
                respondToWinTie(status);

                if (isGameToEnd(status)) {
                    terminateGame();
                    return false;
                }
            } catch (IllegalArgumentException e) {
                gridPositionButton.setText("");
//                gridPositionButton.setForeground(oldButtonTextColour);
                return false;
            }
            return true;
        } else {
            throw new IllegalStateException("Grid is locked!");
        }
    }

    private void aiMove(int timeDelay) {
        isGridLocked = true;
        int aiMove = Integer.parseInt(game.players[game.getGrid().getNextPlayer() - 1].pickPosition());

        try {
            TimeUnit.SECONDS.sleep(timeDelay);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        GridPositionButton button = gridPositionButtons[aiMove - 1];
        button.setText(getPlayerMark());
        button.setForeground(getPlayerColour());

        GameStatus status = game.handleInput(aiMove);
        respondToWinTie(status);

        if (isGameToEnd(status)) {
            terminateGame();
        }
        isGridLocked = false;
    }

    private String getPlayerMark() {
        if (game.getGrid().getNextPlayer() == 1) {
            return GridStatus.X_CLAIMED.toString();
        } else {
            return GridStatus.O_CLAIMED.toString();
        }
    }

    private Color getPlayerColour() {
        if (game.getGrid().getNextPlayer() == 1) {
            return X_MARK_COLOUR;
        } else {
            return O_MARK_COLOUR;
        }
    }

    private void terminateGame() {
        setVisible(false);
        main.setVisible(true);
    }

    private boolean isHumanPlayingAgainstAi() {
        int gameMode = game.getGameMode();
        return gameMode == 2 || gameMode == 3;
    }

    private boolean isGameToEnd(GameStatus status) {
        return status != GameStatus.GAME_TO_CONTINUE;
    }

    private void hideThisWindow() {
        this.setVisible(false);
        main.setVisible(true);
    }

    private void respondToWinTie(GameStatus status) {
        if (status != GameStatus.GAME_TO_CONTINUE) {
            if (status == GameStatus.WIN) {
                game.getGrid().incrementPlayer();
                JOptionPane.showMessageDialog(null, game.players[game.getGrid().getNextPlayer() - 1].getName() + " won!");
            } else if (status == GameStatus.TIE) {
                JOptionPane.showMessageDialog(null, "There has been a tie.");
            }
        }
    }
}
