/**
 * 
 */
package com.mabsisa.service.customermanagement;

import java.util.List;

import com.mabsisa.common.model.CustomerDetail;

/**
 * @author abhinab
 *
 */
public interface CustomerManagementService {

	List<CustomerDetail> retrieveCustomerDetail();
	
}
