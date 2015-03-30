package imageboard.model;

import java.util.Date;

public class UsersModel {

	private int id;
	private String name;
	private String pass;
	private String imageUrl;
	private String keycode;
	private int expiryDate;
	private int admin;

	public UsersModel() {}

	public int getId() {
		return this.id;
	} public void setId(int id) {
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
	public int getExpiryDate() {
		return this.expiryDate;
	} public void setExpiryDate(int expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getAdmin() {
		return this.admin;
	} public void setAdmin(int admin) {
		this.admin = admin;
	}

	public boolean checkRegistered() {
		return !this.name.equals(null);
	}
	public boolean isExpired() {
		return (new Date((long) this.expiryDate)).before(new Date());
	}

}
