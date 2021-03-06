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
		String sql = "SELECT * FROM posts WHERE parent_id=0 ORDER BY last_active DESC";

		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(ThreadsModel.class));
	}

    public int countThreads() {
        String sql = "SELECT COUNT(*) FROM posts WHERE parent_id=0";

        return jdbcTemplate.queryForInt(sql);
    }

    public long selectLastActiveDate() {
        String sql = "SELECT MAX(date) FROM posts";

        return jdbcTemplate.queryForLong(sql);
    }

    public long selectLastActiveDateByThreadId(int id) {
        String sql = "SELECT last_active FROM posts WHERE id=?";
        return jdbcTemplate.queryForLong(sql, new Object[] {id});
    }

    public int selectLastActiveThreadId() {
        String sql = "SELECT id FROM posts ORDER BY date DESC LIMIT 1";
        return jdbcTemplate.queryForInt(sql);
    }

    public int countReplies(int id) {
        String sql = "SELECT COUNT(*) FROM posts WHERE parent_id=?";
        return jdbcTemplate.queryForInt(sql, new Object[] {id});
    }

	public ThreadsModel selectThreadById(int id) {
		String sql = "SELECT * FROM posts WHERE id=?";

		return (ThreadsModel) jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper(ThreadsModel.class));
	}

	public void insertThread(ThreadsModel t) {
		String sql = "INSERT INTO posts (user_id, parent_id, date, image_url, content, subject, last_active) VALUES (?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, new Object[] {t.getUserId(), t.getParentId(), t.getDate(), t.getImageUrl(), t.getContent(), t.getSubject(), t.getDate()});
	}

	public void updateThread(int id, String userId, int parentId, long date, String imageUrl, String content, String subject) {
		String sql = "UPDATE posts SET user_id=?,parent_id=?,date=?,image_url=?,content=?,subject=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {userId, parentId, date, imageUrl, content, id, subject});
	}

	public void setLastActive(int id, long lastActive) {
		String sql = "UPDATE posts SET last_active=? WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {lastActive, id});
	}

	public void removeThreadById(int id) {
		String sql = "DELETE FROM posts WHERE id=?";

		jdbcTemplate.update(sql, new Object[] {id});
	}

}
