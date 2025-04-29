package schedify;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartPage extends Panels{
    
    private JPanel leftPanel, rightPanel, titlePanel, logoPanel, buttonPanel, selectionPanel;
    private JLabel titleLabel, logoLabel;
    public JButton randomInputButton, userDefinedButton, textFileButton, backButton, continueButton, selectButton;
    public JComboBox optionBox;
    
    public int algo;
    
    public StartPage(){
    
    }
    
    @Override
    public void showUIComponents(){
        archivoblack = importFont("archivoblack");
        
        //////left panel//////
        
        leftPanel = new JPanel(new FlowLayout());
        leftPanel.setPreferredSize(new Dimension(500, 700));
        leftPanel.setOpaque(false);
        
        titlePanel = new JPanel(new GridBagLayout());
        titleLabel = new JLabel("<html><body style='text-align:center;'>Choose Data<br>Input</body></html>");
        titleLabel.setFont(archivoblack.deriveFont(54f));
        titleLabel.setForeground(Color.WHITE);
                
        GridBagConstraints tgbc = new GridBagConstraints();
        tgbc.gridx = 0;
        tgbc.gridy = 0;
        tgbc.insets = new Insets(70, 20, 0, 20);

        titlePanel.add(titleLabel, tgbc);
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension (500, 200));
       
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(550, 500));
        
        randomInputButton = createButton("RANDOM INPUT");
        userDefinedButton = createButton("USER-DEFINED");
        textFileButton = createButton("FROM TXT FILE");
        backButton = createButton("BACK");
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        buttonPanel.add(randomInputButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(userDefinedButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(textFileButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(backButton, gbc);
        
        leftPanel.add(titlePanel);
        leftPanel.add(buttonPanel);
        
        ///////right panel////
        
        rightPanel = new JPanel(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(360, 700));
        rightPanel.setOpaque(false);
        
        logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(350, 200));
        logoPanel.setOpaque(false);
        
        selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        selectionPanel.setPreferredSize(new Dimension(340, 300));
        selectionPanel.setOpaque(false);
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(70, 10, 0,0));
        logoPanel.add(logoLabel);
        
        String[] algorithms = {"First Come First Serve", "Round Robin", "Shortest Job First (Non-preemptive)",
                           "Shortest Job First (Preemptive)", "Priority (Non-preemptive)",
                           "Priority (Preemptive)"};
        
        optionBox = new JComboBox(algorithms);
        optionBox.setFocusable(true);
        optionBox.setFont(archivoblack.deriveFont(17f));
        optionBox.setPreferredSize(new Dimension(340, 30));
        optionBox.setBackground(Color.WHITE);
        optionBox.setForeground(new Color(4, 3, 93));
        optionBox.setBorder(null);
        
        
        
        selectButton = createButton("SELECT METHOD");
        selectButton.setPreferredSize(new Dimension(340,30));
        selectButton.setFont(archivoblack.deriveFont(18f));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(110,0,0,0));
        selectionPanel.add(selectButton);
        
        selectionPanel.add(optionBox);
        continueButton = createButton("CONTINUE");
        continueButton.setEnabled(false);
      
        rightPanel.add(logoPanel);
        rightPanel.add(selectionPanel);
        rightPanel.add(continueButton);
        
        add(leftPanel);
        add(rightPanel);

    }
    
    @Override
    public JButton createButton(String text) {
        archivoblack = importFont("archivoblack");
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(320, 60));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 30));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public void setButtonClicked(JButton b){
        b.setBackground(new Color(4, 3, 93));
    }
    
    public void setButtonUnclicked(JButton b){
        b.setBackground(new Color(118,130,138));
    }
    
}
