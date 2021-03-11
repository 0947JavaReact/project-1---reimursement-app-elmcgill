package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDaoConcrete;
import com.revature.exceptions.InvalidCredentialsException;
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
	    	int id = uServ.loginUser(username, password);
	    	res.getWriter().write("Success");
	    } catch(InvalidCredentialsException e) {
	    	res.setStatus(400);
	    	res.getWriter().write("Username or password incorect");
	    }
	    
	}
	
}
