/**
 * 
 */
package com.mabsisa.common.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author abhinab
 *
 */
public class CommonUtils {
	
	public static String getLoggedInUserAccess() {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String role = null;
		for (GrantedAuthority grantedAuthority : roles) {
			role = grantedAuthority.getAuthority();
		}
		return role;
	}

}
