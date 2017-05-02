/**
 * 
 */
package com.mabsisa.service.customermanagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.dao.customermanagement.CustomerManagementDao;
import com.mabsisa.service.customermanagement.CustomerManagementService;

/**
 * @author abhinab
 *
 */
@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

	@Autowired
	private CustomerManagementDao customerManagementDao;
	
	/* (non-Javadoc)
	 * @see com.mabsisa.service.customermanagement.CustomerManagementService#retrieveCustomerDetail()
	 */
	@Override
	public List<CustomerDetail> retrieveCustomerDetail() {
		return customerManagementDao.retrieveCustomerDetail();
	}

}
