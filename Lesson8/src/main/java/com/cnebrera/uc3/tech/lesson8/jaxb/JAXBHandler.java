package com.cnebrera.uc3.tech.lesson8.jaxb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.cnebrera.uc3.tech.lesson8.util.Constants;
import com.cnebrera.uc3.tech.lesson8.xjc.StudentLessons;

/**
 * JAXB Handler
 * --------------------------------------
 * @author Francisco Manuel Benitez Chico
 * --------------------------------------
 */
public class JAXBHandler
{
	/** Attribute - JAXB Context */
	private final JAXBContext jaxbContext ;
	
	/**
	 * Initialize the JAXB Context
	 * @throws JAXBException with an occurred exception
	 */
	public JAXBHandler() throws JAXBException
	{
		// TODO 1
		this.jaxbContext = JAXBContext.newInstance(StudentLessons.class);

	}
	
	/**
	 * @param file 		 	 with the file
	 * @return a new instance of StudentLessons with the filled values from the XML
	 * @throws JAXBException with an occurred exception
	 * @throws SAXException  with an occurred exception
	 */
	public StudentLessons convertToObject(final File file) throws JAXBException, SAXException
	{
		// TODO 2
		final Unmarshaller jaxbUnmarshaller = this.jaxbContext.createUnmarshaller() ;
		// TODO 3
		// Set validator
		final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI) ;
		final Schema schema = sf.newSchema(new File(Constants.XSD_FILE_INPUT)) ;
		jaxbUnmarshaller.setSchema(schema) ; 
		// TODO 4
		// Unmarshal the content of the file
		return (StudentLessons) jaxbUnmarshaller.unmarshal(file) ;
	}
	
	/**
	 * @param studentLessons with the instance of StudentLessons
	 * @return a string with the XML content
	 * @throws JAXBException with an occurred exception
	 */
	public String convertToXml(final StudentLessons studentLessons) throws JAXBException
	{
		// TODO 5
		final Marshaller jaxbMarshaller = this.jaxbContext.createMarshaller() ;
		// TODO 6
		// Output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true) ;
		// TODO 7
		// Output Stream
		final OutputStream outputStream = new ByteArrayOutputStream() ;
		// TODO 8
		// Marshal the StudentLessons object
		jaxbMarshaller.marshal(studentLessons, outputStream) ;
		// TODO 9
		return outputStream.toString() ;
	}
	
	/**
	 * @param namespaceUri 		with the namespace URI
	 * @param suggestedFileName with the suggested file name
	 * @return the generated schema
	 * @throws IOException 		with an occurred exception
	 */
	public void generateSchema(final String namespaceUri, final String suggestedFileName) throws IOException
	{
		// TODO 10
		final SchemaOutputResolver schemaOutputResolver = new MySchemaOutputResolver();
		// TODO 11
		this.jaxbContext.generateSchema(schemaOutputResolver) ;
		// TODO 12
		schemaOutputResolver.createOutput(namespaceUri, suggestedFileName) ;
	}
}
