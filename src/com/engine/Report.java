package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Report 
{

	public Report()
	{
		
	}
	
	public void printTopSellingItems ( Date day, int n )
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT i.title AS `Item Title`, i.company AS `Company`, " );
		stringBuilder.append( "i.stock AS `Stock`, SUM(p.quantity) AS `Number Sold` " );
		stringBuilder.append( "FROM Item i, `Order` o, PurchaseItem p " );
		stringBuilder.append( "WHERE o.date=" ).append( day );
		stringBuilder.append( " AND p.receiptId=o.receiptId AND p.upc=i.upc " );
		stringBuilder.append( "GROUP BY i.title, i.company, i.stock " );
		stringBuilder.append( "ORDER BY SUM(p.quantity) DESC " );
		stringBuilder.append( "LIMIT " ).append( n );

		String query = stringBuilder.toString();
		
		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			// Get Number of columns
			ResultSetMetaData resultMetadata = result.getMetaData();
			int numCol = resultMetadata.getColumnCount();

			// Print the Column names
			stringBuilder.setLength( 0 );
			for ( int i = 0; i < numCol; i++ )
			{
				stringBuilder.append( resultMetadata.getColumnName( i+1 ) ).append( "\t\t" );
			}
			String columns = stringBuilder.toString();
			System.out.println( columns );
			
			// Print the rows
			while ( result.next() )
			{
				stringBuilder.setLength( 0 );
				
				for ( int i = 0; i < numCol; i++ )
				{
					stringBuilder.append( result.getString( i+1 ) ).append( "\t\t" );
				}
				String row = stringBuilder.toString();
				System.out.println( row );
			}
			
			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}
	}
	
	public void printDailyReport ( Date day )
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT i.upc AS `UPC`, i.title AS `Item Name`, i.category AS `Category`, " );
		stringBuilder.append( "i.price AS `Unit Price`, p.quantity AS `Units Sold`, " );
		stringBuilder.append( "ROUND(i.price*p.quantity,2) AS `Total Price`, o.date AS `Date` " );
		stringBuilder.append( "FROM Item i, `Order` o, PurchaseItem p WHERE o.date =" );
		stringBuilder.append( day ).append( " AND p.receiptId = o.receiptId AND i.upc = p.upc" );
		stringBuilder.append( "ORDER BY i.category " );
		
		String query = stringBuilder.toString();
		
		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			// Get Number of columns
			ResultSetMetaData resultMetadata = result.getMetaData();
			int numCol = resultMetadata.getColumnCount();

			// Print the Column names
			stringBuilder.setLength( 0 );
			for ( int i = 0; i < numCol; i++ )
			{
				stringBuilder.append( resultMetadata.getColumnName( i+1 ) ).append( "\t\t" );
			}
			String columns = stringBuilder.toString();
			System.out.println( columns );
			
			// Print the rows
			while ( result.next() )
			{
				stringBuilder.setLength( 0 );
				
				for ( int i = 0; i < numCol; i++ )
				{
					stringBuilder.append( result.getString( i+1 ) ).append( "\t\t" );
				}
				String row = stringBuilder.toString();
				System.out.println( row );
			}
			
			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}
	}
}
