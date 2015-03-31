package imageboard.model;

import java.util.Date;

public class UsersModel {

	private String username;
	private String password;
	private String imageUrl;
	private boolean enabled;

    public UsersModel() {}

	public UsersModel(String username, String password, boolean enabled, String imageUrl) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.imageUrl = imageUrl;
    }

	public String getUsername() {
		return this.username;
	} public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	} public void setPassword(String password) {
		this.password = password;
	}

	public String getImageUrl() {
		return this.imageUrl;
	} public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean getEnabled() {
		return this.enabled;
	} public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
