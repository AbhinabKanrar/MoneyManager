/**
 * 
 */
package com.mabsisa.dao.customermanagement;

import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
public interface CustomerManagementDao {

	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	List<CustomerDetail> retrieveCustomerDetail();
	
}
