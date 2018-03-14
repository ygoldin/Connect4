import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Connect4Frame extends JFrame {
	private Connect4Model connect4Model;
	private ColumnButton[] columns;
	
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		setBackground(Color.BLUE);
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
			addActionListener(e -> {
				if(!connect4Model.columnFilled(columnNumber)) {
					int curPlayer = connect4Model.getCurPlayer();
					int placedRow = connect4Model.putDisk(columnNumber);
					rows[placedRow].setText("player" + curPlayer);
				}
			});
		}
	}
}