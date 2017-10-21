
public class Grid {
    private char[][] grid;

    // constructs a 6x7 grid where each spot is initialized with a space
    public Grid() {
        grid = new char[6][7];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                grid[r][c] = ' ';
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    // checks if a column is filled by looking to see if the top spot is not
    // empty
    public boolean columnFilled(int column) {
        if (grid[0][column] != ' ') {
            return true;
        } else {
            return false;
        }
    }

    // puts *color* into the column
    // since the chip drops to the bottom, it looks for the first open space
    // starting on the last row
    // it then returns the row or -1 if the column is filled
    public int putDisk(int column, char color) {
        if (columnFilled(column) == true) {
            return -1;
        }
        for (int row = 5; row >= 0; row--) {
            if (grid[row][column] == ' ') {
                grid[row][column] = color;
                return row;
            }
        }
        return -1; // should not reach this point because if the column was
                   // filled it would not pass the first
                   // return statement
    }

    // checks if the chart is filled
    public boolean isDraw() {
        for (int column = 0; column < 7; column++) {
            if (grid[0][column] == ' ') { // if there is a space at the top of
                                          // any column, it can't be filled
                return false;
            }
        }
        return true;
    }

    // checks if placing a chip in grid[r][c] caused the player to win
    // returns *color* if they won, or ' ' if there is still no winner
    public char isNowWinner(int r, int c, char color) {
        // check vertical winning only if chip placed is in the top 3 rows
        // because thats the only way for a vertical win to occur
        if (r <= 2 && grid[r + 1][c] == color && grid[r + 2][c] == color && grid[r + 3][c] == color) {
            return color;
        }
        // check horizontal winning
        // first check for if the chip was placed on the leftmost spot of the 4
        // in a row
        // second check for if the chip was placed on the 2nd spot from the left
        // third check for if the chip was placed on the 2nd spot from the right
        // fourth check for if the chip was placed on the right of the 4
        if (c <= 3 && grid[r][c + 1] == color && grid[r][c + 2] == color && grid[r][c + 3] == color) {
            return color;
        } else if (c >= 1 && c <= 4 && grid[r][c - 1] == color && grid[r][c + 1] == color && grid[r][c + 2] == color) {
            return color;
        } else if (c >= 2 && c <= 5 && grid[r][c - 2] == color && grid[r][c - 1] == color && grid[r][c + 1] == color) {
            return color;
        } else if (c >= 3 && grid[r][c - 1] == color && grid[r][c - 2] == color && grid[r][c - 3] == color) {
            return color;
        }
        // checks diagonal winning in same method as checking rows (it could be
        // placed in any of the 4 locations in
        // the diagonal)
        // first set of checks for decreasing left-right diagonal
        if (r <= 2 && c <= 3 && grid[r + 1][c + 1] == color && grid[r + 2][c + 2] == color
                && grid[r + 3][c + 3] == color) {
            // placed in top left spot
            return color;
        } else if (r >= 1 && r <= 3 && c >= 1 && c <= 4 && grid[r - 1][c - 1] == color && grid[r + 1][c + 1] == color
                && grid[r + 2][c + 2] == color) { // 2nd left
            return color;
        } else if (r >= 2 && r <= 4 && c >= 2 && c <= 5 && grid[r - 2][c - 2] == color && grid[r - 1][c - 1] == color
                && grid[r + 1][c + 1] == color) { // 2nd right
            return color;
        } else if (r >= 3 && c >= 3 && grid[r - 1][c - 1] == color && grid[r - 2][c - 2] == color
                && grid[r - 3][c - 3] == color) { // placed in bottom right spot
            return color;
        }
        // second set of checks for increasing left-right
        if (r >= 3 && c <= 3 && grid[r - 1][c + 1] == color && grid[r - 2][c + 2] == color
                && grid[r - 3][c + 3] == color) {
            // placed in bottom left spot
            return color;
        } else if (r >= 2 && r <= 4 && c >= 1 && c <= 4 && grid[r + 1][c - 1] == color && grid[r - 1][c + 1] == color
                && grid[r - 2][c + 2] == color) { // 2nd left
            return color;
        } else if (r >= 1 && r <= 3 && c >= 2 && c <= 5 && grid[r + 1][c - 1] == color && grid[r + 2][c - 2] == color
                && grid[r - 1][c + 1] == color) { // 2nd right
            return color;
        } else if (r <= 2 && c >= 3 && grid[r + 1][c - 1] == color && grid[r + 2][c - 2] == color
                && grid[r + 3][c - 3] == color) { // placed in top right spot
            return color;
        }

        return ' '; // means that placing the chip did not result in a win
    }

    // returns the chart as a two-dimensional array with '|' separating the
    // columns
    @Override
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
