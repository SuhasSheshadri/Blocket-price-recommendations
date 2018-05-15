package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigData {
	
	private ArrayList<Category> config_data;
	
	//Init the configuration data (reading from the file)
	public ConfigData(){
		config_data = new ArrayList<Category>();
		ReadFile();
	}
	
	//Read configuration file and extract the information regarding the categories and the attributes
	public void ReadFile(){
		File file = new File("config.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			//To identify if its a category name
			CharSequence hash = "#";
			String cat_name = " ";
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.contains(hash)) {
					//It is a new category:
					Category cat = new Category();
					String[] parts = line.split("#");
					cat_name = parts[1];
					//System.out.println(cat_name);
					cat.setCategory_name(cat_name);
					config_data.add(cat);
				}else {
					String[] parts = line.split(",");
					String type = parts[0];
					//Its a list, we must save the default name.
					if (type.equals("list")) {
						config_data.get(config_data.size()-1).addDefault(parts[1]);
					}
					Attributes att = new Attributes(type);
					for (int i = 1; i < parts.length; i++) {
						att.insert(parts[i]);
					}
					//System.out.println("Config size:" +config_data.size());
					//Now we have the attribute that we should insert. 
					config_data.get(config_data.size()-1).addAttribute(att);
				}			
			}
			//PrintDefault();
			//PrintDataInfo();
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PrintDefault() {
		for(int i = 0; i < config_data.size(); i++) {
			System.out.println("Category name: " + config_data.get(i).getCategory_name());
			for(int j = 0; j < config_data.get(i).getDefault_names().size(); j++) {
				System.out.println("Default: "+ config_data.get(i).getDefault_names().get(j));
			}
		}
	}
	
	public void PrintDataInfo() {
		for(int i = 0; i < config_data.size(); i++) {
			System.out.println("Category name: " + config_data.get(i).getCategory_name());
			System.out.println("Attributes size:" + config_data.get(i).getAttributes().size());
			for(int j = 0; j < config_data.get(i).getAttributes().size(); j++) {
				System.out.println("Attribute number: "+j);
				System.out.println("Type: " + config_data.get(i).getAttributes().get(j).getType());
				for (int k = 0; k < config_data.get(i).getAttributes().get(j).getValues().size(); k++) {
					System.out.println(config_data.get(i).getAttributes().get(j).getValues().get(k));
				}
			}
		}
	}
	
	public ArrayList<Category> getConfig_data() {
		return config_data;
	}

	public void setConfig_data(ArrayList<Category> config_data) {
		this.config_data = config_data;
	}
	
}
