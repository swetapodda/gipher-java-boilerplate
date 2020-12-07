package com.stackroute.gipherrecommendersystem;

import java.util.ArrayList;
import java.util.List;

public interface IConstants {

	String USER_NOT_LOGGED_IN = "User not Logged In";
	String LOGGED_IN_USER_ID_KEY = "loggedInUserId"; //JUnit uses this Key as Session Id
	String INTERNAL_ERROR="Some Internal Error ..";
	
	// JWT
	interface HEADER{
		String OPTIONS="OPTIONS";
		String AUTHORIZATION="AUTHORIZATION";
		String AUTHORIZATION_HEADER_MISSING="Authorization Header Info Missing";
		String SECRET_KEY="SecretKey";
		String BEARER_KEY="BEARER_KEY:";
	}
	public static List<String> excludeURI = new ArrayList<>();
	
	
	public static void generateExclueURIs() {
		addEndSlash("/api/v1/gipherrecomendersystem");
		
	}
	public static void addEndSlash(String arg) {
		excludeURI.add(arg);
		excludeURI.add(arg + "/");
	}
}
