package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Controller.ButtonController;
import Model.Attributes;
import Model.ConfigData;

public class Search_View extends JFrame implements ActionListener{
	
	//Main panel for the Search screen.
	private JPanel jpMain;
	
	
	//Attributes to send in the HashMap
	private HashMap<String,Object> attributes_final;
	
	private int num_attributes;
	
	//Background
	private static final String BACKGROUND = "../File/TRY3.png";
	
	//The main panel will be based on three main panels: North, Center and South.
	private JPanel jpNorth;
	private JPanel jpCenter;
	private JPanel jpSouth;
	
	//To update the attributes Layout
	private JPanel panel_att;
	private JPanel button_radio;
	private JPanel attributes_panel;
	private JScrollPane attributes_scroll;
	
	//Button
	private JButton jbSearch;
	private JButton jbHome;
	
	//Category selected (this variable must change depending of the category selected)
	private int index_category = 0;
	private String current_category = " ";
	
	//Category button
	private JComboBox category_button;
	
	//Text area for the query space.
	private JTextArea textArea;
	
	//Query written by the user
	private String current_query = " ";
	private ConfigData data;
	
	//ArrayList for the radiobuttons and the JComboBox
	private ArrayList<JRadioButton> radiobuttons = new ArrayList<JRadioButton>();
	private ArrayList<JComboBox> combobuttons = new ArrayList<JComboBox>();
	
	
	//Selected attributes
	private ArrayList<String> selected_att = new ArrayList<String>();
	
	public Search_View(ConfigData data_config) {
		
		attributes_final = new HashMap<String,Object>();
		
		//Update global variable (ConfigData)
		data = data_config;
		
		//Main Panel
		jpMain = new JPanel(new BorderLayout());
	
		//North Panel 
		DrawNorthPanel(data_config);
		
		//Center Panel
		DrawCenterPanel(data_config);
		
		//South Panel
		DrawSouthPanel();
        
		//Add the three panels:
		jpNorth.setBackground(Color.white);
		jpSouth.setBackground(Color.white);
		
		//Adding the three main panels to the main panel.
		jpMain.add(jpNorth, BorderLayout.PAGE_START);
		jpMain.add(jpSouth, BorderLayout.PAGE_END);
		jpMain.setOpaque(false);
								
		//Paint the main background of our screen.
	    paintBackground();				
		//Main features of our main screen
	    this.add(jpMain);
	    this.setTitle("Blocket Price recommendations");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
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
	
	public void DrawSouthPanel() {	
		//Empty panel + empty panel + search button
		jpSouth = new JPanel(new GridLayout(1,3));
		JLabel jlEmpty = new JLabel();
		jlEmpty.setBackground(Color.white);
		JLabel jlEmpty2 = new JLabel();
		jlEmpty2.setBackground(Color.white);

		//HomeButton
		ImageIcon iiHome = new ImageIcon(((new ImageIcon ("home2.png").getImage()).getScaledInstance(70,70, Image.SCALE_SMOOTH)));        
		jbHome = new JButton();
		jbHome.setPreferredSize(new Dimension(70,70));
		jbHome.setIcon(iiHome);
		jbHome.setOpaque(false);
		jbHome.setContentAreaFilled(false);
		jbHome.setBorderPainted(false);        
		JPanel jpButtonhome = new JPanel();
		jpButtonhome.setOpaque(false);        
		jpButtonhome.add(jbHome);
		jbHome.setName("Home");
		jpButtonhome.setBackground(Color.white);
		

		//Search button		
		ImageIcon iiSearch = new ImageIcon(((new ImageIcon ("Search_Button.png").getImage()).getScaledInstance(130,70, Image.SCALE_SMOOTH)));        
		jbSearch = new JButton();
		jbSearch.setPreferredSize(new Dimension(130,70));
		jbSearch.setIcon(iiSearch);
		jbSearch.setOpaque(false);
		jbSearch.setContentAreaFilled(false);
		jbSearch.setBorderPainted(false);        
		JPanel jpButton = new JPanel();
		jpButton.setOpaque(false);        
		jpButton.add(jbSearch);
		jpButton.setBackground(Color.white);
		jpSouth.add(jpButtonhome);
		jpSouth.add(jlEmpty2);
		jpSouth.add(jpButton);
		jbSearch.setName("Search");
	}
	
	//The North Panel of this screen will be the text area for the query + category selection.
	public void DrawNorthPanel(ConfigData data_config) {
		
		//North panel
		jpNorth = new JPanel(new GridLayout(2,2,20,20));
		
		//ComboBox button (Category)
		int list_size = data_config.getConfig_data().size();
		String [] string_category = new String[list_size];
		for(int i = 0; i < list_size; i++) {
			string_category[i] = data_config.getConfig_data().get(i).getCategory_name();
		}
		category_button = new JComboBox(string_category);
		category_button.setSelectedIndex(0);
		category_button.addActionListener(this);
		JPanel category = new JPanel(new BorderLayout());
		category.add(category_button, BorderLayout.CENTER);
		category.setBackground(Color.white);
		
		//Text area (Query)
		textArea = new JTextArea(3, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		scrollPane.setSize(50,50);
		textArea.setEditable(true);
		jpNorth.add(scrollPane);
		jpNorth.add(category);
		JLabel jlempty = new JLabel();
		JLabel jlempty2 = new JLabel();
		jpNorth.add(jlempty);
		jpNorth.add(jlempty2);
		jpNorth.setSize(50,50);
	}
	
	public void DrawCenterPanel(ConfigData data_config) {
		
		radiobuttons.clear();
		combobuttons.clear();
		//JPanel for the center:
		jpCenter = new JPanel(new BorderLayout());
	
		//Attributes data (based on Category)
		int size_att = data_config.getConfig_data().get(index_category).getAttributes().size();
		
		//System.out.println("Size att the index position: " + size_att);
		int columns = 3;
		float division = size_att/((float)columns);
		//System.out.println("Division value: " + division);
		int rows = (int) Math.ceil(division);
		if (rows <= 0) {
			rows = 1;
		}
		
		//System.out.println("Number of columns: " + columns);
		//System.out.println("Number of rows: " + rows);
		
		//Attributes panel (dynamic data)
		GridLayout gridy = new GridLayout(rows,columns,20,20);
		panel_att = new JPanel(gridy);
		panel_att.setSize(100,100);
		for (int i = 0; i < size_att; i++) {
			Attributes aux = data_config.getConfig_data().get(index_category).getAttributes().get(i);
			//RadioButton Type
			if (aux.getType().equals("radio")) {
				ArrayList<String> values = aux.getValues();
				JRadioButton aux_radio = new JRadioButton(values.get(0));
				radiobuttons.add(aux_radio);	
				panel_att.add(radiobuttons.get(radiobuttons.size()-1));
			}else {
				if (aux.getType().equals("list")) {
					ArrayList<String> values = aux.getValues();
					String [] string_attributes = new String[values.size()];
					for (int j = 0; j < values.size(); j++) {
						string_attributes[j] = values.get(j);
					}
					JComboBox aux_combo = new JComboBox(string_attributes);
					combobuttons.add(aux_combo);
					combobuttons.get(combobuttons.size()-1).setSelectedIndex(0);
					panel_att.add(combobuttons.get(combobuttons.size()-1));
				}
			}		
		}
		if (size_att < columns) {
			//System.out.println("hey");
			int howEmpty = columns-size_att;
			for(int i = 0; i < howEmpty; i++) {
				JLabel jlEmpty = new JLabel();
				jlEmpty.setBackground(Color.white);
				jlEmpty.setSize(10,10);;
				panel_att.add(jlEmpty);
			}
		}
		
		//System.out.println("Painting the panel attributes");
		panel_att.setBackground(Color.white);
		panel_att.revalidate();
		panel_att.repaint();
	    attributes_scroll = new JScrollPane(panel_att);
	    attributes_scroll.setSize(100,100);
	    attributes_scroll.revalidate();
	    attributes_scroll.repaint();
		jpCenter.add(attributes_scroll,BorderLayout.CENTER);
		jpCenter.setBackground(Color.white);
		jpCenter.revalidate();
		jpCenter.repaint();
		jpCenter.setVisible(true);
		jpMain.add(jpCenter, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		JComboBox combo = (JComboBox)e.getSource();
        Integer currentCategoryIndex = (Integer)combo.getSelectedIndex();
        String auxCategory = (String)combo.getSelectedItem();
       // System.out.println("The current category is: " + currentCategoryIndex);
        this.index_category = currentCategoryIndex;
        this.current_category = auxCategory;
        //Change the Layout.
        attributes_scroll.removeAll();
        panel_att.removeAll();
        jpCenter.removeAll();
        jpMain.revalidate();
        DrawCenterPanel(this.data);
        jpMain.revalidate();
        this.revalidate();
	}
	
	
	//Return the category selected by the user:
	public String getSelectedCategory() {
		return this.current_category;
	}
	
	public String getSelectedQuery() {
		String query = textArea.getText();
		//System.out.println("The query choosen by the user is: " + query);
		this.current_query = query;
		return this.current_query;
	}
	
	public HashMap<String,Object> getSelectedAttributes(){
		//Get the selected attributes as a list:
		selected_att.clear();
		attributes_final.clear();
		
		//Index of the category
		int index = this.index_category;
		int index_radio = 0;
		int index_combo = 0;
		
		num_attributes = 0;
		//Find in the data:
		ArrayList<Attributes> att = this.data.getConfig_data().get(index).getAttributes();
		for(int i = 0; i < att.size(); i++) {
			//System.out.println(att.get(i).getType());
			if (att.get(i).getType().equals("radio")) {
				JRadioButton aux = radiobuttons.get(index_radio);
				if (aux.isSelected()) {
					num_attributes++;
					selected_att.add(aux.getText());
				}
				index_radio ++;
			}else{
				if (att.get(i).getType().equals("list")){
					JComboBox aux_combo = combobuttons.get(index_combo);
					String element = String.valueOf(aux_combo.getSelectedItem());
					//To check if its a default name.
					if (!this.data.getConfig_data().get(index).getDefault_names().contains(element)) {
						//Now we need to store the default name as a key and the selected index as a value.
						String value = element;
						String key = "attributes."+this.data.getConfig_data().get(index).getDefault_names().get(i);
						System.out.println("The key value is:" + key);
						System.out.println("The value of the element is :" + value);
						//Store the attributes name + the value.
						attributes_final.put(key, value);
						selected_att.add(element);	
						num_attributes++;
					}
					index_combo++;	
				}
			}
		}
		return this.attributes_final;
		//return this.selected_att;
	}
	
	public void registerButtons(ButtonController controller){
		 jbSearch.addActionListener(controller);
		 jbHome.addActionListener(controller);
	}
	

	public int getNum_attributes() {
		return num_attributes;
	}

	public void setNum_attributes(int num_attributes) {
		this.num_attributes = num_attributes;
	}
}
