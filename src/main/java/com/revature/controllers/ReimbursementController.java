package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoConcrete;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.services.ReimbursementService;

public class ReimbursementController {
	
	private static ReimbursementDao rDao = new ReimbursementDaoConcrete();
	private static ReimbursementService rServ = new ReimbursementService(rDao);
	
	public static void getAllById(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		int param1 = Integer.parseInt(req.getParameter("id"));
		
		ArrayList<Reimbursement> rList = rServ.getAllReimbursements();
		
		ArrayList<Reimbursement> retList = new ArrayList<>();
		
		for(int i=0; i<rList.size(); i++) {
			Reimbursement cur = rList.get(i);
			
			if(cur.getReAuthor() == param1){
				retList.add(cur);
			}
		}
		
		res.getWriter().write((new ObjectMapper().writeValueAsString(retList)));
	}
	
	public static void addNewReimbursement(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException{
		
		System.out.println("In the add reimbursement method");
		
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
	    
	    double amount = Double.parseDouble(parsedObj.get("amount").asText());
	    Date submitted = new Date(Long.parseLong(parsedObj.get("date").asText()));
	    ReimbursementType type = ReimbursementType.FOOD;
	    switch(Integer.parseInt(parsedObj.get("type").asText())) {
	    	case 0:
	    		type = ReimbursementType.LODGING;
	    		break;
	    	case 1:
	    		type = ReimbursementType.TRAVEL;
	    		break;
	    	case 2:
	    		type = ReimbursementType.FOOD;
	    		break;
	    	case 3:
	    		type = ReimbursementType.OTHER;
	    		break;
	    }
	    
	    String desc = parsedObj.get("desc").asText();
	    int author = Integer.parseInt(parsedObj.get("author").asText());
	    
	    rServ.sumbitNewReimbursement(amount, submitted, type, desc, author);
	    
	    ObjectNode ret = mapper.createObjectNode();
	    ret.put("message", "successfully submitted a new reimbursement");
	    
	    res.getWriter().write((new ObjectMapper().writeValueAsString(ret)));
		
	}
	
}
