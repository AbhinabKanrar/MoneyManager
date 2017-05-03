/**
 * 
 */
package com.mabsisa.dao.employeemanagement;

import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.mabsisa.common.model.Employee;
import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
public interface EmployeeManagementDao {

	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	Employee save(Employee employee);
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	Employee update(Employee employee);
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	Employee delete(Employee employee);
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	List<Employee> retrieveEmployee();
	
	@Retryable(maxAttempts=CommonConstant.DB_RETRY_COUNT,value=DataAccessResourceFailureException.class,backoff=@Backoff(delay = CommonConstant.DB_RETRY_DELAY))
	Employee fetchByEmployeeId(Long employeeId);
	
}
