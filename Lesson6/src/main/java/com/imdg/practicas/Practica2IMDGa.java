package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.ICountDownLatch;
import com.imdg.datagrid.DataGridNode;
import com.imdg.pojos.Person;

import java.util.concurrent.TimeUnit;

public class Practica2IMDGa {

    public static void main(String[] args) throws InterruptedException {
	    // Instanciar hazelcast y crear una cache
        // Insertar un dato y arrancar 3 veces el main,
        // Leer el output de consola y ver como hazelcast va encontrando "miembros"
        // Comprobar que se conectan (en el output deberian verse 3 miembros en la consola) y capturarlo
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        
        Person p = new Person("Alvaro", 28051, "", "");

        DataGridNode node = new DataGridNode();
        
        ICountDownLatch latch = node.getHzInstance().getCountDownLatch( "countDownLatch" );
        // tokens por numero de nodos
        latch.trySetCount(3);
        System.out.println( "Waiting" );
        //Process to be executed----------------------
        node.addToCache(p);
        //--------------------------------------------

        //-----Restar un token------------------------
        latch.countDown();
        System.out.println("Faltan " + latch.getCount() + " nodos");
        //--------------------------------------------

        latch.await( 100, TimeUnit.SECONDS );
        node.printCache();
        //-------------------------------------------
        latch.destroy();
    }
}
