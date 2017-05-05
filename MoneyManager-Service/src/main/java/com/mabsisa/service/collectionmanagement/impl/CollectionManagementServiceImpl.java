/**
 * 
 */
package com.mabsisa.service.collectionmanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.dao.collectionmanagement.CollectionManagementDao;
import com.mabsisa.service.collectionmanagement.CollectionManagementService;

/**
 * @author abhinab
 *
 */
@Service
public class CollectionManagementServiceImpl implements CollectionManagementService {

	@Autowired
	private CollectionManagementDao collectionManagementDao;
	
	@Override
	public List<CollectionDetail> retrieveCollectionDetails() {
		return collectionManagementDao.retrieveCollectionDetails();
	}

}
