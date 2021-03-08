package com.revature.services;

import com.revature.dao.UserDao;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.InvalidCredentialsException;
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
	
	//Register a new user, returns true or false if the use was created in the db
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
	
	//Logs in a user, returns the id of the logged in user
	public int loginUser(String username, String password) {
		
		User toLogin = uDao.getUserByUserName(username);
		
		if(toLogin == null) {
			throw new InvalidCredentialsException("Your username or password are incorrect");
		}
		
		if(!toLogin.getPassword().equals(password)) {
			throw new InvalidCredentialsException("Your username or password are incorrect");
		}
		
		return toLogin.getUserId();
	}
	
	public boolean removeAccount(User u, String password) {
		
		if(!u.getPassword().equals(password)) {
			throw new InvalidCredentialsException("The password you supplied is incorrect");
		}
		
		return uDao.deleteUser(u.getUserId());
	}
	
}
