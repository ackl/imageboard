package imageboard.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import imageboard.model.UsersModel;

@Repository
public class UsersDao {

	JdbcTemplate jdbcTemplate;

	@Autowired
	public UsersDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<UsersModel> selectAllUsers() {
		String sql = "SELECT * FROM users";

		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public UsersModel selectUserById(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper(UsersModel.class));
	}
	public UsersModel selectUserByKeycode(String keycode) {
		String sql = "SELECT * FROM users WHERE keycode = ?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {keycode},
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public void insertUser(String keycode, long expiryDate) {
		String sql = "INSERT INTO users (keycode, expiry_date) VALUES (?, ?)";

		jdbcTemplate.update(sql, new Object[] {keycode, expiryDate});
	}

	public void updateUser(int id, String name, String pass, String imageUrl) {
		String sql = "UPDATE users SET name=?, pass=?, imageurl=? WHERE keycode=?";

		jdbcTemplate.update(sql, new Object[] {name, pass, imageUrl, keycode});
	}

	public void removeUserById(int id) {
		String sql = "DELETE FROM users WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {id});
	}
	public void removeUserByKeycode(String keycode) {
		String sql = "DELETE FROM users WHERE keycode=?";

		jdbcTemplate.update(sql, new Object[] {keycode});
	}

}
