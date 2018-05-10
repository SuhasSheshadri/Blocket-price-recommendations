package com.price_recomendation;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		Crawler crawler = new Crawler();
		String category = "Bilar";
		String query = "Volvo xc90";
		List<Ad> adList;
		adList = crawler.crawlPage(query, category);
		System.out.println("Number of ads saved in list: " + adList.size());
		
	}

}
