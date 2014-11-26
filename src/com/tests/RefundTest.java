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
		/*String purchaseItem1 = "(123, 456, 2)";
		String purchaseItem2 = "(234, 567, 3)";
		
		String Order1 = "(4, '2015-06-13' , 2, 372296924250204,'2018-01-24','2014-08-26','2014-09-21')";
		
		Engine.getInstance().getQueries().insertQuery( "PurchaseItem", purchaseItem1 );
		Engine.getInstance().getQueries().insertQuery( "PurchaseItem", purchaseItem2 );
		Engine.getInstance().getQueries().insertQuery( "Order", Order1 );*/
		
	}
	
	@After
	public void cleanUp()
	{
		//Engine.getInstance().getQueries().deleteQuery( "PurchaseItem" );
	}

	@Test
	public void isRefundedTest() 
	{
		Return r = new Return();
		// some date tests
		//assertFalse(r.isValid(1) );
		//assertFalse(r.isValid(72) );
		//assertTrue(r.isValid(77) );	// should return true, but is not. check code logic
		
		// tests to see if UPC match
		//assertTrue(r.isCorrectUpc(1, 1));
		//assertFalse(r.isCorrectUpc(0, 1));
		
		// tests to see if refund is new 
		//assertTrue(r.newRefund(1));
	}
	
	@Test
	public void refundTest()
	{
		// need to test if returns are added to return table
		Return r = new Return(); 
		r.refund(66, 1); //truetruetrue
		r.refund(66, 2); // truetruetrue
		r.refund(66, 2); //truefalsetrue
		r.refund(821, 16); //truetruefalse
		r.refund(821, 2); //truetruetrue 
		r.refund(1, 2); //falsetruetrue
		r.refund(6, 3); //falsetruetrue
		r.refund(6, 30); //falsetruefalse
		
	}

}
