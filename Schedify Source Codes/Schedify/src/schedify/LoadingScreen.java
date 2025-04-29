package schedify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private Timer timer;
    private int progress = 0;

    public LoadingScreen() {
        setTitle("Schedify - CPU Scheduling Simulator");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Removes window borders

        JLabel backgroundLabel = null; // Declare outside try block
        try {
            InputStream imgStream = getClass().getResourceAsStream("/img/BackgroundWithLogo.png");
            if (imgStream == null) throw new IOException("Image not found!");
            BufferedImage backgroundImage = ImageIO.read(imgStream);
            backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundLabel = new JLabel("Background Image Not Found");
        }

        backgroundLabel.setLayout(new GridBagLayout());

        // Layout Manager
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Spacer to push progress bar upwards
        gbc.gridy = 0;
        gbc.weighty = 0.8;
        backgroundLabel.add(new JLabel(), gbc);

        // Loading Bar Panel
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(400, 25));
        progressBar.setForeground(new Color(0, 255, 255)); // Light blue
        progressBar.setBorderPainted(false);

        JPanel loadingPanel = new JPanel();
        loadingPanel.setOpaque(false);
        loadingPanel.setLayout(new FlowLayout());
        loadingPanel.add(progressBar);

        // Position progress bar higher
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        backgroundLabel.add(loadingPanel, gbc);

        add(backgroundLabel);

        // Timer for fake loading progress
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 5;
                progressBar.setValue(progress);
                if (progress >= 100) {
                    timer.stop();
                    dispose(); // Close loading screen when done
                }
            }
        });

        timer.start();
    }
}
