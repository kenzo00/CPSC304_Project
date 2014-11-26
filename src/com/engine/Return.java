package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class Return 
{

	private Connection connection;

	int returnId;
	// Initialize the boolean values:
	// success is true if there is a matching receiptId where the order was within 15 days
	boolean success;
	// isGoodUPC is true if there is a matching upc code in the receipt 
	boolean isGoodUPC;
	// isNewRefund is true if the refund does not already exist in the return table
	boolean isNewRefund;

	// Constructor
	public Return() 
	{
		connection = Engine.getInstance().getConnection();
	}
	public void refund(int receiptId, int upc) {
		generateReturnId();
		boolean a = isValid(receiptId);
		boolean b = newRefund(receiptId, upc);
		boolean c = isCorrectUpc(receiptId, upc);
		System.out.println(" booleans: \n" + a + b + c );
		if (isValid(receiptId) == true && newRefund(receiptId, upc) == true && isCorrectUpc(receiptId, upc) == true) {
			
			
			// and increment stock with query;  TODO Should be in Item table instead
			//String query = "UPDATE CPSC304.Item SET stock = stock + pItem.getQuantity(receiptId, upc)  WHERE upc=" + upc; 
			//String query0 = "UPDATE CPSC304.PurchaseItem WHERE receiptId=" + receiptId; 

			// update table that return is made
			LocalDate ld = LocalDate.now();
			//INSERT INTO cpsc304.`Return` VALUES (1016,"2014-11-25",66)
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("(").append(this.returnId).append(",\"").append(ld.toString()).append("\",");
			stringBuilder.append(receiptId);
			
			String values = stringBuilder.append(")").toString();
			
			Engine.getInstance().getQueries().insertQuery("`Return`", values);
			
			// get puchaseItem method
			PurchaseItem pItem = new PurchaseItem();
			// Update ReturnItem table
			StringBuilder stringBuilder1 = new StringBuilder();
			stringBuilder1.append("(").append(this.returnId).append(",\"").append(upc).append("\",");
			stringBuilder1.append(pItem.getQuantity(receiptId, upc));
		
			String values1 = stringBuilder1.append(")").toString();
			
			Engine.getInstance().getQueries().insertQuery("`ReturnItem`", values1);
			
			// Update Item table
			String query = "UPDATE CPSC304.Item SET stock = stock +" + pItem.getQuantity(receiptId, upc) + " WHERE upc=" + upc;

			try 
			{
				PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement(query);
				ps.executeUpdate();
				Engine.getInstance().getConnection().commit();
				ps.close();
			}
			catch ( SQLException e )
			{
				System.out.println( "Failed to execute Update Statement:\n" + query );
				System.out.println(e.getMessage());
			}
			/*// Update Item table
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append("UPDATE CPSC304.Item SET stock = stock +").append(pItem.getQuantity(receiptId, upc)).append("WHERE upc=").append(upc);
		
			String values2 = stringBuilder1.toString();
			
			Engine.getInstance().getQueries().insertQuery("`Item`", values2);
			*/
			
			
			/*try {
				String query1 = "INSERT INTO CPSC304.`Return` VALUES(" + this.returnId + ", \"" + ld.toString() + "\", " + receiptId + ")";
				
				//String query2 = "INSERT INTO CPSC304.ReturnItem VALUES(" + this.returnId + ", " + upc + ", " + 9999 + ")";//quantity needed TODO
				//String query3 = "INSERT INTO CPSC304.ReturnItem WHERE upc=" + upc;

				/*PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement(query);
				System.out.println(query);
				ps.executeUpdate();
				ps.close();*/

				/*PreparedStatement ps0 = Engine.getInstance().getConnection().prepareStatement(query0);
				ps0.executeUpdate();
				ps0.close();*/

				/*PreparedStatement ps1 = Engine.getInstance().getConnection().prepareStatement(query1);
				System.out.println(query1);
				ps1.executeUpdate();
				ps1.close();*/

				/*PreparedStatement ps2 = Engine.getInstance().getConnection().prepareStatement(query2);
				System.out.println(query2);
				ps2.executeUpdate();
				ps2.close();*/

				/*PreparedStatement ps3 = Engine.getInstance().getConnection().prepareStatement(query3);
				ps3.executeUpdate();
				ps3.close();



			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
			}*/

			// display message "credit card refunded"

		}
		// display a message that the item cannot be refunded
		//if (isValid(receiptId) == false) {
			// display that the receipt is either past 15 days or does not match, and cancel refund

		//}
		// display a message that the item has already been refunded
		//if (newRefund(receiptId) == false) {
			// display that the item has already been returned and cancel refund

		//}


	}
	// Checks if the purchase was made within 15 days if there is a matching receiptId
	public boolean isValid( int receiptId) 
	{
		String query = "SELECT date FROM cpsc304.`Order` WHERE receiptId=" + receiptId;
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
				date = result.getDate( 1 );

				isWithinDate = within15Days ( currentDate, date.toLocalDate()  );
				if (isWithinDate == true) {
					success = true;
					//return success;
				}
				else 
					success = false;

			}
			
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
			System.out.println( e.getMessage() );
		}

		return success;
	}

	// Returns true if it is during the period you can return
	// Returns false otherwise
	public boolean within15Days( LocalDate thisDate, LocalDate purchaseDate )
	{
		boolean is15DaysAfter = false;

		//purchaseDate.plusDays( 15 );

		if ( thisDate.isBefore( purchaseDate.plusDays( 15 ) ))// && thisDate.isAfter(purchaseDate)) 
		{
			is15DaysAfter = true;
		}

		return is15DaysAfter;
	}

	// Check if upc is the matching upc
	public boolean isCorrectUpc( int receiptId, int upc) {
		/*Item i = new Item();
		if (!i.itemExist(upc)) {
			return false;
		}*/

		String query = "SELECT upc FROM CPSC304.PurchaseItem WHERE receiptId=" + receiptId;

		isGoodUPC = false;
		
		try 
		{
			// prepare and execute the SELECT
			PreparedStatement ps = connection.prepareStatement( query );
			ResultSet result = ps.executeQuery();


			// checks if there are any matching receiptID's
			while ( result.next() ) {
				int upcDB = result.getInt(1);

				if ( upcDB == upc ) {
					isGoodUPC = true;
					break;
				}	
			}
			
			ps.close();
			
		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
			System.out.println( e.getMessage() );
		}

		return isGoodUPC;
	}

	// Returns true if this refund is new and not in the return table
	// Returns false if the refund is already in the return table
	public boolean newRefund( int receiptId, int upc ) {
		//String query = "SELECT * FROM CPSC304.ReturnItem WHERE (SELECT * FROM CPSC304.`Return` WHERE = receiptId=" + receiptId + " AND " + "upc=" + upc+ ")";
		String query = "SELECT * FROM CPSC304.`Return` r, CPSC304.ReturnItem ri WHERE r.receiptId =" + receiptId + " AND ri.retid = r.retid" + " AND " + "upc=" + upc;
		
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
			if (returnIds.isEmpty()) {
				isNewRefund = true;
				//return isNewRefund;
			}
			else {
				isNewRefund = false;
			}



		}
		catch (SQLException e)
		{
			System.out.println( "Failed to execute select statement:\n" + query );
			System.out.println( e.getMessage() );
		} 
		return isNewRefund;
	}
	
	// generate a returnId
		// take the latest returnId and increment by 1
		private void generateReturnId() {
			String query = "SELECT retid FROM cpsc304.`Return` ORDER BY retid DESC LIMIT 1;";

			try 
			{
				// Create sql query
				PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

				// Execute sql query
				ResultSet result = ps.executeQuery();

				if ( result.next() )
				{
					this.returnId = result.getInt(1)+1;
					/*if (this.returnId == 0) {
						this.returnId = 1;
					}*/
				}
				else 
					this.returnId = 1;

				ps.close();

			}
			catch ( SQLException e )
			{
				System.out.println( "Failed to execute Select Statement:\n" + query );
				System.out.println(e.getMessage());
			}
			
		}


}
