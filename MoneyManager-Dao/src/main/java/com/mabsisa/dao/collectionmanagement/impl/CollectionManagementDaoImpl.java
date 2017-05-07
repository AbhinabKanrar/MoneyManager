/**
 * 
 */
package com.mabsisa.dao.collectionmanagement.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.common.model.CustomerCollectionDetail;
import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.common.model.PaymentDetail;
import com.mabsisa.dao.collectionmanagement.CollectionManagementDao;

/**
 * @author abhinab
 *
 */
@Repository
@EnableRetry
public class CollectionManagementDaoImpl implements CollectionManagementDao {

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate jdbcNTemplate;

	// private static final String RETRIEVE_SQL = "SELECT * FROM
	// mm.collection_details";
	private static final String RETRIEVE_SQL_BY_CUSTOMER = "SELECT * FROM mm.customer_collection_details where customer_id=:customer_id";

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<CollectionDetail> retrieveCollectionDetails() {
		// List<CollectionDetail> collectionDetails =
		// jdbcTemplate.query(RETRIEVE_SQL, new RowMapper<CollectionDetail> () {
		// @Override
		// public CollectionDetail mapRow(ResultSet rs, int rowNum) throws
		// SQLException {
		// final CollectionDetail collectionDetail = new CollectionDetail();
		// collectionDetail.setCustomerPaymentId(rs.getLong("customer_payment_id"));
		// collectionDetail.setCustomerId(rs.getLong("customer_id"));
		// collectionDetail.setUserId(rs.getLong("user_id"));
		// collectionDetail.setRequestedPayementAmount(rs.getBigDecimal("requested_payement_amount"));
		// collectionDetail.setActualPayementAmount(rs.getBigDecimal("actual_payement_amount"));
		// collectionDetail.setPaid(rs.getBoolean("paid"));
		// return collectionDetail;
		// }
		// });
		//
		// if (collectionDetails == null || collectionDetails.isEmpty()) {
		// return null;
		// }
		//
		// return collectionDetails;
		return null;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public CustomerCollectionDetail fetchCustomerCollectionDetail(Long customerId) {
		Map<String, Object> param = new HashMap<>(1);
		param.put("customer_id", customerId);
		CustomerCollectionDetail customerCollectionDetail = jdbcNTemplate.query(RETRIEVE_SQL_BY_CUSTOMER, param,
				new ResultSetExtractor<CustomerCollectionDetail>() {
					@Override
					public CustomerCollectionDetail extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							CustomerCollectionDetail customerCollectionDetail = new CustomerCollectionDetail();
							customerCollectionDetail.setCollectionId(rs.getLong("collection_id"));
							customerCollectionDetail.setCustomerId(rs.getLong("customer_id"));
							customerCollectionDetail.setCollectorId(rs.getLong("collector_id"));
							byte[] json = rs.getBytes("txn_data");
							try {
								List<PaymentDetail> paymentDetails = objectMapper.readValue(json,new TypeReference<List<PaymentDetail>>() {});
								customerCollectionDetail.setPaymentDetails(paymentDetails);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return customerCollectionDetail;
						} else {
							return null;
						}
					}
				});
		return customerCollectionDetail;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CustomerDetail savePayment(CustomerDetail customerDetail, CustomerCollectionDetail customerCollectionDetail, BigDecimal actualPayment) {
		save(customerCollectionDetail);
		return customerDetail;
	}

	
	private CustomerCollectionDetail save(CustomerCollectionDetail customerCollectionDetail) {
		
		return customerCollectionDetail; 

	}
	
}
