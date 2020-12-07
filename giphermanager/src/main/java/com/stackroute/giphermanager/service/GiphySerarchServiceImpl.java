package com.stackroute.giphermanager.service;

import java.text.MessageFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/*
 * Serach Service for given Search String/Key from Giphy 
 * Giphy API Documentation Available:https://developers.giphy.com/docs/
 * 
 */

@Service
public class GiphySerarchServiceImpl implements IGiphySearchService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//Take from Property File
	String api_key = "AeB4XBN3vayp97pH9Vy2KpH8Hh4o1kw3";
	// No Of Records Page
	int noOfRecords=50;
	
	@Override
	public Map<String, Object> searchGiphy(String searchKey,Integer pageNo) {
		System.out.println("Searching API");
		
		SearchAPI searchAPI=new SearchAPI(searchKey,pageNo);
		String convertedUrl=searchAPI.getApiUrl();
		System.out.println(convertedUrl);
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.exchange(convertedUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<String>() {
					});
			//System.out.println(response.getBody());
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> parseList = springParser.parseMap(response.getBody());
			/*System.out.println(response.getBody());
			for (String iterable_element : parseList.keySet()) {
				System.out.println(iterable_element+"\t"+parseList.get(iterable_element));
			}*/
			return parseList;
		} catch (RestClientException e) {
			log.error("Errror while Seraching from Giphy:",e);
		}

		return null;
	}
	
	@Override
	public Map<String, Object> trendhGiphies(Integer pageNo) {
		
		System.out.println("Treanding API");
		TrendAPI trendApi=new TrendAPI(pageNo);
		String convertedUrl=trendApi.getApiUrl();
		System.out.println(convertedUrl);
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> response = restTemplate.exchange(convertedUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<String>() {
					});
			//System.out.println(response.getBody());
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> parseList = springParser.parseMap(response.getBody());
			/*System.out.println(response.getBody());
			for (String iterable_element : parseList.keySet()) {
				System.out.println(iterable_element+"\t"+parseList.get(iterable_element));
			}*/
			return parseList;
		} catch (RestClientException e) {
			log.error("Errror while Getting Trending Gifs from Giphy:",e);
		}

		return null;
	}

	class SearchAPI {
		// https://developers.giphy.com/docs/api/endpoint#search

		String searchApiURL = "https://api.giphy.com/v1/gifs/search?api_key={0}&q={1}&limit={2}&offset={3}&rating=G&lang=en";
		private String apiUrl = null;

		public SearchAPI(String searchKey, Integer pageNo) {
			pageNo = pageNo == null || pageNo <= 0 ? 0 : pageNo;
			int offSet = pageNo * noOfRecords;
			apiUrl = MessageFormat.format(searchApiURL, api_key, searchKey, noOfRecords, offSet);
		}

		public String getApiUrl() {
			return apiUrl;
		}
	}

	class TrendAPI {
		// https://developers.giphy.com/docs/api/endpoint#trending

		String searchApiURL = "https://api.giphy.com/v1/gifs/trending?api_key={0}&&limit={1}&offset={2}&rating=G&lang=en";
		private String apiUrl = null;

		public TrendAPI(Integer pageNo) {
			pageNo = pageNo == null || pageNo <= 0 ? 0 : pageNo;
			int offSet = pageNo * noOfRecords;
			apiUrl = MessageFormat.format(searchApiURL, api_key, noOfRecords, offSet);
		}

		public String getApiUrl() {
			return apiUrl;
		}
	}	
}
