package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {

	@Test
	public void loginAPITest() {
		//Rest Assured Code!
		
//		ConfigManager configManager = new ConfigManager(); Made the class as static
		UserCredentials userCreds = new UserCredentials("iamfd", "password");
		
		given()
			.baseUri(getProperty("BASE_URI"))
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
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"))
		;
		
	}
}
