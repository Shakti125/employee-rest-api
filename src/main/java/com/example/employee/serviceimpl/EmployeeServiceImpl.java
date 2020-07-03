/**
 * 
 */
package com.example.employee.serviceimpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.dao.EmployeeDao;
import com.example.employee.dto.EmployeeBean;
import com.example.employee.exception.EmployeeNotFountException;
import com.example.employee.exception.EmployeeServiceException;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

/**
 * @author Shakti
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeServiceImpl implements EmployeeService {

	private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<EmployeeBean> getAllEmployees() throws EmployeeServiceException {
		logger.info("EmployeeServiceImpl | getAllEmployees | fetching all employee information");
		try {
			return employeeDao.findAll().stream().filter(Objects::nonNull).map(emp -> createEmployeeBean(emp))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EmployeeServiceException(e.getMessage());
		}
	}

	@Override
	public EmployeeBean getEmployeeById(Long id) throws EmployeeServiceException {
		logger.info("EmployeeServiceImpl | getEmployeeById | fetching employee information by id");
		if (id == 0)
			throw new EmployeeServiceException("Unable to fetch employee details for given id: " + id);
		Optional<Employee> employeeOptional = null;
		try {
			employeeOptional = employeeDao.findById(id);
		} catch (Exception e) {
			throw new EmployeeServiceException(e.getMessage());
		}
		return employeeOptional.isPresent() ? createEmployeeBean(employeeOptional.get()) : null;
	}

	@Override
	public EmployeeBean addEmployee(EmployeeBean employee) throws EmployeeServiceException {
		logger.info("EmployeeServiceImpl | addEmployee | adding employee");
		if (getAllEmployees().contains(employee)) {
			throw new EmployeeServiceException(
					"Unable to create employee with given details, Employee is already present in the database.");
		}
		Employee newEmployee = new Employee();
		newEmployee.setFirstName(employee.getFirstName());
		newEmployee.setLastName(employee.getLastName());
		newEmployee.setDepartment(employee.getDepartment());
		newEmployee.setDateOfJoining(convertDateFromString(employee.getJoiningDate()));
		if (employee.getEmployeeId() != null)
			throw new EmployeeServiceException(
					"Unable to create employee with given id, Id will be assigned internally. Do not give Id explicitly.");
		else {
			try {
				newEmployee = employeeDao.saveAndFlush(newEmployee);
				logger.info("EmployeeServiceImpl | addEmployee | Employee added successfully with id: {}",
						newEmployee.getId());
				return createEmployeeBean(newEmployee);
			} catch (Exception e) {
				throw new EmployeeServiceException(e.getMessage());
			}
		}
	}

	private EmployeeBean createEmployeeBean(Employee employee) {
		EmployeeBean newEmployee = new EmployeeBean();
		newEmployee.setEmployeeId(employee.getId());
		newEmployee.setFirstName(employee.getFirstName());
		newEmployee.setLastName(employee.getLastName());
		newEmployee.setDepartment(employee.getDepartment());
		newEmployee.setJoiningDate(employee.getDateOfJoining().format(DateTimeFormatter.ISO_LOCAL_DATE));
		return newEmployee;
	}

	private LocalDate convertDateFromString(String date) throws EmployeeServiceException {
		try {
			return LocalDate.parse(date);
		} catch (Exception e) {
			throw new EmployeeServiceException(e.getMessage());
		}
	}

	@Override
	public boolean deleteEmployee(Long id) throws EmployeeServiceException {
		logger.info("EmployeeServiceImpl | deleteEmployee | deleting employee");
		EmployeeBean employee = getEmployeeById(id);
		if (employee == null) {
			throw new EmployeeNotFountException("Unable to find the employee for the given id: " + id);
		} else {
			try {
				employeeDao.deleteById(employee.getEmployeeId());
				logger.info("EmployeeServiceImpl | deleteEmployee | Employee deleted successfully with id : {}", id);
				return true;
			} catch (Exception e) {
				throw new EmployeeServiceException(e.getMessage());
			}
		}
	}

}
