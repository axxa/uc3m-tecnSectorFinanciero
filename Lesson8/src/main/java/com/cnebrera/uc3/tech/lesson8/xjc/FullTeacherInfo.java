//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.04 at 02:37:06 PM CET 
//


package com.cnebrera.uc3.tech.lesson8.xjc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fullTeacherInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fullTeacherInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{}teacherInfo">
 *       &lt;sequence>
 *         &lt;element name="teacherCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="teacherCountry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fullTeacherInfo", propOrder = {
    "teacherCity",
    "teacherCountry"
})
public class FullTeacherInfo
    extends TeacherInfo
{

    @XmlElement(required = true)
    protected String teacherCity;
    @XmlElement(required = true)
    protected String teacherCountry;

    /**
     * Gets the value of the teacherCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherCity() {
        return teacherCity;
    }

    /**
     * Sets the value of the teacherCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherCity(String value) {
        this.teacherCity = value;
    }

    /**
     * Gets the value of the teacherCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherCountry() {
        return teacherCountry;
    }

    /**
     * Sets the value of the teacherCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherCountry(String value) {
        this.teacherCountry = value;
    }

}
