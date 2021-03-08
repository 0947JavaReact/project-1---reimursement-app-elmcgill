package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.models.User;

public class UserService {
	
	//What do users need to do?
	//Register
	//Login
	//Remove account
	
	private UserDao uDao;
	
	public UserService(UserDao uDao) {
		this.uDao = uDao;
	}
	
	//Register a new user
	public boolean registerUser(User u) {
		
		
		
		return false;
	}
	
}
