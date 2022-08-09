package TicTacToe;

/**
 * An abstract representation of a player, that requires any class that inherits
 * from it to have a name field, and be able to select a position.
 */
public abstract class Player {

    private String name;

    /**
     * Instantiates a new Player.
     *
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * The method that shall enable a player to make a move by picking their
     * next position.
     *
     * @return the position that has been picked.
     */
    public abstract String pickPosition();

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
}
