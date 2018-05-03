package com.price_recomendation;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class ElasticSearch {
	
	public static void main (String[] args) throws IOException{
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
        		new HttpHost("localhost", 9200, "http")));
		
		IndexRequest request = new IndexRequest(
		        "posts", 
		        "doc",  
		        "1");   
		String jsonString = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"2013-01-30\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		        "}";
		request.source(jsonString, XContentType.JSON); 
		
		IndexResponse indexRes = client.index(request);
        
        client.close();
        
        System.out.println("No errors");
	}
	
}
