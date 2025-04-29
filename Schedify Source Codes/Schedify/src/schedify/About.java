package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public final class About extends Panels{
    
    private JPanel header, titlePanel, bodyPanel;
    public JButton backButton;
    private JLabel titleLabel, bodyLabel;
    private BufferedImage logo;
    public Font archivoblack, archivonarrow, font;
    private Icon logoIcon;
    private String aboutString;
    
    public About() {
      
    }
    
    @Override
    public void showUIComponents(){
        
        archivoblack = importFont("archivoblack");
        archivonarrow = importFont("archivonarrow");
        
        header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(950, 130));
        header.setBackground(new Color(0,0,0,0));
        header.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 50));
        
        backButton = createButton("BACK");
        
        header.add(backButton, BorderLayout.EAST);
        
        titleLabel = new JLabel("About");
        titleLabel.setFont(archivoblack.deriveFont(70f));
        titleLabel.setForeground(Color.WHITE);
        
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(950, 60));
        titlePanel.setBackground(new Color(0,0,0,0));
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 50));
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        
        aboutString = "<html><body style='text-align:right;'>This program was developed by Vall James Luceres, Rosemarie Negros, and Brian Pangue  "
                + "as a group project for CMSC 125. It was developed using Java Language and the GUI was made using Netbeans IDE.</body></html>";
        
        bodyLabel = new JLabel();
        bodyLabel.setText(aboutString);
        bodyLabel.setForeground(Color.WHITE);
        bodyLabel.setFont(archivonarrow.deriveFont(20f));
        bodyLabel.setIconTextGap(20);
        bodyLabel.setPreferredSize(new Dimension(750, 200));
        bodyLabel.setBackground(new Color(0,0,0,0));
        bodyLabel.setIcon(logoIcon);
        bodyLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        
        bodyPanel = new JPanel();
        bodyPanel.setOpaque(false);
        bodyPanel.setPreferredSize(new Dimension(950, 300));
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(70,0,0,0));
        bodyPanel.add(bodyLabel);
        
        
        add(header);
        add(titlePanel);
        add(bodyPanel);
        
    }
}
