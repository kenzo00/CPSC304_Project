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
		boolean returnValue = false;

		try {
			String query = "SELECT * FROM cpsc304.Item WHERE upc=" + upc;
			ps = Engine.getInstance().getConnection().prepareStatement(query);
			result = ps.executeQuery();
			returnValue = result.next();

			ps.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
		}
		return returnValue;
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
		stringBuilder.append( type ).append( "','" ).append( category ).append( "'," ).append( "'," ).append( company ).append( "'," );
		stringBuilder.append( year ).append( "," ).append( price ).append( "," ).append( stock );
		String values = stringBuilder.append( ")" ).toString();

		System.out.println("AddItemvalues" + values);

		Engine.getInstance().getQueries().insertQuery("Item", values );
		System.out.println();

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
			System.out.println(ps);
			result = ps.executeQuery();
			System.out.println(result);
		} catch (SQLException e) {
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
			e.printStackTrace();
		}
	}


	// get the upc of an Item by providing category, title, or leading singer
	// c = category, t = title, s = leadSinger
	public int getUpc(String offset, String c, String t, String s) {
		String query;
		int upc = 0;
		if (offset == "c"){
			query = "SELECT upc FROM cpsc304.`Item` WHERE category = '" + c +"'";
		}
		if (offset == "t"){
			query = "SELECT upc FROM cpsc304.`Item` WHERE title = '" + t +"'";
		}
		if (offset == "s"){
			query = "SELECT upc FROM cpsc304.`LeadSinger` WHERE name = '" + s +"'";
		}
		else 
			query = "SELECT A.upc FROM cpsc304.LeadSinger as A INNER JOIN cpsc304.Item as B ON A.upc = B.upc "
					+ "WHERE B.category = '" + c +"' AND"
					+ "B.title = '" + t + "' AND"
					+ "A.name = '" + s + "'";

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				upc = result.getInt(1)+1;
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return upc;

	}

	// Check if there are enough stock for Order
	public boolean isEnoughStock(int upc, int qty){
		String query = "SELECT stock FROM cpsc304.Item WHERE upc =" + upc;

		int stock;
		boolean isEnough = false;

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				stock = result.getInt(1);
				if (qty >= stock)
					isEnough = true;
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return isEnough;
	}

}
