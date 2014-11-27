package com.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.engine.Engine;

public class MainInterface extends JFrame
{
	// Dimensions of the window
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 1000;
	
	// The panel where everything is placed onto
	private JPanel basePanel;
	
	// The label that displays the title of our application
	private JLabel titleLabel;
	
	// The tabs that allow switching between customer, clerk and manager sections
	private JTabbedPane tabs;
	
	// Panels for each tab
	private JPanel customerPanel;
	private JScrollPane customerScrollPane;
	private JPanel clerkPanel;
	private JScrollPane clerkScrollPane;
	private JPanel managerPanel;
	private JScrollPane managerScrollPane;
	
	private JPanel dbConnectPanel;
	private JLabel loginLabel;
	
	public MainInterface()
	{
		basePanel = new JPanel();
		titleLabel = new JLabel( "AMS Store Database System" );
		titleLabel.setFont( new Font( "serif", Font.ITALIC + Font.BOLD, 30 ) );
		
		basePanel.setLayout(null);
		
		titleLabel.setBounds( 20, 0, 500, 50);
		basePanel.add( titleLabel );
		this.add( basePanel );

		setSize( MainInterface.WIDTH, MainInterface.HEIGHT );
		
		// Initialize the tabs
		initializeTabs();		
		basePanel.add( tabs );
		tabs.setBounds( 0, 50, MainInterface.WIDTH - 5, MainInterface.HEIGHT - 80 );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable( false );
		
		setTabsVisible( false );
		
		setVisible( true );
		
		initializeDatabaseConnect();
	}
	
	private void initializeDatabaseConnect()
	{
		dbConnectPanel = new JPanel();
		dbConnectPanel.setBounds( 0, 0, MainInterface.WIDTH, MainInterface.HEIGHT );
		dbConnectPanel.setVisible( true );
		dbConnectPanel.setLayout( null );
		
		loginLabel = new JLabel();
		
		JLabel loginText = new JLabel( "Please enter following information to connect to your MySQL database" );
		loginText.setFont( new Font( "serif", Font.BOLD, 25 ) );

		JLabel URLText = new JLabel( "Database URL" );
		JLabel userText = new JLabel( "Username" );
		JLabel passwordText = new JLabel( "Password" );
		
		int height = 300;
		
		loginText.setBounds( 200, height, 800, 30 );
		URLText.setBounds( 270, height + 30, 500, 20 );
		userText.setBounds( 270, height + 80, 500, 20 );
		passwordText.setBounds( 270, height + 120, 500, 20 );
		
		final JTextField dbURL = new JTextField( "jdbc:mysql://localhost:3306/cpsc304" );
		final JTextField user = new JTextField( "root" );
		final JTextField password = new JTextField();
		
		dbURL.setBounds( 270, height + 50, 500, 20 );
		user.setBounds( 270, height + 100, 500, 20 );
		password.setBounds( 270, height + 140, 500, 20 );

		JButton logonButton = new JButton();
		logonButton.addActionListener( new ActionListener()
		{

			public void actionPerformed(ActionEvent e) 
			{
				String connectURL = dbURL.getText();
				String connectUser = user.getText();
				String connectPassword = password.getText();
				
				try
				{
					Engine.createFirstInstance( connectURL, connectUser, connectPassword );
					dbConnectPanel.setVisible( false );
					setTabsVisible( true );
				}
				catch ( SQLException ex )
				{
					JOptionPane.showMessageDialog(managerPanel, 
							"The specified URL or credentials are invalid.", "Database Connect Error",
							JOptionPane.ERROR_MESSAGE);
				}
				catch ( Exception ex2 )
				{
					// This should never happen
					System.exit( -1 );
				}
			}
			
		});
		
		logonButton.setText( "Connect!" );
		logonButton.setBounds( 400, height + 170, 200, 20 );
		
		dbConnectPanel.add( dbURL );
		dbConnectPanel.add( user );
		dbConnectPanel.add( password );
		dbConnectPanel.add( loginText );
		dbConnectPanel.add( URLText );
		dbConnectPanel.add( userText );
		dbConnectPanel.add( passwordText );
		dbConnectPanel.add( logonButton );
		basePanel.add( dbConnectPanel );
		
	}
	
	private void setTabsVisible( boolean b )
	{
		
//		managerPanel.setVisible( b );
//		managerScrollPane.setVisible( b );
//		clerkPanel.setVisible( b );
//		clerkScrollPane.setVisible( b );
//		customerPanel.setVisible( b );
//		customerScrollPane.setVisible( b );

		tabs.setVisible( b );

	}
	
	private void initializeTabs()
	{
		tabs = new JTabbedPane();
		
		customerPanel = new CustomerPanel();
		customerScrollPane = new JScrollPane();
//	    customerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//	    customerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    customerScrollPane.setViewportView( customerPanel );
	    customerScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Customer", customerScrollPane );

		clerkPanel = new ClerkPanel();
		clerkScrollPane = new JScrollPane();
//		clerkScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		clerkScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		clerkScrollPane.setViewportView( clerkPanel );
	    clerkScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Clerk", clerkScrollPane );
		
		managerPanel = new ManagerPanel();
		managerScrollPane = new JScrollPane();
//		managerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		managerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		managerScrollPane.setViewportView( managerPanel );
//	    managerScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Manager", managerScrollPane );
		
	}

	
}
