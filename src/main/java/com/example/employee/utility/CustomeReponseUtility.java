/**
 * 
 */
package com.example.employee.utility;

import java.time.LocalDateTime;

import com.example.employee.dto.CustomErrorResponse;
import com.example.employee.dto.CustomSuccessResponse;

/**
 * @author Shakti
 *
 */
public class CustomeReponseUtility {

	private CustomeReponseUtility() {

	}

	public static CustomErrorResponse createCustomErrorResponse(String errorMessage, int status) {
		CustomErrorResponse errorResponse = new CustomErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setErrorMessage(errorMessage);
		errorResponse.setStatus(status);
		return errorResponse;
	}

	public static CustomSuccessResponse createCustomSuccessResponse(String successMessage, int status) {
		CustomSuccessResponse successResponse = new CustomSuccessResponse();
		successResponse.setTimestamp(LocalDateTime.now());
		successResponse.setSuccessMessage(successMessage);
		successResponse.setStatus(status);
		return successResponse;
	}
}
