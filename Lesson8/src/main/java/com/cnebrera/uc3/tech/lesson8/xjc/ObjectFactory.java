//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.04 at 02:37:06 PM CET 
//


package com.cnebrera.uc3.tech.lesson8.xjc;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnebrera.uc3.tech.lesson8.xjc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnebrera.uc3.tech.lesson8.xjc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StudentLessons }
     * 
     */
    public StudentLessons createStudentLessons() {
        return new StudentLessons();
    }

    /**
     * Create an instance of {@link Lesson }
     * 
     */
    public Lesson createLesson() {
        return new Lesson();
    }

    /**
     * Create an instance of {@link FullTeacherInfo }
     * 
     */
    public FullTeacherInfo createFullTeacherInfo() {
        return new FullTeacherInfo();
    }

    /**
     * Create an instance of {@link TeacherInfo }
     * 
     */
    public TeacherInfo createTeacherInfo() {
        return new TeacherInfo();
    }

}