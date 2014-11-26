package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.engine.Customer;
import com.engine.Item;
import com.engine.Order;
import com.engine.TableInfo;

public class CustomerPanel extends JPanel
{
	// Panel for displaying login GUI
	private JPanel loginPanel;

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

	// Panel for dispalying purchase GUI
	private JPanel purchaseItemPanel;
	private String customerName;
	private String customerId;

	public CustomerPanel()
	{
		// TODO: add gui for registration - DONE. Make changes if you don't like it?
		// TODO: add gui for purchasing items

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
		int buttonSpacing = 40;
		int headerSpacing = 36;

		// Heading Text
		JLabel welcome = new JLabel( "Welcome to the AMS shopping system." );
		welcome.setFont( new Font( "serif", Font.BOLD, 24 ) );
		welcome.setBounds( 25, 25, 800, 40 );

		JLabel welcome2 = new JLabel( "You are currently logged in as: User Id: " + customerId + ", Name: " + customerName );
		welcome2.setFont( new Font( "serif", Font.PLAIN, 14 ) );
		welcome2.setBounds( 45, 65 , 400, 20 );	

		// Search Bar
		JLabel searchLabel = new JLabel ("Seach for your item...");
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

		//Make Purchase
		JLabel purchaseLabel = new JLabel("Make Purchase");
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
				Order order = new Order();

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
		searchButton.setBounds( 350, topItem, 80, 30 );
		
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
				Order order = new Order();
				Item item = new Item();

				String upcText = customerPanel.upcField.getText();
				int upc = (upcText.equals("") || upcText.matches("^\\s*$")) ? 0 :Integer.parseInt(upcText);
				String qtyText = customerPanel.qtyField.getText();
				int qty = (qtyText.equals("") || qtyText.matches("^\\s*$")) ? 0 :Integer.parseInt(qtyText);

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
						int dialogResult = JOptionPane.showConfirmDialog(null, "Sorry, but there are only "+ stock +" left in stock. Do you want to purchase all of them?");
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

				} //else // TODO, figure out how to display the list on GUI


			}

		});
		addCartButton.setText( "Add to Cart" );
		addCartButton.setBounds( 325, topItem + textFieldSpacing*3, 125, 30 );


		// TODO: maybe add remove from shopping cart? 
		
		
		JButton cartButton = new JButton();
		cartButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Order order = new Order();
				Map<Integer, Integer> shopCart = order.getCart();
				JOptionPane.showMessageDialog(customerPanel, 
						"hi" + "\n" +"hi" + "\n" +"hi" + "\n" +"hi" + "\n" +"hi" + "\n" ,
						"My Shopping Cart",
						JOptionPane.PLAIN_MESSAGE);
				
			}
			
		});
		
		cartButton.setText("My Cart");
		cartButton.setBounds(950, 15, 100, 16);

		// Logout button
		JButton logoutButton = new JButton();
		logoutButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				// Logout and return to login page.
				// TODO: clear any fields that are specific to the logged in user
				customerName = "";
				customerId = "";
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
		

		// Set size for login page
		purchaseItemPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );
	}
	
	private void displaySearchTable() {
		searchTable = new JTable(searchTableInfo.getData(), searchTableInfo.getHeaders());
		searchTablePanel.removeAll();
		searchTablePanel.add(searchTable.getTableHeader(), BorderLayout.NORTH);
		searchTablePanel.add(searchTable, BorderLayout.CENTER);
		System.out.println( searchTableInfo.getData().length);
		searchTablePanel.setBounds( 5, 280, 1000, 16*searchTableInfo.getData().length + 18 );
		// TODO: Change size of the panel so scrolling will happen
		this.setPreferredSize( new Dimension ( 1000, 5000) );
		purchaseItemPanel.setPreferredSize( new Dimension( 1000, 5000));
		// TODO: need to change the preferred size back to normal when logging out
		searchTablePanel.revalidate();
		searchTablePanel.repaint();

		/*if ( searchTitle != null )
	{
		dailyReportPanel.remove( reportTitle );
	}
	reportTitle = new JLabel( "Report:" );
	reportTitle.setFont( new Font( "serif", Font.BOLD, 14 ) );
	dailyReportPanel.add( reportTitle );
	reportTitle.setBounds( 100, 120 + 16*dailyReportTotalsInfo.getData().length + 18, 200, 20 );*/
	}
}
