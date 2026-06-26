package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {

	@Test
	public void loginAPITest() {
		//Rest Assured Code!
		
//		ConfigManager configManager = new ConfigManager(); Made the class as static
		UserCredentials userCreds = new UserCredentials("iamfd", "password");
		
		given()
			.spec(SpecUtil.requestSpec(userCreds))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
		.and()
			.body("message", equalTo("Success"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"))
		;
		
	}
}
