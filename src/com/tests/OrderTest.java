package com.tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Item;
import com.engine.Order;

public class OrderTest 
{
	String fakeCard = "9888888888888889";
	LocalDate currentDate2 = LocalDate.now();
	Date fakeDate = Date.valueOf(currentDate2);
	
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
		o.login(6969, "123456");
		o.purchaseItem(1, 30);
		o.purchaseItem(1, 21);
		o.purchaseItem(2, 10);
		o.checkOut(fakeCard, fakeDate);
		
		
		
	}

}
