package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

public interface ReimbursementDao {
	
	boolean addReimbursement(Reimbursement r);
	
	Reimbursement getReimbursementById(int id);
	
	ArrayList<Reimbursement> getAllReimbursements();
	
	ArrayList<Reimbursement> gettAllReimbursementWithAllInfo();
	
	ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type);
	
	ArrayList<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status);
	
	ArrayList<Reimbursement> getReimbursementsByEmployee(User u);
	
	ArrayList<Reimbursement> getReimbursementsByManager(User u);
	
	Reimbursement updateReimbursement(Reimbursement re, int reId);
	
	boolean deleteReimbursement(int reId);
}
