package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate; //TODO: Define a data source?
	
	public List<User> selectAllUsers() {
		String sql = "SELECT * FROM users";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
	}

	public User selectUserById(long id) {
		String sql = "SELECT * FROM users WHERE id = ?";
	
		return (User)jdbcTemplate.queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper(User.class));
	}

	public User selectUserByKeycode(String keycode) {
		String sql = "SELECT * FROM users WHERE keycode = ?";

		return (User)jdbcTemplate.queryForObject(sql, new Object[] {keycode}, new BeanPropertyMapper(User.class));
	}

	public void insertUser(String keycode, long expiryDate) {
		String sql = "INSERT INTO users (keycode, expiry_date) VALUES (?, ?)";

		jdbcTemplate.update(sql, new Object[] {keycode, expiryDate});
	}

	public void updateUser(String keycode, String name, String pass, String imageUrl) {
		String sql = "UPDATE users SET name=?, pass=?, imageurl=? WHERE keycode=?";

		jdbcTemplate.update(sql, new Object[] {name, pass, imageUrl, keycode});
	}

	public void removeUserById(long id) {
		String sql = "DELETE FROM users WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {id});
	}

	public void removeUserByKeycode(String keycode) {
		String sql = "DELETE FROM users WHERE keycode=?";

		jdbcTemplate.update(sql, new Object[] {keycode});
	}

}
