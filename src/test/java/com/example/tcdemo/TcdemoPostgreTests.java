package com.example.tcdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ImportRuntimeHints(TestcontainersRuntimeHints.class)
class TcdemoPostgreTests {

	@Autowired
	private WebTestClient client;

	@Container
	@ServiceConnection
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.1-alpine");

	@Test
	void contextLoads() {
	}

	@DisabledInNativeImage
	@Test
	void testWeb() {
		byte[] responseBody = client.get()
			.uri("/customers")
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.OK)
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
			.returnResult().getResponseBody();
		assertEquals("[{\"id\":1,\"name\":\"Magnus\"},{\"id\":2,\"name\":\"Frasse\"}]", new String(responseBody));
	}

}
