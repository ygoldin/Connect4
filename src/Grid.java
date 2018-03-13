/**
 * a Grid can be used as the model for a Connect4 game
 */
public class Grid {
    private char[][] grid;
    private int filledSpots;
    private int lastFilledRow;
    private int lastFilledColumn;
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
        lastFilledRow = -1;
        lastFilledColumn = -1;
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
        boolean placed = false;
        for (int row = ROWS - 1; row >= 0 && !placed; row--) {
            if (grid[row][column] == ' ') {
                grid[row][column] = color;
                lastFilledRow = row;
                lastFilledColumn = column;
                filledSpots++;
                placed = true;
            }
        }
        if(!placed) {
        	throw new IllegalStateException("column wasn't full but disk wasn't placed: " + column);
        }
        return lastFilledRow;
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
     * checks if the player who just placed the last move won
     * 
     * @param color The color of the player who placed it
     * @return true if they won, or false if there is still no winner
     */
    public boolean isNowWinner(char color) {
    	if(lastFilledRow == -1) {
    		return false; //no moves yet
    	}
        return wonVertically(color) || wonHorizontally(color) || wonDiagonally(color);
    }
    
    //checks if the player has 4 in a row vertically
    private boolean wonVertically(char color) {
    	return lastFilledRow <= 2 && grid[lastFilledRow + 1][lastFilledColumn] == color &&
    			grid[lastFilledRow + 2][lastFilledColumn] == color &&
    			grid[lastFilledRow + 3][lastFilledColumn] == color;
    }
    
    //checks if the player has 4 in a row vertically
    private boolean wonHorizontally(char color) {
    	int colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	if(grid[lastFilledRow][i] == color) {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	} else {
        		colorInARow = 0;
        	}
        }
        return false;
    }
    
    //checks if the player has 4 in a row diagonally
    private boolean wonDiagonally(char color) {
    	//bottom left -> top right
        int rStart = lastFilledRow + IN_A_ROW - 1;
        int cStart = lastFilledColumn - IN_A_ROW + 1;
        int colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart - i;
        	int curC = cStart + i;
        	if(curR < 0 || curC >= COLUMNS) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR >= ROWS || curC < 0 || grid[curR][curC] != color) {
        		colorInARow = 0; //out of bounds at the start of the loop, or wrong color
        	} else {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	}
        }
        //top left -> bottom right
        rStart = lastFilledRow - IN_A_ROW + 1;
        cStart = lastFilledColumn + IN_A_ROW - 1;
        colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart - i;
        	int curC = cStart + i;
        	if(curR >= ROWS || curC < 0) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR < 0 || curC >= COLUMNS || grid[curR][curC] != color) {
        		colorInARow = 0; //out of bounds at the start of the loop, or wrong color
        	} else {
        		colorInARow++;
        		if(colorInARow == IN_A_ROW) {
        			return true;
        		}
        	}
        }
        return false;
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
