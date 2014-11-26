package com.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
	private JPanel clerkPanel;
	private JPanel managerPanel;
	
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
		
		setVisible( true );
	}
	
	private void initializeTabs()
	{
		tabs = new JTabbedPane();
		
		customerPanel = new CustomerPanel();
		JScrollPane customerScrollPane = new JScrollPane();
//	    customerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//	    customerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    customerScrollPane.setViewportView( customerPanel );
	    customerScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Customer", customerScrollPane );

		clerkPanel = new ClerkPanel();
		JScrollPane clerkScrollPane = new JScrollPane();
//		clerkScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		clerkScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		clerkScrollPane.setViewportView( clerkPanel );
	    clerkScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Clerk", clerkScrollPane );
		
		managerPanel = new ManagerPanel();
		JScrollPane managerScrollPane = new JScrollPane();
//		managerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		managerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		managerScrollPane.setViewportView( managerPanel );
//	    managerScrollPane.getVerticalScrollBar().setUnitIncrement( 16 );
		tabs.addTab( "Manager", managerScrollPane );
		
	}

	
}
