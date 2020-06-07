package com.packt.masteringsqlj.connectors;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.packt.masteringsqlj.models.IOTDevice;
import com.packt.masteringsqlj.models.User;

public class MySQL {



	public static void insert_user(User user) throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;

		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		String query = "INSERT INTO " + USER_TABLE + " VALUE ('" + user.getUsername() +"','" + user.getPassword() +"','" + user.getUser_id() + "')";

		if(!statement.execute(query))

			System.out.println("Add user " + user.getUsername());

		else System.out.println("Error");

	}

	public static void insert_iot(IOTDevice iot_device) throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;

		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);

		String query = "INSERT INTO " +  DEVICE_NAME + " VALUE ('" + iot_device.getId() + "','" + iot_device.getName() +"','" + iot_device.getStatus() +"','" + iot_device.getUser_id() + "')";

		if(!statement.execute(query))

			System.out.println("Add IoT Device " + iot_device.getName());

		else System.out.println("Error");

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}

	}

	public static IOTDevice get_iot(String user_id) throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);

		String query = "SELECT * FROM " + DEVICE_NAME + " WHERE user_id='" + user_id + "'";

		IOTDevice to_return = null;

		if((resultSet = statement.executeQuery(query)) != null)

			resultSet.next();
		to_return = new IOTDevice(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

		if (resultSet != null) {
			resultSet.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}

		return to_return;

	}

	public static User get_user(String user_id, String password) throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);

		String query = "SELECT * FROM " + USER_TABLE + " WHERE username='" + user_id + "' AND password='" + password + "'";

		User to_return = null;

		if((resultSet = statement.executeQuery(query)) != null)

			resultSet.next();
		to_return = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

		if (resultSet != null) {
			resultSet.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}

		return to_return;

	}


	public static boolean is_db_empty() throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + "?useSSL=false","root", "admin");
		statement = connect.createStatement();

		String query = "CREATE DATABASE IF NOT EXISTS IOT_MGMT_SYSTEM;";

		statement.executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS IOT_MGMT_SYSTEM.USER (" + 
				"username VARCHAR(8) UNIQUE, " + 
				"password VARCHAR(8), " + 
				"user_id VARCHAR(8) PRIMARY KEY" + 
				");";

		statement.executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS IOT_MGMT_SYSTEM.IOT_DEVICE (" + 
				"id varchar(8) primary key, " + 
				"name varchar (20), " + 
				"status varchar(8), " + 
				"user_id varchar(8) " + 
				");";

		statement.executeUpdate(query);

		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);

		query = "SELECT * FROM " + USER_TABLE;


		if((resultSet = statement.executeQuery(query)) != null)

			while(resultSet.next()) {
				return false;	
			}


		if (resultSet != null) {
			resultSet.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}

		return true;

	}


	@SuppressWarnings("unused")
	public static boolean set_iot_status(String id, String status) throws ClassNotFoundException, SQLException {

		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		//Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME  + "?useSSL=false","root", "admin");

		statement = connect.createStatement();

		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);

		String query = "UPDATE " + DEVICE_NAME +  " SET status = '" + status + "' WHERE id='" + id +"'";


		boolean to_return = statement.execute(query);


		if (resultSet != null) {
			resultSet.close();
		}

		if (statement != null) {
			statement.close();
		}

		if (connect != null) {
			connect.close();
		}

		return !to_return;

	}


	public static String DB_NAME = "IOT_MGMT_SYSTEM";
	public static String USER_TABLE = "USER";
	public static String DEVICE_NAME = "IOT_DEVICE";
}
