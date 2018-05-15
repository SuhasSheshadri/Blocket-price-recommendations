package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ElasticSearchData {
	
	//Data that we need to send to the Elastic Search.
	private String Location = " ";
	//Attributes selected //HashMap (Key -> attributes.Name, Value -> Value of the attribute selected)
	private HashMap<String,String> attributes;
	//Category selected
	private String Category = " ";
	//Query selected by the user
	private String Query = " ";
	
	
	//Information to fill based on what the ElasticSearch Search function return
	//Prices information:
	private String Lowest = " ";
	private String Highest = " ";
	private String Average = " ";
	//10 top results:
	private ArrayList<String> topNames;
	private ArrayList<Double> topPrices;
	
	
	public ElasticSearchData() {
		topNames = new ArrayList<String>();
		topPrices = new ArrayList<Double>();
		attributes = new HashMap<String,String>();
	}
	
	public void Search() {
		System.out.println("Implementation of the Search function");
		//This function needs: location, attributes, category and query.
		
	}
		
	public void CreateNames() {
		for (int i = 0; i < 10; i++) {
			String aux = "Title of the Result: "+i+ "djdlfakjshjfhlajksdhljfkahlsdkhfa  ";
			topNames.add(aux);
		}
	}
	
	public void CreatePrices() {
		for(int i =0 ; i < 10;i++) {
			topPrices.add((double)i);
		}
	}

	public void CalculatePrices() {
		Double max = Double.MIN_VALUE;
		Double min = Double.MAX_VALUE;
		Double total = (double) 0;
		for (int i = 0; i < topPrices.size(); i++) {
			if (topPrices.get(i)>max) {
				max = topPrices.get(i);
			}
			if (topPrices.get(i)<min) {
				min = topPrices.get(i);
			}
			total = total + topPrices.get(i);
		}
		total = total/(double)topPrices.size();
		this.Average = "Average:" +total.toString() + " SEK";
		this.Highest = "Highest:" +max.toString() + " SEK";
		this.Lowest = "Lowest:"+ min.toString() + " SEK";
	}
	
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}


	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getQuery() {
		return Query;
	}

	public void setQuery(String query) {
		Query = query;
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


	public ArrayList<String> getTopNames() {
		return topNames;
	}


	public void setTopNames(ArrayList<String> topNames) {
		this.topNames = topNames;
	}


	public ArrayList<Double> getTopPrices() {
		return topPrices;
	}


	public void setTopPrices(ArrayList<Double> topPrices) {
		this.topPrices = topPrices;
	}

	
}
