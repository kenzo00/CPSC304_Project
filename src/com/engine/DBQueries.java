package com.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBQueries implements Queries
{

	private Connection connection;

	public DBQueries ( Connection con )
	{
		this.connection = con;
	}

	/*
	 * Inserts 'values' into 'table' by executing a query of the form
	 * "INSERT INTO table VALUES values"
	 * 
	 * table is the name of the database table
	 * values are the values that will be inserted into the table
	 * 		values should be formatted like "(x,y,z)"
	 */
	public void insertQuery ( String table, String values )
	{
		// Build the String for the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "INSERT INTO cpsc304." ).append( table ).append( " VALUES " ).append( values );

		String query = stringBuilder.toString();

		try 
		{
			// Prepare and execute the insert statement
			PreparedStatement ps = connection.prepareStatement( query );
			ps.executeUpdate();

			// Commit the changes
			connection.commit();

			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to insert into " + table + "\nError Message: " + e.getMessage());
			try
			{
				// The update failed. Revert the insert
				connection.rollback();
			}
			catch (SQLException ex)
			{
				// The rollback failed. Something is wrong.
				// Exit the program
				System.out.println("Error Message: " + ex.getMessage() );
				System.exit(-1);
			}
		}
	}
	
	/*
	 * Deletes all rows from 'table'
	 * 
	 * table is the name of the database table
	 * 
	 * returns the number of rows deleted
	 */
	public int deleteQuery ( String table )
	{
		return deleteQuery( table, "" );
	}

	/*
	 *  Deletes rows from 'table' that satisfies 'condition' by executing a query
	 *  of the form "DELETE FROM table WHERE condition"
	 * 
	 *  table is the name of the database table
	 *  condition is the deletion condition
	 *  	should be something like "id=50"
	 *  	if no condition is specified (that is, an empty string is passed for the condition)
	 *  		then all rows will be deleted from the table
	 *  
	 *  returns the number of rows deleted
	 */
	public int deleteQuery ( String table, String condition )
	{
		int numberRowsDeleted = 0;
		
		// Build the String for the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "DELETE FROM " ).append( table );
		if ( condition.length() > 0 )
		{
			stringBuilder.append( " WHERE " ).append( condition );
		}
		
		String query = stringBuilder.toString();
		
		try 
		{
			// Prepare and execute the delete statement
			PreparedStatement ps = connection.prepareStatement( query );
			numberRowsDeleted = ps.executeUpdate();

			// Commit the changes
			connection.commit();

			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to delete from " + table + "\nError Message: " + e.getMessage());
			try
			{
				// The update failed. Revert the delete
				connection.rollback();
			}
			catch (SQLException ex)
			{
				// The rollback failed. Something is wrong.
				// Exit the program
				System.out.println("Error Message: " + ex.getMessage() );
				System.exit(-1);
			}
		}
		
		return numberRowsDeleted;
		
	}
	
	/*
	 * Prints out all rows of a selected table to the console
	 */
	public void displayTable( String table )
	{
		// Build the String for the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "SELECT * FROM ").append( table );
		
		String query = stringBuilder.toString();
		
		ResultSet result;
		try 
		{
			// Prepare and execute the delete statement
			PreparedStatement ps = connection.prepareStatement( query );
			result = ps.executeQuery();
			
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
			System.out.println("Failed to execute select from: " + table + "\nError Message: " + e.getMessage());
		}
		
	}
	
}
