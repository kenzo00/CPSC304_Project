package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

public class Return 
{

	private Connection connection;
	int success = 1; // 1
	int nonMatchingReceipt = 2; // 2
	boolean goodUPC;
	boolean badUPC;

	
	// Constructor
	public Return() 
	{

	}
	public void refund(int receiptId, int upc) {

		if (isValid(receiptId) == success && newRefund(receiptId) == true && isCorrectUpc(receiptId, upc) == goodUPC) {
			// and increment stock with query; 
			String query = "UPDATE stored WHERE upc=" + upc + ",receiptId=" + receiptId; 
			String query0 = "UPDATE stored WHERE receiptId=" + receiptId; 
			
			// update table that return is made
			String query1 = "INSERT INTO return WHERE receiptId=" + receiptId;
			String query2 = "INSERT INTO returnItem WHERE upc=" + upc;
			String query3 = "INSERT INTO returnItem WHERE upc=" + upc;
			// display message "credit card refunded"
			
		}
		if (isValid(receiptId) == nonMatchingReceipt && newRefund(receiptId) == true) {
			// display that the receipt does not match and cancel refund
			
		}
		if (newRefund(receiptId) == false) {
			// display that the item has already been returned and cancel refund
			
		}
		

	}
	// Checks if the purchase was made within 15 days
	public int isValid( int receiptId) 
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
				if (isWithinDate == true) {
					return success;
				}
				
			}
				
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
		}
		return nonMatchingReceipt;
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
	
	// Check if upc is the matching upc
	public boolean isCorrectUpc( int receiptId, int upc) {
		
		String query = "SELECT upc FROM PurchaseItem WHERE receiptId=" + receiptId;

		try 
		{
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			ResultSet result = ps.executeQuery();

			
			boolean hasNext;
			hasNext = result.next();
			// checks if there are any matching receiptID's
			if (hasNext == true) {
				int upcDB = result.getInt(upc);

				boolean isCorrect = (upcDB == upc);
				if (isCorrect == true) {
					goodUPC = true;
					return goodUPC;
				}	
			}
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
		}
		badUPC = false;
		return badUPC;
	}
	
	// Returns true if this refund is new and not in the return table
    // Returns false if the refund is already in the return table
	public boolean newRefund( int receiptId ) {
		String query = "SELECT * FROM Return WHERE receiptId=" + receiptId;
		
		List<Integer> returnIds = new ArrayList<Integer>();
		
		try 
		{
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			ResultSet result = ps.executeQuery();

			// add returnIds that correspond to the receiptId
			while ( result.next() )
			{
				returnIds.add( result.getInt(1) );
			}
			
			// check to see if the item being returned has already been returned
			if (returnIds.isEmpty()) 
				return true;
			
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
		} 
			return false;
	}

	// prompt message to say refunded to credit card
	// check if item has already been returned


}
