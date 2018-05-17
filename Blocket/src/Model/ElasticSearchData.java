package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticSearchData {
	
	//For the score terms
	private Double score_attributes = 0.1;
	private Double score_query = 0.9;

	//Set by the Search View
	private int total_num_attributes = 0;

	// Number of top Ads to be presented
	public int NUM_ADS = 10;
	//Results for the search (using user specifications)
	Map<String, Double> results = new HashMap<String, Double>();
	Map<String, Integer> results_prices = new HashMap<String,Integer>();
    Map<String, String> id2title= new HashMap<String,String>();
    Map<String, String> title2id= new HashMap<String,String>();

    //ElasticSearch connection
	private RestHighLevelClient client; 
	
	//Data that we need to send to the Elastic Search.
	private String Location = " ";
	//Attributes selected //HashMap (Key -> attributes.Name, Value -> Value of the attribute selected)
	private HashMap<String,Object> attributes;
	//Category selected
	private String Category = " ";
	//Query selected by the user
	private String Query = " ";
	
	//Information to fill based on what the ElasticSearch Search function return
	//Prices information:
	private String Lowest = " ";
	private String Highest = " ";
	private String Average = " ";
	
	//10 top results:
	private ArrayList<String> topNames;
	private ArrayList<Integer> topPrices;
	private ArrayList<Double> topScores;

	public ElasticSearchData() {
		topNames = new ArrayList<String>();
		topPrices = new ArrayList<Integer>();
		topScores = new ArrayList<Double>();
		attributes = new HashMap<String,Object>();
		this.client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	}


    private Map<String, Double> sortMapByValues(Map<String, Double> aMap) {

        Set<Entry<String,Double>> mapEntries = aMap.entrySet();

//        System.out.println("Values and Keys before sorting ");
//        for(Entry<String,Double> entry : mapEntries) {
//            System.out.println(entry.getValue() + " - "+ entry.getKey());
//        }

        // used linked list to sort, because insertion of elements in linked list is faster than an array list.
        List<Entry<String,Double>> aList = new LinkedList<Entry<String,Double>>(mapEntries);

        // sorting the List
        Collections.sort(aList, new Comparator<Entry<String,Double>>() {

            @Override
            public int compare(Entry<String, Double> ele1,
                               Entry<String, Double> ele2) {

                return ele2.getValue().compareTo(ele1.getValue());
            }
        });

        // Storing the list into Linked HashMap to preserve the order of insertion.
        Map<String,Double> aMap2 = new LinkedHashMap<String, Double>();
        for(Entry<String,Double> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }

        // printing values after soring of map
//        System.out.println("Value " + " - " + "Key");
//        for(Entry<String,Double> entry : aMap2.entrySet()) {
//            System.out.println(entry.getValue() + " - " + entry.getKey());
//        }
        return aMap2;
    }

	public void Score() {
		System.out.println("Score system");
		//1. Now we have the correspondence score per each ad.
        System.out.println("Start");
        //1.0 Sort the HashMap based on the score associated.
        results = sortMapByValues(results);
        //2.0 Return the top 50 results or less.
        HashMap<String, Integer> topPrices = new HashMap<String, Integer>();

        int numIter = 0;
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;
        int sumPrice = 0;
		for (Entry<String, Double> entry : results.entrySet()) {
            if(numIter < NUM_ADS) {
                int currPrice = results_prices.get(entry.getKey());
                if (currPrice > maxPrice){
                    maxPrice = currPrice;
                }
                if (currPrice < minPrice){
                    minPrice = currPrice;
                }
                topPrices.put(id2title.get(entry.getKey()), currPrice);
                System.out.println(entry.getKey() + " , " + entry.getValue() + " , " + results_prices.get(entry.getKey()) + " " + id2title.get(entry.getKey()));
                sumPrice += currPrice;
                numIter++;
            }
            else break;
        }
		//3.0 Calculate the highest, lowest and average price based on the top 50.
        int iter = 0;
        for(Entry<String, Integer> e : topPrices.entrySet()){
            if(iter < NUM_ADS) {
                this.topNames.add(e.getKey());
                this.topPrices.add(e.getValue());
                //System.out.println(title2id.get(e.getKey()));
                //System.out.println(results.containsKey(title2id.get(e.getKey())));
                this.topScores.add(results.get(title2id.get(e.getKey())));
            } else break;
            iter++;
        }
        Lowest = Integer.toString(minPrice);
        Highest = Integer.toString(maxPrice);
        Average = Integer.toString(sumPrice / topPrices.size());
		System.out.println("Max : " + maxPrice);
        System.out.println("Min : " + minPrice);
        System.out.println("AVG : " + sumPrice / topPrices.size());
		//4.0 Update the names of the top 10 results based on.

	}
	
	
	public void Search() throws IOException{
		
		ArrayList<String> removed_attributes = new ArrayList<String>();
		String location = this.Location;
		String category = "bilar";
		//String category = this.Category;
		String query = this.Query;

		query="volvo s80";
		location ="vasterbotten";
		//System.out.println("Implementation of the Search function");
		SearchRequest searchRequest = new SearchRequest(category);
		int s = 10000;
		
		//Results map will store: DocId, Score
		results.clear();
		results_prices.clear();
		
		String[] query_parts = query.split(" ");
		int query_size = query_parts.length;
		removed_attributes.clear();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.size(s);
		 
		//1. Find all the documents that contain or all the terms in the query or only some of them.
		for (int i = 0; i < query_size; i++) {
		    System.out.println(i);
			String query_part = query_parts[i];
            System.out.println("QUERY parts " + query_part);

            //System.out.println("Find for :" +query_part);
			searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("region",location)).must(QueryBuilders.termQuery("title", query_part)));
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = client.search(searchRequest);
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits();
			for(SearchHit hit: searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				String title = sourceAsMap.get("title").toString();
				if(!results.containsKey(hit.getId().toString())) {
					results.put(hit.getId().toString(), 1.0);
					String price = sourceAsMap.get("price").toString();
					results_prices.put(hit.getId().toString(), Integer.parseInt(price));
					id2title.put(hit.getId().toString(), title);
					title2id.put(title, hit.getId().toString());
				}else {
					results.put(hit.getId().toString(), results.get(hit.getId().toString()) + 1.0);
				}
			}
		}
		//Normalize the query score per each document.
		for(Map.Entry<String,Double> entry: results.entrySet()) {
			if (results.containsKey(entry.getKey())) {	
				results.put(entry.getKey(),score_query*(results.get(entry.getKey())/(double)query_size));
			}
		}
		System.out.println("The number of selected attributes is: " + total_num_attributes);
		
		//2. Now we are going to analyze the attributes.
		Map<String, Double> results_att = new HashMap<String, Double>();
		if (total_num_attributes > 0) {
			for (Map.Entry<String,Object> entry: attributes.entrySet()) {
				if (entry.getKey().toLowerCase().contains("fran") || entry.getKey().toLowerCase().contains("till")) {
					if (!removed_attributes.contains(entry.getKey().toLowerCase())) {
						String[] key_parts = entry.getKey().toString().split("attributes.");
						String[] key_parts2 = key_parts[1].split(" ");
						String common = key_parts2[0];
						if (entry.getKey().toLowerCase().contains("fran")) {
							String find = "attributes." + common +" till";
							if (attributes.containsKey(find)) {
								total_num_attributes--;
								String till = attributes.get(find).toString();	
								common = "attributes." + common;
								searchSourceBuilder.query(QueryBuilders.boolQuery()
										.must(QueryBuilders.termQuery("region", location))
										.must(QueryBuilders.rangeQuery(common).gte(entry.getValue()).lte(till)));
								removed_attributes.add(find.toLowerCase());
							}else {
								//No limit
								common = "attributes." + common;
								searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("region",location)).must(QueryBuilders.rangeQuery(common).gte(entry.getValue().toString())));
							}
						}else {
							//Contains till,
							String find = "attributes." + common + " fran";
							if(attributes.containsKey(find)) {								
								String fran = attributes.get(find).toString();
								common = "attributes." + common;
								total_num_attributes--;
								searchSourceBuilder.query(QueryBuilders.boolQuery()
										.must(QueryBuilders.termQuery("region", location))
										.must(QueryBuilders.rangeQuery(common).gte(fran).lte(entry.getValue())));				
								removed_attributes.add(find.toLowerCase());
							}else {
								//No limit
								common = "attributes."+common;
								searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("region",location)).must(QueryBuilders.rangeQuery(common).lte(entry.getValue().toString())));
							}
						}	
					}
				}else {
					//Non-range query
					searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("region", location)).must(QueryBuilders.termQuery(entry.getKey(),entry.getValue())));
				}
				searchRequest.source(searchSourceBuilder);
				SearchResponse searchResponse = client.search(searchRequest);
				SearchHits hits = searchResponse.getHits();
				SearchHit[] searchHits = hits.getHits();
				for(SearchHit hit: searchHits) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap();
					//System.out.println(sourceAsMap);
                    String title = sourceAsMap.get("title").toString();
                    if(!results_att.containsKey(hit.getId().toString())) {
						results_att.put(hit.getId().toString(), 1.0);
						String price = sourceAsMap.get("price").toString();
						results_prices.put(hit.getId().toString(), Integer.parseInt(price));
                        id2title.put(hit.getId().toString(), title);
                        title2id.put(title, hit.getId().toString());
                    }else {
						results_att.put(hit.getId().toString(), results_att.get(hit.getId().toString()) + 1.0);
					}
				}
			}
			//Now we need to normalize the attributes_results score.
			for(Map.Entry<String,Double> entry: results_att.entrySet()) {
				if (results_att.containsKey(entry.getKey())) {	
					results_att.put(entry.getKey(),score_attributes*(results_att.get(entry.getKey())/(double)total_num_attributes));
				}
			}
			
		}
		//Now we need to sum them:
		for (Map.Entry<String,Double> entry: results_att.entrySet()) {
			if (results.containsKey(entry.getKey())) {
				results.put(entry.getKey(), results.get(entry.getKey())+entry.getValue());
			}else {
				results.put(entry.getKey(),entry.getValue());
			}
		}
        System.out.println(" Size is " + results.size());

        System.out.println("Search finished");
	}
		
//	public void CreateNames() {
//		for (int i = 0; i < 10; i++) {
//			String aux = "Title of the Result: "+i+ "djdlfakjshjfhlajksdhljfkahlsdkhfa  ";
//			topNames.add(aux);
//		}
//	}
//
//	public void CreatePrices() {
//		for(int i =0 ; i < 10;i++) {
//			topPrices.add(i);
//		}
//	}

//	public void CalculatePrices() {
//		Double max = Double.MIN_VALUE;
//		Double min = Double.MAX_VALUE;
//		Double total = (double) 0;
//		for (int i = 0; i < topPrices.size(); i++) {
//			if (topPrices.get(i)>max) {
//				max = topPrices.get(i);
//			}
//			if (topPrices.get(i)<min) {
//				min = topPrices.get(i);
//			}
//			total = total + topPrices.get(i);
//		}
//		total = total/(double)topPrices.size();
//		this.Average = "Average:" +total.toString() + " SEK";
//		this.Highest = "Highest:" +max.toString() + " SEK";
//		this.Lowest = "Lowest:"+ min.toString() + " SEK";
//	}
	
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}


	public int getTotal_num_attributes() {
		return total_num_attributes;
	}

	public void setTotal_num_attributes(int total_num_attributes) {
		this.total_num_attributes = total_num_attributes;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getQuery() {
		return Query;
	}

	public void setQuery(String query) {
		Query = query;
	}
	
	public String getLowest() {
		return Lowest;
	}


	public void setLowest(String lowest) {
		Lowest = lowest;
	}


	public String getHighest() {
		return Highest;
	}


	public void setHighest(String highest) {
		Highest = highest;
	}


	public String getAverage() {
		return Average;
	}


	public void setAverage(String average) {
		Average = average;
	}


	public ArrayList<String> getTopNames() {
		return topNames;
	}


	public void setTopNames(ArrayList<String> topNames) {
		this.topNames = topNames;
	}


	public ArrayList<Integer> getTopPrices() {
		return topPrices;
	}

    public void setTopScores(ArrayList<Double> topScores) {
        this.topScores = topScores;
    }

    public ArrayList<Double> getTopScores() {return this.topScores;}

	public void setTopPrices(ArrayList<Integer> topPrices) {
		this.topPrices = topPrices;
	}

	
}
