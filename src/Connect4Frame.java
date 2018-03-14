import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private Connect4Model connect4Model;
	private ColumnButton[] columns;
	private static final NamedColor[] GAME_COLORS = {new NamedColor(Color.BLUE, "blue"),
			new NamedColor(Color.RED, "red"), new NamedColor(Color.YELLOW, "yellow")};
	
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		connect4Model = new Connect4Model();
		setupButtons();
	}
	
	private void setupButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, Connect4Model.COLUMNS));
		columns = new ColumnButton[Connect4Model.COLUMNS];
		for(int c = 0; c < columns.length; c++) {
			columns[c] = new ColumnButton(c);
			ColumnButton cur = columns[c];
			buttonPanel.add(cur);
		}
		add(buttonPanel);
	}
	
	private static class NamedColor {
		public Color color;
		public String name;
		
		public NamedColor(Color color, String name) {
			this.color = color;
			this.name = name;
		}
	}
	
	private class ColumnButton extends JButton {
		public GridCircle[] rows;
		public int columnNumber;
		
		public ColumnButton(int column) {
			columnNumber = column;
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
						String message = "Game over - ";
						if(connect4Model.isDraw()) {
							message += "it's a draw!";
						} else {
							message += GAME_COLORS[curPlayer].name + " won!";
						}
						JOptionPane.showMessageDialog(Connect4Frame.this, message);
					}
				}
			});
		}
	}
	
	private static class GridCircle extends JComponent {
		public Color color;
		private static final int HEIGHT_OFFSET = 5;
		
		public GridCircle() {
			color = Color.WHITE;
		}
		
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