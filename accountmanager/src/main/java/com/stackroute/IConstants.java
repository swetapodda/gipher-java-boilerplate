package com.stackroute;

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
		addEndSlash("/api/v1/accountmanager/");
		addEndSlash("/api/v1/accountmanager/logOut");
		addEndSlash("/api/v1/accountmanager/authenticateGipherUser");
		addEndSlash("/api/v1/accountmanager/isAuthenticated");
		addEndSlash("/api/v1/accountmanager/recoverPassword");
		addEndSlash("/api/v1/accountmanager/gipherUser");
		addEndSlash("/api/v1/accountmanager/resetPassword");
		
	}
	public static void addEndSlash(String arg) {
		excludeURI.add(arg.toLowerCase());
		excludeURI.add(arg.toLowerCase() + "/");
	}
}
