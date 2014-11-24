package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Item;
import com.engine.Order;
import com.engine.PurchaseItem;

public class PurchaseItemTest 
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
	public void getQuantityTest()
	{
		PurchaseItem pi = new PurchaseItem();
		int result = pi.getQuantity(1, 1);
		System.out.println(result);
		
		
		
		
	}

}
