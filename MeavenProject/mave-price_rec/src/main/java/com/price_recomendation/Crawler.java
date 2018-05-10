package com.price_recomendation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Example program to list links from a URL.
 */
public class Crawler {

	private HashMap<Integer, String> catCode2Str = new HashMap<Integer, String>();
	private HashMap<String, Integer> catStr2Code = new HashMap<String, Integer>();
	private ArrayList<String> regions = new ArrayList<String>();

	public Crawler() throws IOException {
		readCategories();
		readRegions();
	}
	
	public List<Ad> crawlPage(String query, String category) throws IOException {
		
		query = query.replaceAll("\\s+", "+");

		String url;
		Document doc;
		Elements imports;
		Element numb_hits;
		int numResults;
		List<Integer> priceList;
		List<String> titleList;
		List<Ad> adList = new ArrayList<Ad>();
		String newUrl;
		//HashMap<String, Integer> categoriesMap; //Not necessary??
		int counter = 0;

		for (String region : regions) {
			url = "https://www.blocket.se/" + region + "?q=" + query + "&cg=" + catStr2Code.get(category);
			newUrl = url;
			
			doc = getDoc(url);
			imports = doc.select("link[href]");
			numb_hits = doc.select("span.num_hits").first();
			numResults = Integer.parseInt(numb_hits.text());
			Elements titles = doc.select("h1[itemprop=name]");

			while(!(titles.size()==0)) {
				//counter++;
				//System.out.println(counter);
				doc = getDoc(url);
				// Elements links = doc.select("a[href]");
				imports = doc.select("link[href]");
				numb_hits = doc.select("span.num_hits").first();
				numResults = Integer.parseInt(numb_hits.text());
				//System.out.println("This is the pagenav " + doc.select("li[itemprop=url]").first());
				
				//Prints
				print("\nImports: (%d)", imports.size());
				for (Element link : imports) {
					//print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
					if(link.attr("rel").equals("next")) {
						newUrl = link.attr("abs:href");
						System.out.println("This is new url " + newUrl);
					}
				}

				if(numResults == 0) {
					System.out.println(" No results");
					break;
				}else {
					System.out.println(numResults + " results for searched query");
					adList = adList(doc, region, adList);
					System.out.println(" Number of ads with prices " + adList.size());
					if(adList.size() == 0) {
						System.out.println(" The result don't have any price labeled");
					}else {
						for(int i = 0; i < adList.size(); i++) {
						
						}
					}
				}
				
				//Next page 
				if(!newUrl.equals(url)) {
					url = newUrl;
				}else {
					url = "";
				}
				titles = doc.select("h1[itemprop=name]");
			}
		}
		
		return adList;
	}
	
	private List<Ad> adList(Document doc, String region, List<Ad> adList){
		Elements titles = doc.select("h1[itemprop=name]");
		Elements prices = doc.select("p[itemprop=price]");
		String title;
		String price;
		for(int i = 0; i < titles.size(); i++) {
			if(!prices.get(i).text().equals("")) {
				title = titles.get(i).text();
				price = prices.get(i).text();
				Ad ad = new Ad(title, price, region);
				adList.add(ad);
			}
		}
		return adList;
	}
	
	private Document getDoc(String url) throws IOException {
		print("Fetching %s...", url);
		Document doc = Jsoup.connect(url).get();
		return doc;
	}

	private List<Integer> priceList(Document doc) {
		Elements prices = doc.select("p[itemprop=price]");
		List<Integer> priceList = new ArrayList<Integer>();
		int price;
		int numAdsPrices = 0;
		for (Element priceElem : prices) {
			if (!priceElem.text().equals("")) {
				price = Integer.parseInt(priceElem.text().split("kr")[0].replaceAll("[^0-9.]", ""));
				System.out.println(price + " from: " + priceElem.text());
				priceList.add(numAdsPrices, price);
				numAdsPrices += 1;
			}
		}
		return priceList;
	}
	
	private List<String> titleList(Document doc){
		Elements titles = doc.select("h1[itemprop=name]");
		List<String> titleList = new ArrayList<String>();
		String title;
		int numAdsTitles = 0;
		for(Element titleElem : titles) {
			if(!titleElem.text().equals("")) {
				title = titleElem.text();
				System.out.println("Title is: " + title);
				titleList.add(numAdsTitles, title);
				numAdsTitles += 1;
			}
		}
		return titleList;
	}
	
	private HashMap<String, Integer> categoriesMap(Document doc) {
		Elements categories = doc.select("option.mls[^value]");
		HashMap<String, Integer> categoriesMap = new HashMap<String, Integer>();
		for (Element cat : categories) {
			System.out.println(Integer.parseInt(cat.attr("value")) + " " + cat.text());
			categoriesMap.put(cat.text(), Integer.parseInt(cat.attr("value")));
		}
		return categoriesMap;
	}
	
	private void readCategories() throws IOException {
		System.out.println("Printing categories");
		String fileName = "src\\main\\resources\\Categories.txt";
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));

		String line = buf.readLine();
		while (line != null) {
			String newLine = line.toString();
			Integer catCode = Integer.parseInt(newLine.split("\\s")[0]);
			String catName = newLine.split("\\s")[1];
			catCode2Str.put(catCode, catName);
			catStr2Code.put(catName, catCode);
			System.out.println(catName + " : " + catCode);
			line = buf.readLine();
		}
		buf.close();
	}

	private void readRegions() throws IOException {
		System.out.println("Printing regions");
		String fileName = "src\\main\\resources\\Regions.txt";
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));

		String line = buf.readLine();
		while (line != null) {
			regions.add(line.toString());
			System.out.println(line.toString());
			line = buf.readLine();
		}
		buf.close();
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
