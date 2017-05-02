/**
 * 
 */
package com.mabsisa.service.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.UserDetail;
import com.mabsisa.dao.security.AuthenticationDao;
import com.mabsisa.service.security.AuthenticationService;

/**
 * @author abhinab
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private AuthenticationDao authenticationDao; 

	/* (non-Javadoc)
	 * @see com.mabsisa.service.security.AuthenticationService#getUserDetailByUsername(java.lang.String)
	 */
	@Override
	public UserDetail getUserDetailByUsername(String username) {
		return authenticationDao.getUserDetailByUsername(username);
	}

}
