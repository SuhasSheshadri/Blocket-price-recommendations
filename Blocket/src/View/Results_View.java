package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
	import java.awt.Image;
	import java.awt.Point;

	import javax.swing.InputMap;
	import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
	import javax.swing.JDialog;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JRootPane;
	import javax.swing.JScrollPane;
	import javax.swing.JTextArea;
	import javax.swing.KeyStroke;
	import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;

import Controller.ButtonController;

	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import java.awt.event.WindowListener;
	import java.awt.image.BufferedImage;
	import java.io.IOException;
	import java.util.ArrayList;

	import javax.imageio.ImageIO;
	import javax.swing.AbstractAction;
	import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

/* 
 * Implementation of the Results View
 */
public class Results_View extends JFrame{
    		
			public JCheckBox[] box = null;
	 		public JPanel resultWindow = new JPanel();
	 		private JScrollPane resultPane = new JScrollPane(resultWindow);
	 		
	 		public JPanel buttonWindow = new JPanel();
	 		private JScrollPane buttonPane = new JScrollPane(buttonWindow);
	 		
	 		private String empty = "   ";
	 		
			//Main panel
			private JPanel jpMain;
			
			//Center and South panel.
			private JPanel jpCenter;
			private JPanel jpSouth;
			
			//Back button variables
			private JButton jbBack;
		    private ImageIcon iiBack;
			
			//Information from the elastic Search
			private ArrayList<String> topNames = new ArrayList<String>();
			private ArrayList<Double> topScores = new ArrayList<Double>();
			private ArrayList<Integer> topPrices = new ArrayList<Integer>();
			
			private JPanel jpResults;
			private JPanel prices;
			private JScrollPane jsp;
			
			//Prices information from the elastic search.
			private String Lowest = " ";
			private String Highest = " ";
			private String Average = " ";
			
			//Top results
			private ArrayList<JTextArea> topResults = new ArrayList<JTextArea>();
			
			//Variable for changing the background.
			private static final String BACKGROUND = "../File/finalresults3.png";
			
			//Constructor
			public Results_View(){	
				topNames.clear();
				topScores.clear();
				//Paint the main screen
				jbBack = new JButton();
				jpMain = new JPanel();
				jpMain.setLayout(new BoxLayout(jpMain, BoxLayout.Y_AXIS));
				resultWindow.setLayout(new BoxLayout(resultWindow, BoxLayout.Y_AXIS));
				resultWindow.setBackground(Color.white);
				resultPane.setLayout(new ScrollPaneLayout());
			    resultPane.setBorder( new EmptyBorder(10,10,10,0) );
			    resultPane.setPreferredSize(new Dimension(400,450));
			    resultPane.setBackground(Color.white);
				jpMain.add(resultPane);
				jpMain.setBackground(Color.white);
		        		
			
				//Paint the main background of our screen.
				paintBackground();
				
		        //Main features of our main screen
				this.add(jpMain);
				this.setSize(800,600);
				this.setTitle("Blocket Price recommendations");
		        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		        this.setResizable(false);
			}
	
			int partition(ArrayList<Double> arr, int low, int high){
		        Double pivot = arr.get(high);
		        int i = (low-1); // index of smaller element
		        for (int j=low; j<high; j++){
		            if (arr.get(j) >= pivot){
		                i++;		 
		                //Scores
		                Double temp = arr.get(i);
		                arr.set(i, arr.get(j));
		                arr.set(j, temp);
		                
		                //Names
		                String name = this.topNames.get(i);
		                this.topNames.set(i, this.topNames.get(j));
		                this.topNames.set(j,name);
		                
		                //Prices
		                Integer price = this.topPrices.get(i);
		                this.topPrices.set(i, this.topPrices.get(j));
		                this.topPrices.set(j, price);
		            }
		        }
		        Double temp = arr.get(i+1);
		        arr.set(i+1, arr.get(high));
		        arr.set(high,temp);
		        
		        //Names
		        String name = this.topNames.get(i+1);
                this.topNames.set(i+1, this.topNames.get(high));
                this.topNames.set(high,name);
		        
                //Prices
                Integer price = this.topPrices.get(i+1);
                this.topPrices.set(i+1, this.topPrices.get(high));
                this.topPrices.set(high, price);
		        
		        return i+1;
		    }
			
			void sort_array(ArrayList<Double> arr, int low, int high){
		        if (low < high) {
		            int pi = partition(arr, low, high);
		            sort_array(arr, low, pi-1);
		            sort_array(arr, pi+1, high);
		        }
		    }
			
			
			public void Sort() {
		        int high = this.topScores.size();
		        int low = 0;   
		        sort_array(this.topScores, low, high-1);
			}
			
			public void CreateFakeResults() {
				topNames.clear();
				topScores.clear();
				for(int i = 0; i < 10; i++) {
					String resultName = "Result title is: " + i;
					topNames.add(resultName);
					topScores.add((double)i);
				}			
				Highest = "Highest price: 80 SEK";
				Lowest = "Lowest price: 10 SEK";
				Average = "Average price: 50 SEK";
			}
			
			public void PaintInformation() {
				resultWindow.removeAll();
		
				
				int maxResultsToDisplay = 18;
			    int i;
			    for (int j = 0; j < 3; j++) {
			    	String description = " ";
			    	JPanel result = new JPanel();
		    		result.setAlignmentX(Component.LEFT_ALIGNMENT);
		    		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
		    		result.setBackground(Color.white);
		    		JLabel label = new JLabel(description);
		    		label.setBackground(Color.white);
		    		result.add(label);
		    		resultWindow.add(result);
		    		resultWindow.setBackground(Color.white);
			    }
			    for ( i=0;  i<maxResultsToDisplay; i++ ) {
			    	if ( i < 10) {
			    		String description = i + ". " + topNames.get(i) + "  		" + String.format("%.2f", topScores.get(i));
			    		JPanel result = new JPanel();
			    		result.setAlignmentX(Component.LEFT_ALIGNMENT);
			    		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
			    		result.setBackground(Color.white);
			    		JLabel label = new JLabel(description);
			    		label.setBackground(Color.white);
			    		result.add(label);
			    		resultWindow.add(result);
			    		resultWindow.setBackground(Color.white);
			    	}else {
			    		String description = " ";
			    		if (i >= 10 && i <=14) {
			    			description = " ";
			    		}else {
			    			if (i == 15) {
			    				description = Highest;
			    			}else {
			    				if ( i == 16) {
			    					description = Lowest;
			    				}else {
			    					if ( i == 17) {
			    						description = Average;
			    					}
			    				}
			    			}
			    		}
			    		JPanel result = new JPanel();
			    		result.setAlignmentX(Component.LEFT_ALIGNMENT);
			    		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
			    		result.setBackground(Color.white);
			    		JLabel label = new JLabel(description);
			    		label.setBackground(Color.white);
			    		result.add(label);
			    		resultWindow.add(result);
			    		resultWindow.setBackground(Color.white);
			    	}
			    }
				//Create the back button
				//HomeButton
				ImageIcon iiBack = new ImageIcon(((new ImageIcon ("BackButton.png").getImage()).getScaledInstance(70,70, Image.SCALE_SMOOTH)));        
				jbBack.setPreferredSize(new Dimension(80,50));
				jbBack.setIcon(iiBack);
				jbBack.setOpaque(false);
				jbBack.setContentAreaFilled(false);
				jbBack.setBorderPainted(false);        
				jbBack.setName("Back");
				
				JPanel result = new JPanel();
				result.setAlignmentX(Component.RIGHT_ALIGNMENT);
				result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
	    		result.setBackground(Color.white);
	    		result.add(jbBack);
	    		resultWindow.add(result);
	    		resultWindow.setBackground(Color.white);
			
			    revalidate();
		        repaint();
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

			 

			public ArrayList<String> getTopNames() {
				return topNames;
			}

			

			public ArrayList<Integer> getTopPrices() {
				return topPrices;
			}


			public void setTopPrices(ArrayList<Integer> topPrices) {
				this.topPrices = topPrices;
			}

			public void setTopScores(ArrayList<Double> topScores) {
		this.topScores = topScores;
	}

			public ArrayList<Double> getTopScores() {return this.topScores;}
			public void setTopNames(ArrayList<String> topNames) {
				this.topNames = topNames;
			}


			public String getLowest() {
				return Lowest;
			}


			public void setLowest(String lowest) {
				Lowest = lowest;
			}


			public String getHighest() {
				return Highest;
			}


			public void setHighest(String highest) {
				Highest = highest;
			}


			public String getAverage() {
				return Average;
			}


			public void setAverage(String average) {
				Average = average;
			}
			
			 public void registerButtons(ButtonController controller){
				 jbBack.addActionListener(controller);
			 }

}
