/**
 * 
 */
package com.mabsisa.dao.customermanagement.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

	@Autowired
	private NamedParameterJdbcTemplate jdbcNTemplate;

	private static final String INSERT_SQL = "INSERT INTO mm.customer_details ("
			+ "customer_id,region,building,address,client,name,floor,fee,mahal,telephone,left_travel,note"
			+ ") VALUES ("
			+ ":customer_id,:region,:building,:address,:client,:name,:floor,:fee,:mahal,:telephone,:left_travel,:note"
			+ ")";
	private static final String RETRIEVE_SQL = "select * from mm.customer_details";
	private static final String RETRIEVE_SQL_BY_DISTINCT_REGION = "SELECT DISTINCT region FROM mm.customer_details";
	private static final String RETRIEVE_SQL_BY_DISTINCT_BUILDING = "SELECT DISTINCT building FROM mm.customer_details where region=?";
	private static final String RETRIEVE_SQL_BY_ID = "select * from mm.customer_details where customer_id =:customer_id";
	private static final String UPDATE_SQL = "select * from mm.customer_details where customer_id = ? for update";
	private static final String DELETE_SQL = "delete from mm.customer_details where customer_id = ?";
	private static final String UPDATE_SQL_WITH_COLLECTOR_ID = "select * from mm.customer_details where region=? and building=? for update";

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CustomerDetail save(CustomerDetail customerDetail) {
		Map<String, Object> params = new HashMap<>(12);
		customerDetail.setCustomerId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		params.put("customer_id", customerDetail.getCustomerId());
		params.put("region", customerDetail.getRegion());
		params.put("building", customerDetail.getBuilding());
		params.put("address", customerDetail.getAddress());
		params.put("client", customerDetail.getClient());
		params.put("name", customerDetail.getName());
		params.put("floor", customerDetail.getFloor());
		params.put("fee", customerDetail.getFee());
		params.put("mahal", customerDetail.getMahal());
		params.put("telephone", customerDetail.getTelephone());
		params.put("left_travel", customerDetail.getLeftTravel());
		params.put("note", customerDetail.getNote());

		jdbcNTemplate.update(INSERT_SQL, params);

		return customerDetail;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void save(List<CustomerDetail> customerDetails) {
		int counter = 0;
		Map<String, Object>[] params = new Map[customerDetails.size()];

		for (CustomerDetail customerDetail : customerDetails) {
			Map<String, Object> param = new HashMap<>(12);
			customerDetail.setCustomerId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
			param.put("customer_id", customerDetail.getCustomerId());
			param.put("region", customerDetail.getRegion());
			param.put("building", customerDetail.getBuilding());
			param.put("address", customerDetail.getAddress());
			param.put("client", customerDetail.getClient());
			param.put("name", customerDetail.getName());
			param.put("floor", customerDetail.getFloor());
			param.put("fee", customerDetail.getFee());
			param.put("mahal", customerDetail.getMahal());
			param.put("telephone", customerDetail.getTelephone());
			param.put("left_travel", customerDetail.getLeftTravel());
			param.put("note", customerDetail.getNote());
			params[counter] = param;
			counter++;
		}

		jdbcNTemplate.batchUpdate(INSERT_SQL, params);

	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CustomerDetail update(CustomerDetail customerDetail) {
		PreparedStatementCreatorFactory queryFactory = new PreparedStatementCreatorFactory(UPDATE_SQL,
				Arrays.asList(new SqlParameter(Types.BIGINT)));
		queryFactory.setUpdatableResults(true);
		queryFactory.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		PreparedStatementCreator psc = queryFactory
				.newPreparedStatementCreator(new Object[] { customerDetail.getCustomerId() });
		final CustomerDetail oldCustomerDetail = new CustomerDetail();
		RowCallbackHandler rowHandler = new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				oldCustomerDetail.setCustomerId(rs.getLong("customer_id"));
				oldCustomerDetail.setRegion(rs.getString("region"));
				oldCustomerDetail.setBuilding(rs.getString("building"));
				oldCustomerDetail.setAddress(rs.getString("address"));
				oldCustomerDetail.setClient(rs.getString("client"));
				oldCustomerDetail.setName(rs.getString("name"));
				oldCustomerDetail.setFloor(rs.getString("floor"));
				oldCustomerDetail.setFee(rs.getBigDecimal("fee"));
				oldCustomerDetail.setMahal(rs.getString("mahal"));
				oldCustomerDetail.setTelephone(rs.getString("telephone"));
				oldCustomerDetail.setLeftTravel(rs.getString("left_travel"));
				oldCustomerDetail.setNote(rs.getString("note"));

				rs.updateString("region", customerDetail.getRegion());
				rs.updateString("building", customerDetail.getBuilding());
				rs.updateString("address", customerDetail.getAddress());
				rs.updateString("client", customerDetail.getClient());
				rs.updateString("name", customerDetail.getName());
				rs.updateString("floor", customerDetail.getFloor());
				rs.updateBigDecimal("fee", customerDetail.getFee());
				rs.updateString("mahal", customerDetail.getMahal());
				rs.updateString("telephone", customerDetail.getTelephone());
				rs.updateString("left_travel", customerDetail.getLeftTravel());
				rs.updateString("note", customerDetail.getNote());

				rs.updateRow();
			}

		};

		jdbcTemplate.query(psc, rowHandler);
		return customerDetail;
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CustomerDetail updateWithCollector(CustomerDetail customerDetail) {
		PreparedStatementCreatorFactory queryFactory = new PreparedStatementCreatorFactory(UPDATE_SQL_WITH_COLLECTOR_ID,
				Arrays.asList(new SqlParameter(Types.VARCHAR), new SqlParameter(Types.VARCHAR)));
		queryFactory.setUpdatableResults(true);
		queryFactory.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		PreparedStatementCreator psc = queryFactory
				.newPreparedStatementCreator(new Object[] { customerDetail.getRegion(), customerDetail.getBuilding() });
		final CustomerDetail oldCustomerDetail = new CustomerDetail();
		RowCallbackHandler rowHandler = new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				oldCustomerDetail.setCustomerId(rs.getLong("customer_id"));
				oldCustomerDetail.setRegion(rs.getString("region"));
				oldCustomerDetail.setBuilding(rs.getString("building"));
				oldCustomerDetail.setAddress(rs.getString("address"));
				oldCustomerDetail.setClient(rs.getString("client"));
				oldCustomerDetail.setName(rs.getString("name"));
				oldCustomerDetail.setFloor(rs.getString("floor"));
				oldCustomerDetail.setFee(rs.getBigDecimal("fee"));
				oldCustomerDetail.setMahal(rs.getString("mahal"));
				oldCustomerDetail.setTelephone(rs.getString("telephone"));
				oldCustomerDetail.setLeftTravel(rs.getString("left_travel"));
				oldCustomerDetail.setNote(rs.getString("note"));
				oldCustomerDetail.setCollectorId(rs.getLong("collector_id"));

				rs.updateLong("collector_id", customerDetail.getCollectorId());

				rs.updateRow();
			}

		};

		jdbcTemplate.query(psc, rowHandler);
		return customerDetail;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CustomerDetail delete(CustomerDetail customerDetail) {
		Object[] params = { customerDetail.getCustomerId() };
		int[] types = { Types.BIGINT };
		jdbcTemplate.update(DELETE_SQL, params, types);
		return customerDetail;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
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
				customerDetail.setCollectorId(rs.getLong("collector_id"));
				customerDetail.setNote(rs.getString("note"));
				return customerDetail;
			}

		});

		if (customerDetails == null || customerDetails.isEmpty()) {
			return null;
		}
		return customerDetails;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public CustomerDetail fetchByCustomerId(Long customerId) {
		Map<String, Object> param = new HashMap<>(1);
		param.put("customer_id", customerId);
		CustomerDetail customerDetail = jdbcNTemplate.queryForObject(RETRIEVE_SQL_BY_ID, param,
				new RowMapper<CustomerDetail>() {
					@Override
					public CustomerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						CustomerDetail customerDetail = new CustomerDetail();
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
						customerDetail.setCollectorId(rs.getLong("collector_id"));
						customerDetail.setNote(rs.getString("note"));
						return customerDetail;
					}
				});
		return customerDetail;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<CustomerDetail> fetchByRegion() {
		List<CustomerDetail> customerDetails = jdbcTemplate.query(RETRIEVE_SQL_BY_DISTINCT_REGION,
				new RowMapper<CustomerDetail>() {
					@Override
					public CustomerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						final CustomerDetail customerDetail = new CustomerDetail();
						customerDetail.setRegion(rs.getString("region"));
						return customerDetail;
					}
				});
		if (customerDetails == null || customerDetails.isEmpty()) {
			return null;
		}
		return customerDetails;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<CustomerDetail> fetchByBuilding(String region) {
		List<CustomerDetail> customerDetails = jdbcTemplate.query(RETRIEVE_SQL_BY_DISTINCT_BUILDING,
				new Object[] { region }, new RowMapper<CustomerDetail>() {
					@Override
					public CustomerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						final CustomerDetail customerDetail = new CustomerDetail();
						customerDetail.setBuilding(rs.getString("building"));
						return customerDetail;
					}
				});
		if (customerDetails == null || customerDetails.isEmpty()) {
			return null;
		}
		return customerDetails;
	}

}
