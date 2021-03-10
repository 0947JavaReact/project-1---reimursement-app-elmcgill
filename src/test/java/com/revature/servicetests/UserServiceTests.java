package com.revature.servicetests;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.getUserByUserName(anyString())).thenReturn(u1);

		int id = uServ.loginUser("test", "password");

		assertEquals(u1.getUserId(), id);

	}

	@Test(expected = InvalidCredentialsException.class)
	public void testBadUserNameLogin() {
		when(uDao.getUserByUserName(anyString())).thenReturn(null);

		int id = uServ.loginUser("test", "password");
	}

	@Test(expected = InvalidCredentialsException.class)
	public void testBadPasswordLogin() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.getUserByUserName(anyString())).thenReturn(u1);

		int id = uServ.loginUser("test", "badpassword");
	}

	@Test
	public void testValidRegistration() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.addUser(u1)).thenReturn(true);
		when(uDao.getUserByUserName("jdbctest")).thenReturn(null);
		when(uDao.getUserByEmail("testemail@gmail.com")).thenReturn(null);

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

		when(uDao.getUserByUserName("test")).thenReturn(null);
		when(uDao.getUserByEmail("testemail@gmail.com")).thenReturn(u1);

		boolean registered = uServ.registerUser("test", "testemail@gmail.com", "password", UserType.EMPLOYEE);

	}

	@Test
	public void testValidDeleteAccount() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.deleteUser(u1.getUserId())).thenReturn(true);

		boolean deleted = uServ.removeAccount(u1, "password");
		assertTrue(deleted);
	}

	@Test(expected = InvalidCredentialsException.class)
	public void testInvalidDelete() {

		User u1 = new User(1, "test", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, null);

		when(uDao.deleteUser(u1.getUserId())).thenReturn(true);

		boolean deleted = uServ.removeAccount(u1, "pass");

	}

}
