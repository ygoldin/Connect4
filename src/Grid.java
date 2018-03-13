/**
 * a Grid can be used as the model for a Connect4 game
 */
public class Grid {
    private char[][] grid;
    private int filledSpots;
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final int IN_A_ROW = 4;

    /**
     * constructs a ROWSxCOLUMNS grid where each spot is initialized as empty
     */
    public Grid() {
        grid = new char[ROWS][COLUMNS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                grid[r][c] = ' ';
            }
        }
        filledSpots = 0;
    }

    /**
     * returns the grid representation
     * @return an array representation of the grid
     */
    public char[][] getGrid() {
        return grid;
    }

    // checks if a column is filled by looking to see if the top spot is not
    // empty
    /**
     * checks if a column is filled
     * 
     * @param column The column to look at
     * @return true if the column is filled, false otherwise
     * @throws IllegalArgumentException if column isn't in the range [0, COLUMNS)
     */
    public boolean columnFilled(int column) {
    	if(column < 0 || column >= COLUMNS) {
    		throw new IllegalArgumentException("invalid column value: " + column);
    	}
        return grid[0][column] != ' ';
    }

    // puts *color* into the column
    // since the chip drops to the bottom, it looks for the first open space
    // starting on the last row
    // it then returns the row or -1 if the column is filled
    /**
     * puts *color* into the given column
     * 
     * @param column The column to put the disk in
     * @param color The color to put in that column
     * @return the row the color was put in
     * @throws IllegalArgumentException if column isn't in the range [0, COLUMNS) or the column is full
     */
    public int putDisk(int column, char color) {
    	if(columnFilled(column)) {
    		throw new IllegalArgumentException("filled column: " + column);
    	}
        filledSpots++;
        for (int row = ROWS - 1; row > 0; row--) {
            if (grid[row][column] == ' ') {
                grid[row][column] = color;
                return row;
            }
        }
        grid[0][column] = color;
        return 0;
    }

    /**
     * checks if the board is full in a draw
     * 
     * @return true if the game has ended in a draw, false otherwise
     */
    public boolean isDraw() {
        return filledSpots == ROWS*COLUMNS;
    }

    /**
     * checks if placing a chip in that location caused the player to win
     * 
     * @param r The row it was placed in
     * @param c The column it was placed in
     * @param color The color of the player who placed it
     * @return true if they won, or false if there is still no winner
     */
    public boolean isNowWinner(int r, int c, char color) {
        // check vertical winning only if chip placed is in the top 3 rows
        // because thats the only way for a vertical win to occur
        if (r <= 2 && grid[r + 1][c] == color && grid[r + 2][c] == color && grid[r + 3][c] == color) {
            return true;
        }
        // check horizontal winning
        // first check for if the chip was placed on the leftmost spot of the 4
        // in a row
        // second check for if the chip was placed on the 2nd spot from the left
        // third check for if the chip was placed on the 2nd spot from the right
        // fourth check for if the chip was placed on the right of the 4
        if (c <= 3 && grid[r][c + 1] == color && grid[r][c + 2] == color && grid[r][c + 3] == color) {
            return true;
        } else if (c >= 1 && c <= 4 && grid[r][c - 1] == color && grid[r][c + 1] == color && grid[r][c + 2] == color) {
            return true;
        } else if (c >= 2 && c <= 5 && grid[r][c - 2] == color && grid[r][c - 1] == color && grid[r][c + 1] == color) {
            return true;
        } else if (c >= 3 && grid[r][c - 1] == color && grid[r][c - 2] == color && grid[r][c - 3] == color) {
            return true;
        }
        // checks diagonal winning in same method as checking rows (it could be
        // placed in any of the 4 locations in
        // the diagonal)
        // first set of checks for decreasing left-right diagonal
        if (r <= 2 && c <= 3 && grid[r + 1][c + 1] == color && grid[r + 2][c + 2] == color
                && grid[r + 3][c + 3] == color) {
            // placed in top left spot
            return true;
        } else if (r >= 1 && r <= 3 && c >= 1 && c <= 4 && grid[r - 1][c - 1] == color && grid[r + 1][c + 1] == color
                && grid[r + 2][c + 2] == color) { // 2nd left
            return true;
        } else if (r >= 2 && r <= 4 && c >= 2 && c <= 5 && grid[r - 2][c - 2] == color && grid[r - 1][c - 1] == color
                && grid[r + 1][c + 1] == color) { // 2nd right
            return true;
        } else if (r >= 3 && c >= 3 && grid[r - 1][c - 1] == color && grid[r - 2][c - 2] == color
                && grid[r - 3][c - 3] == color) { // placed in bottom right spot
            return true;
        }
        // second set of checks for increasing left-right
        if (r >= 3 && c <= 3 && grid[r - 1][c + 1] == color && grid[r - 2][c + 2] == color
                && grid[r - 3][c + 3] == color) {
            // placed in bottom left spot
            return true;
        } else if (r >= 2 && r <= 4 && c >= 1 && c <= 4 && grid[r + 1][c - 1] == color && grid[r - 1][c + 1] == color
                && grid[r - 2][c + 2] == color) { // 2nd left
            return true;
        } else if (r >= 1 && r <= 3 && c >= 2 && c <= 5 && grid[r + 1][c - 1] == color && grid[r + 2][c - 2] == color
                && grid[r - 1][c + 1] == color) { // 2nd right
            return true;
        } else if (r <= 2 && c >= 3 && grid[r + 1][c - 1] == color && grid[r + 2][c - 2] == color
                && grid[r + 3][c - 3] == color) { // placed in top right spot
            return true;
        }

        return false; // means that placing the chip did not result in a win
    }

    @Override
    /**
     * @return a String representation of the chart as a two-dimensional array with '|' separating the
     */
    public String toString() {
        String result = "  0   1   2   3   4   5   6\n";
        for (int r = 0; r < 6; r++) {
            result += "[ ";
            for (int c = 0; c < 6; c++) {
                result += grid[r][c] + " | ";
            }
            result += grid[r][6] + " ]\n";
        }
        return result;
    }
}
