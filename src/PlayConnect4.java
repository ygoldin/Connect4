import java.awt.*;

import javax.swing.JFrame;

/**
 * PlayConnect4 can be used to play an interactive game of Connect4
 * @author Yael Goldin
 */
public class PlayConnect4 {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame connect4 = new JFrame();
				connect4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				connect4.setMinimumSize(new Dimension(1024, 768));
				connect4.setTitle("Connect 4");
				connect4.add(new Connect4Panel());
				connect4.setVisible(true);
			}
		});
	}

}
