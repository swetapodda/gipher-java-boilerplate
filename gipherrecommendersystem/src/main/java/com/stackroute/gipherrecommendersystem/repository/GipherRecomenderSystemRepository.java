package com.stackroute.gipherrecommendersystem.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.gipherrecommendersystem.model.GipherRecomendation;

@Repository
public interface GipherRecomenderSystemRepository extends MongoRepository<GipherRecomendation, String> {

	
	@Query("{ 'giphyId': ?0}")
	List<GipherRecomendation> getGipherRecomendationByGiphyId(String giphyId);

}
