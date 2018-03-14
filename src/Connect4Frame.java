import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private Connect4Model connect4Model;
	private ColumnButton[] columns;
	private static final Color[] PLAYER_COLORS = {Color.RED, Color.YELLOW};
	
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
	
	private class ColumnButton extends JButton {
		public GridCircle[] rows;
		public int columnNumber;
		
		public ColumnButton(int column) {
			columnNumber = column;
			rows = new GridCircle[Connect4Model.ROWS];
			setLayout(new GridLayout(rows.length, 1));
			setBackground(Color.BLUE);
			setOpaque(true);
			for(int r = 0; r < rows.length; r++) {
				rows[r] = new GridCircle();
				rows[r].setOpaque(true);
				add(rows[r]);
			}
			addActionListener(e -> {
				if(!connect4Model.isGameOver() && !connect4Model.columnFilled(columnNumber)) {
					int curPlayer = connect4Model.getCurPlayer();
					int placedRow = connect4Model.putDisk(columnNumber);
					rows[placedRow].changeColor(PLAYER_COLORS[curPlayer - 1]);
					rows[placedRow].repaint();
				}
			});
		}
	}
	
	private static class GridCircle extends JComponent {
		public Color color;
		
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
			g.fillOval(0, 0, getWidth(), getWidth());
		}
	}
}