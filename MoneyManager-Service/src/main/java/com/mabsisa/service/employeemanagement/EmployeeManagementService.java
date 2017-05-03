/**
 * 
 */
package com.mabsisa.service.employeemanagement;

import java.util.List;

import com.mabsisa.common.model.Employee;

/**
 * @author abhinab
 *
 */
public interface EmployeeManagementService {
	
	Employee save(Employee employee);
	Employee update(Employee employee);
	Employee delete(Employee employee);
	List<Employee> retrieveEmployee();
	Employee fetchByEmployeeId(Long employeeId);

}
