package imageboard.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import imageboard.model.UsersModel;
import imageboard.model.UserRoleModel;
import imageboard.model.KeycodeModel;

/* TODO:
 * Separate database access functions through service layer?
 * Exception handling
 * Consider using another connection handler
 * Column filtering for select statements?
 */

@Repository
public class UsersDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public UsersDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<UsersModel> selectAllUsers() {
		String sql = "SELECT * FROM users";

		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public UsersModel selectUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username=?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {username},
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public void insertKeycode(String username, long expiry) {
		String sql = "INSERT INTO registration_keycodes (keycode, expiry) VALUES (?, ?)";
		jdbcTemplate.update(sql, new Object[] {username, expiry});
	}

	public KeycodeModel selectKeycodeByKeycode(String keycode) {
		String sql = "SELECT * FROM registration_keycodes WHERE keycode=?";

		return (KeycodeModel)jdbcTemplate.queryForObject(sql, new Object[] {keycode},
				new BeanPropertyRowMapper(KeycodeModel.class));
	}

	public void insertUser(String username, String password) {
		String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

		jdbcTemplate.update(sql, new Object[] {username, password});
	}

	public void insertRole(String username, String role) {
		String sql = "INSERT INTO user_roles (username, ROLE) VALUES (?, ?)";

		jdbcTemplate.update(sql, new Object[] {username, role});
	}

	public void updateUserByColourScheme(String username, String colourScheme) {
		String sql = "UPDATE users SET colour_scheme=? WHERE username=?";

		jdbcTemplate.update(sql, new Object[] {colourScheme, username});
	}

	public void updateUserByImageUrl(String username, String imageUrl) {
		String sql = "UPDATE users SET image_url=? WHERE username=?";

		jdbcTemplate.update(sql, new Object[] {imageUrl, username});
	}

	public void updateUser(int id, String name, String pass, String imageUrl) {
		String sql = "UPDATE users SET name=?,pass=?,image_url=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {name, pass, imageUrl, id});
	}

	public void removeUserByUsername(String username) {
		String sql = "DELETE FROM users WHERE username=?";

		jdbcTemplate.update(sql, new Object[] {username});
	}
}
