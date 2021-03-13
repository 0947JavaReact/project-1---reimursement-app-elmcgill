package com.revature.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.Logging;

public class ReimbursementService {

	private ReimbursementDao rDao;

	public ReimbursementService(ReimbursementDao rDao) {
		this.rDao = rDao;
	}

	// Get all Reimbursements with All info
	public ArrayList<Reimbursement> getAllReimbursements() {
		Logging.logger.info("User requested a list of all reimbursement tickets");
		return rDao.gettAllReimbursementWithAllInfo();
	}
	
	public ArrayList<Reimbursement> getAllReimbursementsById(int id){
		Logging.logger.info("User requested a list of their reimbursement tickets");
		return rDao.getReimbursementsByEmployee(id);
	}

	public Reimbursement approveReimbursement(Reimbursement r, int id, Date date) {

		r.setReStatus(ReimbursementStatus.APPROVED);
		r.setReResolver(id);
		r.setReResolved(date);
		
		Logging.logger.info("Manager: " + id + " approved the reimbursement: " + r.getReId());
		
		return rDao.updateReimbursement(r, r.getReId());

	}

	public Reimbursement denyReimbursement(Reimbursement r, int id, Date date) {

		r.setReStatus(ReimbursementStatus.DENIED);
		r.setReResolver(id);
		r.setReResolved(date);
		
		Logging.logger.info("Manager: " + id + " denied the reimbursement: " + r.getReId());
		
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
		
		Logging.logger.info("User: " + userid + " submitted a new reimbursement tiecket");
		
		return rDao.addReimbursement(r);
		
	}
	
	/*
	public ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type){
		return rDao.getReimbursementsByType(type);
	}
	*/
	
	public ArrayList<Reimbursement> getReibursementsByStatus(ReimbursementStatus status){
		
		Logging.logger.info("A user requested a filted list of reimbursements");
		return rDao.getReimbursementsByStatus(status);
		
	}
}
