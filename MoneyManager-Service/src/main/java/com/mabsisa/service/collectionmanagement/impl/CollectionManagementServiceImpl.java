/**
 * 
 */
package com.mabsisa.service.collectionmanagement.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerCollectionDetail;
import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.model.PaymentDetail;
import com.mabsisa.common.model.PaymentStatus;
import com.mabsisa.common.model.UserDetail;
import com.mabsisa.common.utils.CommonUtils;
import com.mabsisa.dao.collectionmanagement.CollectionManagementDao;
import com.mabsisa.dao.customermanagement.CustomerManagementDao;
import com.mabsisa.dao.security.AuthenticationDao;
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
	
	@Autowired
	private AuthenticationDao authenticationDao;
	
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

	@Override
	public CustomerDetail update(CustomerDetail customerDetail) {
		return customerManagementDao.updateWithCollector(customerDetail);
	}

	@Override
	public List<CustomerDetail> fetchByLoggedInUser(String username) {
		return customerManagementDao.fetchByLoggedInUser(username);
	}

	@Override
	public CustomerDetail getCustomerDetailByPaymentHistory(Long customerId) {
		CustomerDetail customerDetail = customerManagementDao.fetchByCustomerId(customerId);
		CustomerCollectionDetail customerCollectionDetail = collectionManagementDao.fetchCustomerCollectionDetail(customerId);
		if (customerCollectionDetail != null) {
			List<PaymentDetail> paymentDetails = customerCollectionDetail.getPaymentDetails();
			if (paymentDetails != null && !paymentDetails.isEmpty()) {
				BigDecimal fee = customerDetail.getFee();
				for (PaymentDetail paymentDetail : paymentDetails) {
					if (paymentDetail.getPaymentStatus() == PaymentStatus.UNPAID) {
						fee.add(paymentDetail.getRequestedAmount()); 
					} else if (paymentDetail.getPaymentStatus() == PaymentStatus.PARTIALLY_PAID) {
						fee.add(paymentDetail.getRequestedAmount().subtract(paymentDetail.getPaidAmount()));
					}
				}
			}
		}
		return customerDetail;
	}

	@Override
	public CustomerDetail save(CustomerDetail customerDetail, BigDecimal actualAmount, String username) {
		UserDetail userDetail = authenticationDao.getUserDetailByUsername(username);
		CustomerCollectionDetail customerCollectionDetail = collectionManagementDao.fetchCustomerCollectionDetail(customerDetail.getCustomerId());
		if (userDetail != null && customerCollectionDetail != null) {
			customerCollectionDetail.setCollectorId(userDetail.getUserId());
			PaymentDetail paymentDetail = new PaymentDetail();
			if (actualAmount.compareTo(customerDetail.getFee()) > 0) {
				BigDecimal excessAmount = actualAmount.subtract(customerDetail.getFee());
				int count = 1;
				List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
				while(excessAmount.compareTo(new BigDecimal("0.00")) == 0) {
					paymentDetail = new PaymentDetail();
					paymentDetail.setPaymentStatus(PaymentStatus.PAID);
					paymentDetail.setMmyy(CommonUtils.getFutureMMYYYY(count-1));
					paymentDetail.setRequestedAmount(customerDetail.getFee());
					paymentDetail.setPaidAmount(excessAmount.compareTo(customerDetail.getFee()) >= 0 ? customerDetail.getFee() : excessAmount);
					paymentDetail.setCollectionDate(new Date(System.currentTimeMillis()));
					excessAmount = excessAmount.subtract(customerDetail.getFee());
					paymentDetails.add(paymentDetail);
					count++;
				}
				customerCollectionDetail.setPaymentDetails(paymentDetails);
			} else {
				if (actualAmount.compareTo(new BigDecimal("0.00")) == 0) {
					paymentDetail.setPaymentStatus(PaymentStatus.UNPAID);
					paymentDetail.setMmyy(CommonUtils.getCurrentMMYYYY());
					paymentDetail.setRequestedAmount(customerDetail.getFee());
					paymentDetail.setPaidAmount(actualAmount);
					paymentDetail.setCollectionDate(new Date(System.currentTimeMillis()));
				} else if (actualAmount.compareTo(customerDetail.getFee()) < 0) {
					paymentDetail.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
					paymentDetail.setMmyy(CommonUtils.getCurrentMMYYYY());
					paymentDetail.setRequestedAmount(customerDetail.getFee());
					paymentDetail.setPaidAmount(actualAmount);
					paymentDetail.setCollectionDate(new Date(System.currentTimeMillis()));
				} else if (actualAmount.compareTo(customerDetail.getFee()) == 0) {
					paymentDetail.setPaymentStatus(PaymentStatus.PAID);
					paymentDetail.setMmyy(CommonUtils.getCurrentMMYYYY());
					paymentDetail.setRequestedAmount(customerDetail.getFee());
					paymentDetail.setPaidAmount(actualAmount);
					paymentDetail.setCollectionDate(new Date(System.currentTimeMillis()));
				}
				if (customerCollectionDetail.getPaymentDetails() != null && !customerCollectionDetail.getPaymentDetails().isEmpty()) {
					customerCollectionDetail.getPaymentDetails().add(paymentDetail);
				} else {
					List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
					paymentDetails.add(paymentDetail);
					customerCollectionDetail.setPaymentDetails(paymentDetails);
				}
			}
			
		}
		return customerDetail;
	}

	@Override
	public void sync() {
		List<CustomerDetail> customerDetails = customerManagementDao.retrieveCustomerDetail();
		if (customerDetails != null && !customerDetails.isEmpty()) {
			CustomerCollectionDetail customerCollectionDetail;
			for (CustomerDetail customerDetail : customerDetails) {
				customerCollectionDetail = new CustomerCollectionDetail();
				customerCollectionDetail.setCustomerId(customerDetail.getCustomerId());
				try {
					collectionManagementDao.save(customerCollectionDetail);
				} catch(Exception e) {
					
				}
			}
		}
	}

}
