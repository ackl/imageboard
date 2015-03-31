package imageboard.model;

public class UserRoleModel {

	private int user_role_id;
	private String username;
	private String ROLE;

    public UserRoleModel() {}

	public UserRoleModel(int user_role_id, String username, String ROLE) {
        this.user_role_id = user_role_id;
        this.username = username;
        this.ROLE = ROLE;
    }

	public String getRole() {
		return this.ROLE;
	} public void setRole(String ROLE) {
		this.ROLE = ROLE;
	}

	public String getUsername() {
		return this.username;
	} public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return this.user_role_id;
	} public void setId(int id) {
		this.user_role_id = id;
	}
}
