package com.packt.masteringsqlj.service;

import com.packt.masteringsqlj.models.IOTDevice;
import com.packt.masteringsqlj.models.User;

public interface IOTMgmtService {

	public void addUser(String user, String password);
	
	public void addDevice(String id, String name, String status, String user_id);
	
	public void changeIOTStatus(String id, String status);
	
	public User getUser(String username, String password);
	
	public IOTDevice getDevice(String id);
	
	public boolean setStatus(String id, String status);
	
}
