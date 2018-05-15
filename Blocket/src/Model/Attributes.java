package Model;

import java.util.ArrayList;

public class Attributes {
	
	//Could be : radiobutton or list
	private String type;
	private ArrayList<String> values;
	
	//Properties of attribute
	public Attributes(String type) {
		this.type = type;
		this.values  = new ArrayList<String>();
	}
	
	//Add values to the Attribute:
	public void insert(String value){
		this.values.add(value);
	}
	
	//Size:
	public int SizeValue(){
		return values.size();
	}
	
	//Getters and setters
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
	
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

}
