package com.price_recomendation;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticSearch {

	private RestHighLevelClient client; 
	
	public ElasticSearch() {
		this.client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}
	
	public void indexAds(List<Ad> adList, String category, String region) throws IOException {
		
		IndexRequest indexRequest;
		Ad ad;
		Map<String, Object> json;
		for(int i = 0; i < adList.size(); i++) {
			ad = adList.get(i);
			json = ad.getJson();
			Integer id = new Integer(i);
			indexRequest = new IndexRequest(region, category, id.toString()).source(json);
			client.index(indexRequest);
		}
	}
	
	public void deleteIndex() throws IOException {
		
		DeleteIndexRequest request = new DeleteIndexRequest("ads");
		client.indices().delete(request);

	}
	
	public void search(String location, String category, String query, HashMap<String, Object> attributes) {
		SearchRequest searchRequest = new SearchRequest(location);
		searchRequest.types(category);

		

		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse);
		
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		
		for(SearchHit hit : searchHits) {
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			System.out.println(sourceAsMap);
			//System.out.println(sourceAsMap.get("price"));
		}
		System.out.println(searchHits.length);

	}
	
	public void searchElastic(String location, List<String> attributes, String query, String category) throws IOException {
		/*
		//Filter by region	WORKS
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.termQuery("region", "vasterbotten")); 
		searchRequest.source(searchSourceBuilder); 
		*/
/*
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.size(100);
		searchSourceBuilder.query(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("provided_name", "twitter")));
				//.must(QueryBuilders.termQuery("title", "volvo v70")));
	
		//searchSourceBuilder.query(QueryBuilders.termQuery("attributes.Miltal","47499")); 
		searchRequest.source(searchSourceBuilder); 
	*/
		
		//This works for query
		//MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(" user", " KimChy");
		SearchRequest searchRequest = new SearchRequest("vasterbotten");
		searchRequest.types("Bilar");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		int year = 1990;
		//searchSourceBuilder.query(QueryBuilders.termQuery("region", "vasterbotten")); 
		searchSourceBuilder.query(QueryBuilders.rangeQuery("attributes.Modellår").gte(year));
		//searchSourceBuilder.query(matchQueryBuilder); 
		searchRequest.source(searchSourceBuilder); 
		
		//SearchHits hits = searchResponse.getHits();
		/*
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("attributes", "{\"Miltal\":\"47499\",\"Växellåda\":\"Automat\",\"Modellår\":\"2005\",\"Bränsle\":\"Diesel\",\"Tillverkningsår\":\"2005\"}");
		SearchRequest searchRequest = new SearchRequest("ads");
		searchRequest.types("ad");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		//searchSourceBuilder.query(QueryBuilders.termQuery("region", "vasterbotten")); 

		searchSourceBuilder.query(matchQueryBuilder); 
		searchRequest.source(searchSourceBuilder); 
		*/
		
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse);
		
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		
		for(SearchHit hit : searchHits) {
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			System.out.println(sourceAsMap);
			//System.out.println(sourceAsMap.get("price"));
		}
		System.out.println(searchHits.length);

		
		/*
		SearchResponse response = client.search 	.prepareSearch("ads")
				.setTypes("ad")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("title", query))
				.setPostFilter(QueryBuilders.termQuery("region", location))
				.get();
	*/	
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
