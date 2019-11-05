package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.imdg.datagrid.DataGridNode;
import com.imdg.pojos.Person;

import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

public class Practica1IMDG {

    public static void main(String[] args) {
	    // Instanciar hazelcast y crear una cache
        // Insertar un dato y arrancar 3 veces el main,
        // Leer el output de consola y ver como hazelcast va encontrando "miembros"
        // Comprobar que se conectan (en el output deberian verse 3 miembros en la consola) y capturarlo
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        
        DataGridNode node = new DataGridNode();
        //node.mockPopulateCache();

        IMap<Long, String> map = node.getHzInstance().getMap("data");
        for (Entry<Long, String> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
        }
    }
}
