package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Item;
import com.engine.Order;

public class OrderTest 
{
	@Before
	public void setUp()
	{
		
		
	}
	
	@After
	public void cleanUp()
	{
		
	}

	
	@Test
	public void orderTest()
	{
		Order o = new Order(); 
		o.onlineOrder(1, "BFX85QZH3CZ");
		
		
		
	}

}
