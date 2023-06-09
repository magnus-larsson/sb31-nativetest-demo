package com.example.tcdemo;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@DataMongoTest
@Testcontainers
@ImportRuntimeHints(TestcontainersRuntimeHints.class)
class TcdemoMongoTests {

	@Value("${spring.data.mongodb.host}") String host;
	@Value("${spring.data.mongodb.port}") int port;
	@Value("${spring.data.mongodb.database}") String db;

	@Container
	static MongoDBContainer database = new MongoDBContainer("mongo:6.0.4"); //.withExposedPorts(27017);

	@DynamicPropertySource
	static void mongoDbProperties(DynamicPropertyRegistry registry) {

		System.err.println(database.isCreated());
		System.err.println(database.isRunning());
		database.start();
		System.err.println(database.isCreated());
		System.err.println(database.isRunning());

		// registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		registry.add("spring.data.mongodb.host", database::getContainerIpAddress);
		registry.add("spring.data.mongodb.port", () -> database.getMappedPort(27017));
		registry.add("spring.data.mongodb.database", () -> "test");
	}

	@Test
	void contextLoads() {
		assertTrue(database.isCreated());
		assertTrue(database.isRunning());
		assertEquals("localhost", host);
		assertEquals("test", db);
		System.err.println(host);
		System.err.println(port);
		System.err.println(db);
	}


}
