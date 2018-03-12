import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private boolean firstPlayersTurn;
	private Grid grid;
	
	public Connect4Frame() {
		firstPlayersTurn = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		grid = new Grid();
	}
}