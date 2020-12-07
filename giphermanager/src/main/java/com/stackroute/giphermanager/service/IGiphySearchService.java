package com.stackroute.giphermanager.service;

import java.util.Map;

public interface IGiphySearchService {

	Map<String, Object> searchGiphy(String searchKey,Integer pageNo);


	Map<String, Object> trendhGiphies(Integer pageNo);

}
