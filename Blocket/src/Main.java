import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;


import Controller.ButtonController;
import Model.Attributes;
import Model.ConfigData;
import Model.ElasticSearchData;
import View.Main_View;
import View.Region_View;
import View.Results_View;
import View.Search_View;

public class Main {
	
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
 
            	    //ElasticSearch Structure (where we are going to store the data)
            		ElasticSearchData data_elastic = new ElasticSearchData();
            		Main_View main_view = new Main_View();
            		Region_View region_view = new Region_View();
            		Results_View results_view = new Results_View();
            		
            		//Generate configuration data:
            		ConfigData data_config = new ConfigData();
            			
            		//Search screen needs the configuration file.
            		Search_View search_view =  new Search_View(data_config);
            		
            		//Link the buttons controllers.
            		ButtonController controller = new ButtonController(main_view,region_view, search_view,data_elastic,results_view);
            		main_view.registerButtons(controller);
            		region_view.registerButtons(controller);
            		search_view.registerButtons(controller);
            		results_view.registerButtons(controller);
            		
            		//Main view (to begin with the main screen)
            		main_view.setVisible(true);
            }
        });
	}	
}
