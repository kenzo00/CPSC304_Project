package com.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManagerPanel extends JPanel
{
	
	public ManagerPanel()
	{
		// TODO: Add gui for adding items to store
		// TODO: add gui for process deliveries
		// TODO: add gui for displaying sales report
		// TODO: add gui for displaying top selling items
		this.setLayout(null);
		String[] names = {"Bryan","Lee"};
		Object[][] data = {{"something",new Integer(3)},{"probelm?",new Integer(4)},{"lololol?",new Integer(6)}};
		JTable table = new JTable(data,names);
		JLabel tablePanel = new JLabel();
		tablePanel.setLayout( new BorderLayout() );
		this.add(tablePanel);
		tablePanel.setBounds( 0, 0, 500, 16*data.length + 18 );
		tablePanel.add( table.getTableHeader(), BorderLayout.NORTH );
		tablePanel.add( table, BorderLayout.CENTER );
		System.out.println(data.length);
		
		JTextField textInput = new JTextField(50);
		this.add( textInput );
		textInput.setBounds( 0, tablePanel.getHeight(), 50, 16 );
		
	}

}
