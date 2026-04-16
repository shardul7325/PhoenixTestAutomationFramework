package com.api.utils;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private AuthTokenProvider() {
		//Creating private Constructor to prevent Object Creation.
	}
	
	public static String getToken(Role role) {
		
		//I want to make the request for the login api and
		//we want to extract the token and print it on the console!!
		
		UserCredentials userCred = null;
		if(role == FD) {
			userCred = new UserCredentials("iamfd", "password");
		}
		
		else if(role == SUP) {
			userCred = new UserCredentials("iamsup", "password");
		}
		
		else if(role == ENG) {
			userCred = new UserCredentials("iameng", "password");
		}
		
		else if(role == SUP) {
			userCred = new UserCredentials("iamqc", "password");
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
