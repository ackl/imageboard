package imageboard.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;

/* TODO:
 * Insert and update methods to take Maps, ThreadsModels etc.
 */

@Repository
public class ThreadsDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ThreadsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ThreadsModel> selectAllThreads() {
		String sql = "SELECT * FROM posts WHERE parent_id=0";

		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(ThreadsModel.class));
	}

	public ThreadsModel selectThreadById(int id) {
		String sql = "SELECT * FROM posts WHERE id=?";

		return (ThreadsModel) jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper(ThreadsModel.class));
	}

	public void insertThread(int userId, int parentId, long date, String imageUrl, String content, String subject) {
		String sql = "INSERT INTO posts (user_id, parent_id, date, image_url, content, subject) VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, new Object[] {userId, parentId, date, imageUrl, content, subject});
	}

	public void updateThread(int id, int userId, int parentId, long date, String imageUrl, String content, String subject) {
		String sql = "UPDATE posts SET user_id=?,parent_id=?,date=?,image_url=?,content=?,subject=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {userId, parentId, date, imageUrl, content, id, subject});
	}

	public void removeThreadById(int id) {
		String sql = "DELETE FROM posts WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {id});
	}

}
