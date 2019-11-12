package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.imdg.datagrid.DataGridNode;

public class Practica1IMDG {

    public static void main(String[] args) {
	    // Instanciar hazelcast y crear una cache
        // Insertar un dato y arrancar 3 veces el main,
        // Leer el output de consola y ver como hazelcast va encontrando "miembros"
        // Comprobar que se conectan (en el output deberian verse 3 miembros en la consola) y capturarlo
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        
        new DataGridNode();
        
    }
}
