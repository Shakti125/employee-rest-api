/**
 * 
 */
package com.example.employee.service;

import java.util.List;

import com.example.employee.dto.EmployeeBean;
import com.example.employee.exception.EmployeeServiceException;

/**
 * @author Shakti
 *
 */
public interface EmployeeService {

	public List<EmployeeBean> getAllEmployees() throws EmployeeServiceException;

	public EmployeeBean getEmployeeById(Long id) throws EmployeeServiceException;

	public EmployeeBean addEmployee(EmployeeBean employee) throws EmployeeServiceException;

	public boolean deleteEmployee(Long id) throws EmployeeServiceException;
}
