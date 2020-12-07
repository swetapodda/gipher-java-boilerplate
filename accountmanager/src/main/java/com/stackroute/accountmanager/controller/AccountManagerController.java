
package com.stackroute.accountmanager.controller;



import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.IConstants;
import com.stackroute.accountmanager.exception.GipherUserAlreadyExistsException;
import com.stackroute.accountmanager.exception.GipherUserNotFoundException;
import com.stackroute.accountmanager.exception.InvalidTokenException;
import com.stackroute.accountmanager.model.GipherUser;
import com.stackroute.accountmanager.service.IAccountManagerService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/v1/accountmanager")
public class AccountManagerController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//This is common for all REST API Response 
	private ResponseEntity<?> responseEntity = null;
	
	@Autowired
	private IAccountManagerService accountManagerService;
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Will create a GihpherUser by reading the Serialized GipherUser object from request body and save the Gipher in database", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "In case of successful creation of the GipherUser"),
			@ApiResponse(code = 409, message = "In case of duplicate GipherUser")
	})
	// Swagger -API Documentation - Injection-Ends	
	@PostMapping(path="/gipherUser")
	public ResponseEntity<?> registerUser(@RequestBody GipherUser gipherUser) {
		
		GipherUser createdUser = null;
		 ResponseEntity<?> responseEntity = null;
		try {
			createdUser = this.accountManagerService.registerUser(gipherUser);
		} catch (GipherUserAlreadyExistsException e) {
			responseEntity = new ResponseEntity<>("User Creation Failed", HttpStatus.CONFLICT);
			return responseEntity;
		}
		if (createdUser != null) {
			responseEntity = new ResponseEntity<>("User Created", HttpStatus.OK);
		}

		return responseEntity;
	}
	
	
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Will authenticate user by Mail Id and Password", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 201, message = "If the user successfully authenticated, and returns token"),
	@ApiResponse(code = 409, message = "If the user authentication failed")
	})
	// Swagger -API Documentation - Injection-Starts
		
	@PostMapping("/authenticateGipherUser")
	public ResponseEntity<?> authenticateUser(@RequestBody GipherUser gipherUser) {

		try {
			String jwtToken = this.accountManagerService.authenticateUser(gipherUser);
			
			GipherUser foundUser=this.accountManagerService.findByEmailId(gipherUser.getEmail());
			Map<String,Object> responseData=new Hashtable<>();
			responseData.put("JWT_TOKEN", jwtToken);
			responseData.put("USER_DATA", foundUser);
			responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
			return responseEntity;
		} catch (GipherUserNotFoundException e) {
			responseEntity = new ResponseEntity<>("Authentication Failed", HttpStatus.CONFLICT);
			return responseEntity;
		}
		
	}
	
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Will Send Password Reset Mail by setting Unique token. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 201, message = "If the Password Reset Request Success"),
	@ApiResponse(code = 409, message = "If the Password Reset Request Failed")
	})
	// Swagger -API Documentation - Injection-Starts
	@PostMapping("/recoverPassword")
	public ResponseEntity<?> recoverPassword(@RequestBody GipherUser gipherUser) {

		try {
			this.accountManagerService.recoverPassword(gipherUser.getEmail());
			responseEntity = new ResponseEntity<>("Password Reset Token EMailed", HttpStatus.OK);
			return responseEntity;
		} catch (GipherUserNotFoundException e) {
			responseEntity = new ResponseEntity<>("Given EMail Id associated User Not Matched", HttpStatus.CONFLICT);
			return responseEntity;
		}
	}
	
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Will Reset password. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 201, message = "If the Password Reset Success"),
	@ApiResponse(code = 409, message = "If the Password Reset Failed")
	})
	// Swagger -API Documentation - Injection-Starts
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody GipherUser gipherUser) {

		try {
			this.accountManagerService.resetPassword(gipherUser);
			responseEntity = new ResponseEntity<>("Password Reset Success", HttpStatus.OK);
			return responseEntity;
		}catch (InvalidTokenException e) {
			responseEntity = new ResponseEntity<>("Associated Email User and Token Not found", HttpStatus.CONFLICT);
			return responseEntity;
		}
		
	}
	
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Validate given JWT Token set in Header Authorization . ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "If the JWT Token Valid"),
	@ApiResponse(code = 403, message = "If the JWT Token InValid/Forbidden")
	})
	// Swagger -API Documentation - Injection-Starts
	
	@PostMapping("/isAuthenticated")
	public ResponseEntity<?> isAuthenticated(@RequestHeader("Authorization")  String jwtTokenArg) {
		if(jwtTokenArg != null) {
			String jwtToken = jwtTokenArg.substring(IConstants.HEADER.BEARER_KEY.length());
			System.out.println("JWT Token:" + jwtToken);
			try {
				Claims claims=this.accountManagerService.isValidJwt(jwtToken);
				HashMap<String, Object> map = new HashMap<>();
				map.clear();
				map.put("claims", claims);
				map.put("isAuthenticated", true);
				return new ResponseEntity<>(map, HttpStatus.OK);
			} catch (Exception e) {
				log.error("Error Occured while validating JWT Token",e);
				return new ResponseEntity<>("Invalid Token", HttpStatus.FORBIDDEN);
			}
		}else {
			return new ResponseEntity<>("Invalid Token", HttpStatus.FORBIDDEN);
		}
		

	}
	
	/*
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Check whether EMail id Exist (Can be Used whether User Already Registered or Not . ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "If the EMail Id is Not Exist"),
	@ApiResponse(code = 409, message = "If the EMail Id is Exis")
	})
	// Swagger -API Documentation - Injection-Starts
	
	@GetMapping("/{emailId}")
	public ResponseEntity<?> findById(@PathVariable String emailId) {
		System.out.println("Find By Id:" + emailId);

		GipherUser gipherUser = this.accountManagerService.findByEmailId(emailId);
		if (gipherUser != null) {
			responseEntity = new ResponseEntity<>("EMail id Exists", HttpStatus.OK);
			return responseEntity;
		}
		responseEntity = new ResponseEntity<>("Given EMail Id User Not Found", HttpStatus.CONFLICT);
		return responseEntity;

	}
	
	*/
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Logout for the Current User. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "If logout Success"),
	
	})
	// Swagger -API Documentation - Injection-Starts
	
	@PostMapping("/logOut")
	public ResponseEntity<?> logOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<>("Logout Success", HttpStatus.OK);
	}
	 
	// Swagger -API Documentation - Injection-Starts
	@ApiOperation(value = "Return the Micro Service Details. ", 
			response = ResponseEntity.class)
	@ApiResponses(value = { 
	@ApiResponse(code = 200, message = "Return the Micro Service Details"),
	
	})
	// Swagger -API Documentation - Injection-Starts
	
	@GetMapping("/")
	public ResponseEntity<?> showRoot() {
		log.debug("Account Manager Micro Service Status");
		return new ResponseEntity<>("Account Manager Mirco Service Running", HttpStatus.OK);

	}

}
