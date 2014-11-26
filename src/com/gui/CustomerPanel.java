package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Item;
import com.engine.Order;
import com.engine.TableInfo;

public class CustomerPanel extends JPanel
{
	// Panel for displaying login GUI
	private JPanel loginPanel;

	// Constant representing the starting year of the AMS store
	private final static int START_YEAR = 2014;

	// Elements used for login GUI
	// Elements for login
	private JTextField userId;
	private JPasswordField password;
	// Elements for registration
	private JTextField newUserId;
	private JPasswordField newPassword;
	private JTextField name;
	private JTextField address;
	private JTextField phone;

	//Elements for PurchaseItem
	private JTextField categoryField;
	private JTextField titleField;
	private JTextField singerField;
	private JTextField upcField;
	private JTextField qtyField;

	//Fields used to display search result
	private TableInfo searchTableInfo;
	private JTable searchTable;
	private JLabel searchTablePanel;
	private JLabel searchTitle;

	//Elements for CheckOut
	private JTextField cardNumField;
	private JTextField expiredDateField;
	private int expiredDateYear;
	private int expiredDateMonth;
	private int expiredDateDay;

	// Panel for dispalying purchase GUI
	private JPanel purchaseItemPanel;
	private String customerName;
	private String customerId;

	// Panel for Checkout GUI
	private JPanel checkOutPanel;

	// Class initialization
	private Order order = new Order();
	private Item item = new Item();

	// CheckOut Label
	private JLabel billInfoLabel;

	// Variables to keep track of User Info
	int cidUser;
	Map<Integer, Integer> myCart;

	public CustomerPanel()
	{

		this.setLayout( null );
		initializeLoginPanel();
	}

	private void initializeLoginPanel()
	{
		loginPanel = new JPanel();
		loginPanel.setLayout( null );
		final CustomerPanel customerPanel = this;

		// Heading Text
		JLabel welcome = new JLabel( "Welcome to the AMS shopping system." );
		welcome.setFont( new Font( "serif", Font.BOLD, 30 ) );
		welcome.setBounds( 25, 25, 800, 40 );

		JLabel welcome2 = new JLabel( "Please login to continue. If this is your first visit, please register a new account." );
		welcome2.setFont( new Font( "serif", Font.PLAIN, 18 ) );
		welcome2.setBounds( 45, 65 , 800, 24 );

		// Constants used for spacing elements out
		int topItem = 206;
		int labelSpacing = 19;
		int textFieldSpacing = 75;
		int buttonSpacing = 40;
		int headerSpacing = 36;

		// Login part of the page
		JLabel loginText = new JLabel( "Login:" );
		loginText.setFont( new Font( "serif", Font.BOLD, 18 ) );
		loginText.setBounds( 140, topItem - headerSpacing , 800, 24 );

		// top login element
		JLabel userId1 = new JLabel( "User ID:" );
		userId1.setFont( new Font( "serif", Font.BOLD, 14 ) );
		userId1.setBounds( 150, topItem, 150, 19);

		userId = new JTextField();
		userId.setBounds( 150, topItem + labelSpacing, 150, 16 );

		JLabel password1 = new JLabel( "Password:" );
		password1.setFont( new Font( "serif", Font.BOLD, 14 ) );
		password1.setBounds( 150, topItem + textFieldSpacing, 150, 19);

		password = new JPasswordField();
		password.setBounds( 150, topItem + textFieldSpacing + labelSpacing, 150, 16 );

		JButton loginButton = new JButton();
		loginButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				Order order = new Order();
				String userId = customerPanel.userId.getText();
				int cid = (userId.equals("") || userId.matches("^\\s*$")) ? 0 :Integer.parseInt(userId);
				String password = new String( customerPanel.password.getPassword() );
				if ( userId.equals("") || password.equals("") )
				{
					// Empty fields. Show error popup
					JOptionPane.showMessageDialog( customerPanel,
							"Please fill in all required fields",
							"Login error",
							JOptionPane.ERROR_MESSAGE);
				}
				// TODO: Check if password is correct in the if statement condition
				else if ( order.login(cid, password) )
				{
					// Update the cid to keep track
					cidUser = cid;
					// Password is correct. Continue to shopping page.
					customerPanel.customerId = userId;
					Customer customer = new Customer();
					customerPanel.customerName = customer.getName( Integer.parseInt( userId ) );
					loginPanel.setVisible( false );
					initializePurchaseItemPanel();
					purchaseItemPanel.setVisible( true );
				}
				else
				{
					// Password is incorrect. Show error popup
					JOptionPane.showMessageDialog( customerPanel,
							"Incorrect user credentials or user is not registered",
							"Login error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		loginButton.setText( "Login" );
		loginButton.setBounds( 175, topItem + textFieldSpacing + labelSpacing + buttonSpacing, 85, 16 );

		// Register Part of the page
		JLabel registerText = new JLabel( "Register:" );
		registerText.setFont( new Font( "serif", Font.BOLD, 18 ) );
		registerText.setBounds( 740, topItem - headerSpacing , 800, 24 );

		// top register element
		JLabel userId2 = new JLabel( "User ID (Must be a number):" );
		userId2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		userId2.setBounds( 750, topItem, 300, 19);

		newUserId = new JTextField();
		newUserId.setBounds( 750, topItem + labelSpacing, 150, 16 );

		JLabel password2 = new JLabel( "Password:" );
		password2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		password2.setBounds( 750, topItem + textFieldSpacing, 150, 19);

		newPassword = new JPasswordField();
		newPassword.setBounds( 750, topItem + textFieldSpacing + labelSpacing, 150, 16 );

		JLabel name2 = new JLabel( "Name:" );
		name2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		name2.setBounds( 750, topItem + 2*textFieldSpacing, 150, 19);

		name = new JTextField();
		name.setBounds( 750, topItem + 2*textFieldSpacing + labelSpacing, 150, 16 );

		JLabel address2 = new JLabel( "Address:" );
		address2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		address2.setBounds( 750, topItem + 3*textFieldSpacing, 150, 19);

		address = new JTextField();
		address.setBounds( 750, topItem + 3*textFieldSpacing + labelSpacing, 150, 16 );

		JLabel phone2 = new JLabel( "Phone:" );
		phone2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		phone2.setBounds( 750, topItem + 4*textFieldSpacing, 150, 19);

		phone = new JTextField();
		phone.setBounds( 750, topItem + 4*textFieldSpacing + labelSpacing, 150, 16 );

		JButton registerButton = new JButton();
		registerButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				Order order = new Order();
				String userId = customerPanel.newUserId.getText();
				int cid = (userId.equals("") || userId.matches("^\\s*$")) ? 0 :Integer.parseInt(userId);
				String password = new String( customerPanel.newPassword.getPassword() );
				String name = customerPanel.name.getText();
				String address = customerPanel.address.getText();
				String phone = customerPanel.phone.getText();
				if ( userId.equals("") || password.equals("") || name.equals("") || address.equals("") || phone.equals("") )
				{
					// Empty fields. Show error popup
					JOptionPane.showMessageDialog( customerPanel,
							"Please fill in all required fields",
							"Register error",
							JOptionPane.ERROR_MESSAGE);
				}
				// TODO: Check if registration is successful in the if statement condition
				else if ( order.registerCustomer(cid, password, name, address, phone) )
				{
					// Registration is successful. Login the user and continue to shopping page.
					cidUser = cid;
					customerName = name;
					customerId = userId;
					loginPanel.setVisible( false );
					initializePurchaseItemPanel();
					purchaseItemPanel.setVisible( true );
				}
				else
				{
					// cid is taken. Show error popup
					JOptionPane.showMessageDialog( customerPanel,
							"Registration failed. Try a different userId",
							"Registration error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		registerButton.setText( "Register" );
		registerButton.setBounds( 775, topItem + 4*textFieldSpacing + labelSpacing + buttonSpacing, 85, 16 );

		// Add login page to CustomerPanel
		this.add( loginPanel );
		// Add header text
		loginPanel.add( welcome );
		loginPanel.add( welcome2 );
		// Add login ui elements
		loginPanel.add( loginText );
		loginPanel.add( userId );
		loginPanel.add( password );
		loginPanel.add( userId1 );
		loginPanel.add( password1 );
		loginPanel.add( loginButton );
		// Add register ui elements
		loginPanel.add( registerText );
		loginPanel.add( newUserId );
		loginPanel.add( newPassword );
		loginPanel.add( name );
		loginPanel.add( address );
		loginPanel.add( phone );
		loginPanel.add( userId2 );
		loginPanel.add( password2 );
		loginPanel.add( name2 );
		loginPanel.add( address2 );
		loginPanel.add( phone2 );
		loginPanel.add( registerButton );
		// Set size for login page
		loginPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );

		JSeparator separator = new JSeparator( JSeparator.VERTICAL );
		loginPanel.add( separator );
		separator.setBounds( 600, 150, 1, 500 );
	}

	private void initializePurchaseItemPanel()
	{
		purchaseItemPanel = new JPanel();
		purchaseItemPanel.setLayout( null );
		purchaseItemPanel.setVisible( false );
		final CustomerPanel customerPanel = this;

		// Constants used for spacing
		int topItem = 90;
		int labelSpacing = 20;
		int textFieldSpacing = 30;
		int xSpacing = 100;


		// Heading Text
		JLabel welcome = new JLabel( "Welcome to the AMS shopping system." );
		welcome.setFont( new Font( "serif", Font.BOLD, 24 ) );
		welcome.setBounds( 25, 25, 800, 40 );

		JLabel welcome2 = new JLabel( "You are currently logged in as: User Id: " + customerId + ", Name: " + customerName );
		welcome2.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		welcome2.setBounds( 45, 65 , 400, 20 );	

		// Search Bar
		JLabel searchLabel = new JLabel ("Search for your item...");
		searchLabel.setFont( new Font( "serif", Font.BOLD, 16 ) );
		searchLabel.setBounds( 50, topItem, 300, 25);

		//Category
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		categoryLabel.setBounds(70, topItem + textFieldSpacing, 75, 20);

		categoryField = new JTextField();
		categoryField.setBounds(70, topItem + textFieldSpacing + labelSpacing, 75, 18);

		//Title
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		titleLabel.setBounds(70 + xSpacing, topItem + textFieldSpacing, 75, 20);

		titleField = new JTextField();
		titleField.setBounds(70 + xSpacing, topItem + textFieldSpacing + labelSpacing, 75, 18);

		//LeadSinger
		JLabel singerLabel = new JLabel("Leading Singer");
		singerLabel.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		singerLabel.setBounds(70 + xSpacing*2, topItem + textFieldSpacing, 120, 20);

		singerField = new JTextField();
		singerField.setBounds(70 + xSpacing*2, topItem + textFieldSpacing + labelSpacing, 75, 18);

		//Add Item to cart
		JLabel purchaseLabel = new JLabel("Add item to cart");
		purchaseLabel.setFont( new Font( "serif", Font.BOLD, 16 ) );
		purchaseLabel.setBounds(50, topItem + textFieldSpacing*3, 150, 20);

		//UPC
		JLabel upcLabel = new JLabel("UPC");
		upcLabel.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		upcLabel.setBounds(70, topItem + textFieldSpacing*4, 75, 20);

		upcField = new JTextField();
		upcField.setBounds(70, topItem + textFieldSpacing*4 + labelSpacing, 75, 18);

		//Quantity
		JLabel qtyLabel = new JLabel("Quantity");
		qtyLabel.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		qtyLabel.setBounds(70 + xSpacing, topItem + textFieldSpacing*4, 75, 20);

		qtyField = new JTextField();
		qtyField.setBounds(70 + xSpacing, topItem + textFieldSpacing*4 + labelSpacing, 75, 18);





		//Search Button
		JButton searchButton = new JButton();
		searchButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String category = customerPanel.categoryField.getText();
				String title = customerPanel.titleField.getText();
				String singer = customerPanel.singerField.getText();

				if (category.isEmpty()&&title.isEmpty()&&singer.isEmpty()){
					JOptionPane.showMessageDialog(customerPanel, 
							"Please fill in at least one of the fields",
							"Search error",
							JOptionPane.ERROR_MESSAGE);

				}
				else {
					customerPanel.searchTableInfo = order.searchItem(category, title, singer);
					displaySearchTable();
				}

			}
		});

		searchButton.setText( "Search" );
		searchButton.setBounds( 70 + xSpacing*3, topItem + textFieldSpacing+ labelSpacing, 80, 18 );

		searchTitle = new JLabel();
		purchaseItemPanel.add(searchTitle);

		searchTablePanel = new JLabel();
		purchaseItemPanel.add(searchTablePanel);
		searchTablePanel.setLayout( new BorderLayout());






		//Add to shopping cart Button
		JButton addCartButton = new JButton();
		addCartButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{

				String upcText = customerPanel.upcField.getText();
				int upc = (upcText.equals("") || upcText.matches("^\\s*$")) ? 0 :Integer.parseInt(upcText);
				String qtyText = customerPanel.qtyField.getText();
				int qty = (qtyText.equals("") || qtyText.matches("^\\s*$")) ? 0 :Integer.parseInt(qtyText);
				if (qty <= 0){
					JOptionPane.showMessageDialog(customerPanel, 
							"Quantity cannot be less than or equal to 0",
							"Input error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (upcText.isEmpty()&&qtyText.isEmpty()) {
					JOptionPane.showMessageDialog(customerPanel, 
							"Please fill the UPC and Quantity you want to purchase",
							"Search error",
							JOptionPane.ERROR_MESSAGE);
				}
				if (!item.itemExist(upc)) {
					JOptionPane.showMessageDialog(customerPanel, 
							"Sorry, but we do not have this item",
							"Search error",
							JOptionPane.ERROR_MESSAGE);
				}

				else if (upc != 0 && qty != 0) {
					if (order.isOutOfStock(upc)) {
						JOptionPane.showMessageDialog(customerPanel, 
								"This item is currently out of stock",
								"Out of Stock",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int stock = order.checkStock(upc, qty);
					if (qty > stock) {
						int dialogResult = JOptionPane.showConfirmDialog(null, "Sorry, but there are only "+ stock +" left in stock. Do you want to purchase all of them?", null, JOptionPane.YES_NO_OPTION);
						if (dialogResult == JOptionPane.YES_OPTION) {
							order.purchaseItem(upc, stock);
							JOptionPane.showMessageDialog(null, "Successfully added to cart");
						}
						else if (dialogResult == JOptionPane.NO_OPTION)
							JOptionPane.showMessageDialog(null, "Is there anything else that you would like to buy?");
					}
					if (qty == stock) {
						order.purchaseItem(upc, qty);
						JOptionPane.showMessageDialog(null, "Successfully added to cart");
					}

				}


			}

		});
		addCartButton.setText( "Add to Cart" );
		addCartButton.setBounds( 70 + xSpacing*2, topItem + textFieldSpacing*4 + labelSpacing, 100, 18 );

		// My cart button
		JButton cartButton = new JButton();
		cartButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Map<Integer, Integer> shopCart = order.getCart();
				if (shopCart.isEmpty()) {
					JOptionPane.showMessageDialog(customerPanel, 
							"The Cart is Empty", "My Shopping Cart",
							JOptionPane.PLAIN_MESSAGE);

				}
				else {
					StringBuilder stringBuilder = new StringBuilder();
					System.out.println(shopCart.keySet());
					System.out.println(shopCart.values());

					for (Entry<Integer, Integer> entry : shopCart.entrySet()) {
						Integer key = entry.getKey();
						Integer value = entry.getValue();

						String title = item.getTitle(key);

						stringBuilder.append("UPC: "+ key.toString() + ", ");
						stringBuilder.append("Title: " + title + ", ");
						stringBuilder.append("Quantity: " + value.toString() + ", ");

						stringBuilder.append("\n");
					}
					String finalString = stringBuilder.toString();
					System.out.println(finalString+"this is final string");
					JOptionPane.showMessageDialog(customerPanel, 
							finalString, "My Shopping Cart",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		cartButton.setText("My Cart");
		cartButton.setBounds(950, 15, 100, 16);

		// CheckOut button
		JButton checkOutButton = new JButton();
		checkOutButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				myCart = order.getCart();
				if (myCart.isEmpty()) {
					JOptionPane.showMessageDialog(customerPanel, 
							"The Cart is Empty, Cannot proceed to check out", "Error",
							JOptionPane.ERROR_MESSAGE);

				}
				else {
					purchaseItemPanel.setVisible( false );
					initializeCheckOutPanel();
					checkOutPanel.setVisible( true );
				}


			}

		});

		checkOutButton.setText("Checkout");
		checkOutButton.setBounds(950, 50, 100, 16);

		// Logout button
		JButton logoutButton = new JButton();
		logoutButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				Map<Integer, Integer> shopCart = order.getCart();
				if(!shopCart.isEmpty()){
					for (Entry<Integer, Integer> entry : shopCart.entrySet()) {
						Integer key = entry.getKey();
						Integer value = entry.getValue();

						item.updateStock(key, value, "+");
					}

				}

				JOptionPane.showMessageDialog(customerPanel, 
						"You have successfully logged out, your cart has been emptied", "",
						JOptionPane.PLAIN_MESSAGE);
				// Logout and return to login page.
				customerName = "";
				customerId = "";
				order = new Order();
				item = new Item();
				myCart = new HashMap<Integer, Integer>();
				purchaseItemPanel.setVisible( false );
				initializeLoginPanel();		
				loginPanel.setVisible( true );

			}

		});

		logoutButton.setText( "Logout" );
		logoutButton.setBounds( 1080, 15, 85, 16 );

		// Add item purchasing page to CustomerPanel
		this.add( purchaseItemPanel );
		// Add header text
		purchaseItemPanel.add( welcome );
		purchaseItemPanel.add( welcome2 );
		// Add search bar
		purchaseItemPanel.add(searchLabel);
		purchaseItemPanel.add(categoryLabel);
		purchaseItemPanel.add(categoryField);
		purchaseItemPanel.add(titleLabel);
		purchaseItemPanel.add(titleField);
		purchaseItemPanel.add(singerLabel);
		purchaseItemPanel.add(singerField);
		purchaseItemPanel.add(purchaseLabel);
		purchaseItemPanel.add(upcLabel);
		purchaseItemPanel.add(upcField);
		purchaseItemPanel.add(qtyLabel);
		purchaseItemPanel.add(qtyField);

		// Add Purchase button
		purchaseItemPanel.add(addCartButton);
		// Add Search button
		purchaseItemPanel.add(searchButton);
		// Add logout button
		purchaseItemPanel.add( logoutButton );
		// Add Display Cart button
		purchaseItemPanel.add(cartButton);
		// Add CheckOut button
		purchaseItemPanel.add(checkOutButton);


		// Set size for PurchaseItem page
		purchaseItemPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );
	}

	private void initializeCheckOutPanel(){

		int textFieldSpacing = 30;
		int xSpacing = 200;

		checkOutPanel = new JPanel();
		checkOutPanel.setLayout( null );
		checkOutPanel.setVisible( false );
		final CustomerPanel customerPanel = this;
		displayBill();

		// Heading Text
		JLabel checkOut = new JLabel( "Checkout Page" );
		checkOut.setFont( new Font( "serif", Font.BOLD, 24 ) );
		checkOut.setBounds( 25, 25, 800, 40 );

		JLabel welcome2 = new JLabel( "You are currently logged in as: User Id: " + customerId + ", Name: " + customerName );
		welcome2.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		welcome2.setBounds( 45, 65 , 400, 20 );	

		JLabel cardNumLabel = new JLabel ("Credit Card Number");
		cardNumLabel.setFont(new Font( "serif", Font.PLAIN, 14 ) );
		cardNumLabel.setBounds(60, 100, 200, 20);

		cardNumField = new JTextField();
		cardNumField.setBounds(60, 100+textFieldSpacing, 150, 18);

		JLabel expiredDateLabel = new JLabel("Expiry Date");
		expiredDateLabel.setFont(new Font( "serif", Font.PLAIN, 14 ) );
		expiredDateLabel.setBounds(60+xSpacing, 100, 200, 20);

		// Create element for entering the year
		List<Integer> yearList = new ArrayList<Integer>();
		int currentYear = LocalDate.now().getYear();
		for ( int i = 0; i < 11; i++ )
		{
			yearList.add( currentYear + 10 - i );
		}
		final Object[] yearArray = yearList.toArray();

		final JComboBox expiredYearDropdown = new JComboBox();

		for (int i = 0; i < yearArray.length; i++)
		{
			expiredYearDropdown.addItem( (Integer)yearArray[i] );
		}
		expiredYearDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expiredDateYear = (Integer)expiredYearDropdown.getSelectedItem();
			}
		});
		expiredDateYear = currentYear;

		checkOutPanel.add(expiredYearDropdown);
		expiredYearDropdown.setBounds( 60+xSpacing, 100+textFieldSpacing, 85, 20 );
		expiredYearDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the month
		List<Integer> monthList = new ArrayList<Integer>();
		for ( int i = 0; i < 12; i++ )
		{
			monthList.add( i + 1 );
		}
		final Object[] monthArray = monthList.toArray();

		final JComboBox expiredMonthDropdown = new JComboBox();

		for (int i = 0; i < monthArray.length; i++)
		{
			expiredMonthDropdown.addItem( (Integer)monthArray[i] );
		}
		expiredMonthDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expiredDateMonth = (Integer)expiredMonthDropdown.getSelectedItem();
			}
		});
		expiredDateMonth = 1;

		checkOutPanel.add(expiredMonthDropdown);
		expiredMonthDropdown.setBounds( 60+xSpacing+100, 100+textFieldSpacing, 60, 20 );
		expiredMonthDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the day

		List<Integer> dayList = new ArrayList<Integer>();

		for ( int i = 0; i < 31; i++ )
		{
			dayList.add( i + 1 );
		}
		final Object[] dayArray = dayList.toArray();

		final JComboBox expiredDayDropdown = new JComboBox();

		for (int i = 0; i < dayArray.length; i++)
		{
			expiredDayDropdown.addItem( (Integer)dayArray[i] );
		}
		expiredDayDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expiredDateDay = (Integer)expiredDayDropdown.getSelectedItem();
			}
		});
		expiredDateDay = 1;

		checkOutPanel.add(expiredDayDropdown);
		expiredDayDropdown.setBounds( 60+xSpacing+175, 100+textFieldSpacing, 80, 20 );
		expiredDayDropdown.setMaximumRowCount(12);


		// Confirm Button
		JButton confirmButton = new JButton();
		confirmButton.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				String cardNum = cardNumField.getText();
				if (cardNum.isEmpty()){
					JOptionPane.showMessageDialog(customerPanel, 
							"Credit Card Number cannot be blank", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String year = String.valueOf( expiredDateYear );
				String month = String.valueOf( expiredDateMonth );
				String date = String.valueOf( expiredDateDay );
				String dateString = year + "-" + month + "-" + date;
				System.out.println(dateString);

				Date sqlDate = Date.valueOf( dateString );
				
				System.out.println(sqlDate.toString()+"this is the expiry date");
				int temp = order.outstandingOrder();
				int numofDays = order.calcDate(temp);

				System.out.println(cidUser);
				int receiptId = order.checkOut(cardNum, sqlDate, cidUser);

				JOptionPane.showMessageDialog(customerPanel, 
						"Checkout completed, your ReceiptId is: "+receiptId, "Success",
						JOptionPane.PLAIN_MESSAGE);

				JOptionPane.showMessageDialog(customerPanel, 
						"Your item will be delievered in " + numofDays + " day(s)", "Success",
						JOptionPane.PLAIN_MESSAGE);

				int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to make more purchases?", null, JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					order = new Order();
					item = new Item();
					myCart = new HashMap<Integer, Integer>();
					checkOutPanel.setVisible( false );
					initializePurchaseItemPanel();		
					purchaseItemPanel.setVisible( true );


				}
				else if (dialogResult == JOptionPane.NO_OPTION){
					order = new Order();
					item = new Item();
					myCart = new HashMap<Integer, Integer>();
					checkOutPanel.setVisible( false );
					initializeLoginPanel();		
					loginPanel.setVisible( true );
				}

			}

		});

		confirmButton.setText("Confirm");
		confirmButton.setBounds(60+xSpacing+225, 100, 85, 16);


		// Logout button
		JButton logoutButton = new JButton();
		logoutButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				Map<Integer, Integer> shopCart = order.getCart();
				if(!shopCart.isEmpty()){
					for (Entry<Integer, Integer> entry : shopCart.entrySet()) {
						Integer key = entry.getKey();
						Integer value = entry.getValue();

						item.updateStock(key, value, "+");
					}

				}
				JOptionPane.showMessageDialog(customerPanel, 
						"You have successfully logged out, your cart has been emptied", "",
						JOptionPane.PLAIN_MESSAGE);
				// Logout and return to login page.
				customerName = "";
				customerId = "";
				order = new Order();
				item = new Item();
				myCart = new HashMap<Integer, Integer>();
				checkOutPanel.setVisible( false );

				initializeLoginPanel();		
				loginPanel.setVisible( true );
			}

		});

		logoutButton.setText( "Logout" );
		logoutButton.setBounds( 1080, 15, 85, 16 );

		// Add item purchasing page to CustomerPanel
		this.add( checkOutPanel );
		// Add header text
		checkOutPanel.add( checkOut );
		checkOutPanel.add( welcome2 );

		// Add Credit Card Label&Field
		checkOutPanel.add(cardNumLabel);
		checkOutPanel.add(cardNumField);

		// Add Expiry Date Label&Field
		checkOutPanel.add(expiredDateLabel);

		// Add billInfo
		checkOutPanel.add(billInfoLabel);

		// Add confirm button
		checkOutPanel.add(confirmButton);

		// Add logout button
		checkOutPanel.add(logoutButton);


		// Set size for CheckOut page
		checkOutPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );



	}

	private void displaySearchTable() {
		searchTable = new JTable(searchTableInfo.getData(), searchTableInfo.getHeaders());
		searchTablePanel.removeAll();
		searchTablePanel.add(searchTable.getTableHeader(), BorderLayout.NORTH);
		searchTablePanel.add(searchTable, BorderLayout.CENTER);
		System.out.println( searchTableInfo.getData().length);
		searchTablePanel.setBounds( 5, 280, 1000, 16*searchTableInfo.getData().length + 18 );
		// TODO: Change size of the panel so scrolling will happen
		this.setPreferredSize( new Dimension ( 1000, 3000) );
		purchaseItemPanel.setPreferredSize( new Dimension( 1000, 3000));
		// TODO: need to change the preferred size back to normal when logging out
		searchTablePanel.revalidate();
		searchTablePanel.repaint();
	}

	private void displayBill() {

		// to keep track of the total Price
		Double totalPrice = 0.0;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");

		stringBuilder.append("<table border=\"0\" style=\"background-color:transparent;border-collapse:collapse;border:0px solid FFCC00;color:000000;width:100%\" cellpadding=\"3\" cellspacing=\"3\">");
		stringBuilder.append("<tr>");
		stringBuilder.append("<td>UPC</td>");
		stringBuilder.append("<td>Title</td>");
		stringBuilder.append("<td>Quantity</td>");
		stringBuilder.append("<td>Unit Price</td>");
		stringBuilder.append("</tr>");

		for (Entry<Integer, Integer> entry : myCart.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			String title = item.getTitle(key);
			Double unitPrice = item.getPrice(key);
			totalPrice += unitPrice * value;
			totalPrice = (double) Math.round(totalPrice * 100) / 100;

			stringBuilder.append("<tr>");
			stringBuilder.append("<td>" +key.toString() + "</td>");
			stringBuilder.append("<td>" +title + "</td>");
			stringBuilder.append("<td>" +value.toString() + "</td>");
			stringBuilder.append("<td>" +unitPrice.toString() + "</td>");
			stringBuilder.append("</tr>");
		}
		stringBuilder.append("<tr>");
		stringBuilder.append("<td> Total Price: " +totalPrice + "</td>");
		stringBuilder.append("</tr>");
		stringBuilder.append("</table>");
		stringBuilder.append("</html>");
		String finalString = stringBuilder.toString();

		billInfoLabel = new JLabel(finalString);
		billInfoLabel.setFont( new Font( "serif", Font.PLAIN, 18 ));
		billInfoLabel.setBounds(50, 180, 1000, 800);
		billInfoLabel.setVerticalTextPosition(JLabel.BOTTOM);
		billInfoLabel.setVerticalAlignment(JLabel.TOP);
		checkOutPanel.setAlignmentY(TOP_ALIGNMENT);
		billInfoLabel.setBorder(BorderFactory.createEmptyBorder( -3 /*top*/, 0, 0, 0 ));


	}
}
