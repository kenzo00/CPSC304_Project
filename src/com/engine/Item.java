package com.engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public boolean itemExist(int upc) {
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
			ps.executeUpdate();
			Engine.getInstance().getConnection().commit();
			ps.close();
		} 
		catch (SQLException e) {
			System.out.println("Failed to execute select from: " + upc + "\nError Message: " + e.getMessage());
			e.printStackTrace();
		}
	}


	// get all the Items that matches the category, title, or leading singer
	// c = category, t = title, s = leadSinger
	public TableInfo getItems(String offset, String c, String t, String s) {
		String query;
		int upc = 0;
		if (offset == "c"){
			query = "SELECT * FROM cpsc304.`Item` WHERE category = '" + c +"'";
		}
		else if (offset == "t"){
			query = "SELECT * FROM cpsc304.`Item` WHERE title = '" + t +"'";
		}
		else if (offset == "s"){
			query = "SELECT * FROM cpsc304.`LeadSinger` WHERE name = '" + s +"'";
		}
		else 
			query = "SELECT * FROM cpsc304.LeadSinger as A INNER JOIN cpsc304.Item as B ON A.upc = B.upc "
					+ "WHERE B.category = '" + c +"' AND"
					+ "B.title = '" + t + "' AND"
					+ "A.name = '" + s + "'";

		TableInfo tableInfo = new TableInfo();

		try 
		{
			// Create sql query
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
	
	//Get the stock of an Item
	public int getStock(int upc) {
		String query = "SELECT stock FROM cpsc304.Item WHERE upc =" + upc;

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				this.stock = result.getInt(1);
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return this.stock;
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
				if (stock >= qty)
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

	// Update the ITEM table
	// Operator can be either +/-
	public void updateStock(int upc, int qty, String operator){
		String query = "UPDATE cpsc304.Item SET stock = stock"+operator +"" + qty + " WHERE upc =" + upc;

		try 
		{
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement(query);
			ps.executeUpdate();
			Engine.getInstance().getConnection().commit();
			ps.close();
		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Update Statement:\n" + query );
			System.out.println(e.getMessage());
		}

	}
	
	// Generate a new upc for a new item entry
	// The new upc is the previous largest upc + 1
	// If failed, returns 0
	public int generateNewUpc()
	{
		int upc = 0;
		
		String query = "SELECT MAX(upc) FROM cpsc304.Item";
		
		try 
		{
			// Prepare and execute the select statement
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
			ResultSet result = ps.executeQuery();
			
			while ( result.next() )
			{
				// There should only be one MAX upc
				upc = result.getInt(1) + 1;
			}
						
			ps.close();

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Failed to execute select statement:\n" + query + "\nError Message: " + e.getMessage());
		}
		
		return upc;
	}

}
