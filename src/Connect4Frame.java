import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private Connect4Model model;
	private JButton[][] buttons;
	
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		setBackground(Color.BLUE);
		model = new Connect4Model();
		setupButtons();
	}
	
	private void setupButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(Connect4Model.ROWS, Connect4Model.COLUMNS));
		buttons = new JButton[Connect4Model.ROWS][Connect4Model.COLUMNS];
		for(int r = 0; r < buttons.length; r++) {
			for(int c = 0; c < buttons[r].length; c++) {
				buttons[r][c] = new JButton("" + r + c);
				buttonPanel.add(buttons[r][c]);
			}
		}
		add(buttonPanel);
	}
}