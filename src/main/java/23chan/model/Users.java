package model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class Users {

	private User currentUser;
	private List<User> userList;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Users(User currentUser) {
		this.currentUser = currentUser;

		String sql = "SELECT * FROM users";
		this.userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
	}

}
