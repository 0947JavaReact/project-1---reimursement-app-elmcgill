package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDaoConcrete implements ReimbursementDao{
	
	private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	private Connection con = conUtil.getConnection();
	
	public boolean addReimbursement(Reimbursement r) {
		
		try {
			String sql = "{? = call create_reimbursement(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.BOOLEAN);
			cs.setInt(2, r.getReId());
			cs.setDouble(3, r.getReAmount());
			cs.setDate(4, r.getReSubmitted());
			cs.setDate(5, r.getReResolved());
			cs.setString(6, r.getReDesc());
			cs.setInt(7, r.getReAuthor());
			cs.setInt(8, r.getReResolver());
			cs.setInt(9, r.getReStatus().ordinal());
			cs.setInt(10, r.getReType().ordinal());
			
			cs.execute();
			
			return cs.getBoolean(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Reimbursement getReimbursementById(int id) {
		
		Reimbursement r = new Reimbursement();
		
		String sql = "SELECT * FROM reimbursement WHERE re_id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch(rs.getInt(8)) {
				case 0:
					r.setReStatus(ReimbursementStatus.PENDING);
					break;
				case 1:
					r.setReStatus(ReimbursementStatus.APPROVED);
					break;
				default:
					r.setReStatus(ReimbursementStatus.DENIED);
					break;
				}
				switch(rs.getInt(9)) {
				case 0:
					r.setReType(ReimbursementType.LODGING);
					break;
				case 1:
					r.setReType(ReimbursementType.TRAVEL);
					break;
				case 2:
					r.setReType(ReimbursementType.FOOD);
					break;
				default:
					r.setReType(ReimbursementType.OTHER);
					break;
				
				}
					
			}
			return r;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<Reimbursement> getAllReimbursements() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Reimbursement> gettAllReimbursementWithAllInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Reimbursement> getReimbursementsByEmployee(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Reimbursement> getReimbursementsByManager(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public Reimbursement updateReimbursement(Reimbursement re, int reId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteReimbursement(int reId) {
		// TODO Auto-generated method stub
		return false;
	}

}
