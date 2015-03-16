package imageboard.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import imageboard.model.PostsModel;

/* TODO:
 * Insert and update methods to take Maps, PostsModels etc.
 */

@Repository
public class PostsDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public PostsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PostsModel> selectAllPosts() {
		String sql = "SELECT * FROM posts";

		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(PostsModel.class));
	}
	public PostsModel selectPostById(int id) {
		String sql = "SELECT * FROM posts WHERE id=?";

		return (PostsModel) jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper(PostsModel.class));
	}
	public List<PostsModel> selectPostsByUserId(int userId) {
		String sql = "SELECT * FROM posts WHERE user_id=?";

		return jdbcTemplate.query(sql, new Object[] {userId},
				new BeanPropertyRowMapper(PostsModel.class));
	}
	public List<PostsModel> selectPostsByParentId(int parentId) {
		String sql = "SELECT * FROM posts WHERE parent_id=?";

		return jdbcTemplate.query(sql, new Object[] {parentId},
				new BeanPropertyRowMapper(PostsModel.class));
	}

	public void insertPost(int userId, int parentId, long date, String imageUrl, String content) {
		String sql = "INSERT INTO posts (user_id, parent_id, date, image_url, content) VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, new Object[] {userId, parentId, date, imageUrl, content});
	}

	public void updatePost(int id, int userId, int parentId, long date, String imageUrl, String content) {
		String sql = "UPDATE posts SET user_id=?,parent_id=?,date=?,image_url=?,content=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {userId, parentId, date, imageUrl, content, id});
	}

	public void removePostById(int id) {
		String sql = "DELETE FROM posts WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {id});
	}

}
