package com.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.engine.Customer;
import com.engine.Item;
import com.engine.Order;
import com.engine.Report;
import com.engine.Return;
import com.engine.TableInfo;

public class ClerkPanel extends JPanel {

	// Panel for displaying return GUI
	private JPanel returnPanel;
	private JPanel displayReturnPanel;
	
	private JTextField receipt;
	private JTextField upc;
	private JTextField item;
	
	private JTable displayRTable;
	
	private TableInfo clerkTableInfo;
	private JLabel clerkTablePanel;
	private JTable clerkTable;

	private String receiptID;
	private String upcNumber;
	private String itemNumber;
	
	private JLabel begin;
	private JLabel reportLabel;
	private JLabel displayRTPanel;
	private JPanel displayRPanel;
	private TableInfo displayRTInfo;

	public ClerkPanel()	{

		// TODO: add gui for return
		this.setLayout( null);
		initializeClerkPanel();

	}

	private void initializeClerkPanel() {
		returnPanel = new JPanel();
		returnPanel.setLayout(null);
		final ClerkPanel clerkPanel = this;

		// Heading Text
		begin = new JLabel("Process returns");
		begin.setFont( new Font( "serif", Font.BOLD, 30 ) );
		begin.setBounds( 25, 25, 800, 40 );

		JLabel begin2 = new JLabel("To update, enter the Receipt-ID, Item UPC, and quantity of item(s) to return: ");
		begin2.setFont( new Font( "serif", Font.PLAIN, 18 ) );
		begin2.setBounds( 45, 65 , 800, 24 );

		// Constants used for spacing elements out
		int yAxis = 100;
		int textFieldSpacing = 75;

		JLabel receiptText = new JLabel("Receipt ID: ");
		receiptText.setFont( new Font( "serif", Font.BOLD, 14 ));
		receiptText.setBounds(40, yAxis, 800, 24);
		receipt = new JTextField();
		receipt.setBounds(40 + textFieldSpacing, yAxis , 200, 20);

		JLabel upcText = new JLabel("UPC ID: ");
		upcText.setFont( new Font( "serif", Font.BOLD, 14 ));
		upcText.setBounds(400, yAxis, 800, 24);
		upc = new JTextField();
		upc.setBounds(400 + textFieldSpacing, yAxis , 200, 20);

		JLabel itemText = new JLabel("Quantity: ");
		itemText.setFont( new Font( "serif", Font.BOLD, 14 ));
		itemText.setBounds(750, yAxis, 400, 24);
		item = new JTextField();
		item.setBounds(750 + textFieldSpacing, yAxis , 200, 20);
		
		final ClerkPanel cPanel = this;
		final Item i = new Item();

		JButton searchButton = new JButton();
		searchButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Return transaction = new Return();
				
				String receiptID = clerkPanel.receipt.getText();
				receiptID = clerkPanel.receipt.getText();

				String upcNumber = new String();
				upcNumber = clerkPanel.upc.getText();

				String itemNumber = new String();
				itemNumber = clerkPanel.item.getText();

				// missing fields
				if (receiptID.equals("") || upcNumber.equals("") || itemNumber.equals("")) {	
					JOptionPane.showMessageDialog( clerkPanel,
							"Please fill in all required fields.",
							"Update error",
							JOptionPane.ERROR_MESSAGE);
					System.out.println("missing");
				}
				

				int itemNum = Integer.parseInt(itemNumber);
				
				if (itemNum < 1) {
					JOptionPane.showMessageDialog( clerkPanel,
							"Please enter valid quantity value.",
							"Update error",
							JOptionPane.ERROR_MESSAGE);
				}
				int rec = Integer.parseInt(receiptID);
				int uNumber = Integer.parseInt(upcNumber);
				
				System.out.println("after rec/uNumber");
				

				// USING BRYAN'S CODE HERE
				
				if ((transaction.isValid(rec) == false)) {
					JOptionPane.showMessageDialog( clerkPanel,
							"Cannot refund, after 15 days or invalid receipt number.",
							"Update error",
							JOptionPane.ERROR_MESSAGE);
					System.out.println("false isvalid");
				}
				
				if ((transaction.isCorrectUpc(rec, uNumber)) == false) {
					JOptionPane.showMessageDialog( clerkPanel,
							"No matching item. Please check receipt.",
							"Update error",
							JOptionPane.ERROR_MESSAGE);
					System.out.println("false is correctupc");
				}
				
				if ((transaction.newRefund(rec, uNumber)) == false) {
					JOptionPane.showMessageDialog( clerkPanel,
					"Refund has already been processed. Cannot refund.",
					"Update error",
					JOptionPane.ERROR_MESSAGE);
					System.out.println("false new refund");
				}
				
				else {
									
					transaction.refund( rec, uNumber );
					System.out.println("actually refunding");
				}
				System.out.println("just after refund");
				
				//KENNETH TO DO NO IDEA WHAT HAPPENS AFTER THIS POINT IN TIME

				// correct fields, update table
				//else if () {
					// Fields are correct, search and update quantity

					//insert method to update table TODO
				
				clerkPanel.clerkTableInfo = i.getRefundItem(rec, uNumber, itemNum);
				displayClerkTable();
			}
		});
		
		searchButton.setText( "Return" );
		searchButton.setBounds( 1050, yAxis, 85, 20 );
		
/*
		String[] columnNames = {"UPC", "Receipt", "Type", "Category", "Company", "Year", "Price", "Stock"};
		
		Object[][] data = {{"12341234", "1234", "Electronic", "Phone", "Apple", "2014", "$12.22", "2"}};
		JTable table = new JTable (data, columnNames);
		JScrollPane tableScrollPane = new JScrollPane(table);
		table.setFont(new Font("serif", Font.BOLD, 18));
		table.setBounds(100, yAxis+200, 1000, 7);

		table.setAutoCreateRowSorter(true);
		table.setShowGrid(true);*/
		
		
		
		
/*		private void fillClerkReportPanel() {
			
			String tableReceipt = receipt.getText();
			int tReceipt = Integer.parseInt(tableReceipt);
			String tableUpc = receipt.getText();
			int tUpc = Integer.parseInt(tableUpc);
			String tableItem = receipt.getText();
			int tItem = Integer.parseInt(tableItem);
			
			clerkPanel.clerkTableInfo = i.getRefundItem(tReceipt, tUpc, tItem);
			displayReturnPanel.setPreferredSize( new Dimension (1000, 210 + 16* clerkTableInfo.getData().length));
			
			}
		
		displayRTPanel = new JLabel();
		displayRPanel.add(displayRTPanel);
		displayRTPanel.setLayout(new BorderLayout());
		

		private void displayRTPanel() {
			displayRTable = new JTable(clerkTableInfo.getData(), clerkTableInfo.getHeaders());
			displayRTPanel.removeAll();
			displayRTPanel.add(displayRTable.getTableHeader(), BorderLayout.NORTH);
			displayRTPanel.add(displayRTable, BorderLayout.CENTER);
			displayRTPanel.setBounds(100, 140+16*displayRTInfo.getData().length + 20, 1000, 16*displayRTInfo.getData().length + 20);
		}*/

		JTextField recieptIdInput = new JTextField();

		List<String> headers;
		// List< List<String> > data;


		this.add(returnPanel);
		returnPanel.add(begin);
		returnPanel.add(upc);
		returnPanel.add(receipt);
		returnPanel.add(itemText);
		returnPanel.add(item);
		returnPanel.add(begin2);
		returnPanel.add(receiptText);
		returnPanel.add(upcText);
		returnPanel.add(searchButton);
		//returnPanel.add(table);
		//returnPanel.add(tableScrollPane);
		returnPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );

		JSeparator separator = new JSeparator( JSeparator.VERTICAL );
		returnPanel.add( separator );
		separator.setBounds( 600, 150, 1, 500 );

		clerkTablePanel = new JLabel();
		clerkTablePanel.setLayout( new BorderLayout() );
		returnPanel.add( clerkTablePanel );
	}
	
	private void displayClerkTable()
	{
		clerkTable = new JTable(clerkTableInfo.getData(), clerkTableInfo.getHeaders());
		clerkTablePanel.removeAll();
		clerkTablePanel.add(clerkTable.getTableHeader(), BorderLayout.NORTH);
		clerkTablePanel.add(clerkTable, BorderLayout.CENTER);
		clerkTablePanel.setBounds( 5, 150, 1000, 16*clerkTableInfo.getData().length + 18 );
		clerkTablePanel.revalidate();
		clerkTablePanel.repaint();
	}
}