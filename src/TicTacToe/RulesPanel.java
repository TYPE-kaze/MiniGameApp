package TicTacToe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Stores the VIEW for a Rules Panel
 */
public class RulesPanel extends JFrame {

    private final AppJButton backBtn;

    /**
     * Constructs a Rules Panel.
     */
    public RulesPanel(JFrame main) {
        main.setVisible(false);
//        1. The game is played on a grid that's 3 squares by 3 squares.
//
//        2. You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares.
//
//        3. The first player to get 3 of her marks in a row (up, down, across, or diagonally) is the winner.
//
//        4. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.

                // Rules Panel Attributes
        String rules = "<html><h3>The rules are simple, there are 9 squares on a grid.</h3><br>"
                + "1. The game is played on a grid that's 3 squares by 3 squares.<br>"
                + "2. You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares.<br>"
                + "3. The first player to get 3 of her marks in a row (up, down, across, or diagonally) is the winner.<br>"
                + "4. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.<br><br>"
                + "<h3> HOW CAN I WIN AT TIC-TAC-TOE?</h3><br>"
                + " > To beat the computer (or at least tie), you need to make use of a little bit of strategy. Strategy means figuring out what you need to do to win. .<br>"
                + " > Part of your strategy is trying to figure out how to get three Xs in a row. The other part is trying to figure out how to stop the computer from getting three Os in a row. <br>"
                + " > After you put an X in a square, you start looking ahead. Where's the best place for your next X? You look at the empty squares and decide which ones are good choices—which ones might let you make three Xs in a row. <br><br>"
                + " > You also have to watch where the computer puts its O. That could change what you do next. If the computer gets two Os in a row, you have to put your next X in the last empty square in that row, or the computer will win. You are forced to play in a particular square or lose the game. <br>"
                + " > If you always pay attention and look ahead, you'll never lose a game of Tic-Tac-Toe. You may not win, but at least you'll tie.  "
                + "<br><br>"
                + "Happy Playing!!</html>";

        setTitle("Tic Tac Toe Game");
        this.setLayout(new GridLayout(1, 0));
        this.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);

        // Main Content of Panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);

            JLabel titleLabel = new JLabel("Game Rules");
            titleLabel.setFont(new Font("Avenir", Font.PLAIN, 24));
            titleLabel.setForeground(Color.white);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            content.add(titleLabel, BorderLayout.NORTH);

            JLabel text = new JLabel(rules);
            text.setFont(new Font("Avenir", Font.PLAIN, 14));
            text.setForeground(Color.white);
            text.setBackground(TicTacToeGameGUI.BKGD_DARK_GRAY);
            text.setBorder(new EmptyBorder(10, 10, 10, 10));
            content.add(text, BorderLayout.CENTER);

            backBtn = new AppJButton("Take me Back", 24, TicTacToeGameGUI.APP_GREEN, TicTacToeGameGUI.BKGD_DARK_GRAY);
            ActionListener backBTnListener = e -> {
                main.setVisible(true);
                this.dispose();
            };
            backBtn.addActionListener(backBTnListener);
            content.add(backBtn, BorderLayout.SOUTH);

        //this.add(new WelcomeImage(500, 550));
        this.add(content);
        setSize(1000,650);
        setLocationRelativeTo(null);

    }
}
