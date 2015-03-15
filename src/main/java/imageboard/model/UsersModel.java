package model;

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

}	
