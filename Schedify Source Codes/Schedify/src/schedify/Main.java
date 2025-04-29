package schedify;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    public static void main(String[] args) {
        // Show the loading screen first
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.setVisible(true);

        // Simulate loading time
        try {
            Thread.sleep(1000); // Adjust loading time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the loading screen
        loadingScreen.dispose();
        
        JFrame frame = new JFrame("Schedify");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        frame.setContentPane(cardPanel);
        
        MainMenu mainMenu = new MainMenu(cardLayout, cardPanel);        
        
        frame.setVisible(true);
    }
    
}
