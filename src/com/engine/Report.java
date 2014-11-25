package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public TableInfo getTopSellingItems ( Date day, int n )
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT i.title AS `Item Title`, i.company AS `Company`, " );
		stringBuilder.append( "i.stock AS `Stock`, SUM(p.quantity) AS `Number Sold` " );
		stringBuilder.append( "FROM Item i, `Order` o, PurchaseItem p " );
		stringBuilder.append( "WHERE o.date='" ).append( day );
		stringBuilder.append( "' AND p.receiptId=o.receiptId AND p.upc=i.upc " );
		stringBuilder.append( "GROUP BY i.title, i.company, i.stock " );
		stringBuilder.append( "ORDER BY SUM(p.quantity) DESC " );
		stringBuilder.append( "LIMIT " ).append( n );

		String query = stringBuilder.toString();

		TableInfo tableInfo = new TableInfo();

		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();

			// Get Number of columns
			ResultSetMetaData resultMetadata = result.getMetaData();
			int numCol = resultMetadata.getColumnCount();

			// Put the Column names into an array
			List<String> headerList = new ArrayList<String>();
			for ( int i = 0; i < numCol; i++ )
			{
				headerList.add( resultMetadata.getColumnName( i+1 ) );
			}
			String[] headerArray = headerList.toArray( new String[ headerList.size() ] );

			// Put the data into an array
			List< List<String> > dataList = new ArrayList< List<String> >();
			while ( result.next() )
			{
				List<String> dataRow = new ArrayList<String>();

				for ( int i = 0; i < numCol; i++ )
				{
					dataRow.add( result.getString( i+1 ) );
				}

				dataList.add( dataRow );
			}
			String[][] dataArray = new String[ dataList.size() ][ headerList.size() ];
			for ( int i = 0; i < dataList.size(); i++ )
			{
				List<String> dataRow = dataList.get( i );
				for ( int j = 0; j < headerList.size(); j++ )
				{
					dataArray[i][j] = dataRow.get( j );
				}
			}

			tableInfo = new TableInfo( headerArray, dataArray );

			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}

		return tableInfo;
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

	public TableInfo getDailyReport( Date day )
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT i.upc AS `UPC`, i.title AS `Item Name`, i.category AS `Category`, " );
		stringBuilder.append( "i.price AS `Unit Price`, p.quantity AS `Units Sold`, " );
		stringBuilder.append( "ROUND(i.price*p.quantity,2) AS `Total Value`, o.date AS `Date` " );
		stringBuilder.append( "FROM Item i, `Order` o, PurchaseItem p WHERE o.date =\"" );
		stringBuilder.append( day ).append( "\" AND p.receiptId = o.receiptId AND i.upc = p.upc" );
		stringBuilder.append( " ORDER BY i.category " );

		String query = stringBuilder.toString();

		TableInfo tableInfo = new TableInfo();

		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();

			// Get Number of columns
			ResultSetMetaData resultMetadata = result.getMetaData();
			int numCol = resultMetadata.getColumnCount();

			// Put the Column names into an array
			List<String> headerList = new ArrayList<String>();
			for ( int i = 0; i < numCol; i++ )
			{
				headerList.add( resultMetadata.getColumnName( i+1 ) );
			}
			String[] headerArray = headerList.toArray( new String[ headerList.size() ] );

			// Put the data into an array
			List< List<String> > dataList = new ArrayList< List<String> >();
			while ( result.next() )
			{
				List<String> dataRow = new ArrayList<String>();

				for ( int i = 0; i < numCol; i++ )
				{
					dataRow.add( result.getString( i+1 ) );
				}

				dataList.add( dataRow );
			}
			String[][] dataArray = new String[ dataList.size() ][ headerList.size() ];
			for ( int i = 0; i < dataList.size(); i++ )
			{
				List<String> dataRow = dataList.get( i );
				for ( int j = 0; j < headerList.size(); j++ )
				{
					dataArray[i][j] = dataRow.get( j );
				}
			}

			tableInfo = new TableInfo( headerArray, dataArray );

			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}

		return tableInfo;
	}
	
	public TableInfo getDailyReportTotals( Date day )
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT i.category AS `Category`, SUM( p.quantity ) AS `Total Units Sold`, " );
		stringBuilder.append( "SUM( ROUND( i.price*p.quantity,2 ) ) AS `Total Value` " );
		stringBuilder.append( "FROM Item i, `Order` o, PurchaseItem p " );
		stringBuilder.append( "WHERE o.date = \"" ).append( day ).append( "\" AND p.receiptId = o.receiptId AND i.upc = p.upc " );
		stringBuilder.append( "GROUP BY i.category" );
		
		String query = stringBuilder.toString();

		TableInfo tableInfo = new TableInfo();

		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();

			// Get Number of columns
			ResultSetMetaData resultMetadata = result.getMetaData();
			int numCol = resultMetadata.getColumnCount();

			// Put the Column names into an array
			List<String> headerList = new ArrayList<String>();
			for ( int i = 0; i < numCol; i++ )
			{
				headerList.add( resultMetadata.getColumnName( i+1 ) );
			}
			String[] headerArray = headerList.toArray( new String[ headerList.size() ] );

			// Put the data into an array
			List< List<String> > dataList = new ArrayList< List<String> >();
			while ( result.next() )
			{
				List<String> dataRow = new ArrayList<String>();

				for ( int i = 0; i < numCol; i++ )
				{
					dataRow.add( result.getString( i+1 ) );
				}

				dataList.add( dataRow );
			}
			String[][] dataArray = new String[ dataList.size() ][ headerList.size() ];
			for ( int i = 0; i < dataList.size(); i++ )
			{
				List<String> dataRow = dataList.get( i );
				for ( int j = 0; j < headerList.size(); j++ )
				{
					dataArray[i][j] = dataRow.get( j );
				}
			}

			tableInfo = new TableInfo( headerArray, dataArray );

			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}

		return tableInfo;
	}
}
