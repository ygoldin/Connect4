import java.awt.*;
import java.util.Scanner;

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

        Grid chart = new Grid();
        Scanner input = new Scanner(System.in);
        char playAgain = 'n';
        int player1wins = 0, player2wins = 0;
        System.out.println("Welcome to Connect4!\n");

        do { // at least one game will be played
            if (playAgain == 'y') { // is only done for the non-first game
                chart = new Grid();
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

            boolean isWinner = false;

            // alternates player 1 and player 2 placing their pieces in the chart
            // ends when there is a draw (which can only happen after player 2 goes)
            // or when one player wins
            while (!chart.isDraw()) {
                isWinner = perPlayer(input, chart, graphic);
                if (isWinner) { // player1 is the winner
                    player1wins++;
                    break;
                }
                isWinner = perPlayer(input, chart, graphic);
                if (isWinner) { // player2 is the winner
                    player2wins++;
                    break;
                }
            }

            graphic.setColor(Color.BLACK);
            if (chart.isDraw()) {
                graphic.setFont(new Font("Title", Font.PLAIN, 40));
                graphic.drawString("IT'S A DRAW!", 400, 885);
            }
            System.out.println();
            System.out.println("Play again? Anything other than a phrase starting with 'y' will mean no");
            String playing = input.next();
            input.nextLine();
            playAgain = playing.charAt(0);

        } while (playAgain == 'y');

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

        input.close();
    }

    // essentially this method runs one turn for a player
    // they put their chip in whichever valid column they want
    // then the method checks if placing that chip made the player win, which
    // returns "true", otherwise it's "false"
    public static boolean perPlayer(Scanner input, Grid chart, Graphics graphic) {
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

        if (chart.isGameOver()) {
            graphic.setColor(Color.WHITE);
            graphic.fillRect(500, 855, 100, 60);
            graphic.setColor(Color.BLACK);
            graphic.setFont(new Font("Title", Font.PLAIN, 40));
            graphic.drawString("PLAYER " + playerN + " IS THE WINNER!", 280, 885);
            return true;
        }
        return false;
    }

    // prompts the user for a column to enter their chip in
    // will keep asking until a valid column is entered
    public static int whichColumn(Scanner input, Grid chart) {
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

}