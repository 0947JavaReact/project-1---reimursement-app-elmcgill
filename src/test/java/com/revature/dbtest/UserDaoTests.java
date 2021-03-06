package com.revature.dbtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

@Ignore
public class UserDaoTests {

	private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	private Connection con = conUtil.getConnection();
	private UserDao uDao = new UserDaoConcrete();
	
	private User u1 = new User(1, "jdbc1", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, "");
	private User u2 = new User(2, "jdbc2", "password", "jdbctest2", "user2", "testemail2@gmail.com", UserType.MANAGER, "");
	private User u3 = new User(3, "jdbc3", "password", "jdbctest3", "user3", "testemail3@gmail.com", UserType.EMPLOYEE, "");
	
	@Before
	public void initDbForTests() {
		
		System.out.println("Initalizing the DB for testing");
		String tearDown = "{? = call tear_down_db()}";
		String insertEnums = "{? = call setup_db()}";
		
		try {
			System.out.println("Trying to truncate all tables");
			CallableStatement cs = con.prepareCall(tearDown);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.execute();
			
			System.out.println("Trying to insert the ENUMs into their tables");
			cs = con.prepareCall(insertEnums);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAddAndGetUserById() {
		uDao.addUser(u1);
		User got = uDao.getUserById(u1.getUserId());
		
		assertEquals(u1.getUserId(), got.getUserId());
		assertEquals(u1.getUsername(), got.getUsername());
		assertEquals(u1.getPassword(), got.getPassword());
		assertEquals(u1.getFirstName(), got.getFirstName());
		assertEquals(u1.getLastName(), got.getLastName());
		assertEquals(u1.getEmail(), got.getEmail());
		assertEquals(u1.getRole(), got.getRole());
	}
	
	@Test
	public void testGetInvalidUserById() {
		uDao.addUser(u1);
		User got = uDao.getUserById(2);
		assertEquals(0, got.getUserId());
		assertEquals(null, got.getUsername());
		assertEquals(null, got.getPassword());
		assertEquals(null, got.getFirstName());
		assertEquals(null, got.getLastName());
		assertEquals(null, got.getEmail());
		assertEquals(null, got.getRole());
	}
	
	@Test
	public void testGetUserByUsername() {
		uDao.addUser(u1);
		User got = uDao.getUserByUserName(u1.getUsername());
		assertEquals(u1.getUserId(), got.getUserId());
		assertEquals(u1.getUsername(), got.getUsername());
		assertEquals(u1.getPassword(), got.getPassword());
		assertEquals(u1.getFirstName(), got.getFirstName());
		assertEquals(u1.getLastName(), got.getLastName());
		assertEquals(u1.getEmail(), got.getEmail());
		assertEquals(u1.getRole(), got.getRole());
	}
	
	@Test
	public void testGetUserByEmail() {
		uDao.addUser(u1);
		User got = uDao.getUserByEmail(u1.getEmail());
		assertEquals(u1.getUserId(), got.getUserId());
		assertEquals(u1.getUsername(), got.getUsername());
		assertEquals(u1.getPassword(), got.getPassword());
		assertEquals(u1.getFirstName(), got.getFirstName());
		assertEquals(u1.getLastName(), got.getLastName());
		assertEquals(u1.getEmail(), got.getEmail());
		assertEquals(u1.getRole(), got.getRole());
	}
	
	@Test
	public void testGetAllUsersByUserType() {
		uDao.addUser(u1);
		uDao.addUser(u2);
		uDao.addUser(u3);
		
		ArrayList<User> uList = uDao.getAllUsersByType(UserType.EMPLOYEE);
		assertEquals(2, uList.size());
	}
	
	@Test
	public void testGetAllUsers() {
		uDao.addUser(u1);
		uDao.addUser(u2);
		ArrayList<User> uList = uDao.getAllUsers();
		assertEquals(2, uList.size());
	}
	
	@Test
	public void testGetAllUsersWithTypeInfo() {
		
		uDao.addUser(u1);
		uDao.addUser(u2);
		ArrayList<User> uList = uDao.getAllUserWithTypeInfo();
		assertEquals(2, uList.size());
		
		User got1 = uList.get(0);
		
		assertTrue(got1.getUserRoleAsString().equals("EMPLOYEE"));
		
	}
	
	@Test
	public void testUpdateUser() {
		uDao.addUser(u1);
		u1.setFirstName("changed");
		uDao.updateUser(u1, u1.getUserId());
		User updated = uDao.getUserById(u1.getUserId());
		
		assertTrue(updated.getFirstName().equals("changed"));
	}
	
	@Test
	public void testDeleteUser() {
		uDao.addUser(u1);
		uDao.addUser(u2);
		
		boolean deleted = uDao.deleteUser(u1.getUserId());
		
		assertTrue(deleted);
		
		ArrayList<User> uList = uDao.getAllUsers();
		assertEquals(1, uList.size());
		assertEquals(2, uList.get(0).getUserId());
		
	}
}
