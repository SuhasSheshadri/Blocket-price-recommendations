package com.price_recomendation;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for storing Ads before indexing it and each Ad has a JSON representation as a HashMap. 
 * 
 */
public class Ad {
	private String titleName = "title";
	private String attributeName = "attributes";
	private String priceName = "price";
	private String regionName = "region";
	private Map<String, Object> json = new HashMap<String, Object>();

	public Ad(String title, HashMap<String, Object> attributes, int price, String region) {
		json.put(titleName, title);
		json.put(attributeName, attributes);
		json.put(priceName, price);
		json.put(regionName, region);
	}
	
	public Map<String, Object> getJson() {
		return this.json;
	}

	public String getTitle() {
		return (String) this.json.get(titleName);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAttributes() {
		return (Map<String, Object>) this.json.get(attributeName);
	}

	public String getPrice() {
		return (String) this.json.get(priceName);
	}

	public String getRegion() {
		return (String) this.json.get(regionName);
	}
}