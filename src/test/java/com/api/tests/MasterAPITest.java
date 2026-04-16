package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class MasterAPITest {

	@Test
	public void masterAPITest() {
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.header("Authorization", AuthTokenProvider.getToken(Role.FD))
		.and()
			.contentType("")
			//default content-type when making a POST request is "application/url-formencoded" which may give us an error
			//hence we need to pass empty string to say we are not attaching any content-type
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(200)
			.time(Matchers.lessThan(1000L))
			.body("message", Matchers.equalTo("Success"))
			.body("data", Matchers.notNullValue())
			.body("data", Matchers.hasKey("mst_oem"))
			.body("data", Matchers.hasKey("mst_model"))
			.body("$", Matchers.hasKey("message"))
			.body("$", Matchers.hasKey("data"))
			.body("data.mst_oem.size()", Matchers.equalTo(2))
			.body("data.mst_model.size()", Matchers.greaterThan(0))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
			.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"))
		;
	}
	
	@Test
	public void invalidTokenMaserAPITest() {
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.header("Authorization", "")
		.and()
			.contentType("")
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
}
