/**
 * 
 */
package com.mabsisa.common.model;

import java.io.Serializable;

/**
 * @author abhinab
 *
 */
public class CustomerPaymentDetail implements Serializable {

	private static final long serialVersionUID = 181747957601252667L;
	
	private String month;
	private boolean paid;
	
	public CustomerPaymentDetail() {}

	public CustomerPaymentDetail(String month, boolean paid) {
		this.month = month;
		this.paid = paid;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

}
