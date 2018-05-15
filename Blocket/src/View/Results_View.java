package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
	import java.awt.Image;
	import java.awt.Point;

	import javax.swing.InputMap;
	import javax.swing.JButton;
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
import javax.swing.ImageIcon;

public class Results_View extends JFrame{
		
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
			private ArrayList<Double> topPrices = new ArrayList<Double>();
			
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
			private static final String BACKGROUND = "../File/results5.png";
			
			//Constructor
			public Results_View(){
			
				jpMain = new JPanel(new BorderLayout());
				//Paint Center Panel.
				DrawCenterPanel();
				//Paint South Panel
				DrawSouthPanel();
				
				//Do you really want to close the screen?
				WindowListener exitListener = new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		                int confirm = JOptionPane.showOptionDialog(
		                        null, "Do you really want to close Blocket Price Recommendations app? ",
		                        "Close Blocket Price Recommendations", JOptionPane.YES_NO_OPTION,
		                        JOptionPane.WARNING_MESSAGE, null, null, null);
		                if (confirm == 0) {
		                    System.exit(1);
		                }
		            }
		        };
		        this.addWindowListener(exitListener);
						        
		        //Paint the main background of our screen.
				paintBackground();
				
		        //Main features of our main screen
				this.add(jpMain);
				this.setTitle("Blocket Price recommendations");
		        this.setLayout(new BorderLayout());
		        this.setSize(new Dimension(800,600));
		        this.setResizable(false);
			}
			
			public void DrawSouthPanel() {
				jpSouth = new JPanel(new GridLayout(1,4));
				
				//Next button
				iiBack = new ImageIcon(((new ImageIcon ("BackButton.png").getImage()).getScaledInstance(70,50, Image.SCALE_SMOOTH)));        
			    jbBack = new JButton();
			    jbBack.setPreferredSize(new Dimension(70,50));
			    jbBack.setIcon(iiBack);
			    jbBack.setOpaque(false);
			    jbBack.setContentAreaFilled(false);
			    jbBack.setBorderPainted(false);
			    jbBack.setName("Back");
					
				//3.Add prices
				JTextArea text_highest = new JTextArea(this.Highest);
				JTextArea text_lowest = new JTextArea(this.Lowest);
				JTextArea text_average = new JTextArea(this.Average);
				text_highest.setEditable(false);
				Font f = new Font("Courier", Font.BOLD, 13);
				text_highest.setFont(f);
				text_lowest.setEditable(false);
				text_lowest.setFont(f);
				text_average.setEditable(false);
				text_average.setFont(f);
				text_average.setSize(100,100);
				text_highest.setSize(100,100);
				text_lowest.setSize(100,100);
				
		
				/*JLabel jlEmpty1 = new JLabel();
				JLabel jlEmpty2 = new JLabel();
				JLabel jlEmpty3 = new JLabel();
				jlEmpty3.setBackground(Color.white);
				jlEmpty1.setBackground(Color.white);
				jlEmpty2.setBackground(Color.white);*/
				
			    jpSouth.add(text_highest,0,0);
			    jpSouth.add(text_lowest,0,1);
			    jpSouth.add(text_average,0,2);
			    jpSouth.revalidate();
			    jpSouth.add(jbBack,0,3);
				jpSouth.setBackground(Color.white);
			    jpMain.add(jpSouth,BorderLayout.SOUTH);
			}
			
			public void DrawCenterPanel() {
				
				//1. Painting the Results 				
				jpCenter = new JPanel(new GridLayout(1,1,20,20));

				jpResults = new JPanel(new GridLayout(10,1));
				for(int i = 0; i < 10; i++) {					
					JTextArea text = new JTextArea();
					String aux;
					if (topNames.isEmpty()) {
						aux = " ";
					}else {
						aux = topNames.get(i);
					}
					text.setText(aux);
					Font f = new Font("Courier", Font.BOLD, 13);
					text.setFont(f);
					text.setEditable(false);
					text.setVisible(true);
					//text.setSize(100,100);
					text.setBackground(Color.white);
					jpResults.add(text);
				}
				jpResults.revalidate();
				jpResults.setBackground(Color.white);
				jpCenter.add(jpResults);			
								
				//3.Add prices
				/*prices = new JPanel(new GridLayout(3,1));
				JTextArea text_highest = new JTextArea(this.Highest);
				JTextArea text_lowest = new JTextArea(this.Lowest);
				JTextArea text_average = new JTextArea(this.Average);
				text_highest.setEditable(false);
				Font f = new Font("Courier", Font.BOLD, 13);
				text_highest.setFont(f);
				text_lowest.setEditable(false);
				text_lowest.setFont(f);
				text_average.setEditable(false);
				text_average.setFont(f);
				text_average.setSize(100,100);
				text_highest.setSize(100,100);
				text_lowest.setSize(100,100);
				prices.setSize(400,200);
				prices.add(text_highest);
				prices.add(text_lowest);
				prices.add(text_average);
				prices.revalidate();
				prices.setBackground(Color.white);
				jpCenter.add(prices);*/

							
				jpCenter.setBackground(Color.white);
				jpCenter.revalidate();
				jpMain.add(jpCenter,BorderLayout.CENTER);
			}
			
			
			public void PaintInformation() {
				this.setVisible(false);
				//prices.removeAll();
				jpResults.removeAll();
				jpCenter.removeAll();
				jpSouth.removeAll();
				jpMain.remove(jpSouth);
				jpMain.remove(jpCenter);
				jpMain.revalidate();
				DrawCenterPanel();
				DrawSouthPanel();
				jpMain.revalidate();
				this.revalidate();
				this.repaint();
				this.setVisible(true);
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

			

			public ArrayList<Double> getTopPrices() {
				return topPrices;
			}


			public void setTopPrices(ArrayList<Double> topPrices) {
				this.topPrices = topPrices;
			}


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
