package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Return 
{
	
	private Connection connection;
	
	// Constructor
	public Return() 
	{
		
	}
	
	// Checks if the purchase was made within 15 days
	public boolean withinDate( int receiptId, Date currentDate ) 
	{
		String query = "SELECT date FROM Order WHERE receiptId=" + receiptId;
		boolean isWithinDate = false;
		
		try 
		{
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			Date date;
			
			result.next();
			date = result.getDate( 2 );
			
			isWithinDate = day15DaysAfter ( date, currentDate );
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
		}
		return isWithinDate;
	}
	
	// Returns true if firstDate is 15 days after secondDate
	// Returns false otherwise
	public boolean day15DaysAfter( Date firstDate, Date secondDate )
	{
		boolean is15DaysAfter = false;
		
		return is15DaysAfter;
	}
	
}
