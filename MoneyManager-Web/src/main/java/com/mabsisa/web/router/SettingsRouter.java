/**
 * 
 */
package com.mabsisa.web.router;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.service.security.AuthenticationService;

/**
 * @author abhinab
 *
 */
@Controller
@RequestMapping("/settings")
public class SettingsRouter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping
	public String settings(Model model) {
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "settings/settings";
	}
	
	@PostMapping("/changepwd")
	private String changePwd(HttpServletRequest request, Model model, Principal principal) {
		try {
			String newPassword = ServletRequestUtils.getRequiredStringParameter(request, "newPassword");
			String username = principal.getName();
			authenticationService.update(username, newPassword);
		} catch (ServletRequestBindingException e) {
			model.addAttribute("message", "Invalid password");
		}
		model.addAttribute("message", "Password chnaged successfully");
		model.addAttribute("access", CommonUtils.getLoggedInUserAccess());
		return "settings/settings";
	}
	
}
