package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDao {
	
	Reimbursement getReimbursementById();
	
	ArrayList<Reimbursement> getAllReimbursements();
	
	ArrayList<Reimbursement> gettAllReimbursementWithAllInfo();
	
	ArrayList<Reimbursement> getReimbursementsByType();
	
	ArrayList<Reimbursement> getReimbursementsByStatus();
	
	ArrayList<Reimbursement> getReimbursementsByUser(User u);
	
	ArrayList<Reimbursement> getReimbursementsByManager(User u);
	
	Reimbursement updateReimbursement(Reimbursement re, int reId);
	
	boolean deleteReimbursement(int reId);
}
