package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.utils.ConnectionUtil;

public class UserDaoConcrete implements UserDao{
	
	private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	private Connection con = conUtil.getConnection();
	
	public boolean addUser(User u) {
		
		try {
			String sql = "{? = call create_user(?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.BOOLEAN);
			cs.setInt(2, u.getUserId());
			cs.setString(3, u.getUsername());
			cs.setString(4, u.getPassword());
			cs.setString(5, u.getFirstName());
			cs.setString(6, u.getLastName());
			cs.setString(7, u.getEmail());
			cs.setInt(8, (int) u.getRole().ordinal());
			
			cs.execute();
			
			return cs.getBoolean(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public User getUserById(int id) {
		User u = new User();

		String sql = "SELECT * FROM users WHERE user_id = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u.setUserId(id);
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 0) {
					u.setRole(UserType.EMPLOYEE);
				} else {
					u.setRole(UserType.MANAGER);
				}
			}
			
			return u;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public User getUserByUserName(String username) {
		User u = new User();

		String sql = "SELECT * FROM users WHERE username = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if(rs.getInt(1) == 0) {
					u.setUserId(-1);
				}
				else {
					u.setUserId(rs.getInt(1));
				}
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 0) {
					u.setRole(UserType.EMPLOYEE);
				} else {
					u.setRole(UserType.MANAGER);
				}
			}
			
			if(u.getUserId() == 0) {
				u.setUserId(-1);
			}
			
			return u;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public User getUserByEmail(String email) {
		User u = new User();

		String sql = "SELECT * FROM users WHERE email = ?;";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 0) {
					u.setRole(UserType.EMPLOYEE);
				} else {
					u.setRole(UserType.MANAGER);
				}
			}
			
			return u;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<User> getAllUsersByType(UserType type) {
		
		ArrayList<User> uList = new ArrayList<User>();
		
		String sql = "SELECT * FROM users WHERE type=?;";
		
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, type.ordinal());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 0) {
					u.setRole(UserType.EMPLOYEE);
				} else {
					u.setRole(UserType.MANAGER);
				}
				uList.add(u);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return uList;
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> uList = new ArrayList<User>();
		
		String sql = "SELECT * FROM users;";
		
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getInt(7) == 0) {
					u.setRole(UserType.EMPLOYEE);
				} else {
					u.setRole(UserType.MANAGER);
				}
				uList.add(u);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uList;
	}

	public ArrayList<User> getAllUserWithTypeInfo() {
		ArrayList<User> uList = new ArrayList<User>();
		try {
			String sql = "{? = call get_all_users_with_types()}";
			con.setAutoCommit(false);
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.OTHER);
			cs.execute();
			ResultSet rs = (ResultSet)cs.getObject(1);
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setFirstName(rs.getString(4));
				u.setLastName(rs.getString(5));
				u.setEmail(rs.getString(6));
				if(rs.getString(7).equals("EMPLOYEE")) {
					u.setRole(UserType.EMPLOYEE);
					u.setUserRoleAsString("EMPLOYEE");
				} else {
					u.setRole(UserType.MANAGER);
					u.setUserRoleAsString("MANAGER");
				}
				uList.add(u);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uList;
	}

	public User updateUser(User u, int userId) {
		String sql =
				"UPDATE users SET "+
				"username = ?, password = ?, user_firstname = ?, user_lastname = ?, email = ?, type = ? "+
				"WHERE user_id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getRole().ordinal());
			ps.setInt(7,  userId);
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return u;
	}

	public boolean deleteUser(int userId) {
		String sql = "DELETE from users WHERE user_id = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
