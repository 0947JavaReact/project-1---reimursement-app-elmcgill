package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton utility for creating and retrieving database connection
 */
public class ConnectionUtil {
	private static ConnectionUtil cu;
	private static Properties prop;
	
	/**
	 * This method should read in the "database.properties" file and load
	 * the values into the Properties variable
	 */
	private ConnectionUtil() {
	}
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if(cu == null)
			cu = new ConnectionUtil();
		return cu;
	}
	
	/**
	 * This method should create and return a Connection object
	 * @return a Connection to the database
	 */
	public Connection getConnection() {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream("database.properties");
		prop = new Properties();
		String url = "";
		String username = "";
		String password="";
		try {
			prop.load(is);
			url = (String)prop.getProperty("url");
			username = (String)prop.getProperty("usr");
			password = (String)prop.getProperty("pswd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection con;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
