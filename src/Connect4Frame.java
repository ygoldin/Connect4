import javax.swing.*;
import java.awt.*;

/**
 * Connect4Frame can be used to control and display a game of Connect4
 * @author Yael Goldin
 */
@SuppressWarnings("serial")
public class Connect4Frame extends JFrame {
	private Connect4Model connect4Model;
	private ColumnButton[] columns;
	private static final NamedColor[] GAME_COLORS = {new NamedColor(Color.BLUE, "blue"),
			new NamedColor(Color.RED, "red"), new NamedColor(Color.YELLOW, "yellow")};
	
	/**
	 * constructs the initial frame
	 */
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		connect4Model = new Connect4Model();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, Connect4Model.COLUMNS));
		columns = new ColumnButton[Connect4Model.COLUMNS];
		for(int c = 0; c < columns.length; c++) {
			columns[c] = new ColumnButton(c);
			buttonPanel.add(columns[c]);
		}
		add(buttonPanel);
	}
	
	//runs the end of game actions such as printing the game over message with whether it's a draw or
	//the "curPlayer" won, and resetting the game if the user wants to play again
	private void gameOverActions(int curPlayer) {
		assert(connect4Model.isGameOver());
		String message = "Game over - ";
		if(connect4Model.isFull()) {
			message += "it's a draw!";
		} else {
			message += GAME_COLORS[curPlayer].name + " won!";
		}
		if(JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION) { //play again
			connect4Model = new Connect4Model();
			for(ColumnButton column : columns) {
				for(GridCircle row : column.rows) {
					row.changeColor(GridCircle.INITIAL_COLOR);
				}
			}
			repaint();
		} else { //end game
			System.exit(0);
		}
	}
	
	//this class represents a color with a name
	private static class NamedColor {
		public final Color color;
		public final String name;
		
		/**
		 * constructs a NamedColor object
		 * 
		 * @param color The color to set it to
		 * @param name The name to give that color
		 */
		public NamedColor(Color color, String name) {
			this.color = color;
			this.name = name;
		}
	}
	
	//this class represents a column in the game of connect4
	private class ColumnButton extends JButton {
		private GridCircle[] rows;
		private final int columnNumber;
		
		/**
		 * constructs a clickable button representing one column in the connect 4 grid
		 * @param columnNum The column of the grid that this button represents
		 */
		public ColumnButton(int columnNum) {
			columnNumber = columnNum;
			rows = new GridCircle[Connect4Model.ROWS];
			setLayout(new GridLayout(rows.length, 1));
			setBackground(GAME_COLORS[0].color);
			setOpaque(true);
			setBorderPainted(false);
			setRolloverEnabled(false);
			for(int r = 0; r < rows.length; r++) {
				rows[r] = new GridCircle();
				rows[r].setOpaque(true);
				add(rows[r]);
			}
			addActionListener(e -> {
				if(!connect4Model.isGameOver() && !connect4Model.columnFilled(columnNumber)) {
					int curPlayer = connect4Model.getCurPlayer();
					int placedRow = connect4Model.putDisk(columnNumber);
					rows[placedRow].changeColor(GAME_COLORS[curPlayer].color);
					rows[placedRow].repaint();
					if(connect4Model.isGameOver()) {
						Connect4Frame.this.gameOverActions(curPlayer);
					}
				}
			});
		}
	}
	
	//this class represents the displayed circle in one spot on the connect 4 grid
	private static class GridCircle extends JComponent {
		private Color color;
		private static final int HEIGHT_OFFSET = 5;
		private static final Color INITIAL_COLOR = Color.WHITE;
		
		/**
		 * constructs a blank GridCircle with a background of the initial color
		 */
		public GridCircle() {
			color = INITIAL_COLOR;
		}
		
		/**
		 * changes the background
		 * 
		 * @param newColor The new color to set the background to
		 */
		public void changeColor(Color newColor) {
			color = newColor;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color);
			int diameter = Math.min(getWidth(), getHeight() - HEIGHT_OFFSET);
			g.fillOval(0, 0, diameter, diameter);
		}
	}
}