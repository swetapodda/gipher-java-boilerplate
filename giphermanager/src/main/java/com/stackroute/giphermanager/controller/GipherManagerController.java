
package com.stackroute.giphermanager.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 * 
 * @CrossOrigin,@EnableFeignClients,@RibbonClient
 * 
 */

import com.stackroute.giphermanager.exception.GipherNotCreatedException;
import com.stackroute.giphermanager.exception.GipherNotFoundException;
import com.stackroute.giphermanager.model.Gipher;
import com.stackroute.giphermanager.model.GipherRecomendation;
import com.stackroute.giphermanager.service.IGipherManagerService;
import com.stackroute.giphermanager.service.IGiphySearchService;
import com.stackroute.giphermanager.stream.RecomendationMessageProcessor;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableBinding(RecomendationMessageProcessor.class)
@RequestMapping("/api/v1/giphermanager")
public class GipherManagerController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// This is common for all REST API Response
	private ResponseEntity<?> responseEntity = null;
	
	@Autowired
	private IGipherManagerService gipherManagerService;
	@Autowired
	private IGiphySearchService giphySearchService;
	
	@Autowired
	private RecomendationMessageProcessor recomendationMessageProcessor;
	
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Returns all Favourtes Gipher (GIF Images) of the User Id. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns List of Gipher (Favourite GIFs)"),
	@ApiResponse(code = 409, message = "If No Favouite Gipher found")
	})
	
	// Swagger -API Documentation - Injection-Ends

	@GetMapping("/gipher")
	public ResponseEntity<?> getAllGipherByGipherUser(@RequestParam String gipherUserId) {

		log.debug("Getting Gif Images from Favourites-Started:"+ gipherUserId);
		List<Gipher> foundGipoher;
		try {
			if(gipherUserId != null) {
				foundGipoher = this.gipherManagerService.getAllGipherByGipherUser(gipherUserId);
				responseEntity = new ResponseEntity<>(foundGipoher, HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<>("User Id is Invalid", HttpStatus.CONFLICT);
			}
			return responseEntity;
		} catch (Exception e) {
			log.error("Could not Find Favourites for the User", e);
			responseEntity = new ResponseEntity<>("Could not Find Favourites for the User", HttpStatus.CONFLICT);
			return responseEntity;
		}
	}

	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Add Gipher to Favourites. (Note: Message will be posted to RabbitMQ Server for external Micro Service to increase Favourites Count) ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 201, message = "Returns Created Gipher Object"),
	@ApiResponse(code = 409, message = "If Could not Insert into Favourties")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@PostMapping("/gipher")
	public ResponseEntity<?> addToFavourites(@RequestBody Gipher gipher) {

		log.debug("Adding Gif Image to Favourite-Started");
		Gipher createdGhipher;
		try {
			createdGhipher = this.gipherManagerService.addToFavourites(gipher);
			if (createdGhipher != null) {
				responseEntity = new ResponseEntity<>(createdGhipher, HttpStatus.CREATED);
				//Notify to Rabbit MQ Stream for Increment
				if(recomendationMessageProcessor != null) {
					recomendationMessageProcessor.gipherRecomendationsIncrementOutput().send(MessageBuilder.withPayload(
							new GipherRecomendation(gipher.getGiphyId(), gipher.getTitle(), gipher.getGiphyUrl(), 1L)
					).build());
				}
			} else {
				responseEntity = new ResponseEntity<>("Could Not add to Favourites", HttpStatus.CONFLICT);
			}
		} catch (GipherNotCreatedException e) {
			log.error("Could not Add to Favourites:", e);
			responseEntity = new ResponseEntity<>("Could Not add to Favourites", HttpStatus.CONFLICT);
		}

		log.debug("Adding Gif Image to Favourite-Ends");

		return responseEntity;
	}

	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Remove Gipher from Favourites.  (Note: Message will be posted to RabbitMQ Server for external Micro Service to decrease Favourites Count) ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns Sucess message if successfully removed from  Gipher Object from favouirtes"),
	@ApiResponse(code = 409, message = "Failure message incase could not remove from favourites ")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@DeleteMapping("/{userId}/{gipherId}")
	public ResponseEntity<?> removeFromFavourites(@PathVariable Long userId,@PathVariable String gipherId) {
		
		log.debug("Deleting GIF from Favourites-Started:"+gipherId);
		boolean flag;
		try {
			flag = this.gipherManagerService.removeFromFavourites(userId,gipherId);
			if(flag ) {
				responseEntity = new ResponseEntity<>("Successfully Removed from Favourites", HttpStatus.OK);
				//Notify to Rabbit MQ Stream for Decrement
				if(recomendationMessageProcessor != null) {
					recomendationMessageProcessor.gipherRecomendationsDecrementOutput().send(MessageBuilder.withPayload(
							new GipherRecomendation(gipherId, null, null, 1L)
					).build());
				}
			}else {
				responseEntity = new ResponseEntity<>("Could not Remove from Favourites", HttpStatus.CONFLICT);
			}
		} catch (GipherNotFoundException e) {
			log.error("Could not Removed from Favourites",e);
			responseEntity = new ResponseEntity<>("Could not Remove from Favourites", HttpStatus.CONFLICT);
		}
		
		log.debug("Deleting GIF from Favourites-Ends");

		return responseEntity;
	}
	
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Search GIFs for the given Search Key from External System  (https://giphy.com/). ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns Map Search Results"),
	@ApiResponse(code = 409, message = "Failure message incase could not Search from External System")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@GetMapping("/searchGiphies")
	public ResponseEntity<?> searchGiphies(@RequestParam String searchKey,@RequestParam Integer pageNo) {
		
		log.debug("Search GIFs from Giphy - Started");
		try {
			Map<String, Object> responseData = this.giphySearchService.searchGiphy(searchKey,pageNo);
			responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Could not Removed from Favourites",e);
			responseEntity = new ResponseEntity<>("Could not Search from Giphy", HttpStatus.CONFLICT);
		}
		
		log.debug("Search GIFs from Giphy - Ends");

		return responseEntity;
	}
	
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Search Current Trend GIFs from External System (https://giphy.com/). ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns Map of Trend Results"),
	@ApiResponse(code = 409, message = "Failure message incase could not get Trend GIFs from External System")
	})
	
	// Swagger -API Documentation - Injection-Ends

	@GetMapping("/trendGiphies")
	public ResponseEntity<?> trendhGiphies(@RequestParam Integer pageNo) {

		log.debug("Getting Trend Giphy -Started");
		
		try {
			System.out.println("Page No:"+pageNo);
			Map<String, Object> responseData = this.giphySearchService.trendhGiphies(pageNo);
			responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Could not Removed from Favourites", e);
			responseEntity = new ResponseEntity<>("Could not get Trend Gifs from Giphy", HttpStatus.CONFLICT);
		}

		log.debug("Getting Trend Giphy -Ends");

		return responseEntity;
	}
	// Swagger -API Documentation - Injection-Starts
	
	@ApiOperation(value = "Returns Micro Service Details. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Returns Micro Service Details")
	})
	
	// Swagger -API Documentation - Injection-Ends
	
	@GetMapping("/")
	public ResponseEntity<?> showRoot() {

		return new ResponseEntity<>("Gipher Manager Mirco Service Running", HttpStatus.OK);

	}

}
