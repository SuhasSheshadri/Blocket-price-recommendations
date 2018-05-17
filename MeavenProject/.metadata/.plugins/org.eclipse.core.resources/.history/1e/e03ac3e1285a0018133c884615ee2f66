package com.price_recomendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {

		Crawler crawler = new Crawler();
		String query = "";
		List<Ad> adList;
		ElasticSearch elastic = new ElasticSearch();
		adList = crawler.crawlPage(query, elastic);
		
		elastic.closeClient();
		System.out.println("done");
	}
}
