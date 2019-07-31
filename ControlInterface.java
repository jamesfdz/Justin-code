package justin;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ControlInterface{

    public ControlInterface() {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.add(new JButton("Test"));
        frame.setVisible(true);
    }
}
