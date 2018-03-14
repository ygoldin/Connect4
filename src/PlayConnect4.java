import java.awt.*;

/**
 * PlayConnect4 can be used to play an interactive game of Connect4
 * @author Yael Goldin
 */
public class PlayConnect4 {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connect4Frame connect4 = new Connect4Frame();
				connect4.pack();
				connect4.setVisible(true);
			}
		});
	}

}
