/**
 * 
 */
package com.mabsisa.service.employeemanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.Employee;
import com.mabsisa.dao.employeemanagement.EmployeeManagementDao;
import com.mabsisa.service.employeemanagement.EmployeeManagementService;

/**
 * @author abhinab
 *
 */
@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

	@Autowired
	private EmployeeManagementDao employeeManagementDao;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public Employee save(Employee employee) {
		return employeeManagementDao.save(generatePasswordEncodedEmployee(employee));
	}

	@Override
	public Employee update(Employee employee) {
		return employeeManagementDao.update(employee);
	}

	@Override
	public Employee delete(Employee employee) {
		return employeeManagementDao.delete(employee);
	}

	@Override
	public List<Employee> retrieveEmployee() {
		return employeeManagementDao.retrieveEmployee();
	}

	@Override
	public Employee fetchByEmployeeId(Long employeeId) {
		return employeeManagementDao.fetchByEmployeeId(employeeId);
	}

	private Employee generatePasswordEncodedEmployee(Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employee;
	}
	
}
