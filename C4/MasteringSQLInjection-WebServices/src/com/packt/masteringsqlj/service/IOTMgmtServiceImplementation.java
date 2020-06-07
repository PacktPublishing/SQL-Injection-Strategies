package com.packt.masteringsqlj.service;

import java.sql.SQLException;

import com.packt.masteringsqlj.connectors.MySQL;
import com.packt.masteringsqlj.models.IOTDevice;
import com.packt.masteringsqlj.models.User;

public class IOTMgmtServiceImplementation implements IOTMgmtService{

	@Override
	public void addUser(String user, String password) {
		// TODO Auto-generated method stub
		try {
			MySQL.insert_user(new User(user, password));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addDevice(String id, String name, String status, String user_id) {
		// TODO Auto-generated method stub
		try {
			MySQL.insert_iot(new IOTDevice(id, name, status, user_id));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void changeIOTStatus(String user_id, String status) {
		// TODO Auto-generated method stub
		
		try {
			IOTDevice device = MySQL.get_iot(user_id);
			
			device.setStatus(status);
			
			System.out.println(device.getStatus());
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(String username, String password) {
		// TODO Auto-generated method stub
		
		try {
			
			if(MySQL.is_db_empty()) {
				
				//If db is empty add user and iot device
				User new_user = new User(username, password);
				User new_user2 = new User("admin2", "admin2");
				User new_user3 = new User("admin3", "admin3");
				
				MySQL.insert_user(new_user);
				MySQL.insert_user(new_user2);
				MySQL.insert_user(new_user3);
				
				MySQL.insert_iot(new IOTDevice("001", "IOT Device 1", "OFF", new_user.getUsername()));
				MySQL.insert_iot(new IOTDevice("002", "IOT Device 2", "OFF", new_user2.getUsername()));
				MySQL.insert_iot(new IOTDevice("003", "IOT Device 3", "OFF", new_user3.getUsername()));
			}
			
			return MySQL.get_user(username, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public IOTDevice getDevice(String user_id) {
		// TODO Auto-generated method stub
	
		try {
			return MySQL.get_iot(user_id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean setStatus(String id, String status) {
		try {
			return MySQL.set_iot_status(id, status);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

}
