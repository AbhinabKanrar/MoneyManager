/**
 * 
 */
package com.mabsisa.dao.customermanagement.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mabsisa.common.model.CustomerDetail;
import com.mabsisa.dao.customermanagement.CustomerManagementDao;

/**
 * @author abhinab
 *
 */
@Repository
@EnableRetry
public class CustomerManagementDaoImpl implements CustomerManagementDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String RETRIEVE_SQL = "select * from mm.customer_details";

	/* (non-Javadoc)
	 * @see com.mabsisa.dao.customermanagement.CustomerManagementDao#retrieveCustomerDetail()
	 */
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, readOnly=true)
	public List<CustomerDetail> retrieveCustomerDetail() {
		List<CustomerDetail> customerDetails = jdbcTemplate.query(RETRIEVE_SQL, new RowMapper<CustomerDetail>() {

			@Override
			public CustomerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				final CustomerDetail customerDetail = new CustomerDetail();
				customerDetail.setCustomerId(rs.getLong("customer_id"));
				customerDetail.setRegion(rs.getString("region"));
				customerDetail.setBuilding(rs.getString("building"));
				customerDetail.setAddress(rs.getString("address"));
				customerDetail.setClient(rs.getString("client"));
				customerDetail.setName(rs.getString("name"));
				customerDetail.setFloor(rs.getString("floor"));
				customerDetail.setFee(rs.getBigDecimal("fee"));
				customerDetail.setMahal(rs.getString("mahal"));
				customerDetail.setTelephone(rs.getString("telephone"));
				customerDetail.setLeftTravel(rs.getString("left_travel"));
				customerDetail.setNote(rs.getString("note"));
				return customerDetail;
			}
			
		});
		
		if(customerDetails == null || customerDetails.isEmpty()) {
			return null;
		}
		return customerDetails;
	}

}
