/**
 * 
 */
package com.mabsisa.service.customermanagement;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.mabsisa.common.model.CustomerDetail;

/**
 * @author abhinab
 *
 */
public interface CustomerManagementService {

	List<CustomerDetail> retrieveCustomerDetail();
	CustomerDetail save(CustomerDetail customerDetail);
	void save(File excel) throws IOException, InvalidFormatException;
	CustomerDetail fetchByCustomerId(Long customerId);
	CustomerDetail update(CustomerDetail customerDetail);
	CustomerDetail delete(CustomerDetail customerDetail);
	
}
