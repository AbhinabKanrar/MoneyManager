/**
 * 
 */
package com.mabsisa.web.router;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.service.customermanagement.CustomerManagementService;

/**
 * @author abhinab
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerManagementRouter {

	@Autowired
	private CustomerManagementService customerManagementService;

	private static final String fileExtentions = ".xlsx";
	
	@GetMapping("/add")
	public String add(Model model) {
		CustomerDetail customerDetail = new CustomerDetail(new BigDecimal("0.00"));
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomer";
	}

	@GetMapping("/add/excel")
	public String addExcel(Model model) {
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomerexcel";
	}
	
	@PostMapping("/customerexcelupload")
	public String customerexcelupload(@RequestParam MultipartFile excel, Model model) {
		if (!isValidExcel(excel)) {
			model.addAttribute("message", "Invalid file");
			model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
			return "customermanagement/addcustomerexcel";
		}
		try {
			String destination = "/tmp/"+ excel.getOriginalFilename();
			File file = new File(destination);
			excel.transferTo(file);
			customerManagementService.save(file);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Unable to save the data at this moment");
		}
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomerexcel";
	}
	
	@PostMapping(value = "/addupdate", params = "action=add")
	public String add(@ModelAttribute("customerDetail") CustomerDetail customerDetail, Model model) {
		if (!isValid(customerDetail)) {
			model.addAttribute("message", "Invalid data detected");
			model.addAttribute("customerDetail", customerDetail);
			model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
			return "customermanagement/addcustomer";
		}
		try {
			customerDetail = customerManagementService.save(customerDetail);
			model.addAttribute("message", "Record added successfully");
		} catch (Exception e) {
			model.addAttribute("message", "Can't add customer at this moment");
		}
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomer";
	}

	@PostMapping(value = "/addupdate", params = "action=update")
	public String update(@ModelAttribute("customerDetail") CustomerDetail customerDetail, Model model) {
		if (!isValid(customerDetail)) {
			model.addAttribute("message", "Invalid data detected");
			model.addAttribute("status", 1);
			model.addAttribute("customerDetail", customerDetail);
			model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
			return "customermanagement/addcustomer";
		}
		try {
			customerDetail = customerManagementService.update(customerDetail);
			model.addAttribute("message", "Record updated successfully");
		} catch (Exception e) {
			model.addAttribute("message", "Can't update customer at this moment");
		}
		model.addAttribute("status", 1);
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomer";
	}

	@PostMapping(value = "/addupdate", params = "action=delete")
	public String delete(@ModelAttribute("customerDetail") CustomerDetail customerDetail, Model model) {
		try {
			customerDetail = customerManagementService.delete(customerDetail);
			model.addAttribute("message", "Record deleted successfully");
		} catch (Exception e) {
			model.addAttribute("message", "Can't delete customer at this moment");
		}
		model.addAttribute("status", 2);
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomer";
	}
	
	@GetMapping("/view")
	public String view(Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			customerDetails = customerManagementService.retrieveCustomerDetail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/listcustomer";
	}

	@GetMapping("/update/{customerId}")
	public String update(@PathVariable("customerId") String customerId, Model model) {
		CustomerDetail customerDetail;
		try {
			customerDetail = customerManagementService.fetchByCustomerId(Long.valueOf(customerId));
		} catch (Exception e) {
			model.addAttribute("message", "No data found");
			customerDetail = new CustomerDetail(new BigDecimal("0.00"));
		}
		model.addAttribute("status", 1);
		model.addAttribute("customerDetail", customerDetail);
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "customermanagement/addcustomer";
	}

	private boolean isValid(CustomerDetail customerDetail) {
		if (customerDetail != null && customerDetail.getRegion() != null && customerDetail.getAddress() != null
				&& Pattern.matches("[0-9]+(\\.){0,1}[0-9]*", String.valueOf(customerDetail.getFee()))) {
			return true;
		}
		return false;
	}

	private boolean isValidExcel(MultipartFile excel) {
		if (excel != null) {
			String fileName = excel.getOriginalFilename();
			if (fileName != null && !fileName.isEmpty()) {
				int lastIndex = fileName.lastIndexOf('.');
				if (fileName.substring(lastIndex, fileName.length()).equalsIgnoreCase(fileExtentions)) {
					return true;
				}
			}
		}
		return false;
	}
	
}
