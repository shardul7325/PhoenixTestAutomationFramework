package com.api.utils;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
// static methods
	
	//Will Work for GET--DEL
	public static RequestSpecification requestSpec() {
		
		//To take care of the common request sections (methods)
		return new RequestSpecBuilder()
			.setBaseUri(ConfigManager.getProperty("BASE_URI"))
			.setContentType(ContentType.JSON)
			.setAccept(ContentType.JSON)
			.log(LogDetail.URI)
			.log(LogDetail.METHOD)
			.log(LogDetail.HEADERS)
			.log(LogDetail.BODY)
			.build()
		;
	}
	
	//POST-PUT-PATCH {BODY}
	//Changing the Paramenters from "UserCredentials userCreds" to "Object payload" for loose coupling
	//Meaning we can attach other POJO objects here in future too
	public static RequestSpecification requestSpec(Object payload) {
		
		//To take care of the common request sections (methods)
		return new RequestSpecBuilder()
			.setBaseUri(ConfigManager.getProperty("BASE_URI"))
			.setContentType(ContentType.JSON)
			.setAccept(ContentType.JSON)
			.setBody(payload)
			.log(LogDetail.URI)
			.log(LogDetail.METHOD)
			.log(LogDetail.HEADERS)
			.log(LogDetail.BODY)
			.build()
		;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		
		return new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build()
			;
	}
	
	public static ResponseSpecification responseSpec_OK() {
		
		return new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(200)
			.expectResponseTime(Matchers.lessThan(2000L))
			.log(LogDetail.ALL)
			.build()
		;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		
		return new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(2000L))
			.log(LogDetail.ALL)
			.build()
		;
	}
	
public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		
		return new ResponseSpecBuilder()
			.expectStatusCode(statusCode)
			.expectResponseTime(Matchers.lessThan(2000L))
			.log(LogDetail.ALL)
			.build()
		;
	}
}
