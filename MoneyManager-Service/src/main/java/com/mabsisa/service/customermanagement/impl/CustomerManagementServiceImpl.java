/**
 * 
 */
package com.mabsisa.service.customermanagement.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.utils.CommonUtils;
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

	@Override
	public CustomerDetail save(CustomerDetail customerDetail) {
		return customerManagementDao.save(customerDetail);
	}

	@Override
	public void save(File excel) throws IOException, InvalidFormatException {
		List<CustomerDetail> customerDetails = CommonUtils.generateCollectionDetails(excel);
		customerManagementDao.save(customerDetails);
	}
	
	@Override
	public CustomerDetail update(CustomerDetail customerDetail) {
		return customerManagementDao.update(customerDetail);
	}
	
	@Override
	public CustomerDetail delete(CustomerDetail customerDetail) {
		return customerManagementDao.delete(customerDetail);
	}

	@Override
	public List<CustomerDetail> retrieveCustomerDetail() {
		return customerManagementDao.retrieveCustomerDetail();
	}
	
	@Override
	public CustomerDetail fetchByCustomerId(Long customerId) {
		return customerManagementDao.fetchByCustomerId(customerId);
	}

}
