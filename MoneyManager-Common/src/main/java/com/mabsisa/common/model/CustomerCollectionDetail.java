package com.mabsisa.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author abhinab
 *
 */
public class CustomerCollectionDetail implements Serializable {

	private static final long serialVersionUID = -5541869313920348648L;
	
	private Long collectionId;
	private Long customerId;
	private Long collectorId;
	private List<PaymentDetail> paymentDetails;
	
	public Long getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}
	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	
}
