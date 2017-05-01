/**
 * 
 */
package com.mabsisa.web.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
@Controller
public class DashboardRouter {

	@GetMapping(CommonConstant.URL_DEFAULT_SUCCESS)
	public String dahsboard() {
		return "dashboard";
	}
	
}
