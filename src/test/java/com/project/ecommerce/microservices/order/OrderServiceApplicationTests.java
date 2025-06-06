package com.project.ecommerce.microservices.order;

import com.project.ecommerce.microservices.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0) // Use a random port for WireMock
class OrderServiceApplicationTests {

	@Container
	@ServiceConnection
	public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));

	@LocalServerPort
	private Integer portValue;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = portValue;
	}






	@Test
	void shouldSubmitNewOrder() {
		String submitOrderRequest = """
				{
					"skuCode": "ps5_pro",
					"quantity": 5,
					"price": 750000
				}
				""";

		InventoryClientStub.stubInventoryCall("ps5_pro", 5);

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderRequest)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();
		assertThat(responseBodyString, Matchers.containsString("Order placed successfully for Order Number: "));
	}

	@Test
	void shouldNotSubmitNewOrderWhenInventoryIsNotAvailable() {
		String submitOrderRequest = """
				{
					"skuCode": "ps5_pro",
					"quantity": 1000,
					"price": 750000
				}
				""";

		InventoryClientStub.stubInventoryCall("ps5_pro", 1000);

		RestAssured.given()
				.contentType("application/json")
				.body(submitOrderRequest)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(500);
	}

}
