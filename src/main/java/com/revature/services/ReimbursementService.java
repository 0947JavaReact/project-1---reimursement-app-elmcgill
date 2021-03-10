package com.revature.services;

import java.util.ArrayList;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

public class ReimbursementService {

	private ReimbursementDao rDao;

	public ReimbursementService(ReimbursementDao rDao) {
		this.rDao = rDao;
	}

	// Get all Reimbursements with All info
	public ArrayList<Reimbursement> getAllReimbursements() {
		return rDao.gettAllReimbursementWithAllInfo();
	}

	public Reimbursement approveReimbursement(Reimbursement r) {

		r.setReStatus(ReimbursementStatus.APPROVED);

		return rDao.updateReimbursement(r, r.getReId());

	}

	public Reimbursement denyReimbursement(Reimbursement r) {

		r.setReStatus(ReimbursementStatus.DENIED);

		return rDao.updateReimbursement(r, r.getReId());

	}
	
	public boolean sumbitNewReimbursement(Reimbursement r) {
		
		return rDao.addReimbursement(r);
		
	}
	
	public ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type){
		return rDao.getReimbursementsByType(type);
	}
}
