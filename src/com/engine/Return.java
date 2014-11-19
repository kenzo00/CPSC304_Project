package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.mysql.jdbc.Connection;

public class Return 
{
	
	private Connection connection;
	
	// Constructor
	public Return() 
	{
		
	}
	
	// Checks if the purchase was made within 15 days
	public boolean withinDate( int receiptId ) 
	{
		String query = "SELECT date FROM Order WHERE receiptId=" + receiptId;
		boolean isWithinDate = false;
		
		try 
		{
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			Date date;
			
			// Get the current date
			LocalDate currentDate = LocalDate.now();
			
			result.next();
			date = result.getDate( 2 );
			
			isWithinDate = day15DaysAfter ( date.toLocalDate(), currentDate );
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
		}
		return isWithinDate;
	}
	
	// Returns true if secondDate is 15 days after firstDate
	// Returns false otherwise
	public boolean day15DaysAfter( LocalDate firstDate, LocalDate secondDate )
	{
		boolean is15DaysAfter = false;
		
		firstDate.plusDays( 15 );
		
		if ( firstDate.isAfter( secondDate ) )
		{
			is15DaysAfter = true;
		}
		
		return is15DaysAfter;
	}
	
}
