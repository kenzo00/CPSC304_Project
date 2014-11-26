package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer 
{
	
	public Customer ()
	{
		
	}
	
	public boolean isPassCorrect (int cid, String password) {
		String query = "SELECT * FROM cpsc304.Customer where cid=" + cid +" AND password=\"" +password + "\"";
		
		boolean isCorrect = false;
		
		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			
			// Execute sql query
			ResultSet result = ps.executeQuery();
			
			if ( result.next() )
			{
				isCorrect = true;
			}
			
			ps.close();
			
		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
		}
		
		return isCorrect;
	}
	
	// Checks if the customer is registered
	// Returns true if the customer is registered
	// Returns false if the customer is not registered or the sql query fails
	public boolean isRegistered ( int cid )
	{
		String query = "SELECT * FROM cpsc304.Customer WHERE cid=" + cid;
		
		boolean isRegistered = false;
		
		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			
			// Execute sql query
			ResultSet result = ps.executeQuery();
			
			if ( result.next() )
			{
				isRegistered = true;
			}
			
			ps.close();
			
		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
		}
		
		return isRegistered;
	}
	
	// Registers a customer
	public void register ( int cid, String password, String name, String address, String phone )
	{
		// Build the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "(" ).append( cid ).append( ",'" ).append( password ).append( "','" );
		stringBuilder.append( name ).append( "','" ).append( address ).append( "'," ).append( phone );
		String values = stringBuilder.append( ")" ).toString();
		
		// Execute the query
		Engine.getInstance().getQueries().insertQuery( "Customer", values );
	}
	
	public String getName( int cid )
	{
		String name = "";
		
		String query = "SELECT name FROM cpsc304.Customer WHERE cid=" + cid;
		
		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			while ( result.next() )
			{
				// There should only be one result because cid is the primary key of Customer
				name = result.getString(1);
			}
						
			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}
		
		return name;
	}
}
