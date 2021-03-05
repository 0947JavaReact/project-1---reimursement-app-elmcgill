package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.User;
import com.revature.models.UserType;

public interface UserDao {
	
	boolean addUser(User u);
	
	User getUserById(int id);
	
	User getUserByUserName(String username);
	
	User getUserByEmail(String email);
	
	User getAllUsersByType(UserType type);
	
	ArrayList<User> getAllUsers();
	
	ArrayList<User> getAllUserWithTypeInfo();
	
	User updateUser(User u, int userId);
	
	boolean deleteUser(int userId);
}
