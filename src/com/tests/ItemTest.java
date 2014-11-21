package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Item;

public class ItemTest {

	@Before
	public void setUp()
	{
		String item1 = "(12345, 'banana', ' yellow ', ' fruit ', 'california', 2014, 1.99, 100)";
		String item2 = "(15555, ' tomato ', ' red ', ' vegetable ', 'okanagon', 2014, 2.99, 55)";
		Engine.getInstance().getQueries().insertQuery( "Item", item1 );
		Engine.getInstance().getQueries().insertQuery( "Item", item2 );
		System.out.println("Done setup");
	}
	
	@After
	public void cleanUp()
	{
		Engine.getInstance().getQueries().deleteQuery( "Item" );
	}

	@Test
	public void itemExistTest() 
	{
		Item item = new Item();
		
		assertTrue( item.itemExist(12345) );
		System.out.println(38);
		assertTrue( item.itemExist(15555) );
		System.out.println(40);
		assertFalse( item.itemExist(5) );
		System.out.println(42);
	}
	
	@Test
	public void addItemTest(){
		Item item = new Item();
		assertFalse( item.itemExist(55555) );
		System.out.println("addItemTest1");
		item.addItem(55555,"ball","red","category","company",2011,10.00,7);
		assertTrue( item.itemExist(55555));
		System.out.println("addItemTest2");
	}
	
	public void updateItemTest() {
		
		System.out.println();
	}


}
