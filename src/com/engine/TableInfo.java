package com.engine;

public class TableInfo 
{
	
	private String[] headers;
	private String[][] data;
	private boolean isEmpty;
	
	public TableInfo( String[] headers, String[][] data )
	{
		this.headers = headers;
		this.data = data;
		isEmpty = false;
	}
	
	public TableInfo()
	{
		isEmpty = true;
	}
	
	public String[] getHeaders()
	{
		return headers;
	}
	
	public String[][] getData()
	{
		return data;
	}

	public boolean isEmpty()
	{
		return isEmpty;
	}
	
}
