package imageboard.model;

import java.util.List;

public class ThreadsModel extends PostsModel {

	private String subject;
	private List<PostsModel> replies;
	private long lastActive;


	public ThreadsModel() {}


	public long getLastActive() {
		return this.lastActive;
	} public void setLastActive(long lastActive) {
		this.lastActive = lastActive;
	}
	public String getSubject() {
		return this.subject;
	} public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<PostsModel> getReplies() {
		return this.replies;
	} public void setReplies(List<PostsModel> replies) {
		this.replies = replies;
	}

}
