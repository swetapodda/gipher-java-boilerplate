
package com.stackroute.gipherrecommendersystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gipherrecommendersystem.model.GipherRecomendation;
import com.stackroute.gipherrecommendersystem.service.IGipherRecomenderSystemService;
import com.stackroute.gipherrecommendersystem.stream.RecomendationMessageProcessor;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController

@RequestMapping("/api/v1/gipherrecomendersystem")
public class GipherRecomenderSystemController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// This is common for all REST API Response
	//private ResponseEntity<?> responseEntity = null;
	
	@Autowired
	IGipherRecomenderSystemService gipherRecomenderSystemService;
	
	// Swagger -API Documentation - Injection-Starts
	// Swagger -API Documentation - Injection-Ends

	
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Returns Gipher Recomender Miscro Service Details ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns Gipher Recomender Miscro Service Details")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@GetMapping("/")
	public ResponseEntity<?> showRoot() {
		return new ResponseEntity<>("Gipher Recomender System Mirco Service Running", HttpStatus.OK);

	}
	
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Returns all Recomeneded Giphers based on Favourites Count.  (Note: Default Order is Favourite Count in Descending Order) ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns all Favourites GipherRecomendation. If no favourites added then this will be empty")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@GetMapping("/listAllRecomendations")
	public ResponseEntity<?> fetchAllRecomendations() {
		return new ResponseEntity<>(gipherRecomenderSystemService.getAllFavourite(), HttpStatus.OK);

	}
	
	@StreamListener(RecomendationMessageProcessor.INCREMENT_INPUT)
	public void increaseCountFavourite(GipherRecomendation gipherRecomendation) {
        System.out.println("Message Received for Increment"+gipherRecomendation);
        if(gipherRecomendation != null) {
        	gipherRecomenderSystemService.addedToFavourite(gipherRecomendation);
        	log.debug(gipherRecomendation.getGiphyId()+":"+gipherRecomendation.getCount());
        }
    }
	
	
	@StreamListener(RecomendationMessageProcessor.DECREMENT_INPUT)
	public void decreaseCountFavourite(GipherRecomendation gipherRecomendation) {
        System.out.println("Message Received for Decrement"+gipherRecomendation);
        if(gipherRecomendation != null) {
        	gipherRecomenderSystemService.removedFromFavourite(gipherRecomendation);
        	log.debug(gipherRecomendation.getGiphyId()+":"+gipherRecomendation.getCount());
        }
    }
}
