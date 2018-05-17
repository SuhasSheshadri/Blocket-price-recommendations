package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import Model.ElasticSearchData;
import View.Main_View;
import View.Region_View;
import View.Results_View;
import View.Search_View;


public class ButtonController implements ActionListener{

	private Main_View mainview;
	private Region_View regionview;
	private Search_View searchview;
	private ElasticSearchData data_elastic;
	private Results_View results;
	
	public ButtonController(Main_View mainview, Region_View regionview, Search_View searchview, ElasticSearchData data_elastic,Results_View results) {
		this.mainview = mainview;
		this.regionview = regionview;
		this.searchview = searchview;
		this.data_elastic = data_elastic;
		this.results = results;
	}

	//ActionPerformed by the buttons
	public void actionPerformed(ActionEvent event){
		AbstractButton jBoto = (AbstractButton) event.getSource();

		if (jBoto.getName().equals("Start")) {
			mainview.setVisible(false);
			regionview.setVisible(true);
		}else {
			//Next button (Region screen -> Search screen)
			if (jBoto.getName().equals("Next")) {
				//Place :)
				String place_aux = regionview.getPlace();
				String[] parts = place_aux.split(".png");				
				data_elastic.setLocation(parts[0].toLowerCase());
				regionview.setVisible(false);
				searchview.setVisible(true);
			}else {
				if (jBoto.getName().equals("Search")) {
					//Attributes stored in the HashMap (default value, value selected by the user)
					data_elastic.setAttributes(searchview.getSelectedAttributes());
					data_elastic.setCategory(searchview.getSelectedCategory());
					data_elastic.setQuery(searchview.getSelectedQuery());
					data_elastic.setTotal_num_attributes(searchview.getNum_attributes());
					try {
						data_elastic.Search();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					data_elastic.Score();
					//Update information to print in the results
					results.setTopNames(data_elastic.getTopNames());
					results.setTopScores(data_elastic.getTopScores());
					results.setTopPrices(data_elastic.getTopPrices());
					results.Sort();
					results.setAverage(data_elastic.getAverage());
					results.setHighest(data_elastic.getHighest());
					results.setLowest(data_elastic.getLowest());
					searchview.setVisible(false);
					results.PaintInformation();
					results.setVisible(true);
				}else {
					if(jBoto.getName().equals("Back")) {
						results.setVisible(false);
						searchview.setVisible(true);
					}else {
						if (jBoto.getName().equals("Home")) {
							searchview.setVisible(false);
							regionview.setVisible(true);
						}
					}
				}
			}
		}
	}
}
