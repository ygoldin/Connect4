import javax.swing.JApplet;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class PlayConnect4Online extends JApplet {
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			    public void run() {
			    	getContentPane().add(new Connect4Panel());
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
