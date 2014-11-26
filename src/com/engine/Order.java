package com.engine;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Order {
	//Fields
	int receiptId, cid;
	Date date, expiryDate, expectedDate, deliveredDate;
	String category, title, leadingSinger, cardNum;
	ResultSet searchList;
	Double maxOrder = 50.0; // maximum number of order that can be delivered in a day


	Customer c = new Customer();
	Item i = new Item();
	Map<Integer, Integer> shopCart = new HashMap<Integer, Integer>();


	public Order() {


	}

	// Check if the login is valid
	public boolean login(int cid, String password) {
		boolean isSuccessful = c.isPassCorrect(cid, password);
		if (isSuccessful) {
			this.cid = cid;
		}
		return isSuccessful;

	}

	// Check if customer is registered, if not, register the customer
	public boolean registerCustomer(int cid, String password, String name, String address, String phone) {
		boolean registered; 
		registered = c.isRegistered(cid);
		if (registered){
			return false;
		}
		else {
			c.register(cid, password, name, address, phone);
			this.cid = cid;
			return true;
		}

	}

	// Search & add item to the shopping cart
	public TableInfo searchItem(String category, String title, String leadSinger){
		// Call method in Item, should return a TableInfo containing all the searched Item.
		if (!category.isEmpty()&&(title.isEmpty()||leadSinger.isEmpty())){
			if (!title.isEmpty())
				return i.getItems("ct", category, title, leadSinger);
			if (!leadSinger.isEmpty())
				return i.getItems("cs", category, title, leadSinger);
			else
				return i.getItems("c", category, title, leadSinger);
		}

		else if (!title.isEmpty()&&(category.isEmpty()||leadSinger.isEmpty())){
			if (!category.isEmpty())
				return i.getItems("ct", category, title, leadSinger);
			if (!leadSinger.isEmpty())
				return i.getItems("ts", category, title, leadSinger);
			else
				return i.getItems("t", category, title, leadSinger);
		}

		else if (!leadSinger.isEmpty()&&(title.isEmpty()||category.isEmpty())){
			if (!category.isEmpty())
				return i.getItems("cs", category, title, leadSinger);
			if (!title.isEmpty())
				return i.getItems("ts", category, title, leadSinger);
			else
				return i.getItems("s", category, title, leadSinger);
		}
		else{
			return i.getItems("all", category, title, leadSinger);

		}
	}

	// Check if the item is out of stock
	public boolean isOutOfStock(int upc){
		int newQuantity;
		newQuantity = i.getStock(upc);
		return (newQuantity == 0);

	}

	// Check if there are enough stock given the quantity
	public int checkStock(int upc, int qty){
		if (i.isEnoughStock(upc, qty)) {
			return qty;
		}
		else 
			return i.getStock(upc);
	}

	// Add the Item and the Quantity to the shopCart
	// Assuming the upc is valid
	public void purchaseItem(int upc, int qty){
		boolean outOfStock = isOutOfStock(upc);
		boolean enoughStock = (qty == checkStock(upc, qty));

		if (outOfStock) {
			return;
		}

		else if (enoughStock) {
			if(shopCart.containsKey(upc)) {
				shopCart.put(upc, shopCart.get(upc)+qty);
			}
			else
				shopCart.put(upc, qty);
			i.updateStock(upc, qty, "-");

		}
		else {
			int newQty = i.getStock(upc);
			if(shopCart.containsKey(upc)) {
				shopCart.put(upc, shopCart.get(upc)+newQty);
			}
			else
				shopCart.put(upc, newQty);
			i.updateStock(upc, newQty, "-");
		}
		//System.out.println(enoughStock);
		//System.out.println(upc);
		System.out.println(shopCart.keySet());
		System.out.println(shopCart.values());
		System.out.println("========");
	}


	public void checkOut(String cardNum, Date expiryDate, int cid){
		this.cardNum = cardNum;
		this.expiryDate = expiryDate;
		generateId();

		int outstandingNum = outstandingOrder(); 
		calcDate(outstandingNum); 

		// Build the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "(" ).append( this.receiptId ).append( ",\"" ).append( this.date ).append( "\"," );
		stringBuilder.append( cid ).append( ",\"" ).append( this.cardNum ).append( "\",\"" ).append( this.expiryDate ).append( "\",\"" );
		stringBuilder.append( this.expectedDate ).append( "\"," ).append( "NULL" );

		String values = stringBuilder.append( ")" ).toString();

		// Execute the query
		Engine.getInstance().getQueries().insertQuery( "`Order`", values );


		Set<Map.Entry<Integer, Integer>> set = shopCart.entrySet();
		for (Map.Entry<Integer, Integer> m : set) {

			//Insert this record to PurchaseItem table
			// Build the sql query
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append( "(" ).append( this.receiptId ).append( "," ).append( m.getKey() ).append( "," ).append( m.getValue() );
			String values2 = stringBuilder2.append( ")" ).toString();

			// Execute the query
			Engine.getInstance().getQueries().insertQuery( "PurchaseItem", values2 );

		}

	}

	// get the Cart
	public Map<Integer, Integer> getCart() {
		return this.shopCart;
	}


	// generate a receiptId
	// take the latest receiptId and increment by 1
	private void generateId() {
		String query = "SELECT receiptId FROM cpsc304.`Order` ORDER BY receiptId DESC LIMIT 1;";

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				this.receiptId = result.getInt(1)+1;

			}
			else
				this.receiptId = 1;

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}

	}

	// Calculate the expectedDate
	public int calcDate(int num) {

		LocalDate currentDate = LocalDate.now();
		int numDays = (int) Math.ceil(num/maxOrder);

		LocalDate calculatedDate = currentDate.plusDays(numDays);

		this.date = Date.valueOf(currentDate);
		this.expectedDate = Date.valueOf(calculatedDate);

		return numDays;
	}

	// Get the number of outstanding order
	public int outstandingOrder() {

		int outstandingNum = 0;
		String query = "Select COUNT(*) from cpsc304.`Order` where deliveredDate is NULL";

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				outstandingNum = result.getInt(1);
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return outstandingNum;
	}

	// return false if order has already been delivered
	// return true if order is successfully delivered
	public boolean deliverOrder( int receiptId, Date date )
	{
		boolean isDelivered = false;

		isDelivered = isOrderDelivered( receiptId );

		if ( !isDelivered )
		{
			String query = "UPDATE cpsc304.`Order` SET deliveredDate = " + date;

			try 
			{
				// Prepare and execute the insert statement
				PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );
				ps.executeUpdate();

				// Commit the changes
				Engine.getInstance().getConnection().commit();

				ps.close();

			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				System.out.println("Failed to deliver order.\nReceiptId:" + "\nError Message: " + e.getMessage());
				try
				{
					// The update failed. Revert the insert
					Engine.getInstance().getConnection().rollback();
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

		return isDelivered;
	}

	public boolean isOrderDelivered( int receiptId )
	{
		boolean isDelivered = false;
		String query = "SELECT deliveredDate FROM cpsc304.`Order` WHERE receiptId=" + receiptId;

		try 
		{
			// Create sql query
			PreparedStatement ps = Engine.getInstance().getConnection().prepareStatement( query );

			// Execute sql query
			ResultSet result = ps.executeQuery();

			if ( result.next() )
			{
				if ( result.getDate(1) != null )
				{
					isDelivered = true;
				}
			}

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		return isDelivered;
	}


}
