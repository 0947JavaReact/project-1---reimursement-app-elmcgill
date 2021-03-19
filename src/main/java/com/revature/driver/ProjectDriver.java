package com.revature.driver;

import java.sql.Date;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoConcrete;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class ProjectDriver {

	public static void main(String[] args) {
		
		User u = new User(4, "jdbc1", "password", "jdbctest", "user", "testemail@gmail.com", UserType.EMPLOYEE, "");
		Reimbursement r1 = new Reimbursement(1, 100.0d, new Date(1612802770000l), new Date(1615221970000l), "This is a test 1", 932027899, -1,
				ReimbursementStatus.PENDING, ReimbursementType.FOOD, null, null, null, null);
		
		UserDao uDao = new UserDaoConcrete();
		
		UserService uServ = new UserService(uDao);
		
		//uServ.registerUser("hashtest", "hashTest@email.com", "password", UserType.EMPLOYEE);
		User loggedIn = uServ.loginUser("hashtest", "password");
		System.out.println(loggedIn);
		//uDao.addUser(u);
		
		Reimbursement r2 = new Reimbursement(2, 200.0d, new Date(1612802770000l), new Date(1615221970000l),
				"This is a test 2", 932027899, 193485667, ReimbursementStatus.APPROVED, ReimbursementType.LODGING, null, null, null, null);
		
		ReimbursementDao rDao = new ReimbursementDaoConcrete();
		
		System.out.println(rDao.addReimbursement(r2));
		
		ReimbursementService rServ = new ReimbursementService(rDao);
		System.out.println(rServ.sumbitNewReimbursement(100, new Date(1612802770000l), ReimbursementType.FOOD, "Desc", 932027899));
	}
	
}
