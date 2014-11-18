package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Return {
	private Connection connection;
	// Constructor
	public Return() {
		
	}
	
	public boolean withinDate() {
		String query = "SELECT * FROM cpsc304.Order WHERE DateB >= DATEADD(DAY, -15, DateA)";
		boolean bool = false;
		ResultSet result;
		try {
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			result = ps.executeQuery();
			
			while (result.next()) {
				bool = true;
			} 
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return bool;	
	}
	
}
