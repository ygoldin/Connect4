import java.awt.*;

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
