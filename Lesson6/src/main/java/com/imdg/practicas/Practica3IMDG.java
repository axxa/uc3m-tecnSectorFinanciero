package com.imdg.practicas;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.imdg.datagrid.DataGridNode;
import com.imdg.pojos.Person;

import java.util.ArrayList;

public class Practica3IMDG {

    public static void main(String[] args) {
        // Instanciar hazelcast Cliente y crear una cache
        ClientConfig config = new ClientConfig();
        ArrayList<String> ips=new ArrayList();
        ips.add("127.0.0.1");
        config.getNetworkConfig().setAddresses(ips);

        HazelcastInstance client = HazelcastClient.newHazelcastClient( config );




        
        //Vuestro código va aqui
        DataGridNode node = new DataGridNode();
        node.printCache();
        Person p = new Person("Alvaro", 28005, "", "");
        node.addToCache(p);
        node.printCache();



        client.shutdown();

    }
}
