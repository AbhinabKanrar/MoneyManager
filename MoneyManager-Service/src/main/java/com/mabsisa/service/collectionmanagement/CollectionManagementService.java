/**
 * 
 */
package com.mabsisa.service.collectionmanagement;

import java.math.BigDecimal;
import java.util.List;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerDetail;

/**
 * @author abhinab
 *
 */
public interface CollectionManagementService {

	List<CollectionDetail> retrieveCollectionDetails();
	List<CustomerDetail> fetchByRegion();
	List<CustomerDetail> fetchByBuilding(String region);
	CustomerDetail update(CustomerDetail customerDetail);
	void updateWithCollector(CustomerDetail customerDetail);
	List<CustomerDetail> fetchByLoggedInUser(String username);
	CustomerDetail getCustomerDetailByPaymentHistory(Long customerId);
	CustomerDetail save(CustomerDetail customerDetail, BigDecimal actualAmount, String username);
	
}
