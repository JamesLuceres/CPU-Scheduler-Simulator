package schedify;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class MainMenu extends JPanel implements ActionListener{
    public CardLayout cardLayout;
    public JPanel cardPanel, outerPanel, titlePanel, buttonPanel;
    public JButton startButton, helpButton, aboutButton, exitButton;
    public JLabel titleLabel;
    public BufferedImage img, logo, bg;
    public Icon logoIcon;
    public Font archivoblack;
    public int inputMode;
    public int algo=0;
    
    public Help helpPanel;
    About aboutPanel = new About();
    StartPage startPanel = new StartPage();
    Simulator simulatorPanel = new Simulator();
    CPUScheduling schedulingPanel = new CPUScheduling(simulatorPanel);
    
    UserInputPagePriority upPanel = new UserInputPagePriority(simulatorPanel); 
    RandomInputPagePriority rpPanel = new RandomInputPagePriority(simulatorPanel); 
    TextInputPagePriority tpPanel = new TextInputPagePriority(simulatorPanel);
    
    RandomInputPageRR rrPanel = new RandomInputPageRR(simulatorPanel);
    UserInputPageRR urPanel = new UserInputPageRR(simulatorPanel);
    TextInputPageRR trPanel = new TextInputPageRR(simulatorPanel);
    
    RandomInputPageDefault rdPanel = new RandomInputPageDefault(simulatorPanel);
    UserInputPageDefault udPanel = new UserInputPageDefault(simulatorPanel); 
    TextInputPageDefault tdPanel = new TextInputPageDefault(simulatorPanel);
    
    public MainMenu (CardLayout cardLayout, JPanel cardPanel){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        
        if (helpPanel == null) {
            helpPanel = new Help();
        }
        
        showComponents();
    }
    
    public void showComponents(){
        importFont();
        setLayout(new GridBagLayout());
        
        schedulingPanel.backButton.addActionListener(this);
        
        outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        outerPanel.setOpaque(false);
        outerPanel.setPreferredSize(new Dimension(975, 700));
        
        titlePanel = new JPanel(new GridBagLayout());
        logo = getImg("/img/logo.png");
        logoIcon = new ImageIcon(logo);
        
        titleLabel = new JLabel();
        titleLabel.setIcon(logoIcon);
        
        GridBagConstraints tgbc = new GridBagConstraints();
        tgbc.gridx = 0;
        tgbc.gridy = 0;
        tgbc.insets = new Insets(90, 0, 0, 0);

        titlePanel.add(titleLabel, tgbc);
        titlePanel.setOpaque(false);
        titlePanel.setPreferredSize(new Dimension (900, 200));
       
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(900, 500));
        
        startButton = createButton("START");
        helpButton = createButton("HELP");
        aboutButton = createButton("ABOUT");
        exitButton = createButton("EXIT");
        
        startButton.addActionListener(this);
        helpButton.addActionListener(this);
        aboutButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0);
        buttonPanel.add(startButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(helpButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(aboutButton, gbc);
        
        gbc.gridy++;
        buttonPanel.add(exitButton, gbc);
        
        outerPanel.add(titlePanel);
        outerPanel.add(buttonPanel);
        
        add(outerPanel);
        
        cardPanel.add(this, "MAIN_MENU");
        cardPanel.add(helpPanel, "HELP");
        cardPanel.add(aboutPanel, "ABOUT");
        cardPanel.add(startPanel, "START");
        cardPanel.add(schedulingPanel, "SCHEDULING");
        
        cardPanel.add(upPanel, "USER_INPUT_PRIORITY");
        cardPanel.add(rpPanel, "RANDOM_INPUT_PRIORITY");
        cardPanel.add(tpPanel, "TEXT_INPUT_PRIORITY");
        
        cardPanel.add(rrPanel, "RANDOM_INPUT_RR");
        cardPanel.add(urPanel, "USER_INPUT_RR");
        cardPanel.add(trPanel, "TEXT_INPUT_RR");
        
        cardPanel.add(rdPanel, "RANDOM_INPUT_DEFAULT");
        cardPanel.add(udPanel, "USER_INPUT_DEFAULT");
        cardPanel.add(tdPanel, "TEXT_INPUT_DEFAULT");
        
        helpPanel.backButton.addActionListener(this);
        aboutPanel.backButton.addActionListener(this);
        startPanel.backButton.addActionListener(this);
        startPanel.continueButton.addActionListener(this);
        startPanel.randomInputButton.addActionListener(this);
        startPanel.userDefinedButton.addActionListener(this);
        startPanel.textFileButton.addActionListener(this);
        startPanel.optionBox.addActionListener(this);
        
        upPanel.backButton.addActionListener(this);
        rpPanel.backButton.addActionListener(this);
        tpPanel.backButton.addActionListener(this);
        
        rrPanel.backButton.addActionListener(this);
        urPanel.backButton.addActionListener(this);
        trPanel.backButton.addActionListener(this);
        
        rdPanel.backButton.addActionListener(this);
        udPanel.backButton.addActionListener(this);
        tdPanel.backButton.addActionListener(this);
        
        upPanel.runButton.addActionListener(this);
        rpPanel.runButton.addActionListener(this);
        tpPanel.runButton.addActionListener(this);
        
        rrPanel.runButton.addActionListener(this);
        urPanel.runButton.addActionListener(this);
        trPanel.runButton.addActionListener(this);
        
        rdPanel.runButton.addActionListener(this);
        udPanel.runButton.addActionListener(this);
        tdPanel.runButton.addActionListener(this);
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(320, 60));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 30));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public BufferedImage getImg(String filePath){
        try{
            img = ImageIO.read(getClass().getResourceAsStream(filePath));
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }
    
    public void importFont(){
        try {
            InputStream i = getClass().getResourceAsStream("/font/archivoblack.ttf");
            try {
                archivoblack = Font.createFont(Font.TRUETYPE_FONT, i);
            } catch (FontFormatException ex) {
                System.out.println("no font");
            }
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(archivoblack);
            
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton){
            System.exit(0);
        }
        else if (e.getSource() == startButton || e.getSource()==upPanel.backButton 
                || e.getSource()==rpPanel.backButton || e.getSource()==tpPanel.backButton
                || e.getSource()==rrPanel.backButton || e.getSource()==rdPanel.backButton
                || e.getSource()==udPanel.backButton || e.getSource()==urPanel.backButton
                || e.getSource()==tdPanel.backButton || e.getSource()==trPanel.backButton){
            cardLayout.show(cardPanel, "START");
            simulatorPanel.clearProcessList();
            rdPanel.clearOutputRow();
            rrPanel.clearOutputRow();
            rpPanel.clearOutputRow();
            udPanel.clearOutputRow();
            urPanel.clearOutputRow();
            upPanel.clearOutputRow();
            tdPanel.clearOutputRow();
            trPanel.clearOutputRow();
            tpPanel.clearOutputRow();
        }
        else if (e.getSource() == helpButton){
            cardLayout.show(cardPanel, "HELP");
        }
        else if (e.getSource() == aboutButton){
            cardLayout.show(cardPanel, "ABOUT");
        }
        else if (e.getSource() == helpPanel.backButton || e.getSource()==aboutPanel.backButton
                || e.getSource()==startPanel.backButton) {
            cardLayout.show(cardPanel, "MAIN_MENU");
        }
        else if (e.getSource() == startPanel.randomInputButton){
            inputMode = 1;
            startPanel.setButtonClicked(startPanel.randomInputButton);
            startPanel.setButtonUnclicked(startPanel.userDefinedButton);
            startPanel.setButtonUnclicked(startPanel.textFileButton);
            startPanel.continueButton.setEnabled(true);
            System.out.println("input mode: " + inputMode);
        }
        else if (e.getSource() == startPanel.userDefinedButton){
            inputMode = 2;
            startPanel.setButtonUnclicked(startPanel.randomInputButton);
            startPanel.setButtonClicked(startPanel.userDefinedButton);
            startPanel.setButtonUnclicked(startPanel.textFileButton);
            startPanel.continueButton.setEnabled(true);
            System.out.println("input mode: " + inputMode);
        }
        else if (e.getSource() == startPanel.textFileButton){
            inputMode = 3;
            startPanel.setButtonUnclicked(startPanel.randomInputButton);
            startPanel.setButtonUnclicked(startPanel.userDefinedButton);
            startPanel.setButtonClicked(startPanel.textFileButton);
            startPanel.continueButton.setEnabled(true);
            System.out.println("input mode: " + inputMode);
        }
        else if (e.getSource() == startPanel.continueButton && inputMode==1){
            switch (algo) {
                case 1: {
                    cardLayout.show(cardPanel, "RANDOM_INPUT_RR");
                    break;
                }
                case 4,5:{
                    cardLayout.show(cardPanel, "RANDOM_INPUT_PRIORITY");
                    break;
                }
                default: {
                    cardLayout.show(cardPanel, "RANDOM_INPUT_DEFAULT");
                    break;
                }
            }            
        }
        else if (e.getSource() == startPanel.continueButton && inputMode==2){
            switch (algo) {
                case 1: {
                    cardLayout.show(cardPanel, "USER_INPUT_RR");
                    break;
                }
                case 4,5:{
                    cardLayout.show(cardPanel, "USER_INPUT_PRIORITY");
                    break;
                }
                default: {
                    cardLayout.show(cardPanel, "USER_INPUT_DEFAULT");
                    break;
                }
            }                        
        }
        else if (e.getSource() == startPanel.continueButton && inputMode==3){
            switch (algo) {
                case 1: {
                    cardLayout.show(cardPanel, "TEXT_INPUT_RR");
                    break;
                }
                case 4,5:{
                    cardLayout.show(cardPanel, "TEXT_INPUT_PRIORITY");
                    break;
                }
                default: {
                    cardLayout.show(cardPanel, "TEXT_INPUT_DEFAULT");
                    break;
                }
            }              
        }
        else if (e.getSource()==startPanel.optionBox){
            algo = startPanel.optionBox.getSelectedIndex();
            System.out.println("Algo: " + algo);
        }
        else if (e.getSource()==upPanel.runButton 
                || e.getSource()==rpPanel.runButton || e.getSource()==tpPanel.runButton
                || e.getSource()==rrPanel.runButton || e.getSource()==rdPanel.runButton
                || e.getSource()==udPanel.runButton || e.getSource()==urPanel.runButton
                || e.getSource()==tdPanel.runButton || e.getSource()==trPanel.runButton){
            schedulingPanel.startSimulation(algo);
            cardLayout.show(cardPanel, "SCHEDULING");
        } else if (e.getSource() == schedulingPanel.backButton) {
            cardLayout.show(cardPanel, "START");
            simulatorPanel.clearProcessList();
            rdPanel.clearOutputRow();
            rrPanel.clearOutputRow();
            rpPanel.clearOutputRow();
            udPanel.clearOutputRow();
            urPanel.clearOutputRow();
            upPanel.clearOutputRow();
            tdPanel.clearOutputRow();
            trPanel.clearOutputRow();
            tpPanel.clearOutputRow();
            schedulingPanel.clearGanttChartPanel();
            schedulingPanel.stopCurrentSimulation();
        }  
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        bg = getImg("/img/background.jpg");
        g.drawImage(bg, 0, 0, 1000, 750, null);
    }
}