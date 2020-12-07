package com.stackroute.giphermanager.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.giphermanager.model.Gipher;

@Repository
public interface GipherManagerRepository extends MongoRepository<Gipher, String> {

	@Query("{ 'bookMarkedBy': ?0}")
	List<Gipher> getAllBookMarkedBy(Long bookMarkedBy);
	@DeleteQuery("{ 'bookMarkedBy': ?0},{ 'giphyId': ?1}")
	Long deleteBookMarkedByAndGiphyId(Long userId,String giphyId);

}
