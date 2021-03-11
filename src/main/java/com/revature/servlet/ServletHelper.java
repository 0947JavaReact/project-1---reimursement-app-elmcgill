package com.revature.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.controllers.LoginController;

public class ServletHelper {
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		System.out.println(req.getRequestURI());
		
		switch(req.getRequestURI()) {
		
		case "/project1/login":
			LoginController.doLogin(req, res);
			break;
		}
		
	}
	
}
