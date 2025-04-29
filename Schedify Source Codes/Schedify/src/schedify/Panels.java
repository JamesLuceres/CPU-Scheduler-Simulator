package schedify;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Panels extends JPanel{
    public BufferedImage bg, img, logo;
    public Icon logoIcon;
    public Font archivoblack, archivonarrow, font;
    
    public Panels(){
        showUIComponents();
    }
    
    public void showUIComponents(){
        
    }
    
    public JButton createButton(String text) {
        archivoblack = importFont("archivoblack");
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 10));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 30));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public Font importFont(String filePath){
        try {
            InputStream i = getClass().getResourceAsStream("/font/" + filePath + ".ttf");
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, i);
            } catch (FontFormatException ex) {
                System.out.println("no font");
            }
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        return font;
    }
    
    
    public BufferedImage getImg(String filePath){
        try{
            img = ImageIO.read(getClass().getResourceAsStream(filePath));
        }catch(Exception e){
            e.printStackTrace();
        }
        return img;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        bg = getImg("/img/background.jpg");
        g.drawImage(bg, 0, 0, 1000, 750, null); 
    }
}
