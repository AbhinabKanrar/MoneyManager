/**
 * 
 */
package com.mabsisa.dao.collectionmanagement;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerCollectionDetail;
import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
public interface CollectionManagementDao {

	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	List<CollectionDetail> retrieveCollectionDetails();
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	CustomerCollectionDetail fetchCustomerCollectionDetail(Long customerId);
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	public CustomerDetail savePayment(CustomerDetail customerDetail, CustomerCollectionDetail customerCollectionDetail, BigDecimal actualPayment);
	
}
