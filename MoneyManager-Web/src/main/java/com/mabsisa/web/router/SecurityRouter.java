/**
 * 
 */
package com.mabsisa.web.router;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
@Controller
public class SecurityRouter {

	@GetMapping(CommonConstant.URL_LOGIN)
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:"+CommonConstant.URL_DEFAULT_SUCCESS;
		}
		return "login";
	}
	
}
