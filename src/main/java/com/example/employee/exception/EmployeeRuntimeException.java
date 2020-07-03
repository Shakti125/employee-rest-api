/**
 * 
 */
package com.example.employee.exception;

/**
 * @author Shakti
 *
 */
public class EmployeeRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public EmployeeRuntimeException() {
	}

	/**
	 * @param message
	 */
	public EmployeeRuntimeException(String message) {
		super(message);
	}

}
