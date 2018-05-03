public class Ad{
	private String title;
	private String[] attributes;
	private String price;
	private String url;
	private String region;
	//String text;
	
	public Ad(String title, String[] attributes, String price, String url, String region){//String text){
		this.title = title;
		this.attributes = attributes;
		this.price = price;
		this.url = url;
		this.region = region;
		//this.text = text;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String[] getAttributes(){
		return attributes;
	}
	
	public String getPrice(){
		return price;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getRegion(){
		return region;
	}
	
	/*
	public String getText(){
		return text;
	}*/
}