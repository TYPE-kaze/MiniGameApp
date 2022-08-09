package TicTacToe;

import java.util.Scanner;

/**
 * Represents a human player of a game where to make a move, one must enter a
 * string to pick a position.
 */
public class HumanPlayer extends Player {
    /**
     * Instantiates a new Human player.
     *
     * @param name the name of the player.
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Allows a human player to manually input a position.
     *
     * @return the position that has been picked.
     */
    @Override
    public String pickPosition() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
