import java.awt.*;
import java.util.Scanner;

/**
 * this class can be used to play a game of Connect4 via a DrawingPanel and user input given in System.in
 * it is an old version, and the better version is in "PlayConnect4"
 * @author Yael Goldin
 */
public class Connect4 {

    public static void main(String[] args) {
        DrawingPanel Connect4Grid = new DrawingPanel(1100, 950);
        Graphics graphic = Connect4Grid.getGraphics();
        graphic.setColor(Color.BLUE);
        graphic.fillRect(50, 50, 1000, 800);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(50, 50, 1000, 800);
        graphic.setFont(new Font("Title", Font.BOLD, 40));
        graphic.drawString("CONNECT 4", 450, 45);
        graphic.setFont(new Font("Columns", Font.PLAIN, 20));

        int left = 125, up = 100;
        for (int i = 0; i < 7; i++) {
            graphic.drawString("" + i, left + 43 + 125 * i, up - 15);
        }
        graphic.setColor(Color.WHITE);
        for (int c = 0; c < 7; c++) {
            for (int r = 0; r < 6; r++) {
                graphic.drawOval(left + 125 * c, up + 125 * r, 100, 100);
                graphic.fillOval(left + 125 * c, up + 125 * r, 100, 100);
            }
        }
        // everything up to this point simply creates the chart in the drawing
        // panel with the 6x7 grid

        Connect4Model chart = new Connect4Model();
        Scanner input = new Scanner(System.in);
        int player1wins = 0;
        int player2wins = 0;
        int draws = 0;
        System.out.println("Welcome to Connect4!");
        System.out.println();

        do { // at least one game will be played
            if (draws + player1wins + player2wins > 0) { // is only done for the non-first game
                chart = new Connect4Model();
                graphic.setColor(Color.WHITE);
                graphic.fillRect(50, 851, 900, 50);
                for (int c = 0; c < 7; c++) {
                    for (int r = 0; r < 6; r++) {
                        graphic.fillOval(left + 125 * c, up + 125 * r, 100, 100);
                    }
                }
                graphic.setColor(Color.BLACK);
                graphic.setFont(new Font("Title", Font.PLAIN, 50));
                graphic.drawString(player1wins + " : " + player2wins, 505, 895);
            }
            
            while(!chart.isGameOver()) {
            	onePlayersMove(input, chart, graphic);
            }

            graphic.setColor(Color.BLACK);
            int winner = chart.winner();
            if (winner == 0) { //draw
                graphic.setFont(new Font("Title", Font.PLAIN, 40));
                graphic.drawString("IT'S A DRAW!", 400, 885);
                draws++;
            } else { //someone won
            	assert (winner != -1) : "game is over";
            	if(winner == 1) {
            		player1wins++;
            	} else {
            		player2wins++;
            	}
            	graphic.setColor(Color.WHITE);
                graphic.fillRect(500, 855, 100, 60);
                graphic.setColor(Color.BLACK);
                graphic.setFont(new Font("Title", Font.PLAIN, 40));
                graphic.drawString("PLAYER " + winner + " IS THE WINNER!", 280, 885);
            }
        } while (playAgain(input));
        printFinalStats(graphic, player1wins, player2wins);
        input.close();
    }
    
    // checks if the user wants to play again 
    private static boolean playAgain(Scanner input) {
    	System.out.println();
        System.out.println("Play again? Anything other than a phrase starting with 'y/Y' will mean no");
        char playing = input.nextLine().charAt(0);
        return playing == 'y' || playing == 'Y';
    }

    // essentially this method runs one turn for a player
    // they put their chip in whichever valid column they want
    // then the method checks if placing that chip made the player win, which
    // returns "true", otherwise it's "false"
    private static void onePlayersMove(Scanner input, Connect4Model chart, Graphics graphic) {
    	int playerN = chart.getCurPlayer();
        System.out.print("Player " + playerN + ": which column do you want to put a chip in? ");
        int column = whichColumn(input, chart);
        int row = chart.putDisk(column);
        // change the circle in the graphic to be the player's color
        if (playerN == 1) { // player 1 is red
            graphic.setColor(Color.RED);
        } else { // player 2 is yellow
            graphic.setColor(Color.YELLOW);
        }
        graphic.fillOval(125 + 125 * column, 100 + 125 * row, 100, 100);
    }

    // prompts the user for a column to enter their chip in
    // will keep asking until a valid column is entered
    private static int whichColumn(Scanner input, Connect4Model chart) {
        int column = -1;
        if (input.hasNextInt()) { // they entered an integer
            column = input.nextInt();
            input.nextLine();
            if (column > 6 || column < 0 || chart.columnFilled(column)) { //doesn't exist or is full
                System.out.print("Please enter a valid column number (0-6) that isn't filled: ");
                return whichColumn(input, chart); // need to enter again
            }
            return column;
        }
        input.nextLine();
        System.out.print("Please enter a valid column number (0-6) that isn't filled: ");
        return whichColumn(input, chart); // need to enter something else
    }
    
    // prints the stats for who won more games
    private static void printFinalStats(Graphics graphic, int player1wins, int player2wins) {
    	graphic.setColor(Color.WHITE);
        graphic.fillRect(280, 855, 540, 60); // "erases" any previous words at the bottom of the screen
        graphic.setColor(Color.BLACK);
        graphic.setFont(new Font("Title", Font.PLAIN, 50));
        graphic.drawString("FINAL SCORE " + player1wins + " : " + player2wins, 350, 895);

        if (player1wins > player2wins) {
            System.out.print("Player 1 has won more!");
        } else if (player2wins > player1wins) {
            System.out.print("Player 2 has won more!");
        } else {
            System.out.print("It's a tie!");
        }
    }

}