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
	private Double price;
	private int stock;
	private ResultSet result;
	private PreparedStatement ps;
	
	public Boolean exist;
	
	public Item() {
		
	}
	
	/* users chooses item and returns the stock price */
	
	public int checkStock(int upc) {
		
		int stock = 0;
		return stock;
	}
	
	public Boolean itemExist(int upc) {
		
		try {
		String query = "SELECT * FROM cpsc304.Item WHERE upc=" + upc;
		ps = Engine.getInstance().getConnection().prepareStatement(query);
		result = ps.executeQuery();
		
		while (result.next() == false) {
			// no item in result
			exist = false;
		}
		
		while (result.next() == true) {
			exist = true;
		}
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
		}
		
		return exist;
	}
	
	public void addOrUpdate() {
		if (exist = false) {
			addItem(upc, title, type, category, company, year, price, stock);
		}
		else
			updateItem(upc, stock, price);
	}
	
	public void addItem(int upc, String title, String type, String category, String company, int year, double price, int stock) {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "(" ).append( upc ).append( ",'" ).append( title ).append( "','" );
		stringBuilder.append( type ).append( "','" ).append( category ).append( "'," ).append( company );
		stringBuilder.append( year ).append( "','" ).append( price ).append( "'," ).append( stock );
		String values = stringBuilder.append( ")" ).toString();
		
		System.out.println(values);
		
		Engine.getInstance().getQueries().insertQuery(" Item ", values );
	}
	
	public void updateItem(int upc, int stock, Double price) {
		
		//check if new price is given
		if (price == 0) {
			this.stock = stock;
		}
		else {
			this.price = price;
			this.stock = stock;	
		}
		
		try {
			String query = "UPDATE cpsc304.Item" + "SET price=" + price + "WHERE upc =" + upc;
			ps = Engine.getInstance().getConnection().prepareStatement(query);
			result = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
			e.printStackTrace();
		}
		}
}
