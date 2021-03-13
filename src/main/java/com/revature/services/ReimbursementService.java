package com.revature.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

public class ReimbursementService {

	private ReimbursementDao rDao;

	public ReimbursementService(ReimbursementDao rDao) {
		this.rDao = rDao;
	}
	
	public Reimbursement getReimbursementById(int id) {
		
		return rDao.getReimbursementById(id);
		
	}

	// Get all Reimbursements with All info
	public ArrayList<Reimbursement> getAllReimbursements() {
		return rDao.gettAllReimbursementWithAllInfo();
	}
	
	public ArrayList<Reimbursement> getAllReimbursementsById(int id){
		return rDao.getReimbursementsByEmployee(id);
	}

	public Reimbursement approveReimbursement(Reimbursement r, int id, Date date) {

		r.setReStatus(ReimbursementStatus.APPROVED);
		r.setReResolver(id);
		r.setReResolved(date);
		
		return rDao.updateReimbursement(r, r.getReId());

	}

	public Reimbursement denyReimbursement(Reimbursement r, int id, Date date) {

		r.setReStatus(ReimbursementStatus.DENIED);
		r.setReResolver(id);
		r.setReResolved(date);
		
		
		return rDao.updateReimbursement(r, r.getReId());

	}
	
	public boolean sumbitNewReimbursement(double amount, Date date, ReimbursementType type, String desc, int userid) {
		
		Reimbursement r = new Reimbursement();
		String id = UUID.randomUUID().toString();       
        int uid = Math.abs(id.hashCode());
        r.setReId(uid);
		r.setReAmount(amount);
		r.setReSubmitted(date);
		r.setReAuthor(userid);
		r.setReResolver(-1);
		r.setReDesc(desc);
		r.setReStatus(ReimbursementStatus.PENDING);
		r.setReType(type);
		
		return rDao.addReimbursement(r);
		
	}
	
	public ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type){
		return rDao.getReimbursementsByType(type);
	}
	
	public ArrayList<Reimbursement> getReibursementsByStatus(ReimbursementStatus status){
		
		return rDao.getReimbursementsByStatus(status);
		
	}
}
