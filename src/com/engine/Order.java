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
	Double maxOrder = 50.0; // maximum number of order that can be delivered in a day


	Customer c = new Customer();
	Item i = new Item();
	Map<Integer, Integer> shopCart = new HashMap<Integer, Integer>();
	
	//Testing Variables 
	String fakeCard = "8888888888888888";
	String blankString = "";
	LocalDate currentDate2 = LocalDate.now();
	Date fakeDate = Date.valueOf(currentDate2);


	public Order() {


	}

	// TODO need to talk to UI to get the info we need
	// Assuming we have all the info we need atm for the purpose of testing
	public void onlineOrder(int cid, String password) {
		login(cid, password);
		//UI - get Item name info TODO
		purchaseItem(blankString, blankString, "Jay Chou", 5);

		checkOut(fakeCard, fakeDate);

	}

	// Check if customer is registered, if not, register the customer
	public void login(int cid, String password) {
		boolean registered; 
		registered = c.isRegistered(cid);
		if (!registered){
			//UI - get name,address,phone TODO
			c.register(cid, password, "Bob", "LalaLand", "604-888-8888");
		}
		this.cid = cid;
	}

	// Search & add item to the shopping cart
	public void purchaseItem(String category, String title, String leadSinger, int qty) {
		int upc = findUpc(category, title, leadSinger);
			
		boolean enoughStock = i.isEnoughStock(upc, qty);
		// Check if there are enough quantity in store TODO
		// Input: upc (get from above), qty
		// Output: If enough stock, add (upc,qty) to the list, if not, ask user to accept the exisiting qty left in stock
		if (enoughStock) {
			shopCart.put(upc, qty);
		}
		else {
			int newQty = i.getStock(upc);
			shopCart.put(upc, newQty);
		}
		System.out.println(enoughStock);
		System.out.println(upc);
		System.out.println(shopCart.keySet());
		System.out.println(shopCart.values());
		

	}


	public void checkOut(String cardNum, Date expiryDate){
		this.cardNum = cardNum;
		this.expiryDate = expiryDate;
		generateId();

		int outstandingNum = outstandingOrder(); 
		calcDate(outstandingNum); 
		
		// Build the sql query
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "(" ).append( this.receiptId ).append( ",\"" ).append( this.date ).append( "\"," );
		stringBuilder.append( this.cid ).append( ",\"" ).append( this.cardNum ).append( "\",\"" ).append( this.expiryDate ).append( "\",\"" );
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
		
		
		/*for (Integer upc: shopCart.keySet()){	
			int qty = shopCart.get(upc);
			
			//Insert this record to PurchaseItem table
			// Build the sql query
			StringBuilder stringBuilder2 = new StringBuilder();
			stringBuilder2.append( "(" ).append( this.receiptId ).append( "," ).append( upc ).append( "," ).append( qty );
			String values2 = stringBuilder2.append( ")" ).toString();
			
			// Execute the query
			Engine.getInstance().getQueries().insertQuery( "PurchaseItem", values2 );
			

			String upcKey = upc.toString();
			String qtyValue = shopCart.get(upc).toString();  
			System.out.println(upcKey + " " + qtyValue);  
		} */

	}
	
	// find the upc of an Item
	private int findUpc(String category, String title, String leadSinger) {
		int upc;
		// Call method in Item, should return the upc.
		if (!category.isEmpty()&&(title.isEmpty()||leadSinger.isEmpty())){
			upc = i.getUpc("c", category, title, leadSinger);
			System.out.println("Checkc" + upc);
		}
			
		else if (!title.isEmpty()&&(category.isEmpty()||leadSinger.isEmpty())){
			upc = i.getUpc("t", category, title, leadSinger);
			System.out.println("Checkt" + upc);
		}
			
		else if (!leadSinger.isEmpty()&&(title.isEmpty()||category.isEmpty())){
			upc = i.getUpc("s", category, title, leadSinger);
			System.out.println("Checks" + upc);
		}
			
		else{
			upc = i.getUpc("all", category, title, leadSinger);
			System.out.println("Checka" + upc);
		}
		
		
		return upc;
		
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

			ps.close();

		}
		catch ( SQLException e )
		{
			System.out.println( "Failed to execute Select Statement:\n" + query );
			System.out.println(e.getMessage());
		}
		
	}

	// Calculate the expectedDate
	private void calcDate(int num) {

		LocalDate currentDate = LocalDate.now();
		int numDays = (int) Math.ceil(num/maxOrder);
		
		LocalDate calculatedDate = currentDate.plusDays(numDays);
		
		this.date = Date.valueOf(currentDate);
		this.expectedDate = Date.valueOf(calculatedDate);
	}

	// Get the number of outstanding order
	private int outstandingOrder() {

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




}
