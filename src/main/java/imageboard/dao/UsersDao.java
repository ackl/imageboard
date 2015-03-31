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

	public UsersModel selectUserById(int id) {
		String sql = "SELECT * FROM users WHERE id=?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public UsersModel selectUserByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username=?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {username},
				new BeanPropertyRowMapper(UsersModel.class));
	}

	public UsersModel selectUserByKeycode(String keycode) {
		String sql = "SELECT * FROM users WHERE keycode=?";

		return (UsersModel)jdbcTemplate.queryForObject(sql, new Object[] {keycode},
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
	//public void insertUser(String keycode, long expiryDate) {
		//String sql = "INSERT INTO users (keycode, expiry_date) VALUES (?, ?)";

		//jdbcTemplate.update(sql, new Object[] {keycode, expiryDate});
	//}

	public void updateUser(int id, String name, String pass, String imageUrl) {
		String sql = "UPDATE users SET name=?,pass=?,image_url=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {name, pass, imageUrl, id});
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
