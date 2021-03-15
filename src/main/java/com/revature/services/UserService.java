package com.revature.services;

import java.util.UUID;

import com.revature.dao.UserDao;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.utils.Logging;

public class UserService {
	
	//What do users need to do?
	//Register
	//Login
	//Remove account
	
	private UserDao uDao;
	
	public UserService(UserDao uDao) {
		this.uDao = uDao;
	}
	
	//Register a new user, returns true or false if the use was created in the db
	public boolean registerUser(String username, String email, String password, UserType type) {
		
		if(uDao.getUserByUserName(username).getUserId() != -1) {
			Logging.logger.warn("User attempted to create an account with a username that already exists.");
			throw new UsernameAlreadyExistsException("This username has already been taken");
		}
		else if(uDao.getUserByEmail(email).getUserId() != -1) {
			Logging.logger.warn("User attempted to create an account with a emails that already owns an account.");
			throw new EmailAlreadyExistsException("This email already has an account");
		}
		else {
			
			User u = new User();
			String id = UUID.randomUUID().toString();       
	        int uid = Math.abs(id.hashCode());
			u.setUserId(uid);
			u.setUsername(username);
			u.setEmail(email);
			u.setPassword(password);
			u.setRole(type);
			
			Logging.logger.info("User: " + username + " successfully created an account.");
			return uDao.addUser(u);
		}
	}
	
	//Logs in a user, returns the id of the logged in user
	public User loginUser(String username, String password) {
		
		User toLogin = uDao.getUserByUserName(username);
		
		System.out.println(toLogin);
		
		if(toLogin.getUserId() == -1) {
			Logging.logger.warn("User attempted to login with invalid credentials.");
			throw new InvalidCredentialsException("Your username or password are incorrect");
		}
		
		if(!toLogin.getPassword().equals(password)) {
			Logging.logger.warn("User attempted to login with invalid credentials.");
			throw new InvalidCredentialsException("Your username or password are incorrect");
		}
		
		return toLogin;
	}
	
	public User getUserById(int id) {
		
		return uDao.getUserById(id);
		
	}
}
