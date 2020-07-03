/**
 * 
 */
package com.example.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.model.Employee;

/**
 * @author Shakti
 *
 */
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
