/**
 * 
 */
package com.mabsisa.dao.security.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mabsisa.common.model.UserDetail;
import com.mabsisa.common.model.UserStatus;
import com.mabsisa.dao.security.AuthenticationDao;

/**
 * @author abhinab
 *
 */
@Repository
@EnableRetry
public class AuthenticationDaoImpl implements AuthenticationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNTemplate;

	private static final String RETRIEVE_SQL_BY_USERNAME = "SELECT * FROM mm.user_auth_details where username =:username";
	private static final String UPDATE_SQL = "SELECT * FROM mm.user_auth_details where username=?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mabsisa.dao.security.AuthenticationDao#getUserDetailByUsername(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public UserDetail getUserDetailByUsername(String username) {

		Map<String, Object> param = new HashMap<>(1);
		param.put("username", username);

		UserDetail userDetail = jdbcNTemplate.query(RETRIEVE_SQL_BY_USERNAME, param,
				new ResultSetExtractor<UserDetail>() {
					@Override
					public UserDetail extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							UserDetail userDetail = new UserDetail(rs.getString("username"), rs.getString("password"), rs.getString("role"));
							userDetail.setUserId(rs.getLong("user_id"));
							userDetail.setMail(rs.getString("mail"));
							userDetail.setPhoneNumber(rs.getString("phone_number"));
							userDetail.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
							return userDetail;
						} else {
							return null;
						}
					}
				});

		return userDetail;

	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void update(String username, String password) {
		PreparedStatementCreatorFactory queryFactory = new PreparedStatementCreatorFactory(UPDATE_SQL,
				Arrays.asList(new SqlParameter(Types.VARCHAR)));
		queryFactory.setUpdatableResults(true);
		queryFactory.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		PreparedStatementCreator psc = 
				queryFactory.newPreparedStatementCreator(
	            new Object[] {username});
		final UserDetail userDetail = new UserDetail("NA", "NA", "NA");
		RowCallbackHandler rowHandler = new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				userDetail.setUserId(rs.getLong("user_id"));
				userDetail.setPassword(rs.getString("password"));
				userDetail.setMail(rs.getString("mail"));
				userDetail.setPhoneNumber(rs.getString("phone_number"));
				userDetail.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
				
				rs.updateString("password", password);
				
				rs.updateRow();
			}
		};
		
		jdbcTemplate.query(psc, rowHandler);
	}

}
