package com.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.engine.Customer;
import com.engine.Engine;

public class CustomerTest 
{
	@Before
	public void setUp()
	{
		String insertValues1 = "(1,'IAMHOKAGE','Naruto Uzumaki','123 Konoha',6041234567)";
		String insertValues2 = "(2,'Pikapika?','Pikachu','4262 Pallet Town',682135752)";
		
		Engine.getInstance().getQueries().insertQuery( "Customer", insertValues1 );
		Engine.getInstance().getQueries().insertQuery( "Customer", insertValues2 );
	}
	
	@After
	public void cleanUp()
	{
		Engine.getInstance().getQueries().deleteQuery( "Customer" );
	}

	@Test
	public void isRegisteredTest() 
	{
		Customer customer = new Customer();
		
		assertTrue( customer.isRegistered( 1 ) );
		assertTrue( customer.isRegistered( 2 ) );
	}
	
	@Test
	public void registerTest()
	{
		Customer customer = new Customer();
		
		assertFalse( customer.isRegistered( 3 ) );
		
		customer.register( 3, "ONEPIECE", "Monkey D. Luffy", "East Blue", "8888888888" );
		
		assertTrue( customer.isRegistered( 3 ) );
	}

}
