package com.price_recomendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class ElasticSearch {
	
	public static void main (String[] args) throws IOException{
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
        		new HttpHost("localhost", 9200, "http")));
		
		
		Map<String, Object> json = new HashMap<String, Object>();
		List<String> listString = new ArrayList<String>();
		listString.add("Blue");
		listString.add("Fast");
		listString.add("Furious");

		json.put("title","Honda Civic 1992");
		json.put("atttributes",listString);
		json.put("price", 100.12);
		json.put("url", "www.blocket.se");
		json.put("region","Hela Sverige");
		
		
		IndexRequest indexRequest = new IndexRequest("ads", "ad", "1")
        .source(json);
		
		IndexResponse indexResponse = client.index(indexRequest);
		
		
		Map<String, Object> json2 = new HashMap<String, Object>();
		List<String> listString2 = new ArrayList<String>();
		listString.add("Blue");
		listString.add("Fast");
		listString.add("Furious");

		json.put("title","Mazda 1992");
		json.put("atttributes",listString);
		json.put("price", 100.12);
		json.put("url", "www.blocket.se");
		json.put("region","Hela Sverige");
		
		
		IndexRequest indexRequest2 = new IndexRequest("ads", "ad", "2")
        .source(json);
		
		IndexResponse indexResponse2 = client.index(indexRequest2);
		
		GetRequest getRequest = new GetRequest(
		        "ads", 
		        "ad",  
		        "1"); 
		
        GetResponse getResponse = client.get(getRequest);
        listString = (List<String>) getResponse.getSource().get("message");
        System.out.println(listString.get(0));
        if (getResponse.getSource().get("price") instanceof String)  
        System.out.println("Double");
        
		
        client.close();
        
        
        System.out.println(getResponse);
	}
	
}
