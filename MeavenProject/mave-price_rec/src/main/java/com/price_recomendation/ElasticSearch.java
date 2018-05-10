package com.price_recomendation;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticSearch {

	private RestHighLevelClient client; 
	
	public ElasticSearch() {
		this.client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}
	
	public void indexAds(List<Ad> adList) throws IOException {
		
		IndexRequest indexRequest;
		//IndexResponse indexResponse; 
		Ad ad;
		Map<String, Object> json;
		for(int i = 0; i < adList.size(); i++) {
			ad = adList.get(i);
			json = ad.getJson();
			indexRequest = new IndexRequest("ads", "ad", "").source(json);
			//indexResponse = 
			client.index(indexRequest);
		}
		
	}
	
	public void deleteIndex() throws IOException {
		
		DeleteIndexRequest request = new DeleteIndexRequest("ads");
		//DeleteIndexResponse deleteIndexResponse = 
		client.indices().delete(request);

	}
	
	public void closeClient() throws IOException {
		
		client.close();
	}
	/*
	public static void main(String[] args) throws IOException {

		
		Map<String, Object> json = new HashMap<String, Object>();
		List<String> listString = new ArrayList<String>();
		listString.add("Blue");
		listString.add("Fast");
		listString.add("Furious");

		json.put("title", "Honda Civic 1992");
		json.put("atttributes", listString);
		json.put("price", 100.12);
		json.put("url", "www.blocket.se");
		json.put("region", "Hela Sverige");

		IndexRequest indexRequest = new IndexRequest("ads", "ad", "1").source(json);

		IndexResponse indexResponse = client.index(indexRequest);

		// Map<String, Object> json2 = new HashMap<String, Object>();
		// List<String> listString2 = new ArrayList<String>();
		// listString.add("Blue");
		// listString.add("Fast");
		// listString.add("Furious");
		//
		// json.put("title","Mazda 1992");
		// json.put("atttributes",listString);
		// json.put("price", 100.12);
		// json.put("url", "www.blocket.se");
		// json.put("region","Hela Sverige");
		//
		//
		// IndexRequest indexRequest2 = new IndexRequest("ads", "ad", "2")
		// .source(json);
		//
		// IndexResponse indexResponse2 = client.index(indexRequest2);
		//
		// GetRequest getRequest = new GetRequest(
		// "ads",
		// "ad",
		// "1");
		//
		// GetResponse getResponse = client.get(getRequest);
		// listString = (List<String>) getResponse.getSource().get("message");
		// System.out.println(listString.get(0));
		// if (getResponse.getSource().get("price") instanceof String)
		// System.out.println("Double");

		

		// System.out.println(getResponse);
	}*/

}
