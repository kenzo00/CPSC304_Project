package com.tests;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import com.gui.MainInterface;

public class GuiTest {

	@Test
	public void displayTest() 
	{
		MainInterface gui = new MainInterface();
		
		// This is here so the gui doesn't disappear right away
		Scanner in = new Scanner( System.in );
		String something = in.nextLine();
	}

}
