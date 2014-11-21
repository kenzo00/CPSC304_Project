package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;
import com.engine.Return;

public class RefundTest 
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
	public void isRefundedTest() 
	{
		
	}
	
	@Test
	public void refundTest()
	{
		Return r = new Return(); 
		
		r.refund(9999, 888);
		
	}

}
