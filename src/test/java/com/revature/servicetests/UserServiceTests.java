package com.revature.servicetests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.UserDao;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.services.UserService;

public class UserServiceTests {

	@InjectMocks
	public UserService uServ;

	@Mock
	public UserDao uDao;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void testValidLogin() {

		User u1 = new User(1, "test", "$2a$12$bTSdopUOmgCX/54YX70ZF.qW0.xHLJ6T1x4W/E/IfDJPzUlhYL1IK", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.getUserByUserName(anyString())).thenReturn(u1);

		User loggedIn = uServ.loginUser("test", "password");

		assertEquals(u1.getUserId(), loggedIn.getUserId());

	}

	@Test(expected = InvalidCredentialsException.class)
	public void testBadUserNameLogin() {
		
		User bad = new User(-1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);
		
		when(uDao.getUserByUserName(anyString())).thenReturn(bad);

		User loggedIn = uServ.loginUser("test", "password");
	}

	@Test(expected = InvalidCredentialsException.class)
	public void testBadPasswordLogin() {

		User u1 = new User(1, "test", "$2a$12$bTSdopUOmgCX/54YX70ZF.qW0.xHLJ6T1x4W/E/IfDJPzUlhYL1Ir", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.getUserByUserName(anyString())).thenReturn(u1);

		User loggedIn = uServ.loginUser("test", "password");
	}

	@Test
	public void testValidRegistration() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);
		User not = new User(-1, "test", "password", "jdbctest", "user", "testemail1@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.addUser(any())).thenReturn(true);
		when(uDao.getUserByUserName("test")).thenReturn(not);
		when(uDao.getUserByEmail("testemail@gmail.com")).thenReturn(not);

		boolean registered = uServ.registerUser("test", "testemail@gmail.com", "password", UserType.EMPLOYEE);

		assertTrue(registered);

	}

	@Test(expected = UsernameAlreadyExistsException.class)
	public void testUsernameAlreadyExists() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.getUserByUserName("test")).thenReturn(u1);

		boolean registered = uServ.registerUser("test", "testemail@gmail.com", "password", UserType.EMPLOYEE);

	}

	@Test(expected = EmailAlreadyExistsException.class)
	public void testEmailAlreadyExists() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);
		User not = new User(-1, "test", "password", "jdbctest", "user", "testemail1@gmail.com", UserType.EMPLOYEE, null);
		
		when(uDao.getUserByUserName("test")).thenReturn(not);
		when(uDao.getUserByEmail("testemail@gmail.com")).thenReturn(u1);

		boolean registered = uServ.registerUser("test", "testemail@gmail.com", "password", UserType.EMPLOYEE);

	}

}
