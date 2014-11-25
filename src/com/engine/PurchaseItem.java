package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseItem {
	
	int quantity;
	
	public PurchaseItem() {
		
	}
	
	public int getQuantity(int receiptId, int upc) {
		
		String query = "Select quantity from cpsc304.PurchaseItem where receiptId =" + receiptId +" AND upc =" + upc;
		
		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				this.quantity = result.getInt(1);
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return quantity;
	}

}
