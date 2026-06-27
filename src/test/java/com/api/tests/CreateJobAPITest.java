package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.WarrantyStatus;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtility;

public class CreateJobAPITest {

	CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating the createJob API request payload")
	public void setup() {
		
		Customer customer = new Customer("Shardul", "Pakhare"
				, "8655843470", ""
				, "shardulpakhare2512@gmail.com", "");
		
		CustomerAddress customerAddress = new CustomerAddress("B2 0109"
				, "Blue Ridge Apartments"
				, ""
				, "Blue Ridge Circle"
				, "Hinjewadi Phase 1"
				, "411057", "India", "Maharashtra");
		
		String getDateTimeOfPurchase = DateTimeUtility.getTimeWithNDaysAgo(10);
		
		CustomerProduct customerProduct = new CustomerProduct(getDateTimeOfPurchase
				, "32474072156819"
				, "32474072156819"
				, "32474072156819"
				, getDateTimeOfPurchase
				, Product.NEXUS_2.getCode()
				, Model.NEXUS_2_BLUE.getCode());
		
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOWLY.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode()
				, Platform.FRONT_DESK.getCode()
				, WarrantyStatus.IN_WARRANTY.getCode()
				, OEM.GOOGLE.getCode()
				, customer
				, customerAddress
				, customerProduct
				, problemsList);
	}
	
	@Test(description = "Verify if the CreateJob API is able to create Inwarranty job"
			, groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
		
		given()
			.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"))
		;
	}
}
