/**
 * 
 */
package com.mabsisa.service.collectionmanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.dao.collectionmanagement.CollectionManagementDao;
import com.mabsisa.dao.customermanagement.CustomerManagementDao;
import com.mabsisa.service.collectionmanagement.CollectionManagementService;

/**
 * @author abhinab
 *
 */
@Service
public class CollectionManagementServiceImpl implements CollectionManagementService {

	@Autowired
	private CollectionManagementDao collectionManagementDao;
	
	@Autowired
	private CustomerManagementDao customerManagementDao;
	
	@Override
	public List<CollectionDetail> retrieveCollectionDetails() {
		return collectionManagementDao.retrieveCollectionDetails();
	}

	@Override
	public List<CustomerDetail> fetchByRegion() {
		return customerManagementDao.fetchByRegion();
	}

	@Override
	public List<CustomerDetail> fetchByBuilding(String region) {
		return customerManagementDao.fetchByBuilding(region);
	}

	@Override
	public void updateWithCollector(CustomerDetail customerDetail) {
		customerManagementDao.updateWithCollector(customerDetail);
	}

}
