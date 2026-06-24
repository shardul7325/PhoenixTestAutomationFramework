package com.api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

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
				, "20974072156818", "20974072156818", "20974072156818"
				, "2025-04-13T18:30:00.000Z", 1, 1);
		
		Problems problems = new Problems(1, "Battery Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0] = problems;
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1
				, customer, customerAddress, customerProduct, problemsArray);
		
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
			.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
		;
	}
}
