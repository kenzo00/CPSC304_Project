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
	int success = 1;
	
	// Constructor
	public Return() 
	{
		
	}
	public int refund(int receiptId, int upc) {
		
		if (isValid(receitptId, upc) == success) {
			// increment stock, and update table that return is made 
			String query = "UPDATE stored WHERE upc=" + upc + ",receiptId=" + receiptId //and increment stock; 
		}
	}
	// Checks if the purchase was made within 15 days
	public int isValid( int receiptId, int upc ) //also check upc
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
			
			boolean hasNext;
			hasNext = result.next();
			// checks if there are any matching receiptID's
			if (hasNext == true) {
				date = result.getDate( 2 );
				
				isWithinDate = !day15DaysAfter ( date.toLocalDate(), currentDate );

			}
			else 
				return 1;
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
		
		if ( firstDate.isBefore( secondDate ) )
		{
			is15DaysAfter = true;
		}
		
		return is15DaysAfter;
	}
	
	// prompt message to say refunded to credit card
	// check if item has already been returned
	
	
}
