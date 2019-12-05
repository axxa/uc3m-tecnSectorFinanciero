/*********************************************************************
This class has been automatically generated using Velocity!
Thu Dec 05 16:45:23 CET 2019
**********************************************************************/


package com.cnebrera.uc3.tech.lesson8.velocity ;

import java.util.List ;
import java.util.ArrayList ;


public class VelocityClassExample
{
   /** Attribute - myListStringValues */
   private List<String> myListStringValues ;
	
	/**
	 * Public Constructor
	 */ 
	// TODO 5
	{
		this.myListStringValues = new ArrayList<String>() ;
		
		// Set values from Velocity
		
// TODO 6
	}

	/**
	 * @return myListStringValues as string
	 */ 
	public String toString()
	{
		return myListStringValues.toString() ;
	}
	
	/**
	 * @param args with the input arguments
	 */ 
	public static void main(final String[] args)
	{
		final VelocityClassExample myExample = new VelocityClassExample() ;
		System.out.println(myExample.toString()) ;
	}
}
