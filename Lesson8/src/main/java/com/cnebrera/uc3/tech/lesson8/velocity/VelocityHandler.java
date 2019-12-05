package com.cnebrera.uc3.tech.lesson8.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.cnebrera.uc3.tech.lesson8.util.Constants;

/**
 * Velocity Handler
 * --------------------------------------
 * @author Francisco Manuel Benitez Chico
 * --------------------------------------
 */
public class VelocityHandler
{
	/**
	 * @param packageName with the package name
	 * @param className	  with the class name
	 * Generate the class from the velocity template
	 */
	public void generateClassFromVelocityTemplate(final String packageName, final String className)
	{
		// TODO 1
        // Initialize the Velocity engine
		final VelocityEngine velocityEngine = new VelocityEngine() ;
		//final VelocityEngine velocityEngine = initializeVelocityEngine() ;
		// TODO 2
		// Get the template
		final Template template = velocityEngine.getTemplate(Constants.VELOCITY_TEMPLATE_NAME) ;
		// TODO 3
        // Set the context parameters
		final VelocityContext context = this.setContextParameters(packageName, className) ;
		// TODO 4
        // Render the template into a StringWriter
		final StringWriter writer = new StringWriter() ;
		template.merge(context, writer) ;
		// TODO 5
		// Store the outcome
		this.storeFileInProject(packageName, className, writer) ;
	}
	
	/**
	 * @return a new instance of the velocity engine
	 */
	private VelocityEngine initializeVelocityEngine()
	{
		final VelocityEngine velocityEngine = new VelocityEngine() ;

		// TODO 6
        velocityEngine.init() ;
        return velocityEngine ;
	}
	
	/**
	 * @param packageName with the package name
	 * @param className	  with the class name
	 * @return the velocity context
	 */
	private VelocityContext setContextParameters(final String packageName, final String className)
	{
		final VelocityContext context = new VelocityContext() ;
		
		// TODO 7
        // Store the outcome
		context.put("now", new Date()) ;
		context.put("packageName", packageName) ;
		context.put("className", className) ;
		context.put("classImports", this.generateClassImports()) ;
		context.put("stringValues", this.generateStringValues()) ;
        return context ;
	}
	
	/**
	 * @return the class imports
	 */
	private List<String> generateClassImports()
	{
		final List<String> classImports = new ArrayList<String>() ;
		
		// TODO 8
        classImports.add("java.util.List") ;
		classImports.add("java.util.ArrayList") ;
        return classImports ;
	}
	
	/**
	 * @return the string values
	 */
	private List<String> generateStringValues()
	{
        final List<String> stringValues = new ArrayList<String>() ;
        
		// TODO 9
        // Store the outcome
		stringValues.add("Hello") ;
		stringValues.add("World!") ;
        return stringValues ;
	}
	
	/**
	 * @param packageName with the package name
	 * @param className	  with the class name
	 * @param writer 	  with the writer
	 */
	private void storeFileInProject(final String packageName, final String className, final StringWriter writer)
	{
		final File outputFile = new File(Constants.JAVA_FOLDER 					  + File.separator +
										 packageName.replace(".", File.separator) + File.separator +  
										 className + ".java") ;
		FileWriter fileWriter = null ;
				
		try
		{
			fileWriter = new FileWriter(outputFile) ;
			fileWriter.write(writer.toString());
		}
		catch (IOException ioException)
		{
			throw new RuntimeException("IOException while storing the generated file: " + ioException.getMessage()) ;
		}
		finally
		{
			if (fileWriter != null)
			{
				try
				{
					fileWriter.close() ;
				}
				catch (IOException ioException)
				{
					throw new RuntimeException("IOException while closing the generated file: " + ioException.getMessage()) ;
				}
			}
		}
	}
}
