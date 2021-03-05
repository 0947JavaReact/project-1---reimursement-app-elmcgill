package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			System.out.println(u.getRole().ordinal());
			cs.setInt(8, (int) u.getRole().ordinal()+1);
			
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
				if(rs.getInt(7) == 1) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getAllUsersByType(UserType type) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<User> getAllUserWithTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public User updateUser(User u, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteUser(int userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
