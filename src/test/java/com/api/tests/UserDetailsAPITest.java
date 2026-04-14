package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPITest() {
		
//		ConfigManager configManager = new ConfigManager(); Made the class as static
		Header authHeader = new Header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwiZmlyc3RfbmFtZSI6ImZkIiwibGFzdF9uYW1lIjoiZmQiLCJsb2dpbl9pZCI6ImlhbWZkIiwibW9iaWxlX251bWJlciI6Ijg4OTk3NzY2NTUiLCJlbWFpbF9pZCI6Im1hcmtAZ21haWwuY29tIiwicGFzc3dvcmQiOiI1ZjRkY2MzYjVhYTc2NWQ2MWQ4MzI3ZGViODgyY2Y5OSIsInJlc2V0X3Bhc3N3b3JkX2RhdGUiOm51bGwsImxvY2tfc3RhdHVzIjowLCJpc19hY3RpdmUiOjEsIm1zdF9yb2xlX2lkIjo1LCJtc3Rfc2VydmljZV9sb2NhdGlvbl9pZCI6MSwiY3JlYXRlZF9hdCI6IjIwMjEtMTEtMDNUMDg6MDY6MjMuMDAwWiIsIm1vZGlmaWVkX2F0IjoiMjAyMS0xMS0wM1QwODowNjoyMy4wMDBaIiwicm9sZV9uYW1lIjoiRnJvbnREZXNrIiwic2VydmljZV9sb2NhdGlvbiI6IlNlcnZpY2UgQ2VudGVyIEEiLCJpYXQiOjE3NzU5MjcxNzF9.0mdCctQyC2hu_i_kAoANLceZIIB7IhW2kDuEZUYNV28");
		
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
