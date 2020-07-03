/**
 * 
 */
package com.example.employee.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.employee.EmployeeApplicationTests;
import com.example.employee.dto.EmployeeBean;

/**
 * @author Shakti
 *
 */
public class EmployeeResourceTest extends EmployeeApplicationTests {

	@DisplayName("GET_EMPLOYEE_BY_ID")
	@Test
	public void testGetEmployeeById() {
		Response response1 = client.target("http://localhost:8080/employees/api/v1/1").request().get();
		assertEquals(HttpStatus.OK.value(), response1.getStatus(), "Http Response should be 200: ");

		Response response2 = client.target("http://localhost:8080/employees/api/v1/1.5").request().get();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response2.getStatus(), "Http Response should be 400: ");

		Response response3 = client.target("http://localhost:8080/employees/api/v1/10").request().get();
		assertEquals(HttpStatus.NOT_FOUND.value(), response3.getStatus(), "Http Response should be 404: ");

		Response response4 = client.target("http://localhost:8080/employees/api/v1/0").request().get();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response4.getStatus(), "Http Response should be 500: ");
	}

	@DisplayName("GET_ALL_EMPLOYEES")
	@Test
	public void testGetAllEmployees() {
		Response response1 = client.target("http://localhost:8080/employees/api/v1/allEmployess").request().get();
		assertEquals(HttpStatus.OK.value(), response1.getStatus(), "Http Response should be 200: ");
	}

	@DisplayName("ADD_EMPLOYEE")
	@Test
	public void testAddEmployee() {
		EmployeeBean employee = new EmployeeBean();
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setDepartment("Accounts");

		Entity<EmployeeBean> empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_VALUE);
		Response response1 = client.target("http://localhost:8080/employees/api/v1/addEmployee").request()
				.post(empEntity);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response1.getStatus(), "Http Response should be 400: ");

		employee.setJoiningDate("2019/01/01");
		empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_VALUE);
		Response response2 = client.target("http://localhost:8080/employees/api/v1/addEmployee").request()
				.post(empEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response2.getStatus(), "Http Response should be 500: ");

		employee.setJoiningDate("2019-03-01");
		employee.setEmployeeId(4l);
		empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_VALUE);
		Response response3 = client.target("http://localhost:8080/employees/api/v1/addEmployee").request()
				.post(empEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response3.getStatus(), "Http Response should be 500: ");

		employee.setJoiningDate("2019-01-01");
		employee.setEmployeeId(null);
		empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_VALUE);
		Response response4 = client.target("http://localhost:8080/employees/api/v1/addEmployee").request()
				.post(empEntity);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response4.getStatus(), "Http Response should be 500: ");

		employee.setJoiningDate("2019-04-01");
		employee.setFirstName("Rob");
		employee.setLastName("Johnshon");
		empEntity = Entity.entity(employee, MediaType.APPLICATION_JSON_VALUE);
		Response response5 = client.target("http://localhost:8080/employees/api/v1/addEmployee").request()
				.post(empEntity);
		assertEquals(HttpStatus.CREATED.value(), response5.getStatus(), "Http Response should be 204: ");
	}

	@DisplayName("DELETE_EMPLOYEE")
	@Test
	public void testDeleteEmployee() {
		Response response1 = client.target("http://localhost:8080/employees/api/v1/delete/1.5").request().delete();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response1.getStatus(), "Http Response should be 400: ");

		Response response2 = client.target("http://localhost:8080/employees/api/v1/delete/10").request().delete();
		assertEquals(HttpStatus.NOT_FOUND.value(), response2.getStatus(), "Http Response should be 404: ");

		Response response3 = client.target("http://localhost:8080/employees/api/v1/delete/0").request().delete();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response3.getStatus(), "Http Response should be 500: ");

		Response response4 = client.target("http://localhost:8080/employees/api/v1/delete/2").request().delete();
		assertEquals(HttpStatus.NO_CONTENT.value(), response4.getStatus(), "Http Response should be 204: ");
	}

}
