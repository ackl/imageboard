package imageboard.model;

import java.util.Date;

public class UsersModel {

	private long id;
	private String name;
	private String pass;
	private String imageUrl;
	private String keycode;
	private long expiryDate;

	public UsersModel() {}

	public boolean checkRegistered() {
		return !this.name.equals(null);
	}

	public boolean checkExpired() {
		return (new Date(expiryDate)).before(new Date());
	}

	public long getId() {
		return this.id;
	} public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	} public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return this.pass;
	} public void setPass(String pass) {
		this.pass = pass;
	}
	public String getImageUrl() {
		return this.imageUrl;
	} public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getKeycode() {
		return this.keycode;
	} public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	public long getExpiryDate() {
		return this.expiryDate;
	} public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

}
