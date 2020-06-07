package com.packt.masteringsqlj.models;

import java.io.Serializable;
import java.security.SecureRandom;

public class User implements Serializable{

	private String username;
	private String password;
	private String user_id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
		
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", user_id=" + user_id + "]";
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		this.user_id = bytes.toString().substring(8);
	}
	
	public User(String username, String password, String user_id) {
		super();
		this.username = username;
		this.password = password;
		this.user_id = user_id;
	}
	
	public User() {
		super();
	}
	
	
}
