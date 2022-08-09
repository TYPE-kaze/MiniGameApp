package TicTacToe;

/**
 * The main class of the program; used to launch the program.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments, if the first argument is -cmd, game will
     *             be launched in command-line mode.
     */
    public static void main(String[] args) {
        TicTacToeMainMenu menu = new TicTacToeMainMenu();
        menu.setVisible(true);
    }
}
