/**
 * 
 */
package com.example.employee.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.employee.utility.CustomeReponseUtility;

/**
 * @author Shakti
 *
 */
@Provider
public class EmployeeNotFountException extends EmployeeRuntimeException
		implements ExceptionMapper<EmployeeNotFountException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public EmployeeNotFountException() {
	}

	/**
	 * @param message
	 */
	public EmployeeNotFountException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(EmployeeNotFountException exception) {
		return Response
				.status(HttpStatus.NOT_FOUND.value()).entity(CustomeReponseUtility
						.createCustomErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()))
				.type(MediaType.APPLICATION_JSON_VALUE).build();
	}

}
