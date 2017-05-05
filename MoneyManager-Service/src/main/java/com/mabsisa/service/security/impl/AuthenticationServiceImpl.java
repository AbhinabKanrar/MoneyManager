/**
 * 
 */
package com.mabsisa.service.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	private static BCryptPasswordEncoder passwordEncoder;
	
	static {
		passwordEncoder = new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetail getUserDetailByUsername(String username) {
		return authenticationDao.getUserDetailByUsername(username);
	}

	@Override
	public void update(String username, String password) {
		authenticationDao.update(username, passwordEncoder.encode(password));
	}

}
