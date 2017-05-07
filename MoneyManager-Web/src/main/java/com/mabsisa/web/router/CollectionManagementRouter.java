/**
 * 
 */
package com.mabsisa.web.router;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.model.Employee;
import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.service.collectionmanagement.CollectionManagementService;
import com.mabsisa.service.customermanagement.CustomerManagementService;
import com.mabsisa.service.employeemanagement.EmployeeManagementService;

/**
 * @author abhinab
 *
 */
@Controller
@RequestMapping("/collection")
public class CollectionManagementRouter {
	
	@Autowired
	private CollectionManagementService collectionManagementService;
	
	@Autowired
	private CustomerManagementService customerManagementService;
	
	@Autowired
	private EmployeeManagementService employeeManagementService;
	
	@GetMapping("/view")
	public String view(Model model) {
//		List<CollectionDetail> collectionDetails = new ArrayList<CollectionDetail>();
//		try {
//			collectionDetails = collectionManagementService.retrieveCollectionDetails();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("collectionDetails", collectionDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/collectiondashboard";
	}
	
	@GetMapping("/assignment/view")
	public String assignmentView(Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			customerDetails = customerManagementService.retrieveCustomerDetail();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/listassignment";
	}
	
	@GetMapping("/bulkassignment")
	public String bulkassignment(Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			customerDetails = collectionManagementService.fetchByRegion();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetail", new CustomerDetail());
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/bulkassignment";
	}

	@PostMapping("/assign")
	public String assign(@ModelAttribute("customerDetail") CustomerDetail customerDetail, Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			if (customerDetail.getRegion() != null && customerDetail.getBuilding() != null && customerDetail.getCollectorId() != 0) {
				collectionManagementService.updateWithCollector(customerDetail);
			} else if (customerDetail.getRegion() != null && customerDetail.getBuilding() != null) {
				List<Employee> employees = new ArrayList<Employee>();
				employees = employeeManagementService.retrieveEmployee();
				model.addAttribute("employees", employees);
			} else if (customerDetail.getRegion() != null && customerDetail.getBuilding() == null) {
				customerDetails = collectionManagementService.fetchByBuilding(customerDetail.getRegion());
			}
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/bulkassignment";
	}
	
}
