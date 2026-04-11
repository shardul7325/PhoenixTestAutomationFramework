package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {

	@Test
	public void loginAPITest() {
		//Rest Assured Code!
		
		UserCredentials userCreds = new UserCredentials("iamfd", "password");
		
		given()
			.baseUri("http://64.227.160.186:9000/v1")
		.and()
			.contentType(ContentType.JSON)
		.and()
			.accept(ContentType.ANY)
		.and()
			.body(userCreds)
		.and()
			.log().uri()
			.log().method()
			.log().headers()
			.log().body()
		.when()
			.post("login")
		.then()
			.log().all()
		.and()
			.statusCode(200)
			.time(lessThan(1000L))
		.and()
			.body("message", equalTo("Success"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"));
	}
}
