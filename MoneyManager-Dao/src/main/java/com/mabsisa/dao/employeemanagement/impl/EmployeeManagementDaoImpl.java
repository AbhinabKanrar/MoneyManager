/**
 * 
 */
package com.mabsisa.dao.employeemanagement.impl;

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

import com.mabsisa.common.model.Employee;
import com.mabsisa.common.model.UserStatus;
import com.mabsisa.dao.employeemanagement.EmployeeManagementDao;

/**
 * @author abhinab
 *
 */
@Repository
@EnableRetry
public class EmployeeManagementDaoImpl implements EmployeeManagementDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate jdbcNTemplate;
	
	private static final String INSERT_SQL = "INSERT INTO mm.user_auth_details ("
			+ "user_id,username,password,role,mail,phone_number,user_status"
			+ ") VALUES ("
			+ ":user_id,:username,:password,:role,:mail,:phone_number,:user_status"
			+ ")";
	private static final String RETRIEVE_SQL = "select * from mm.user_auth_details";
	private static final String RETRIEVE_SQL_BY_ID = "select * from mm.user_auth_details where user_id =:user_id";
	private static final String UPDATE_SQL = "select * from mm.user_auth_details where user_id = ? for update";
	private static final String DELETE_SQL = "delete from mm.user_auth_details where user_id = ?";

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Employee save(Employee employee) {
		Map<String, Object> params = new HashMap<>(7);
		employee.setEmployeeId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		params.put("user_id", employee.getEmployeeId());
		params.put("username", employee.getUsername());
		params.put("password", employee.getPassword());
		params.put("role", employee.getRole());
		params.put("mail", employee.getMail());
		params.put("phone_number", employee.getPhoneNumber());
		params.put("user_status", employee.getUserStatus().name());
		
		jdbcNTemplate.update(INSERT_SQL, params);
		
		return employee;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Employee update(Employee employee) {
		PreparedStatementCreatorFactory queryFactory = new PreparedStatementCreatorFactory(UPDATE_SQL,
				Arrays.asList(new SqlParameter(Types.BIGINT)));
		queryFactory.setUpdatableResults(true);
		queryFactory.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		PreparedStatementCreator psc = 
				queryFactory.newPreparedStatementCreator(
	            new Object[] {employee.getEmployeeId()});
		final Employee oldEmployee = new Employee();
		RowCallbackHandler rowHandler = new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				oldEmployee.setEmployeeId(rs.getLong("user_id"));
				oldEmployee.setUsername(rs.getString("username"));
				oldEmployee.setRole(rs.getString("role"));
				oldEmployee.setMail(rs.getString("mail"));
				oldEmployee.setPhoneNumber(rs.getString("phone_number"));
				oldEmployee.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
				
				rs.updateString("username", employee.getUsername());
				rs.updateString("role", employee.getRole());
				rs.updateString("mail", employee.getMail());
				rs.updateString("phone_number", employee.getPhoneNumber());
				rs.updateString("user_status", employee.getUserStatus().name());
				
				rs.updateRow();
			}
		};
		
		jdbcTemplate.query(psc, rowHandler);
		
		return employee;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Employee delete(Employee employee) {
		Object[] params = { employee.getEmployeeId() };
		int[] types = {Types.BIGINT};
		jdbcTemplate.update(DELETE_SQL, params, types);
		return employee;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Employee> retrieveEmployee() {
		List<Employee> employees = jdbcTemplate.query(RETRIEVE_SQL, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				final Employee employee = new Employee();
				employee.setEmployeeId(rs.getLong("user_id"));
				employee.setUsername(rs.getString("username"));
				employee.setRole(rs.getString("role"));
				employee.setMail(rs.getString("mail"));
				employee.setPhoneNumber(rs.getString("phone_number"));
				employee.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
				return employee;
			}
		});
		
		if (employees == null || employees.isEmpty()) {
			return null;
		}
		
		return employees;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Employee fetchByEmployeeId(Long employeeId) {
		Map<String, Object> param = new HashMap<>(1);
		param.put("user_id", employeeId);
		Employee employee = jdbcNTemplate.queryForObject(RETRIEVE_SQL_BY_ID, param, new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setEmployeeId(rs.getLong("user_id"));
				employee.setUsername(rs.getString("username"));
				employee.setRole(rs.getString("role"));
				employee.setMail(rs.getString("mail"));
				employee.setPhoneNumber(rs.getString("phone_number"));
				employee.setUserStatus(UserStatus.valueOf(rs.getString("user_status")));
				return employee;
			}
		});
		return employee;
	}

}
