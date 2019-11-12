package com.imdg.practicas;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import com.imdg.pojos.Person;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Practica3IMDG {

    public static void main(String[] args) {
        // Instanciar hazelcast Cliente y crear una cache
        ClientConfig config = new ClientConfig();
        ArrayList<String> ips=new ArrayList();
        ips.add("127.0.0.1");
        config.getNetworkConfig().setAddresses(ips);

        HazelcastInstance client = HazelcastClient.newHazelcastClient( config );
        //print cache
        printClientCache(client);
        //Vuestro código va aqui
        IMap<Long, Object> cacheNode = client.getMap("data");
        Person p = new Person("Alvaro", 28005, "", "");
        //put to cache
        IdGenerator idGenerator = client.getIdGenerator("newid");
        cacheNode.put(idGenerator.newId(), p);
        //print cache
        printClientCache(client);

        client.shutdown();

    }

    private static void printClientCache(HazelcastInstance client){
        System.out.println( "printCache\n" );
        IMap<Long, Person> map = client.getMap("data");
        for (Entry<Long, Person> entry : map.entrySet()) {
            System.out.println("Entry key: "+ entry.getKey() + " Person name: " + entry.getValue().getName() + 
            " zipCode: " + entry.getValue().getZipCode());
        }
    }
}
