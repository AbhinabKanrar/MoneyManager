/**
 * 
 */
package com.mabsisa.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author abhinab
 *
 */
public class CollectionDetail implements Serializable {

	private static final long serialVersionUID = 181747957601252667L;
	
	private Long customerPaymentId;
	private Long customerId;
	private Long userId;
	private BigDecimal requestedPayementAmount;
	private BigDecimal actualPayementAmount;
	private boolean paid;

	public Long getCustomerPaymentId() {
		return customerPaymentId;
	}
	public void setCustomerPaymentId(Long customerPaymentId) {
		this.customerPaymentId = customerPaymentId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getRequestedPayementAmount() {
		return requestedPayementAmount;
	}
	public void setRequestedPayementAmount(BigDecimal requestedPayementAmount) {
		this.requestedPayementAmount = requestedPayementAmount;
	}
	public BigDecimal getActualPayementAmount() {
		return actualPayementAmount;
	}
	public void setActualPayementAmount(BigDecimal actualPayementAmount) {
		this.actualPayementAmount = actualPayementAmount;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
}
