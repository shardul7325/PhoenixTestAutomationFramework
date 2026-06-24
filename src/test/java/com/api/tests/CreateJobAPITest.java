package com.api.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {

	
	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("Shardul", "Pakhare"
				, "8655843470", ""
				, "shardulpakhare2512@gmail.com", "");
		
		CustomerAddress customerAddress = new CustomerAddress("B2 0109", "Blue Ridge Apartments"
				, "", "Blue Ridge Circle", "Hinjewadi Phase 1"
				, "411057", "India", "Maharashtra");
		
		CustomerProduct customerProduct = new CustomerProduct("2025-04-13T18:30:00.000Z"
				, "32074072156819", "32074072156819", "32074072156819"
				, "2025-04-13T18:30:00.000Z", 1, 1);
		
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1
				, customer, customerAddress, customerProduct, problemsList);
		
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", Matchers.equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", Matchers.equalTo(1))
			.body("data.job_number", Matchers.startsWith("JOB_"))
		;
	}
}
