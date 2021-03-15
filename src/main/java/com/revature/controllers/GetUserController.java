package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.models.User;
import com.revature.services.UserService;

public class GetUserController {
	
	private static UserDao uDao = new UserDaoConcrete();
	private static UserService uServ = new UserService(uDao);
	
	public static void getUser(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		int param1 = Integer.parseInt(req.getParameter("userid"));
		
		User u = uServ.getUserById(param1);
		
		res.getWriter().write((new ObjectMapper().writeValueAsString(u)));
		
	}
}
