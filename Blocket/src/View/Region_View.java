package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Controller.ButtonController;

public class Region_View extends JFrame implements ActionListener{

	//Main Panel of the screen.
	private JPanel jpMain;
	
	//Actual place in the map to show:
	private String Place = "Stockholm.png";
	private String Location = "AllSweden";
	
	//Variable for changing the background.
	private static final String BACKGROUND = "../File/RegionBackground2.png";
	
	//The panel will be divided into two different panels:Center and South.
	private JPanel jpSouth;
	private JPanel jpCenter;
	
	//Panel for the buttons group.
	private JPanel jpButtons;
	private JPanel jpButtons2;
	
	//Panel for the map image
	private JPanel jpMap;
	private ImageIcon iiMap;
	
	//Next button variables
	private JButton jbNext;
    private ImageIcon iiNext;
	
	public Region_View(){
		
		//Main panel
		jpMain = new JPanel(new BorderLayout());
		
		//Paint the central panel (radio buttons group + image )
		DrawCentralPanel();
		DrawSouthPanel();
		
        //Add panels to the main panel
		jpMain.add(jpCenter, BorderLayout.CENTER);
		jpMain.add(jpSouth, BorderLayout.PAGE_END);
									
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
	
	//Place on the map that we need to show.
	public String getPlace() {
		return Place;
	}

	public void setPlace(String place) {
		Place = place;
	}
	
	//When a button is clicked...
	public void actionPerformed(ActionEvent e) {
		 String new_place = e.getActionCommand().toString();
		 this.setPlace(new_place);
		 //Paint again the map.
		 jpMap.removeAll();
		 jpMain.revalidate(); 
		 jpMain.repaint();
		 PaintMap();
		 System.out.print("Revalidate");
		 jpMain.revalidate(); 
		 jpMain.repaint();
	 }
	
	 public void registerButtons(ButtonController controller){
		 jbNext.addActionListener(controller);
	 }
	 
	 public void DrawSouthPanel() {
		//South panel: Just a button on the left side.
		jpSouth = new JPanel(new GridLayout(1,4));
		jpSouth.setBackground(Color.WHITE);
		
		//2 Empty panels + button
		JLabel jlEmpty = new JLabel();
		JLabel jlEmpty2 = new JLabel();
		JLabel jlEmpty3 = new JLabel();
		jlEmpty3.setBackground(Color.white);
		jlEmpty.setBackground(Color.white);
		jlEmpty2.setBackground(Color.white);
			
		//Next button
		iiNext = new ImageIcon(((new ImageIcon ("NextButton.png").getImage()).getScaledInstance(70,50, Image.SCALE_SMOOTH)));        
	    jbNext = new JButton();
	    jbNext.setPreferredSize(new Dimension(70,50));
	    jbNext.setIcon(iiNext);
	    jbNext.setOpaque(false);
	    jbNext.setContentAreaFilled(false);
	    jbNext.setBorderPainted(false);
	    jbNext.setName("Next");
	    jpSouth.add(jlEmpty,0,0);
	    jpSouth.add(jlEmpty2,0,1);
	    jpSouth.add(jlEmpty3,0,2);
	    jpSouth.add(jbNext,0,3);
	 }
	 
	 
	 public void PaintMap() {
		//Image JpMap
	    jpMap = new JPanel(new BorderLayout());        
	    iiMap = new ImageIcon(((new ImageIcon (Place).getImage()).getScaledInstance(175,400, Image.SCALE_SMOOTH)));        
	    JLabel label = new JLabel(iiMap, JLabel.CENTER);
	    jpMap.add(label, BorderLayout.CENTER);
	    jpMap.setBackground(Color.WHITE);
	    
	    //remove jpCenter.
	    jpCenter.removeAll();
		jpCenter.add(jpButtons);
		jpCenter.add(jpButtons2);
		//System.out.println("The new string is: "+ Place);
		jpCenter.add(jpMap);
		jpMain.add(jpCenter, BorderLayout.CENTER);
	 }
		 
	 public void DrawCentralPanel() {
		 
		 	//1 row, 4 columns (empty panel + buttons group 1 + buttons group 2 + map image)
			jpCenter = new JPanel(new GridLayout(1,4));
			
			//Empty Panel
			JPanel jpEmpty = new JPanel();
			jpEmpty.setOpaque(false);
					
			//Buttons group 1 (2nd column of the main Central panel)
			//The action command functions are used to change the image.
			//JRadioButton option1 = new JRadioButton("Norrbotten");
			//option1.setActionCommand("Norbotten.png"); 
	        JRadioButton option2 = new JRadioButton("Västerbotten");
			option2.setActionCommand("Vasterbotten.png");
	        JRadioButton option3 = new JRadioButton("Jämtland");
			option3.setActionCommand("Jamtland.png");
	        JRadioButton option4 = new JRadioButton("Västernorrland");
			option4.setActionCommand("Vasternorrland.png");
	        JRadioButton option5 = new JRadioButton("Gävleborg");
	        option5.setActionCommand("Gavleborg.png");
	        JRadioButton option6 = new JRadioButton("Dalarna");
	        option6.setActionCommand("Dalarna.png");
	        JRadioButton option7 = new JRadioButton("Värmland");
	        option7.setActionCommand("Vasternorrland.png");
	        JRadioButton option8 = new JRadioButton("Örebro");
	        option8.setActionCommand("Orebro.png");
	        JRadioButton option9 = new JRadioButton("Västmanland");
	        option9.setActionCommand("Vastmanland.png");
	        JRadioButton option10 = new JRadioButton("Uppsala");
	        option10.setActionCommand("Uppsala.png");
	        JRadioButton option11 = new JRadioButton("Stockholm");
	        option11.setSelected(true);
	        option11.setActionCommand("Stockholm.png");
	        JRadioButton option12 = new JRadioButton("Södermanland");
	        option12.setActionCommand("Sodermanland.png");
	        JRadioButton option13 = new JRadioButton("Skaraborg");
	        option13.setActionCommand("Skaraborg.png");
	        
	        
	    	//Creating a panel for buttons group 1
			jpButtons = new JPanel(new GridLayout(13,1));
			//jpButtons.add(option1);
			jpButtons.add(option2);
			jpButtons.add(option3);
			jpButtons.add(option4);
			jpButtons.add(option5);
			jpButtons.add(option6);
			jpButtons.add(option7);
			jpButtons.add(option8);
			jpButtons.add(option9);
			jpButtons.add(option10);
			jpButtons.add(option11);
			jpButtons.add(option12);
			jpButtons.add(option13);
			jpButtons.setBackground(Color.white);
			
			//Buttons group 2 (3rd column of the main Central panel)     
	        JRadioButton option14 = new JRadioButton("Östergötland");
	        option14.setActionCommand("Ostergotland.png");
	        JRadioButton option15 = new JRadioButton("Göteborg");
	        option15.setActionCommand("Goteborg.png");
	        JRadioButton option16 = new JRadioButton("Älvsborg");
	        option16.setActionCommand("Alsvborg.png");
	        JRadioButton option17 = new JRadioButton("Jönköping");
	        option17.setActionCommand("Jonkoping.png");
	        JRadioButton option18 = new JRadioButton("Kalmar");
	        option18.setActionCommand("Kalmar.png");
	        JRadioButton option19 = new JRadioButton("Gotland");
	        option19.setActionCommand("Gotland.png");
	        JRadioButton option20 = new JRadioButton("Halland");
	        option20.setActionCommand("Halland.png");
	        JRadioButton option21 = new JRadioButton("Kronoberg");
	        option21.setActionCommand("Kronoberg.png");
	        JRadioButton option22 = new JRadioButton("Blekinge");
	        option22.setActionCommand("Blekinge.png");	        
	        //JRadioButton option23 = new JRadioButton("Skåne");
	        //option23.setActionCommand("Skane.png");	        
	        JRadioButton option24 = new JRadioButton("All Sweden");
	        //All Sweden radio button selected by default
	        option24.setActionCommand("AllSweden.png");
	        
	         //Set ActionListener for all the radio buttons.
	        //option1.addActionListener(this);
	        option2.addActionListener(this);
	        option3.addActionListener(this);
	        option4.addActionListener(this);
	        option5.addActionListener(this);
	        option6.addActionListener(this);
			option7.addActionListener(this);
			option8.addActionListener(this);
			option9.addActionListener(this);
			option10.addActionListener(this);
			option11.addActionListener(this);
			option12.addActionListener(this);
			option13.addActionListener(this);
			option14.addActionListener(this);
			option15.addActionListener(this);
			option16.addActionListener(this);
			option17.addActionListener(this);
			option18.addActionListener(this);
			option19.addActionListener(this);
			option20.addActionListener(this);
			option21.addActionListener(this);
			option22.addActionListener(this);
			//option23.addActionListener(this);
			option24.addActionListener(this);
			
			
	    	//Panel for buttons group 2
			jpButtons2 = new JPanel(new GridLayout(11,1));
			jpButtons2.add(option14);
			jpButtons2.add(option15);
			jpButtons2.add(option16);
			jpButtons2.add(option17);
			jpButtons2.add(option18);
			jpButtons2.add(option19);
			jpButtons2.add(option20);
			jpButtons2.add(option21);
			jpButtons2.add(option22);
			//jpButtons2.add(option23);
			jpButtons2.add(option24);
			jpButtons2.setBackground(Color.white);

			
			//RadioButton group: (avoid multiple selections)
	        ButtonGroup group = new ButtonGroup();
	        //group.add(option1);
	        group.add(option2);
	        group.add(option3);
	        group.add(option4);
	        group.add(option5);
	        group.add(option6);
	        group.add(option7);
	        group.add(option8);
	        group.add(option9);
	        group.add(option10);
	        group.add(option11);
	        group.add(option12);
	        group.add(option13);
	        group.add(option14);
	        group.add(option15);
	        group.add(option16);
	        group.add(option17);
	        group.add(option18);
	        group.add(option19);
	        group.add(option20);
	        group.add(option21);
	        group.add(option22);
	        //group.add(option23);
	        group.add(option24);
	         
	        PaintMap();
	        
			//Add to specific localizations.
			jpCenter.add(jpButtons);
			jpCenter.add(jpButtons2);
			jpCenter.add(jpMap);
	 }
}
