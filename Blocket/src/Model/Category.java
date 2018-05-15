package Model;

import java.util.ArrayList;

public class Category {
	
	private String category_name;
	private ArrayList<Attributes> attributes;
	private ArrayList<String> default_names;
	
	public Category() {
		attributes = new ArrayList<Attributes>();
		default_names = new ArrayList<String>();
	}
	
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	public ArrayList<String> getDefault_names() {
		return default_names;
	}

	public void setDefault_names(ArrayList<String> default_names) {
		this.default_names = default_names;
	}

	public ArrayList<Attributes> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<Attributes> attributes) {
		this.attributes = attributes;
	}
	
	public void addDefault(String def) {
		this.default_names.add(def);
	}
	
	public void addAttribute(Attributes att) {
		this.attributes.add(att);
	}
}
