package com.api.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private AuthTokenProvider() {
		//Creating private Constructor to prevent Object Creation.
	}
	
	public static String getToken(Role role) {
		
		//I want to make the request for the login api and
		//we want to extract the token and print it on the console!!
		
		UserCredentials userCred = null;
				
		switch(role) {
		case FD -> userCred = new UserCredentials("iamfd", "password");
		case SUP -> userCred = new UserCredentials("iamsup", "password");
		case ENG -> userCred = new UserCredentials("iameng", "password");
		case QC -> userCred = new UserCredentials("iamqc", "password");
		default -> System.out.println("Invalid Role Provided!! Please check again!!");
		}
		
		String token = given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.contentType(ContentType.JSON)
			.accept(ContentType.ANY)
			.body(userCred)
		.when()
			.post("login")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.extract().jsonPath().getString("data.token")
		;
		
		System.out.println("---------------------------------\n");
//		System.out.println("Authorization token used: "+ token + "\n");
		
		return token;
			
	}
}
