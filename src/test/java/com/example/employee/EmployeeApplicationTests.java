package com.example.employee;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.example.employee.configuration.JerseyConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class EmployeeApplicationTests {

	public Client client;

	@Autowired
	private JerseyConfiguration jerseyConfiguration;

	@BeforeEach
	public void initializeJerseyClient() {
		client = ClientBuilder.newClient(jerseyConfiguration);
	}
}
