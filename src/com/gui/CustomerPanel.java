package com.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.engine.Customer;
import com.engine.Order;

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

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Order order = new Order();
				String userId = customerPanel.userId.getText();
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
				else if ( true )
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

		name = new JPasswordField();
		name.setBounds( 750, topItem + 2*textFieldSpacing + labelSpacing, 150, 16 );

		JLabel address2 = new JLabel( "Address:" );
		address2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		address2.setBounds( 750, topItem + 3*textFieldSpacing, 150, 19);

		address = new JPasswordField();
		address.setBounds( 750, topItem + 3*textFieldSpacing + labelSpacing, 150, 16 );

		JLabel phone2 = new JLabel( "Phone:" );
		phone2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		phone2.setBounds( 750, topItem + 4*textFieldSpacing, 150, 19);

		phone = new JPasswordField();
		phone.setBounds( 750, topItem + 4*textFieldSpacing + labelSpacing, 150, 16 );

		JButton registerButton = new JButton();
		registerButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Order order = new Order();
				String userId = customerPanel.newUserId.getText();
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
				else if ( true )
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
					// Password is incorrect. Show error popup
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

		// Heading Text
		JLabel welcome = new JLabel( "Welcome to the AMS shopping system." );
		welcome.setFont( new Font( "serif", Font.BOLD, 30 ) );
		welcome.setBounds( 25, 25, 800, 40 );

		JLabel welcome2 = new JLabel( "You are currently logged in as: User Id: " + customerId + ", Name: " + customerName );
		welcome2.setFont( new Font( "serif", Font.PLAIN, 18 ) );
		welcome2.setBounds( 45, 65 , 800, 24 );		

		// TODO: Add inputs for searching for items
		/* Example of text input
		JLabel userId2 = new JLabel( "User ID (Must be a number):" );
		userId2.setFont( new Font( "serif", Font.BOLD, 14 ) );
		userId2.setBounds( 750, topItem, 300, 19);

		 * Example of Label
		newUserId = new JTextField();
		newUserId.setBounds( 750, topItem + labelSpacing, 150, 16 );

		 * Example of reading a text input
		String text = userId2.getText();
		 */
		// TODO: Add display for items that match the users search criteria 
		// TODO: Add display for shopping cart
		/* Probably display it in a table?

		 * Example of table

		   	String[] names = {"Bryan","Lee"};
			Object[][] data = {{"something",new Integer(3)},{"probelm?",new Integer(4)},{"lololol?",new Integer(6)}};
			topSalesTable = new JTable(data,names);
			topSalesTablePanel = new JLabel();
			topSalesTablePanel.setLayout( new BorderLayout() );
			this.add(topSalesTablePanel);
			topSalesTablePanel.setBounds( 0, 0, 500, 16*data.length + 18 );
			topSalesTablePanel.add( topSalesTable.getTableHeader(), BorderLayout.NORTH );
			topSalesTablePanel.add( topSalesTable, BorderLayout.CENTER );
			

		 */
		// TODO: maybe add remove from shopping cart? 

		// Logout button
		JButton logoutButton = new JButton();
		logoutButton.addActionListener( new ActionListener() {

			@Override
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
		// Add logout button
		purchaseItemPanel.add( logoutButton );

		// Set size for login page
		purchaseItemPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );
	}
}
