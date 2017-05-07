/**
 * 
 */
package com.mabsisa.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author abhinab
 *
 */
public class PaymentDetail implements Serializable {

	private static final long serialVersionUID = -7939864228465554065L;
	
	private String mmyy;
	private BigDecimal requestedAmount;
	private BigDecimal paidAmount;
	private PaymentStatus paymentStatus;
	private Date collectionDate;
	private String location;
	private String comment;

	public String getMmyy() {
		return mmyy;
	}
	public void setMmyy(String mmyy) {
		this.mmyy = mmyy;
	}
	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
