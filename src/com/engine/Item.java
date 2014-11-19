package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Item {
	
	private int upc;
	private String title;
	private String type;
	private String category;
	private String company;
	private int year;
	private double price;
	private int stock;
	
	private Item() {
		
	}
	
	/* users chooses item and returns the stock price */
	
	private int checkStock(int upc) {
		
		int stock = 0;
		return stock;
	}
	
	private void itemExist(int upc) {
		
		try {
		String query = "SELECT * FROM cpsc304.Item WHERE upc=" + upc;
		ResultSet result;
		PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement(query);
		result = ps.executeQuery();
		while (result.next() == false) {
			addItem(upc, price);
		}
		updateItem();
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
		}
	}
	
	private int addItem(int upc, double price) {

		return 0;
	}
	
	private int updateItem() {
		return 0;
	}
		
	
}