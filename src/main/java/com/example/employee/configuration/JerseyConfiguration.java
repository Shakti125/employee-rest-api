/**
 * 
 */
package com.example.employee.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.example.employee.exception.EmployeeNotFountException;
import com.example.employee.resource.EmployeeResource;

/**
 * @author Shakti
 *
 */
@Component
@ApplicationPath("/employees/")
public class JerseyConfiguration extends ResourceConfig {

	/**
	 * 
	 */
	public JerseyConfiguration() {
		register(EmployeeResource.class);
		register(EmployeeNotFountException.class);
		register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO,
				LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
	}

}
