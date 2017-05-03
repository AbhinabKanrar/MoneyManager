/**
 * 
 */
package com.mabsisa.web.router;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mabsisa.common.utils.CommonConstant;
import com.mabsisa.common.utils.CommonUtils;

/**
 * @author abhinab
 *
 */
@Controller
public class DashboardRouter {

	@GetMapping(CommonConstant.URL_DEFAULT_SUCCESS)
	public String dahsboard(Model model) {
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "dashboard";
	}
	
}
