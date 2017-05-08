/**
 * 
 */
package com.mabsisa.web.router;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/collectiondashboard";
	}
	
	@GetMapping("/assignment/view/{customerId}")
	public String assignmentViewById(@PathVariable("customerId") String customerId, Model model) {
		CustomerDetail customerDetail = null;
		List<Employee> employees = new ArrayList<Employee>();
		try {
			customerDetail = customerManagementService.fetchByCustomerId(Long.valueOf(customerId));
			employees = employeeManagementService.retrieveEmployee();
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("employees", employees);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/singleassignment";
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
	
	@GetMapping("/myassignment/view")
	public String myAssignmentView(Principal principal, Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			customerDetails = collectionManagementService.fetchByLoggedInUser(principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/viewmyassignment";
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
				model.addAttribute("successMessage", "Assignment done successfully");
				model.addAttribute("status", 1);
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
	
	@PostMapping("/update")
	public String update(@ModelAttribute("customerDetail") CustomerDetail customerDetail, Model model) {
		try {
			collectionManagementService.updateWithCollector(customerDetail);
			model.addAttribute("successMessage", "Assignment done successfully");
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to update the data at this moment");
		}
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/singleassignment";
	}

	@GetMapping("/myassignment/viewbycustomerid/{customerId}")
	public String myAssignmentView(@PathVariable("customerId") String customerId, Model model) {
		CustomerDetail customerDetail = null;
		try {
			customerDetail = collectionManagementService.getCustomerDetailByPaymentHistory(Long.valueOf(customerId));
		} catch (Exception e) {
			e.printStackTrace();
			customerDetail = new CustomerDetail();
			model.addAttribute("errMessage", "Unable to fetch the data at this moment");
		}
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/payment/customerpaymentdetail";
	}
	
	@PostMapping("/payment/update")
	public String paymentUpdate(@ModelAttribute("customerDetail") CustomerDetail customerDetail, HttpServletRequest request, Model model, Principal principal) {
		try {
			customerDetail = collectionManagementService.save(customerDetail, new BigDecimal(ServletRequestUtils.getStringParameter(request, "payingamount")), principal.getName());
			model.addAttribute("successMessage", "Customer payment data updated successfully");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Unable to update customer data at this moment");
		}
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "collectionmanagement/payment/customerpaymentdetail";
	}

	@GetMapping("/sync")
	public String sync(Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			collectionManagementService.sync();
			customerDetails = customerManagementService.retrieveCustomerDetail();
			model.addAttribute("successMessage", "Synced with the system");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errMessage", "Data synced; but unable to fetch the customer data at this moment");
		}
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/listcustomer";
	}
	
}
