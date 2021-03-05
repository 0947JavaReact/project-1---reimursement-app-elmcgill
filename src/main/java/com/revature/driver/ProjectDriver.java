package com.revature.driver;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.models.User;
import com.revature.models.UserType;

public class ProjectDriver {

	public static void main(String[] args) {
		
		User u = new User(4, "jdbc1", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE);
		
		UserDao uDao = new UserDaoConcrete();
		
		uDao.addUser(u);
	}
	
}
