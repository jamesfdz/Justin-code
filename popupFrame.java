package justin;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class popupFrame {
	private static JFrame f = new JFrame("Please wait...");
	
	public void showPopup() {
		System.out.println("Showing Popup");
		
		f.setSize(300, 150);
		
		f.setLayout(new FlowLayout());
		
		f.add(new JButton("Test"));
		
		f.setVisible(true);
	}
	
	public void hidePopup() {
		System.out.println("Hiding Popup");
		f.setVisible(false);
	}
}
