/**
 * 
 */
package com.mabsisa.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mabsisa.common.model.UserDetail;
import com.mabsisa.common.model.UserStatus;
import com.mabsisa.service.security.AuthenticationService;

/**
 * @author abhinab
 *
 */
@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			UserDetail userDetail;
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				userDetail = authenticationService.getUserDetailByUsername(username);
				if (userDetail != null && userDetail.getUserStatus() == UserStatus.ACTIVE) {
					return userDetail;
				} else {
					throw new UsernameNotFoundException("user credential not found");
				}
			}
		};
	}

	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(11);
		return encoder;
	}

}
