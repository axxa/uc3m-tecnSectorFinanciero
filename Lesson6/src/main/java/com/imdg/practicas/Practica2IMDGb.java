package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IMap;
import com.imdg.datagrid.DataGridNode;
import com.imdg.pojos.Person;

import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class Practica2IMDGb {

    public static void main(String[] args) throws InterruptedException {
	    // Instanciar hazelcast y crear una cache
        // Insertar un dato y arrancar 3 veces el main,
        // Leer el output de consola y ver como hazelcast va encontrando "miembros"
        // Comprobar que se conectan (en el output deberian verse 3 miembros en la consola) y capturarlo
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        
        DataGridNode node = new DataGridNode();
        //Follower node
        ICountDownLatch latch = node.getHzInstance().getCountDownLatch( "countDownLatch" );
        System.out.println( "Waiting" );
        boolean success = latch.await( 10, TimeUnit.SECONDS );
        System.out.println( "Complete: " + success );
        //Process to be executed----------------------
        Person p = new Person("Irene", 28052, "", "");
        node.addToCache(p);
        node.printCache();
        //-------------------------------------------
    }
}