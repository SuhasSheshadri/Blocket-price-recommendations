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

	private Integer id_num;
	private RestHighLevelClient client; 
	
	public ElasticSearch() {
		id_num = 0;
		this.client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}
	
	public void indexAds(List<Ad> adList, String category, String region) throws IOException {
		category = category.toLowerCase();
		IndexRequest indexRequest;
		Ad ad;
		Map<String, Object> json;
		String type = "ad";
		for(int i = 0; i < adList.size(); i++) {
			ad = adList.get(i);
			json = ad.getJson();
			Integer id = new Integer(i);
			indexRequest = new IndexRequest(category, type, id_num.toString()).source(json);
			id_num++;
			client.index(indexRequest);
		}
	}
	
	public void deleteIndex() throws IOException {
		
		DeleteIndexRequest request = new DeleteIndexRequest("båtar");
		client.indices().delete(request);

	}
	
	public void search(String location, String category, String query, HashMap<String, Object> attributes) throws IOException {
		SearchRequest searchRequest = new SearchRequest(category);
		//searchRequest.types(category);
		int s = 10000;
		Map<Map<String, Object>, Integer> results = new HashMap<Map<String, Object>, Integer>();
		
		for(Map.Entry<String,Object> entry : attributes.entrySet()) {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
			searchSourceBuilder.size(s);
			if(!location.equals("hela sverige")) {
				//searchSourceBuilder.query(QueryBuilders.boolQuery().must());
			}
			if(entry.getKey().contains("från")) {

				String key = entry.getKey().split("\\s+")[0];
				searchSourceBuilder.query(QueryBuilders.rangeQuery(key).gte(entry.getValue()));

			}else if(entry.getKey().contains("till") ) {

				String key = entry.getKey().split("\\s")[0];
				searchSourceBuilder.query(QueryBuilders.rangeQuery(key).lte(entry.getValue()));

			}else {
				searchSourceBuilder.query(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));

			}
		
			//System.out.println(searchSourceBuilder);
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = client.search(searchRequest);

			//System.out.println(searchResponse);
			/*
			System.out.println(hits.getTotalHits());
			for(int i = 0; i < hits.getTotalHits(); i++) {
				SearchHit searchHits = hits.getAt(i);
				hits.
				i += s;
			*/
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			//hits.getAt(position)
			for(SearchHit hit : searchHits) {
				hit.docId();
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				System.out.println(sourceAsMap);
				if(results.containsKey(sourceAsMap)) {
					//System.out.println("I'm here");
					results.put(sourceAsMap, results.get(sourceAsMap)+1);
				}else {
					results.put(sourceAsMap, 1);
				}
				//System.out.println(sourceAsMap.get("price"));
			}

			for(Map.Entry<Map<String, Object>, Integer> e : results.entrySet()) {
				//System.out.println(e.getKey().get("title") + "  " + e.getValue());
				//System.out.println(e.getValue());
			}
			System.out.println(searchHits.length);

		}
	}
		
	public void closeClient() throws IOException {
		
		client.close();
	}
}
