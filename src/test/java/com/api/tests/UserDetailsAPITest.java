package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPITest() {
		
//		ConfigManager configManager = new ConfigManager(); Made the class as static
		Header authHeader = new Header("Authorization", AuthTokenProvider.getToken(FD));
		
		given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.header(authHeader)
			.accept(ContentType.ANY)
		.and()
			.log().uri()
			.log().headers()
			.log().method()
		.when()
			.get("userdetails")
		.then()
			.log().status()
			.log().headers()
			.log().body()
		.and()
			.statusCode(200)
			.time(lessThan(1000L))
		.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsAPIResponseSchema.json"))
		;
	}
}
