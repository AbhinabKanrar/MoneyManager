/**
 * 
 */
package com.mabsisa.dao.employeemanagement.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Employee save(Employee employee) {
		Map<String, Object> params = new HashMap<>(7);
		employee.setEmployeeId(UUID.randomUUID().getMostSignificantBits());
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
	public Employee update(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee delete(Employee employee) {
		// TODO Auto-generated method stub
		return null;
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
	public Employee fetchByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
