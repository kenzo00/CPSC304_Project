package com.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.engine.Item;
import com.engine.Report;
import com.engine.TableInfo;

public class ManagerPanel extends JPanel
{	
	// Constant representing the starting year of the AMS store
	private final static int START_YEAR = 1990;

	// tabs for the different things a manager needs to do
	private JTabbedPane tabs;
	// Panel for viewing the daily report
	private JPanel dailyReportPanel;
	// Panel for displaying the top sales for a certain day
	private JPanel topSalesPanel;
	// Panel for adding items to the store and processing orders
	private JPanel itemOrderPanel;

	// Fields used for displaying top sales info
	private TableInfo topSalesTableInfo;
	private JTable topSalesTable;
	private JLabel topSalesTablePanel;
	private int topSalesYear;
	private int topSalesMonth;
	private int topSalesDay;
	private int topSalesNumberElements;

	// Fields used for displaying daily report
	private TableInfo dailyReportTableInfo;
	private TableInfo dailyReportTotalsInfo;
	private JTable dailyReportTable;
	private JTable dailyReportTotalsTable;
	private JLabel dailyReportTablePanel;
	private JLabel dailyReportTotalsTablePanel;
	private int dailyReportYear;
	private int dailyReportMonth;
	private int dailyReportDay;
	private JLabel reportTotalTitle;
	private JLabel reportTitle;

	// Fields used in ItemOrderPanel
	private int deliveryYear;
	private int deliveryMonth;
	private int deliveryDay;

	public ManagerPanel()
	{
		// TODO: Add gui for adding items to store
		// TODO: add gui for process deliveries
		this.setLayout(null);

		// Add the tabs to the panel
		initializeTabs();
		this.add( tabs );
		tabs.setBounds( 0, 0, MainInterface.WIDTH - 5, MainInterface.HEIGHT - 85 );

		fillTopSalesPanel();
		fillDailyReportPanel();
		fillItemOrderPanel();

	}

	private void initializeTabs()
	{
		// Initialize the tabs
		tabs = new JTabbedPane();
		dailyReportPanel = new JPanel();
		topSalesPanel = new JPanel();
		itemOrderPanel = new JPanel();

		JScrollPane dailyReportScrollPane = new JScrollPane();
		dailyReportScrollPane.setViewportView( dailyReportPanel );
		dailyReportScrollPane.getVerticalScrollBar().setUnitIncrement(16);

		JScrollPane topSalesScrollPane = new JScrollPane();
		topSalesScrollPane.setViewportView( topSalesPanel );

		JScrollPane itemOrderScrollPane = new JScrollPane();
		itemOrderScrollPane.setViewportView( itemOrderPanel );

		tabs.addTab( "Daily Report", dailyReportScrollPane );
		tabs.addTab( "Top Sales Report", topSalesScrollPane );
		tabs.addTab( "Inventory/Order Management", itemOrderScrollPane );

	}

	private void fillTopSalesPanel()
	{
		topSalesPanel.setLayout( null );

		int rowHeight = 40;

		// ==========================================================================

		// Create element for entering the year
		List<Integer> yearList = new ArrayList<Integer>();
		int currentYear = LocalDate.now().getYear();
		for ( int i = 0; i < currentYear - START_YEAR + 1; i++ )
		{
			yearList.add( currentYear - i );
		}
		final Object[] yearArray = yearList.toArray();

		final JComboBox topSalesYearDropdown = new JComboBox();

		for (int i = 0; i < yearArray.length; i++)
		{
			topSalesYearDropdown.addItem( (Integer)yearArray[i] );
		}
		topSalesYearDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topSalesYear = (Integer)topSalesYearDropdown.getSelectedItem();
			}
		});
		topSalesYear = currentYear;

		topSalesPanel.add(topSalesYearDropdown);
		topSalesYearDropdown.setBounds( 100, rowHeight, 60, 18 );
		topSalesYearDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the month
		List<Integer> monthList = new ArrayList<Integer>();
		for ( int i = 0; i < 12; i++ )
		{
			monthList.add( i + 1 );
		}
		final Object[] monthArray = monthList.toArray();

		final JComboBox topSalesMonthDropdown = new JComboBox();

		for (int i = 0; i < monthArray.length; i++)
		{
			topSalesMonthDropdown.addItem( (Integer)monthArray[i] );
		}
		topSalesMonthDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topSalesMonth = (Integer)topSalesMonthDropdown.getSelectedItem();
			}
		});
		topSalesMonth = 1;

		topSalesPanel.add(topSalesMonthDropdown);
		topSalesMonthDropdown.setBounds( 170, rowHeight, 50, 18 );
		topSalesMonthDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the day
		List<Integer> dayList = new ArrayList<Integer>();
		for ( int i = 0; i < 31; i++ )
		{
			dayList.add( i + 1 );
		}
		final Object[] dayArray = dayList.toArray();

		final JComboBox topSalesDayDropdown = new JComboBox();

		for (int i = 0; i < dayArray.length; i++)
		{
			topSalesDayDropdown.addItem( (Integer)dayArray[i] );
		}
		topSalesDayDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topSalesDay = (Integer)topSalesDayDropdown.getSelectedItem();
			}
		});
		topSalesDay = 1;

		topSalesPanel.add(topSalesDayDropdown);
		topSalesDayDropdown.setBounds( 230, rowHeight, 50, 18 );
		topSalesDayDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the number of elements

		final JComboBox topSalesNumberDropdown = new JComboBox();

		for (int i = 0; i < 50; i++)
		{
			topSalesNumberDropdown.addItem( i+1 );
		}
		topSalesNumberDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topSalesNumberElements = (Integer)topSalesNumberDropdown.getSelectedItem();
			}
		});
		topSalesNumberElements = 1;

		topSalesPanel.add(topSalesNumberDropdown);
		topSalesNumberDropdown.setBounds( 300, rowHeight, 50, 18 );
		topSalesNumberDropdown.setMaximumRowCount(12);

		// ==========================================================================

		final ManagerPanel managerPanel = this;
		final Report report = new Report();

		JButton generateTopSalesButton = new JButton();
		generateTopSalesButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				String year = String.valueOf( topSalesYear );
				String month = String.valueOf( topSalesMonth );
				String date = String.valueOf( topSalesDay );
				String dateString = year + "-" + month + "-" + date;
				System.out.println(dateString);

				Date sqlDate = Date.valueOf( dateString );

				managerPanel.topSalesTableInfo = report.getTopSellingItems( sqlDate, topSalesNumberElements );
				displayTopSalesTable();
			}

		});

		JLabel dateLabel = new JLabel( "Enter the date:" );
		dateLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		topSalesPanel.add( dateLabel );
		dateLabel.setBounds( 100, rowHeight - 25, 200, 20 );

		JLabel numberLabel = new JLabel( "Number of records:" );
		numberLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		topSalesPanel.add( numberLabel );
		numberLabel.setBounds( 300, rowHeight - 25, 200, 20 );

		topSalesPanel.add(generateTopSalesButton);
		generateTopSalesButton.setText( "Generate Table" );
		generateTopSalesButton.setBounds( 450, rowHeight, 120, 18 );

		topSalesTablePanel = new JLabel();
		topSalesPanel.add( topSalesTablePanel );
		topSalesTablePanel.setLayout( new BorderLayout() );
	}

	private void displayTopSalesTable()
	{
		topSalesTable = new JTable(topSalesTableInfo.getData(), topSalesTableInfo.getHeaders());
		topSalesTablePanel.removeAll();
		topSalesTablePanel.add(topSalesTable.getTableHeader(), BorderLayout.NORTH);
		topSalesTablePanel.add(topSalesTable, BorderLayout.CENTER);
		topSalesTablePanel.setBounds( 5, 80, 1000, 16*topSalesTableInfo.getData().length + 18 );
		topSalesTablePanel.revalidate();
		topSalesTablePanel.repaint();
	}

	private void fillDailyReportPanel()
	{
		dailyReportPanel.setLayout( null );

		int rowHeight = 40;

		// ==========================================================================

		// Create element for entering the year
		List<Integer> yearList = new ArrayList<Integer>();
		int currentYear = LocalDate.now().getYear();
		for ( int i = 0; i < currentYear - START_YEAR + 1; i++ )
		{
			yearList.add( currentYear - i );
		}
		final Object[] yearArray = yearList.toArray();

		final JComboBox dailyReportYearDropdown = new JComboBox();

		for (int i = 0; i < yearArray.length; i++)
		{
			dailyReportYearDropdown.addItem( (Integer)yearArray[i] );
		}
		dailyReportYearDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyReportYear = (Integer)dailyReportYearDropdown.getSelectedItem();
			}
		});
		dailyReportYear = currentYear;

		dailyReportPanel.add(dailyReportYearDropdown);
		dailyReportYearDropdown.setBounds( 100, rowHeight, 60, 18 );
		dailyReportYearDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the month
		List<Integer> monthList = new ArrayList<Integer>();
		for ( int i = 0; i < 12; i++ )
		{
			monthList.add( i + 1 );
		}
		final Object[] monthArray = monthList.toArray();

		final JComboBox dailyReportMonthDropdown = new JComboBox();

		for (int i = 0; i < monthArray.length; i++)
		{
			dailyReportMonthDropdown.addItem( (Integer)monthArray[i] );
		}
		dailyReportMonthDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyReportMonth = (Integer)dailyReportMonthDropdown.getSelectedItem();
			}
		});
		dailyReportMonth = 1;

		dailyReportPanel.add(dailyReportMonthDropdown);
		dailyReportMonthDropdown.setBounds( 170, rowHeight, 50, 18 );
		dailyReportMonthDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the day
		List<Integer> dayList = new ArrayList<Integer>();
		for ( int i = 0; i < 31; i++ )
		{
			dayList.add( i + 1 );
		}
		final Object[] dayArray = dayList.toArray();

		final JComboBox dailyReportDayDropdown = new JComboBox();

		for (int i = 0; i < dayArray.length; i++)
		{
			dailyReportDayDropdown.addItem( (Integer)dayArray[i] );
		}
		dailyReportDayDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyReportDay = (Integer)dailyReportDayDropdown.getSelectedItem();
			}
		});
		dailyReportDay = 1;

		dailyReportPanel.add(dailyReportDayDropdown);
		dailyReportDayDropdown.setBounds( 230, rowHeight, 50, 18 );
		dailyReportDayDropdown.setMaximumRowCount(12);

		// ==========================================================================

		JLabel dateLabel = new JLabel( "Enter the date:" );
		dateLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		dailyReportPanel.add( dateLabel );
		dateLabel.setBounds( 100, rowHeight - 25, 200, 20 );

		final ManagerPanel managerPanel = this;
		final Report report = new Report();

		JButton generateDailyReportButton = new JButton();
		generateDailyReportButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				String year = String.valueOf( dailyReportYear );
				String month = String.valueOf( dailyReportMonth );
				String date = String.valueOf( dailyReportDay );
				String dateString = year + "-" + month + "-" + date;
				System.out.println(dateString);

				Date sqlDate = Date.valueOf( dateString );

				managerPanel.dailyReportTableInfo = report.getDailyReport( sqlDate );
				managerPanel.dailyReportTotalsInfo = report.getDailyReportTotals( sqlDate );
				displayDailyReportTotalsTable();
				displayDailyReportTable();		
				dailyReportPanel.setPreferredSize( new Dimension( 1000, 210 + 16*dailyReportTotalsInfo.getData().length + 16*dailyReportTableInfo.getData().length ) );
			}

		});

		dailyReportPanel.add(generateDailyReportButton);
		generateDailyReportButton.setText( "Generate Table" );
		generateDailyReportButton.setBounds( 320, rowHeight, 120, 18 );

		dailyReportTablePanel = new JLabel();
		dailyReportPanel.add( dailyReportTablePanel );
		dailyReportTablePanel.setLayout( new BorderLayout() );

		dailyReportTotalsTablePanel = new JLabel();
		dailyReportPanel.add( dailyReportTotalsTablePanel );
		dailyReportTotalsTablePanel.setLayout( new BorderLayout() );
	}

	private void displayDailyReportTable()
	{
		dailyReportTable = new JTable(dailyReportTableInfo.getData(), dailyReportTableInfo.getHeaders());
		dailyReportTablePanel.removeAll();
		dailyReportTablePanel.add(dailyReportTable.getTableHeader(), BorderLayout.NORTH);
		dailyReportTablePanel.add(dailyReportTable, BorderLayout.CENTER);
		dailyReportTablePanel.setBounds( 5, 140 + 16*dailyReportTotalsInfo.getData().length + 18, 1000, 16*dailyReportTableInfo.getData().length + 18 );
		dailyReportTablePanel.revalidate();
		dailyReportTablePanel.repaint();

		if ( reportTitle != null )
		{
			dailyReportPanel.remove( reportTitle );
		}
		reportTitle = new JLabel( "Report:" );
		reportTitle.setFont( new Font( "serif", Font.BOLD, 14 ) );
		dailyReportPanel.add( reportTitle );
		reportTitle.setBounds( 100, 120 + 16*dailyReportTotalsInfo.getData().length + 18, 200, 20 );
	}

	private void displayDailyReportTotalsTable()
	{
		dailyReportTotalsTable = new JTable(dailyReportTotalsInfo.getData(), dailyReportTotalsInfo.getHeaders());
		dailyReportTotalsTablePanel.removeAll();
		dailyReportTotalsTablePanel.add(dailyReportTotalsTable.getTableHeader(), BorderLayout.NORTH);
		dailyReportTotalsTablePanel.add(dailyReportTotalsTable, BorderLayout.CENTER);
		dailyReportTotalsTablePanel.setBounds( 5, 100, 1000, 16*dailyReportTotalsInfo.getData().length + 18 );
		dailyReportTotalsTablePanel.revalidate();
		dailyReportTotalsTablePanel.repaint();

		if ( reportTotalTitle != null )
		{
			dailyReportPanel.remove( reportTotalTitle );
		}
		reportTotalTitle = new JLabel( "Report Totals:" );
		reportTotalTitle.setFont( new Font( "serif", Font.BOLD, 14 ) );
		dailyReportPanel.add( reportTotalTitle );
		reportTotalTitle.setBounds( 100, 80, 200, 20 );
	}

	private void fillItemOrderPanel()
	{
		itemOrderPanel.setLayout( null );

		int rowHeightOrder = 80;

		// ==========================================================================

		// Create element for entering the year
		List<Integer> yearList = new ArrayList<Integer>();
		int currentYear = LocalDate.now().getYear();
		for ( int i = 0; i < currentYear - START_YEAR + 1; i++ )
		{
			yearList.add( currentYear - i );
		}
		final Object[] yearArray = yearList.toArray();

		final JComboBox deliverYearDropdown = new JComboBox();

		for (int i = 0; i < yearArray.length; i++)
		{
			deliverYearDropdown.addItem( (Integer)yearArray[i] );
		}
		deliverYearDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deliveryYear = (Integer)deliverYearDropdown.getSelectedItem();
			}
		});
		deliveryYear = currentYear;

		itemOrderPanel.add(deliverYearDropdown);
		deliverYearDropdown.setBounds( 100, rowHeightOrder, 60, 18 );
		deliverYearDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the month
		List<Integer> monthList = new ArrayList<Integer>();
		for ( int i = 0; i < 12; i++ )
		{
			monthList.add( i + 1 );
		}
		final Object[] monthArray = monthList.toArray();

		final JComboBox deliveryMonthDropdown = new JComboBox();

		for (int i = 0; i < monthArray.length; i++)
		{
			deliveryMonthDropdown.addItem( (Integer)monthArray[i] );
		}
		deliveryMonthDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deliveryMonth = (Integer)deliveryMonthDropdown.getSelectedItem();
			}
		});
		deliveryMonth = 1;

		itemOrderPanel.add(deliveryMonthDropdown);
		deliveryMonthDropdown.setBounds( 170, rowHeightOrder, 50, 18 );
		deliveryMonthDropdown.setMaximumRowCount(12);

		// ==========================================================================

		// Create element for entering the day
		List<Integer> dayList = new ArrayList<Integer>();
		for ( int i = 0; i < 31; i++ )
		{
			dayList.add( i + 1 );
		}
		final Object[] dayArray = dayList.toArray();

		final JComboBox deliveryDayDropdown = new JComboBox();

		for (int i = 0; i < dayArray.length; i++)
		{
			deliveryDayDropdown.addItem( (Integer)dayArray[i] );
		}
		deliveryDayDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deliveryDay = (Integer)deliveryDayDropdown.getSelectedItem();
			}
		});
		deliveryDay = 1;

		itemOrderPanel.add(deliveryDayDropdown);
		deliveryDayDropdown.setBounds( 230, rowHeightOrder, 50, 18 );
		deliveryDayDropdown.setMaximumRowCount(12);

		// ==========================================================================

		JLabel dateLabel = new JLabel( "Enter the date:" );
		dateLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		itemOrderPanel.add( dateLabel );
		dateLabel.setBounds( 100, rowHeightOrder - 25, 200, 20 );

		final ManagerPanel managerPanel = this;
		final Report report = new Report();

		JButton setDeliveryDateButton = new JButton();
		setDeliveryDateButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				String year = String.valueOf( deliveryYear );
				String month = String.valueOf( deliveryMonth );
				String date = String.valueOf( deliveryDay );
				String dateString = year + "-" + month + "-" + date;
				System.out.println(dateString);

				Date sqlDate = Date.valueOf( dateString );

				// TODO: Implement setting delivery date for a certain order
			}

		});
		
		JLabel receiptIdLabel = new JLabel( "Enter the receipt Id:" );
		receiptIdLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		itemOrderPanel.add( receiptIdLabel );
		receiptIdLabel.setBounds( 320, rowHeightOrder - 25, 200, 20 );

		itemOrderPanel.add(setDeliveryDateButton);
		setDeliveryDateButton.setText( "Deliver Order" );
		setDeliveryDateButton.setBounds( 470, rowHeightOrder, 120, 18 );
		
		// Process order delivery
		final JTextField receiptIdInput = new JTextField();
		receiptIdInput.setBounds( 320, rowHeightOrder, 100, 20 );
		
		// Heading Text
		JLabel orderProcessLabel = new JLabel( "Order Processing" );
		orderProcessLabel.setFont( new Font( "serif", Font.BOLD, 24 ) );
		orderProcessLabel.setBounds( 25, 15, 800, 40 );
		itemOrderPanel.add( orderProcessLabel );

		itemOrderPanel.add( receiptIdInput );
		
		JSeparator separator = new JSeparator( JSeparator.HORIZONTAL );
		itemOrderPanel.add( separator );
		separator.setBounds( 60, 150, 1000, 1 );
		
		// ==================================================================================================================
		// Heading Text
		JLabel itemProcessLabel = new JLabel( "Item Processing" );
		itemProcessLabel.setFont( new Font( "serif", Font.BOLD, 24 ) );
		itemProcessLabel.setBounds( 25, 165, 800, 40 );
		itemOrderPanel.add( itemProcessLabel );
		
		int rowHeightItem = 230;
		
		// Process order delivery
		final JTextField itemUpcInput = new JTextField();
		itemUpcInput.setBounds( 100, rowHeightItem, 100, 20 );

		final JTextField itemQuantityInput = new JTextField();
		itemQuantityInput.setBounds( 210, rowHeightItem, 100, 20 );

		final JTextField itemPriceInput = new JTextField();
		itemPriceInput.setBounds( 320, rowHeightItem, 100, 20 );
		
		itemOrderPanel.add( itemUpcInput );
		itemOrderPanel.add( itemQuantityInput );
		itemOrderPanel.add( itemPriceInput );
		
		JLabel itemUpcLabel = new JLabel( "UPC:" );
		itemUpcLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		itemOrderPanel.add( itemUpcLabel );
		itemUpcLabel.setBounds( 100, rowHeightItem - 25, 200, 20 );
		
		JLabel itemQuantityLabel = new JLabel( "Quantity:" );
		itemQuantityLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		itemOrderPanel.add( itemQuantityLabel );
		itemQuantityLabel.setBounds( 210, rowHeightItem - 25, 200, 20 );
		
		JLabel itemPriceLabel = new JLabel( "Price:" );
		itemPriceLabel.setFont( new Font( "serif", Font.BOLD, 14 ) );
		itemOrderPanel.add( itemPriceLabel );
		itemPriceLabel.setBounds( 320, rowHeightItem - 25, 200, 20 );
		
		JButton updateItemButton = new JButton();
		updateItemButton.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) 
			{
				String upcString = itemUpcInput.getText();
				String quantityString = itemQuantityInput.getText();
				String priceString = itemPriceInput.getText();
				
				int upc = Integer.parseInt( upcString );
				int quantity = Integer.parseInt( quantityString );
				double price;
				
				if ( priceString.equals( "" ) )
				{
					price = 0.0;
				}
				else
				{
					price = Double.parseDouble( priceString );
				}

				// TODO: Implement updating item?
				Item item = new Item();
				
			}

		});
		
		itemOrderPanel.add(updateItemButton);
		updateItemButton.setText( "Add Item" );
		updateItemButton.setBounds( 470, rowHeightItem, 120, 18 );

	}

}
