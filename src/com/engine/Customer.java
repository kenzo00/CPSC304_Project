package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer 
{
	
	public Customer ()
	{
		
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
}
