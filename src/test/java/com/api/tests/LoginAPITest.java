package com.api.tests;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

public class LoginAPITest {

	private UserCredentials userCreds;
	
	@BeforeMethod(description = "Create the Payload for the Login API")
	public void setup() {
		
		userCreds = new UserCredentials("iamfd", "password");
	}
	
	@Test(description = "Verifying if login API is working for FD user"
			, groups = {"api", "regression", "smoke"})
	public void loginAPITest() {
				
		given()
			.spec(requestSpec(userCreds))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
		.and()
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("response-schema/loginAPIResponseSchema.json"))
		;
		
	}
}
