package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.UserDao;
import com.revature.dao.UserDaoConcrete;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.models.UserType;
import com.revature.services.UserService;

public class RegisterController {
	
	private static UserDao uDao = new UserDaoConcrete();
	private static UserService uServ = new UserService(uDao);
	
	public static void registerUser(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		System.out.println("In the register method");
		
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
	    String email = parsedObj.get("email").asText();
	    String password = parsedObj.get("password").asText();
	    int role = Integer.parseInt(parsedObj.get("role").asText());
	    
	    UserType type = null;
	    
	    switch(role) {
	    	case 0:
	    		type = UserType.EMPLOYEE;
	    		break;
	    	case 1:
	    		type = UserType.MANAGER;
	    	break;
	    }
	    
	    System.out.println(username);
	    System.out.println(email);
	    System.out.println(password);
	    System.out.println(role);
	    
	    ObjectNode ret = mapper.createObjectNode();
	    
	    try {
	    	
	    	uServ.registerUser(username, email, password, type);
	    	ret.put("message", "User successfully registered");
	    	res.getWriter().write((new ObjectMapper().writeValueAsString(ret)));
	    	
	    } catch(UsernameAlreadyExistsException e) {
	    	res.setStatus(400);
	    	ret.put("message", "Username already exists, please choose a different one.");
	    	res.getWriter().write((new ObjectMapper().writeValueAsString(ret)));
	    } catch(EmailAlreadyExistsException e) {
	    	res.setStatus(400);
	    	ret.put("message", "This email already has an account associated with it. Please try to sign in with that accounts username.");
	    	res.getWriter().write((new ObjectMapper().writeValueAsString(ret)));
	    }
	}
	
}
