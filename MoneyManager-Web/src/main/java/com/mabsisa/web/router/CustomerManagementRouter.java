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
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabsisa.common.model.CustomerDetail;
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

	@GetMapping("/view")
	public String view(Model model) {
		List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
		try {
			customerDetails = customerManagementService.retrieveCustomerDetail();
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("customerDetails", customerDetails);
		return "customermanagement/listcustomer";
	}
	
}
