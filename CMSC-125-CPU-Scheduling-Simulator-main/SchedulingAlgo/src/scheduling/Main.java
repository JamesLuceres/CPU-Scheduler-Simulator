package scheduling;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
	
	public Main() {
		setTitle("CPU Scheduling Simulator");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add CPUSchedulingSimulatorPanel instance
        add(new SchedulingSimulator(), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}