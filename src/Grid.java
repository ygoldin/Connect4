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
        // check horizontal winning, if there are 4 consecutive "color" disks in the row
        int colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	if(grid[r][i] == color) {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	} else {
        		colorInARow = 0;
        	}
        }
        //check diagonal winning        
        //bottom left -> top right
        int rStart = r + IN_A_ROW - 1;
        int cStart = c - IN_A_ROW + 1;
        colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart - i;
        	int curC = cStart + i;
        	if(curR < 0 || curC >= COLUMNS) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR >= ROWS || curC < 0 || grid[curR][curC] != color) {
        		colorInARow = 0; //currently out of bounds or wrong color
        	} else {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	}
        }
        //top left -> bottom right
        rStart = r - IN_A_ROW + 1;
        cStart = c + IN_A_ROW - 1;
        colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart - i;
        	int curC = cStart + i;
        	if(curR >= ROWS || curC < 0) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR < 0 || curC >= COLUMNS || grid[curR][curC] != color) {
        		colorInARow = 0; //currently out of bounds or wrong color
        	} else {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	}
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
