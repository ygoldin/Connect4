import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private Connect4Model model;
	private ColumnButton[] columns;
	
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		setBackground(Color.BLUE);
		model = new Connect4Model();
		//setupButtons();
		setupButtons();
	}
	
	private void setupButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, Connect4Model.COLUMNS));
		columns = new ColumnButton[Connect4Model.COLUMNS];
		for(int c = 0; c < columns.length; c++) {
			columns[c] = new ColumnButton(c);
			buttonPanel.add(columns[c]);
		}
		add(buttonPanel);
	}
	
	private static class ColumnButton extends JButton {
		public JLabel[] rows;
		public int columnNumber;
		
		public ColumnButton(int column) {
			columnNumber = column;
			rows = new JLabel[Connect4Model.ROWS];
			this.setLayout(new GridLayout(rows.length, 1));
			for(int r = 0; r < rows.length; r++) {
				rows[r] = new JLabel("" + r + columnNumber);
				this.add(rows[r]);
			}
		}
	}
}