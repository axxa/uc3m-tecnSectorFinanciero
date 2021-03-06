package com.imdg.listeners;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.imdg.pojos.MarketOrder;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Sobremesa on 31/10/2016.
 */
public class VolumeListener
            implements EntryAddedListener<String, MarketOrder>,
        EntryUpdatedListener<String, MarketOrder>, Serializable {
         
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String instrumentoAControlar;
    private int volumenAcumulado=0;
    private HashMap<String, MarketOrder> orderMap;
    
    public VolumeListener(String instrument) {
        this.instrumentoAControlar=instrument;
        this.orderMap = new HashMap<String, MarketOrder>();
    }

    /**
     * Escuchar entradas que se añaden y sumarlo al volumen/imprimir alerta si llegamos a 30000
     * @param entryEvent
     */
    @Override
    public void entryAdded(EntryEvent<String, MarketOrder> entryEvent) {
        MarketOrder order = entryEvent.getValue();
        
        if (order.getInstrument().equals(this.instrumentoAControlar)) {
            this.volumenAcumulado += order.getVolume();
        }

        verifyOrderVolumn();
    }
    /**
     * Escuchar entradas que se añaden, restar valor antiguo y
     * sumar el nuevo al volumen/imprimir alerta si llegamos a 30000
     * @param entryEvent
     */
    @Override
    public void entryUpdated(EntryEvent<String, MarketOrder> entryEvent) {
        MarketOrder order = entryEvent.getValue();

        if (order.getInstrument().equals(this.instrumentoAControlar)) {
            this.volumenAcumulado += (order.getVolume() - entryEvent.getOldValue().getVolume());
        }

        verifyOrderVolumn();
    }

    private void verifyOrderVolumn(){
        if (this.volumenAcumulado > 30000){
            System.out.println("\nAlerta: 30000 unidades acumuladas\n");
            this.volumenAcumulado = 0;
        }
    }
}
