package com.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Engine 
{

	private static Engine instance;
	private static Queries queries;

	private Engine ()
	{

	}

	public static Engine getInstance()
	{
		if ( instance == null )
		{
			instance = new Engine();
			initialize();
		}
		return instance;
	}

	private static void initialize()
	{
		System.out.println("YAY FIRST CLASS :D");

		Scanner scanner = new Scanner(System.in);

		// URL is in format "jdbc:mysql://<ip>:<port>/<databasename>"
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
			
			queries = new DBQueries( con );

		} 
		catch (SQLException e) 
		{
			System.out.println("Message: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	public Queries getQueries()
	{
		return queries;
	}

}
