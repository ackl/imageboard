package imageboard.model;

public class PostsModel {

	private int id;
	private int parentId;
	private long date;
	private String userId;
	private String imageUrl;
	private String content;


	public PostsModel() {}

	public int getId() {
		return this.id;
	} public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return this.userId;
	} public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getParentId() {
		return this.parentId;
	} public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public long getDate() {
		return this.date;
	} public void setDate(long date) {
		this.date = date;
	}
	public String getImageUrl() {
		return this.imageUrl;
	} public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getContent() {
		return content;
	} public void setContent(String content) {
		this.content = content;
	}

	public boolean isThread() {
		return this.parentId == 0;
	}

}

