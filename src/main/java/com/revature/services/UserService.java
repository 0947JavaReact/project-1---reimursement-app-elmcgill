package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
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
		
		if(uDao.getUserByUserName(u.getUsername()) != null) {
			throw new UsernameAlreadyExistsException("This username has already been taken");
		}
		else if(uDao.getUserByEmail(u.getEmail()) != null) {
			throw new EmailAlreadyExistsException("This email already has an account");
		}
		else {
			return uDao.addUser(u);	
		}
	}
	
	
	
}
