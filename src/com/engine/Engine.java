package com.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Engine 
{

	private static Engine instance;
	private static Queries queries;
	private static Connection connection;
	private static boolean isInitialized;

	private Engine ()
	{

	}

	public static Engine getInstance()
	{
		return instance;
	}
	
	public static void createFirstInstance( String connectURL, String user, String password ) throws Exception
	{
		if ( instance == null )
		{
			instance = new Engine();
		}
		if ( isInitialized )
		{
			throw new Exception( "Engine has already been created and initialized!" );
		}
		else
		{
			initialize( connectURL, user, password );
		}
	}

	private static void initialize( String connectURL, String user, String password ) throws SQLException
	{
		System.out.println("YAY FIRST CLASS :D");

//		Scanner scanner = new Scanner(System.in);

		// URL is in format "jdbc:mysql://<ip>:<port>/<databasename>"
//		String connectURL = "jdbc:mysql://localhost:3306/cpsc304"; 
//		String username = "root";
//		String password = "root";
//		System.out.println("Please Enter your username");
		//String username = scanner.nextLine();
//		System.out.println("Please Enter your passowrd");
		//String password = scanner.nextLine();

		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		connection = DriverManager.getConnection( connectURL, user, password );
		System.out.println("Connected! :D");

		// Set Autocommit false
		connection.setAutoCommit( false );
		
		queries = new DBQueries( connection );

	}
	
	public Queries getQueries()
	{
		return queries;
	}
	
	public Connection getConnection()
	{
		return connection;
	}

}
