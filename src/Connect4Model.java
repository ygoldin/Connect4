/**
 * a Grid can be used as the model for a Connect4 game
 */
public class Connect4Model {
    private int[][] grid;
    private int filledSpots;
    private int curPlayer;
    private boolean lastMoveWon;
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final int IN_A_ROW = 4;

    /**
     * constructs a ROWSxCOLUMNS grid where each spot is initialized as empty
     */
    public Connect4Model() {
        grid = new int[ROWS][COLUMNS];
        filledSpots = 0;
        curPlayer = 1;
        lastMoveWon = false;
    }

    /**
     * returns the grid
     * 
     * @return an array representation of the grid
     */
    public int[][] getGrid() {
        return grid;
    }
    
    /**
     * gets the current player
     * 
     * @return 1 if it's player 1's turn, 2 if player 2's
     */
    public int getCurPlayer() {
    	return curPlayer;
    }

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
        return grid[0][column] != 0;
    }

    /**
     * puts *color* into the given column
     * 
     * @param column The column to put the disk in
     * @param color The color to put in that column
     * @return the row the color was put in
     * @throws IllegalStateException if the game is over
     * @throws IllegalArgumentException if column isn't in the range [0, COLUMNS) or the column is full
     */
    public int putDisk(int column) {
    	if(isGameOver()) {
    		throw new IllegalStateException("game is over");
    	} else if(columnFilled(column)) {
    		throw new IllegalArgumentException("filled column: " + column);
    	}
        int lastFilledRow = -1;
        int lastFilledColumn = column;
        for (int row = ROWS - 1; row >= 0 && lastFilledRow == -1; row--) {
            if (grid[row][column] == 0) {
                grid[row][column] = curPlayer;
                lastFilledRow = row;
                filledSpots++;
            }
        }
        assert(lastFilledRow != -1) : "column wasn't full but disk wasn't placed: " + column;
        lastMoveWon = wonVertically(lastFilledRow, lastFilledColumn) || wonHorizontally(lastFilledRow) ||
        		wonDiagonally(lastFilledRow, lastFilledColumn);
        curPlayer = (curPlayer % 2) + 1; //1 -> 2, 2 -> 1
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
     * checks if the game ended
     * 
     * @return true if someone won or it's a draw, false otherwise
     */
    public boolean isGameOver() {
    	return lastMoveWon || isDraw();
    }
    
    /**
     * returns the winner or if there is none
     * 
     * @return -1 if the game isn't over, 0 if it ended in a draw, 1 if player 1 won, 2 if player 2 won
     */
    public int winner() {
    	if(lastMoveWon) {
    		return (curPlayer % 2) + 1; //curPlayer changes after every move, need to change it back
    	} else if(isDraw()) {
    		return 0;
    	} else {
    		return -1;
    	}
    }
    
    //checks if the player has 4 in a row vertically
    private boolean wonVertically(int lastFilledRow, int lastFilledColumn) {
    	return lastFilledRow <= 2 && grid[lastFilledRow + 1][lastFilledColumn] == curPlayer &&
    			grid[lastFilledRow + 2][lastFilledColumn] == curPlayer &&
    			grid[lastFilledRow + 3][lastFilledColumn] == curPlayer;
    }
    
    //checks if the player has 4 in a row vertically
    private boolean wonHorizontally(int lastFilledRow) {
    	int colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	if(grid[lastFilledRow][i] == curPlayer) {
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
    private boolean wonDiagonally(int lastFilledRow, int lastFilledColumn) {
    	//bottom left -> top right
        int rStart = lastFilledRow + IN_A_ROW - 1;
        int cStart = lastFilledColumn - IN_A_ROW + 1;
        int colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart - i;
        	int curC = cStart + i;
        	if(curR < 0 || curC >= COLUMNS) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR >= ROWS || curC < 0 || grid[curR][curC] != curPlayer) {
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
        cStart = lastFilledColumn - IN_A_ROW + 1;
        colorInARow = 0;
        for(int i = 0; i < COLUMNS; i++) {
        	int curR = rStart + i;
        	int curC = cStart + i;
        	if(curR >= ROWS || curC >= COLUMNS) {
        		break; //out of bounds for the rest of the loop
        	} else if(curR < 0 || curC < 0 || grid[curR][curC] != curPlayer) {
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
