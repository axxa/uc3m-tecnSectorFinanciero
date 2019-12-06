package com.cnebrera.uc3.tech.lesson9.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "instrument"
})
@XmlRootElement(name = "referenceData")
public class ReferenceData {

    /** a int that identifies the market */
    @XmlAttribute
    private int marketId;

    /** a {@link java.lang.String} with the algorithm identifier */
    @XmlAttribute
    private String algorithmIdentifier;

    /** a List with the Instruments information **/
    private List<Instrument> instrument;

    
    public int getMarketId()
    {
        return marketId;
    }

    public void setMarketId(int marketId)
    {
        this.marketId = marketId;
    }

    public String getAlgorithmIdentifier()
    {
        return algorithmIdentifier;
    }

    public void setAlgorithmIdentifier(String algorithmIdentifier)
    {
        this.algorithmIdentifier = algorithmIdentifier;
    }

    public List<Instrument> getListOfInstruments()
    {
        return instrument;
    }

    public void setListOfInstruments(List<Instrument> listOfInstruments)
    {
        this.instrument = listOfInstruments;
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

        ReferenceData that = (ReferenceData) o;

        if (marketId != that.marketId)
        {
            return false;
        }
        if (algorithmIdentifier != null ? !algorithmIdentifier.equals(that.algorithmIdentifier) : that.algorithmIdentifier != null)
        {
            return false;
        }
        return instrument != null ? instrument.equals(that.instrument) : that.instrument == null;

    }

    @Override
    public int hashCode()
    {
        int result = marketId;
        result = 31 * result + (algorithmIdentifier != null ? algorithmIdentifier.hashCode() : 0);
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        return result;
    }
}
