package model;

public class Users {

	private long id;
	private String name;
	private String pass;
	private String imageUrl;
	private String keycode;
	private long expiryDate;
	
	public Users(long id, String name, String pass, String imageUrl, String keycode, long expiryDate) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.imageUrl = imageUrl;
		this.keycode = keycode;
		this.expiryDate = expiryDate;
	}

	public boolean checkRegistered() {
		return !this.name.equals(null);
	}

	public boolean checkExpired() {
		return (new Date(expiryDate)).before(new Date());
	}

}	
