/**
 * 
 */
package com.mabsisa.service.collectionmanagement;

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
	void updateWithCollector(CustomerDetail customerDetail);
	
}
