package TicTacToe;

/**
 * An enum with 3 possible values, X claimed, O claimed, and
 * unclaimed, which model the possible states of a position on a tic tac toe
 * grid. Each possible value has a to string method which prints the relevant
 * symbol.
 */

public enum GridStatus {
    X_CLAIMED {
        public String toString() {
            return "X";
        }
    },
    O_CLAIMED {
        public String toString() {
            return "O";
        }
    },
    UNCLAIMED {
        public String toString() {
            return " ";
        }
    }
}
