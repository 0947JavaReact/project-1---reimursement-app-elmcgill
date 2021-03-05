package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.User;
import com.revature.models.UserType;

public interface UserDao {
	
	User getUser();
	
	User getUserById();
	
	User getUserByUserName();
	
	User getUserByEmail();
	
	User getAllUsersByType(UserType type);
	
	ArrayList<User> getAllUsers();
	
	ArrayList<User> getAllUserWithTypeInfo();
	
	User updateUser(User u, int userId);
	
	boolean deleteUser(int userId);
}
