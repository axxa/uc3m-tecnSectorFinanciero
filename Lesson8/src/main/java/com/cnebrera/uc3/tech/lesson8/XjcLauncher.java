package com.cnebrera.uc3.tech.lesson8 ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cnebrera.uc3.tech.lesson8.util.Constants;

/**
 * Launcher class - XJC Launcher
 * --------------------------------------
 * @author Francisco Manuel Benitez Chico
 * --------------------------------------
 */
public class XjcLauncher
{
	/**
	 * Generate Classes from XSD
	 * @throws IOException with an occurred exception
	 * @throws InterruptedException 
	 */
	private void generateClassesFromXsd()
	{
		try
		{
			// TODO 1
			final Process process = Runtime.getRuntime().exec("xjc -d " + Constants.FOLDER_OUTPUT + " -p " +
			Constants.PACKAGE_NAME_OUTPUT + " " + Constants.XSD_FILE_INPUT) ;
			// TODO 2
			process.waitFor() ;
			// TODO 3
			this.printOutput(process) ;
		}
	    catch (IOException ioException)
		{
	    	ioException.printStackTrace() ;	
		}
		catch (InterruptedException interruptedExc)
		{
			interruptedExc.printStackTrace() ;
		}
	}
	
	/**
	 * @param process with the process
	 * @throws IOException with an occurred exception
	 */
	private void printOutput(final Process process) throws IOException
	{
		final StringBuffer output   = new StringBuffer() ;
		final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())) ;

		String line 				= reader.readLine() ;

		while (line != null)
		{
			output.append(line + "\n") ;
			
			line = reader.readLine() ;
		}
		
		System.out.println(output.toString()) ;
	}

	/**
	 * @param args with the input arguments
	 */
	public static void main(final String[] args)
	{
		final XjcLauncher xjcLauncher = new XjcLauncher() ;
		
		xjcLauncher.generateClassesFromXsd() ;
	}
}
