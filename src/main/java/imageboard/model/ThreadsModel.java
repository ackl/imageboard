package imageboard.model;

public class ThreadsModel extends PostsModel {

	private String subject;

	public ThreadsModel() {}

	public String getSubject() {
		return this.subject;
	} public void setSubject(String subject) {
		this.subject = subject;
	}

}
