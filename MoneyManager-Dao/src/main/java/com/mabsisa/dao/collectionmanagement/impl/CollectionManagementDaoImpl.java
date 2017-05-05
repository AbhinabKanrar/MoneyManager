/**
 * 
 */
package com.mabsisa.dao.collectionmanagement.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mabsisa.common.model.CollectionDetail;
import com.mabsisa.dao.collectionmanagement.CollectionManagementDao;

/**
 * @author abhinab
 *
 */
@Repository
@EnableRetry
public class CollectionManagementDaoImpl implements CollectionManagementDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate jdbcNTemplate;

	private static final String RETRIEVE_SQL = "SELECT * FROM mm.collection_details";
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<CollectionDetail> retrieveCollectionDetails() {
		List<CollectionDetail> collectionDetails = jdbcTemplate.query(RETRIEVE_SQL, new RowMapper<CollectionDetail> () {
			@Override
			public CollectionDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				final CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setCustomerPaymentId(rs.getLong("customer_payment_id"));
				collectionDetail.setCustomerId(rs.getLong("customer_id"));
				collectionDetail.setUserId(rs.getLong("user_id"));
				collectionDetail.setRequestedPayementAmount(rs.getBigDecimal("requested_payement_amount"));
				collectionDetail.setActualPayementAmount(rs.getBigDecimal("actual_payement_amount"));
				collectionDetail.setPaid(rs.getBoolean("paid"));
				return collectionDetail;
			}
		});
		
		if (collectionDetails == null || collectionDetails.isEmpty()) {
			return null;
		}
		
		return collectionDetails;
	}
	
}
