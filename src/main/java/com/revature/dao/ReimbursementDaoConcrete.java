package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class ReimbursementDaoConcrete implements ReimbursementDao {

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
			if(r.getReResolver() == -1) {
				cs.setNull(4, Types.INTEGER);
			}else {
				cs.setInt(8, r.getReResolver());
			}
			cs.setInt(9, r.getReStatus().ordinal());
			cs.setInt(10, r.getReType().ordinal());

			cs.execute();

			return cs.getBoolean(1);

		} catch (SQLException e) {
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

			while (rs.next()) {
				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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
		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM reimbursement;";

		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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

				rList.add(r);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rList;
	}

	public ArrayList<Reimbursement> gettAllReimbursementWithAllInfo() {

		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "{? = call get_all_re_with_users_status_type()}";

		try {
			con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.OTHER);
			cs.execute();
			ResultSet rs = (ResultSet) cs.getObject(1);

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setAuthorString(rs.getString(6));
				r.setResolverString(rs.getString(7));
				r.setStatusString(rs.getString(8));
				r.setTypeString(rs.getString(9));

				rList.add(r);
			}

			return rList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Reimbursement> getReimbursementsByType(ReimbursementType type) {
		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM reimbursement WHERE re_type = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, type.ordinal());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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

				rList.add(r);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rList;
	}

	public ArrayList<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status) {
		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM reimbursement WHERE re_status = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, status.ordinal());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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

				rList.add(r);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rList;
	}

	public ArrayList<Reimbursement> getReimbursementsByEmployee(User u) {
		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM reimbursement WHERE re_author = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, u.getUserId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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

				rList.add(r);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rList;
	}

	public ArrayList<Reimbursement> getReimbursementsByManager(User u) {
		ArrayList<Reimbursement> rList = new ArrayList<Reimbursement>();
		String sql = "SELECT * FROM reimbursement WHERE re_resolver = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, u.getUserId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReId(rs.getInt(1));
				r.setReAmount(rs.getDouble(2));
				r.setReSubmitted(rs.getDate(3));
				r.setReResolved(rs.getDate(4));
				r.setReDesc(rs.getString(5));
				r.setReAuthor(rs.getInt(6));
				r.setReResolver(rs.getInt(7));
				switch (rs.getInt(8)) {
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
				switch (rs.getInt(9)) {
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

				rList.add(r);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rList;
	}

	public Reimbursement updateReimbursement(Reimbursement re, int reId) {
		
		String sql =
				"UPDATE reimbursement SET "+
				"amount = ?, submitted_date = ?, resolved_date = ?, description = ?, re_author = ?, re_resolver = ?, re_status = ?, re_type = ? "+
				"WHERE re_id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, re.getReAmount());
			ps.setDate(2, re.getReSubmitted());
			ps.setDate(3, re.getReResolved());
			ps.setString(4, re.getReDesc());
			ps.setInt(5, re.getReAuthor());
			ps.setInt(6, re.getReResolver());
			ps.setInt(7, re.getReStatus().ordinal());
			ps.setInt(8, re.getReType().ordinal());
			ps.setInt(9, reId);
			
			ps.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return re;
	}

	public boolean deleteReimbursement(int reId) {
		String sql = "DELETE from users WHERE re_id = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, reId);
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
