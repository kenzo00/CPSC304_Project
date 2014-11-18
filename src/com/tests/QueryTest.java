package com.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.Test;

import com.engine.DBQueries;
import com.engine.Queries;

public class QueryTest 
{

	public QueryTest()
	{
		
	}
	
	@Test
	public void insertDeleteTest() 
	{
		Scanner scanner = new Scanner(System.in);
		
		String connectURL = "jdbc:mysql://localhost:3306/cpsc304"; 
    	System.out.println("Please Enter your username");
    	String username = scanner.nextLine();
    	System.out.println("Please Enter your passowrd");
    	String password = scanner.nextLine();
    	
		try 
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection con = DriverManager.getConnection(connectURL,username,password);
			System.out.println("Connected! :D");
			
			// Set Autocommit false
			con.setAutoCommit( false );
			
			Queries queries = new DBQueries( con );
			String table1 = "Customer";
			String insertValues1 = "(1,'IAMHOKAGE','Naruto Uzumaki','123 Konoha',6041234567)";
			
			System.out.println( "Initial table" );
			queries.displayTable( table1 );
			
			System.out.println( "Insert Naruto" );
			queries.insertQuery( table1, insertValues1 );
			queries.displayTable( table1 );
			
			System.out.println( "Insert Pikachu" );
			String insertValues2 = "(2,'Pikapika?','Pikachu','4262 Pallet Town',682135752)";
			queries.insertQuery ( table1, insertValues2 );
			queries.displayTable( table1 );

			System.out.println( "Delete Naruto" );
			queries.deleteQuery( table1, "cid=1" );
			queries.displayTable( table1 );
			
			System.out.println( "Delete Everything" );
			queries.deleteQuery( table1 );
			queries.displayTable( table1 );
			
			con.close();
		} 
		catch (SQLException e) 
		{
			System.out.println("Message: " + e.getMessage());
			System.exit(-1);
		}

	}

}
