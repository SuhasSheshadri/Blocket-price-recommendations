package com.price_recomendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		//Crawler crawler = new Crawler();
		String category = "Bilar";
		String query = "";
		List<Ad> adList;
		ElasticSearch elastic = new ElasticSearch();
		//adList = crawler.crawlPage(query, category, elastic);
		
		//System.out.println("Number of ads saved in list: " + adList.size());
		//elastic.deleteIndex();
		//elastic.indexAds(adList, category);
		System.out.println("done");
		List<String> list = new ArrayList<String>();
		list.add("Hej");
		elastic.searchElastic("", list, "", "");
	}

}
