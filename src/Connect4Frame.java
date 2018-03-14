import javax.swing.*;
import java.awt.*;

public class Connect4Frame extends JFrame {
	private Connect4Model model;
	
	public Connect4Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Connect 4");
		model = new Connect4Model();
	}
}