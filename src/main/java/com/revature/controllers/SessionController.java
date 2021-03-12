package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SessionController {
	
	public static void getSession(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		System.out.println("In the Session Controller");
		
		HttpSession session = req.getSession();
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode sesInfo = mapper.createObjectNode();
		
		System.out.println(session.getAttribute("currentUser"));
		
		if(session.getAttribute("currentUser") == null && session.getAttribute("userRole") == null) {
			int id = -1;
			int role = -1;
			
			sesInfo.put("userid", id);
			sesInfo.put("role", role);
		}
		else {
			int id = (Integer) session.getAttribute("currentUser");
			int role = (Integer) session.getAttribute("userRole");
			
			sesInfo.put("userid", id);
			sesInfo.put("role", role);
		}
		
		res.getWriter().write((new ObjectMapper().writeValueAsString(sesInfo)));
		//res.getWriter().write("Default");
	}
}
