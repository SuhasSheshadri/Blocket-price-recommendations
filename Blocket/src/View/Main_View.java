package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.ButtonController;

//This class will generate the view of the first screen.
public class Main_View extends JFrame{
	
	//Main panel
	private JPanel jpMain;
	
	//Variable for changing the background.
	private static final String BACKGROUND = "../File/Background_1.png";
	
	//Start button (button, panel and image)
	private JButton jbStart;
	private JPanel jpButton;
    private ImageIcon iiStart;
	 
	//Constructor
	public Main_View(){
		
		//Main JPanel of the screen.
		jpMain = new JPanel(new GridBagLayout());
		jpMain.setPreferredSize(new Dimension(800,600));
        jpMain.setOpaque(false);
	
        //Start button 
        iiStart = new ImageIcon(((new ImageIcon ("Start_Button.png").getImage()).getScaledInstance(130,70, Image.SCALE_SMOOTH)));        
        jbStart = new JButton("Start");
        jbStart.setPreferredSize(new Dimension(130,70));
        jbStart.setIcon(iiStart);
        jbStart.setOpaque(false);
        jbStart.setContentAreaFilled(false);
        jbStart.setBorderPainted(false);
        jbStart.setName("Start");
        jpButton = new JPanel();
        jpButton.setOpaque(false);        
        jpButton.add(jbStart);

        //Place the button in the main screen using GridBagConstraints.
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 100;
        c.gridy = 300;
        jpMain.add(jpButton,c);
        
		
        //Paint the main background of our screen.
		paintBackground();
		
        //Main features of our main screen
		this.add(jpMain);
		this.setTitle("Blocket Price recommendations");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800,600));
        this.setResizable(false);
	}
	
	
	//Function to paint the background with the picture that we want.
	 private void paintBackground(){
		 try {
			 BufferedImage img = ImageIO.read(getClass().getResource(BACKGROUND));
	         ImageIcon icon = new ImageIcon(img);
	         Image image = icon.getImage();
	         Image newImage = image.getScaledInstance(800,600, Image.SCALE_SMOOTH);
	         ImageIcon newIcon = new ImageIcon(newImage);

	         this.setContentPane(new JLabel(newIcon));
	         this.setLayout(new GridBagLayout());
	         GridBagConstraints gbc = new GridBagConstraints();
	         gbc.gridwidth = GridBagConstraints.REMAINDER;
	         this.add(jpMain);
	         this.pack();

	      }catch (IOException e){
	    	  	e.printStackTrace();
	      }
	 }
	 
	 //This function will link the Start button with the main controller.
	 public void registerButtons(ButtonController controller){
		 jbStart.addActionListener(controller); 
	 }
}
