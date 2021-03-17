package com.revature.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controllers.GetUserController;
import com.revature.controllers.LoginController;
import com.revature.controllers.LogoutController;
import com.revature.controllers.RegisterController;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.SessionController;

public class ServletHelper {
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		System.out.println(req.getRequestURI());
		
		switch(req.getRequestURI()) {
		
		case "/project1/login":
			LoginController.doLogin(req, res);
			break;
		case "/project1/register":
			RegisterController.registerUser(req, res);
			break;
		case "/project1/getSession":
			SessionController.getSession(req,res);
			break;
		case "/project1/getUser":
			GetUserController.getUser(req, res);
			break;
		case "/project1/logout":
			LogoutController.doLogout(req, res);
			break;
		case "/project1/getAllReimbursements":
			ReimbursementController.getAllReimbursements(req, res);
			break;
		case "/project1/getAllReimbursementsById":
			ReimbursementController.getAllById(req,res);
			break;
		case "/project1/filterReimbursements":
			ReimbursementController.filterReimbursements(req, res);
			break;
		case "/project1/newReimbursement":
			ReimbursementController.addNewReimbursement(req, res);
			break;
		case "/project1/approveReimbursement":
			ReimbursementController.acceptReimbursement(req, res);
			break;
		case "/project1/denyReimbursement":
			ReimbursementController.denyReimbursement(req, res);
			break;
		}
	}
	
}
