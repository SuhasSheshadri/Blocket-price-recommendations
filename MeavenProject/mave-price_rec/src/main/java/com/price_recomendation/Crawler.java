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
	
	public void crawlPage(String query, String category) throws IOException {
		
		query = query.replaceAll("\\s+", "+");

		String url;
		Document doc;
		Elements imports;
		Element numb_hits;
		int numResults;
		List<Integer> priceList;
		List<String> titleList;
		//HashMap<String, Integer> categoriesMap; //Not necessary??
		for (String listRegion : regions) {
			url = "https://www.blocket.se/" + listRegion + "?q=" + query + "&cg=" + catStr2Code.get(category);
			doc = getDoc(url);
			// Elements links = doc.select("a[href]");
			imports = doc.select("link[href]");
			numb_hits = doc.select("span.num_hits").first();
			numResults = Integer.parseInt(numb_hits.text());
			
			//Prints
			print("\nImports: (%d)", imports.size());
			for (Element link : imports) {
				print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
			}

			if(numResults == 0) {
				System.out.println(" No results");
			}else {
				System.out.println(numResults + " results for searched query");
				priceList = priceList(doc);
				System.out.println(" Number of ads with prices " + priceList.size());
				if(priceList.size() == 0) {
					System.out.println(" The result don't have any price labeled");
				}else {
					for(int i = 0; i < priceList.size(); i++) {
						
					}
					//categoriesMap = categoriesMap(doc);
				}
			}
		}
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
	
	public void readCategories() throws IOException {
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

	public void readRegions() throws IOException {
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
