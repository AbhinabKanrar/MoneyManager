/**
 * 
 */
package com.mabsisa.service.security;

import com.mabsisa.common.model.UserDetail;

/**
 * @author abhinab
 *
 */
public interface AuthenticationService {

	UserDetail getUserDetailByUsername(String username);
	
}
