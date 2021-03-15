package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.UserDaoConcrete;
import com.revature.exceptions.InvalidCredentialsException;
import com.revature.models.User;
import com.revature.services.UserService;

public class LoginController {
	
	private static UserDaoConcrete uDao = new UserDaoConcrete();
	private static UserService uServ = new UserService(uDao);
	
	public static void doLogin(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		System.out.println("In the doLogin");
		
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = req.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	        buffer.append(System.lineSeparator());
	    }
	    String data = buffer.toString();
	    
	    System.out.println(data);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode parsedObj = mapper.readTree(data);
		
	    String username = parsedObj.get("username").asText();
	    String password = parsedObj.get("password").asText();
	    
	    System.out.println(username);
	    System.out.println(password);
	    
	    try {
	    	User logged = uServ.loginUser(username, password);
	    	
	    	int id = logged.getUserId();
	    	int type = logged.getRole().ordinal();
	    	String message = "sucess";
	    	//We need to store the user in session
	    	HttpSession session = req.getSession();
			session.setAttribute("currentUser", id);
			session.setAttribute("userRole", type);
	    	
			
			
	    	ObjectNode user = mapper.createObjectNode();
	    	user.put("userId", id);
	    	user.put("userType", type);
	    	user.put("message", message);
	    	
	    	res.getWriter().write((new ObjectMapper().writeValueAsString(user)));
	    } catch(InvalidCredentialsException e) {
	    	res.setStatus(400);
	    	res.getWriter().write("Username or password incorect");
	    }
	}
	
}
