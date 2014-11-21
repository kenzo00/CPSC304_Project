package com.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ManagerPanel extends JPanel
{
	
	public ManagerPanel()
	{
		this.setLayout(null);
		String[] names = {"Bryan","Lee"};
		Object[][] data = {{"something",new Integer(3)},{"probelm?",new Integer(4)},{"lololol?",new Integer(6)}};
		JTable table = new JTable(data,names);
		JLabel tablePanel = new JLabel();
		tablePanel.setLayout( new BorderLayout() );
		this.add(tablePanel);
		tablePanel.setBounds( 0, 0, 500, 200 );
		tablePanel.add( table.getTableHeader(), BorderLayout.NORTH );
		tablePanel.add( table, BorderLayout.CENTER );
		System.out.println(data.length);
		
	}

}
