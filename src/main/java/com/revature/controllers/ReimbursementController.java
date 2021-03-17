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
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.services.ReimbursementService;

public class ReimbursementController {

	private static ReimbursementDao rDao = new ReimbursementDaoConcrete();
	private static ReimbursementService rServ = new ReimbursementService(rDao);

	public static void getAllById(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {

		int param1 = Integer.parseInt(req.getParameter("id"));

		ArrayList<Reimbursement> rList = rServ.getAllReimbursements();
		
		System.out.println(rList);
		
		ArrayList<Reimbursement> retList = new ArrayList<>();

		for (int i = 0; i < rList.size(); i++) {
			Reimbursement cur = rList.get(i);

			if (cur.getReAuthor() == param1) {
				retList.add(cur);
			}
		}

		res.getWriter().write((new ObjectMapper().writeValueAsString(retList)));
	}

	public static void getAllReimbursements(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {

		ArrayList<Reimbursement> rList = rServ.getAllReimbursements();
		System.out.println(rList);
		res.getWriter().write((new ObjectMapper().writeValueAsString(rList)));

	}

	public static void addNewReimbursement(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {

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
		switch (Integer.parseInt(parsedObj.get("type").asText())) {
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

		System.out.println(rServ.sumbitNewReimbursement(amount, submitted, type, desc, author));

		ObjectNode ret = mapper.createObjectNode();
		ret.put("message", "successfully submitted a new reimbursement");

		res.getWriter().write((new ObjectMapper().writeValueAsString(ret)));

	}

	public static void filterReimbursements(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {
		
		System.out.println("in the filter controller");

		int param1 = Integer.parseInt(req.getParameter("status"));
		
		System.out.println(param1);
		
		ReimbursementStatus status = null;
		switch (param1) {
		case 0:
			status = ReimbursementStatus.PENDING;
			break;
		case 1:
			status = ReimbursementStatus.APPROVED;
			break;
		case 2:
			status = ReimbursementStatus.DENIED;
			break;
		}

		ArrayList<Reimbursement> rList = rServ.getReibursementsByStatus(status);

		System.out.println(rList);
		res.getWriter().write((new ObjectMapper().writeValueAsString(rList)));
	}

	public static void acceptReimbursement(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {

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

		int userId = Integer.parseInt(parsedObj.get("userid").asText());
		int reId = Integer.parseInt(parsedObj.get("reid").asText());
		Date resolved = new Date(Long.parseLong(parsedObj.get("date").asText()));

		Reimbursement r = rServ.getReimbursementById(reId);

		rServ.approveReimbursement(r, userId, resolved);

		res.getWriter().write("OK");
	}

	public static void denyReimbursement(HttpServletRequest req, HttpServletResponse res)
			throws JsonProcessingException, IOException {

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

		int userId = Integer.parseInt(parsedObj.get("userid").asText());
		int reId = Integer.parseInt(parsedObj.get("reId").asText());
		Date resolved = new Date(Long.parseLong(parsedObj.get("date").asText()));

		Reimbursement r = rServ.getReimbursementById(reId);

		rServ.denyReimbursement(r, userId, resolved);

	}

}
