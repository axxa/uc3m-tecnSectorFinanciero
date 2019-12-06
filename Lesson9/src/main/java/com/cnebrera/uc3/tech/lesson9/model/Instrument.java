package com.cnebrera.uc3.tech.lesson9.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the instrument info
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Instrument")
public class Instrument 
{
    /** a int that identifies the instrument */
    @XmlAttribute
    @JsonProperty(value = "instrument_id")
    private int instrumentId;

    /** human understood representation of the security */
    @XmlAttribute
    @JsonProperty(value = "symbol")
    private String symbol;

    
    public int getInstrumentId() 
    {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) 
    {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() 
    {
        return symbol;
    }

    public void setSymbol(String symbol) 
    {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Instrument that = (Instrument) o;

        if (instrumentId != that.instrumentId)
        {
            return false;
        }
        return symbol != null ? symbol.equals(that.symbol) : that.symbol == null;

    }

    @Override
    public int hashCode()
    {
        int result = instrumentId;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }
}
