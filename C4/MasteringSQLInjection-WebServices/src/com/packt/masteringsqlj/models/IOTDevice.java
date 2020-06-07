package com.packt.masteringsqlj.models;

import java.io.Serializable;

public class IOTDevice implements Serializable {

	private String id;
	private String name;
	private String status;
	private String username;
	public IOTDevice(String id, String name, String status, String user_id) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.username = user_id;
	}
	public IOTDevice() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser_id() {
		return username;
	}
	public void setUser_id(String user_id) {
		this.username = user_id;
	}
	
	@Override
	public String toString() {
		return "IOTDevice [id=" + id + ", name=" + name + ", status=" + status + ", user_id=" + username + "]";
	}
	
}
