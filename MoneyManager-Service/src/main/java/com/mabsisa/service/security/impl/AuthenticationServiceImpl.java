/**
 * 
 */
package com.mabsisa.service.security.impl;

import org.springframework.stereotype.Service;

import com.mabsisa.common.model.UserDetail;
import com.mabsisa.common.model.UserStatus;
import com.mabsisa.service.security.AuthenticationService;

/**
 * @author abhinab
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	/* (non-Javadoc)
	 * @see com.mabsisa.service.security.AuthenticationService#getUserDetailByUsername(java.lang.String)
	 */
	@Override
	public UserDetail getUserDetailByUsername(String username) {
		return new UserDetail(username, "$2a$11$aINNKEQ/DMB9jKB/vD0ewui6ajj/8rkntk1EKOwhJU4eMw81NEJTq", "ADMIN", "", "", "", UserStatus.ACTIVE);
	}

}
