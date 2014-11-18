package com.engine;

import java.sql.ResultSet;

public interface Queries 
{

	/*
	 * Inserts 'values' into 'table' by executing a query of the form
	 * "INSERT INTO table VALUES values"
	 * 
	 * table is the name of the database table
	 * values are the values that will be inserted into the table
	 * 		values should be formatted like "(x,y,z)"
	 */
	public void insertQuery ( String table, String values );
	
	/*
	 * Deletes all rows from 'table'
	 * 
	 * table is the name of the database table
	 * 
	 * returns the number of rows deleted
	 */
	public int deleteQuery ( String table );
	
	/*
	 *  Deletes rows from 'table' that satisfies 'condition' by executing a query
	 *  of the form "DELETE FROM table WHERE condition"
	 * 
	 *  table is the name of the database table
	 *  condition is the deletion condition
	 *  	should be something like "id=50"
	 *  	if no condition is specified (that is, an empty string is passed for the condition)
	 *  		then all rows will be deleted from the table
	 *  
	 *  returns the number of rows deleted
	 */
	public int deleteQuery ( String table, String condition );
	
	/*
	 * Prints out all rows of a selected table to the console
	 */
	public void displayTable ( String table );
	
}
