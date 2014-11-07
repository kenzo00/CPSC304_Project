package com.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MusicStoreDB {

	public static void main ( String[] args )
	{
		System.out.println("YAY FIRST CLASS :D");
		
		Scanner scanner = new Scanner(System.in);
		
		// URL is in format "jdbc:mysql://<ip>:<port>/<databasename>"
    	String connectURL = "jdbc:mysql://localhost:3306/test"; 
    	System.out.println("Please Enter your username");
    	String username = scanner.nextLine();
    	System.out.println("Please Enter your passowrd");
    	String password = scanner.nextLine();
    	
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection con = DriverManager.getConnection(connectURL,username,password);
			con.close();
		} catch (SQLException e) {
			System.out.println("Message: " + e.getMessage());
			System.exit(-1);
		}

	}
}
