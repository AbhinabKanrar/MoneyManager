/**
 * 
 */
package com.mabsisa.service.collectionmanagement;

import java.util.List;

import com.mabsisa.common.model.CollectionDetail;

/**
 * @author abhinab
 *
 */
public interface CollectionManagementService {

	List<CollectionDetail> retrieveCollectionDetails();
	
}
