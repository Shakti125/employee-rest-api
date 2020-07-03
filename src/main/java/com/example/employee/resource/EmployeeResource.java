/**
 * 
 */
package com.example.employee.resource;

import java.lang.reflect.Field;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.employee.dto.EmployeeBean;
import com.example.employee.exception.EmployeeNotFountException;
import com.example.employee.exception.EmployeeServiceException;
import com.example.employee.service.EmployeeService;
import com.example.employee.utility.CustomeReponseUtility;

/**
 * @author Shakti
 *
 */
@Path("/api/v1/")
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class EmployeeResource {

	private final Logger logger = LoggerFactory.getLogger(EmployeeResource.class);

	@Autowired
	private EmployeeService employeeService;

	@GET
	@Path("/{empId}")
	public Response getEmployeeById(@PathParam("empId") String empId) {
		logger.info("EmployeeResource | getEmployeeById | entry");
		Long id = null;
		try {
			id = Long.parseLong(empId);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException | getEmployeeById | Invalid employee id: {}", empId);
			return Response
					.status(HttpStatus.BAD_REQUEST.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse("Invalid employee id: " + empId, HttpStatus.BAD_REQUEST.value()))
					.build();
		}
		EmployeeBean employee = null;
		try {
			employee = employeeService.getEmployeeById(id);
			if (employee == null) {
				logger.error("EmployeeNotFountException | Unable to find the employee for the given id: {}", id);
				throw new EmployeeNotFountException("Unable to find the employee for the given id: " + id);
			}
			logger.info("EmployeeResource | getEmployeeById | exit");
			return Response.status(HttpStatus.OK.value()).entity(employee).build();
		} catch (EmployeeServiceException e) {
			logger.error("EmployeeServiceException | getEmployeeById | message: {}", e.getMessage());
			return Response
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.build();
		}
	}

	@GET
	@Path("/allEmployess")
	public Response getAllEmployees() {
		logger.info("EmployeeResource | getAllEmployees | exit");
		List<EmployeeBean> employees = null;
		try {
			employees = employeeService.getAllEmployees();
			logger.info("EmployeeResource | getAllEmployees | exit");
			return Response.status(HttpStatus.OK.value()).entity(employees).build();
		} catch (EmployeeServiceException e) {
			return Response
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.build();
		}
	}

	@POST
	@Path("/addEmployee")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response addEmployee(EmployeeBean newEmployee) throws IllegalArgumentException, IllegalAccessException {
		logger.info("EmployeeResource | addEmployee | entry");
		EmployeeBean employee = null;

		if (!validateAddEmployeeRequestBody(newEmployee)) {
			logger.error("Falied to validate request body since request body is invalid");
			return Response
					.status(HttpStatus.BAD_REQUEST.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse("Invalid employee details ", HttpStatus.BAD_REQUEST.value()))
					.build();
		}

		try {
			employee = employeeService.addEmployee(newEmployee);
			String message = "Employee added successfully with id " + employee.getEmployeeId();
			logger.info("EmployeeResource | addEmployee | exit");
			return Response.status(HttpStatus.CREATED.value())
					.entity(CustomeReponseUtility.createCustomSuccessResponse(message, HttpStatus.CREATED.value()))
					.build();
		} catch (EmployeeServiceException e) {
			logger.error("EmployeeServiceException | addEmployee | message: {}", e.getMessage());
			return Response
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.build();
		}

	}

	private boolean validateAddEmployeeRequestBody(EmployeeBean employee)
			throws IllegalArgumentException, IllegalAccessException {
		boolean valid = true;
		for (Field f : employee.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (!f.getName().equalsIgnoreCase("employeeId")
					&& (f.get(employee) == null || f.get(employee).toString().isEmpty())) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	@DELETE
	@Path("/delete/{empId}")
	public Response deleteEmployee(@PathParam("empId") String empId) {
		logger.info("EmployeeResource | addEmployee | entry");
		Long id = null;
		try {
			id = Long.parseLong(empId);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException | deleteEmployee | Invalid employee id: {}", empId);
			return Response
					.status(HttpStatus.BAD_REQUEST.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse("Invalid employee id: " + empId, HttpStatus.BAD_REQUEST.value()))
					.build();
		}
		try {
			employeeService.deleteEmployee(id);
			logger.info("EmployeeResource | addEmployee | exit");
			return Response.noContent().build();
		} catch (EmployeeServiceException e) {
			logger.error("EmployeeServiceException | deleteEmployee | message: {}", e.getMessage());
			return Response
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(CustomeReponseUtility
							.createCustomErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.build();
		}
	}

}
