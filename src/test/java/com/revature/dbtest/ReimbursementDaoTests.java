package com.revature.dbtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import org.junit.Before;
import org.junit.Test;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoConcrete;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDaoTests {
	
	private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	private Connection con = conUtil.getConnection();
	private UserDao uDao = new UserDaoConcrete();
	private ReimbursementDao rDao = new ReimbursementDaoConcrete();
	
	Date sqlDate = new Date(1615133769896l);
	
	private Reimbursement r1 = new Reimbursement(1, 100.0d, sqlDate, (Date)null, "This is a test 1", 1, -1, ReimbursementStatus.PENDING, ReimbursementType.FOOD, null, null, null, null);
	private Reimbursement r2 = new Reimbursement(2, 200.0d, new Date(1610452800), new Date(1610452800), "This is a test 2", 1, 2, ReimbursementStatus.APPROVED, ReimbursementType.LODGING, null, null, null, null);
	private Reimbursement r3 = new Reimbursement(3, 200.0d, new Date(1610452800), new Date(1610452800), "This is a test 3", 1, 2, ReimbursementStatus.DENIED, ReimbursementType.TRAVEL, null, null, null, null);
	
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
			
			System.out.println("Insert users for the author and resolver fields");
			User u1 = new User(1, "jdbc1", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, "");
			User u2 = new User(2, "jdbc2", "password", "jdbctest2", "user2", "testemail2@gmail.com", UserType.MANAGER, "");
			uDao.addUser(u1);
			uDao.addUser(u2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAddAndGetReById() {
		
		rDao.addReimbursement(r1);
		Reimbursement retrieved = rDao.getReimbursementById(r1.getReId());
		
		assertEquals(r1.getReId(), retrieved.getReId());
		assertEquals(r1.getReAmount(), retrieved.getReAmount(), 0.001);
		
		int year1 = r1.getReSubmitted().getYear();
		int year2 = retrieved.getReSubmitted().getYear();
		
		int month1 = r1.getReSubmitted().getMonth();
		int month2 = retrieved.getReSubmitted().getMonth();
		
		int day1 = r1.getReSubmitted().getDay();
		int day2 = retrieved.getReSubmitted().getDay();
		
		assertEquals(year1, year2);
		assertEquals(month1, month2);
		assertEquals(day1, day2);
		
		assertEquals(null, retrieved.getReResolved());
		assertEquals(r1.getReDesc(), retrieved.getReDesc());
		assertEquals(r1.getReAuthor(), retrieved.getReAuthor());
		assertEquals(-1, retrieved.getReResolver());
		assertEquals(r1.getReStatus(), retrieved.getReStatus());
		assertEquals(r1.getReType(), retrieved.getReType());
	}
	
}
