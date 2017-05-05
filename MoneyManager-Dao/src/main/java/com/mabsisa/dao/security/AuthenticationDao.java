/**
 * 
 */
package com.mabsisa.dao.security;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.mabsisa.common.model.UserDetail;
import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
public interface AuthenticationDao {

	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	UserDetail getUserDetailByUsername(String username);
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	void update(String username, String password);
	
}
