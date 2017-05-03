/**
 * 
 */
package com.mabsisa.web.router;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabsisa.common.model.Employee;
import com.mabsisa.common.model.Role;
import com.mabsisa.common.model.UserStatus;
import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.service.employeemanagement.EmployeeManagementService;

/**
 * @author abhinab
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeManagementRouter {
	
	@Autowired
	private EmployeeManagementService employeeManagementService; 
	
	private List<String> roles;
	private List<String> userStatuses;
	
	@PostConstruct
	public void init() {
		if (null == roles) {
			roles = new ArrayList<String>();
			for (Role role : Role.values()) {
				roles.add(role.name());
			}
		}
		if (null == userStatuses) {
			userStatuses = new ArrayList<String>();
			for (UserStatus userStatus : UserStatus.values()) {
				userStatuses.add(userStatus.name());
			}
		}
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("roles", roles);
		model.addAttribute("userStatuses", userStatuses);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "employeemanagement/addemployee";
	}
	
	@PostMapping(value = "/addupdate", params = "action=add")
	public String add(@ModelAttribute("employee") Employee employee, Model model) {
		if (!isValid(employee)) {
			model.addAttribute("message", "Invalid data detected");
			model.addAttribute("employee", employee);
			model.addAttribute("roles", roles);
			model.addAttribute("userStatuses", userStatuses);
			model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
			return "employeemanagement/addemployee";
		}
		try {
			employee = employeeManagementService.save(employee);
			model.addAttribute("message", "Record added successfully");
		} catch(Exception e) {
			model.addAttribute("message", "Can't add employee at this moment");
		}
		model.addAttribute("employee", employee);
		model.addAttribute("roles", roles);
		model.addAttribute("userStatuses", userStatuses);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "employeemanagement/addemployee";
	}
	
	@GetMapping("/view")
	public String view(Model model) {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			employees = employeeManagementService.retrieveEmployee();
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("employees", employees);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "employeemanagement/listemployee";
	}
	
	@GetMapping("/update/{employeeId}")
	public String update(@PathVariable("employeeId") String employeeId, Model model) {
		Employee employee;
		try {
			employee = employeeManagementService.fetchByEmployeeId(Long.valueOf(employeeId));
		} catch(Exception e) {
			model.addAttribute("message", "No data found");
			employee = new Employee();
		}
		model.addAttribute("roles", roles);
		model.addAttribute("userStatuses", userStatuses);
		model.addAttribute("status", 1);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "employeemanagement/addemployee";
	}

	private boolean isValid(Employee employee) {
		if (employee != null && 
				employee.getUsername() != null &&
				employee.getPassword() != null &&
				employee.getRole() != null) {
			return true;
		}
		return false;
	}
	
}
