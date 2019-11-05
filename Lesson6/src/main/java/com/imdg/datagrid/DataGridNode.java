package com.imdg.datagrid;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;

public class DataGridNode {
    private HazelcastInstance hzInstance;

    /*
    * To create multiple nodes we can start the multiple instances of ServerNode application. 
    * Hazelcast will automatically create and add a new member to the cluster.
    * 
        Members [2] {
            Member [192.168.1.105]:5701 - 899898be-b8aa-49aa-8d28-40917ccba56c
            Member [192.168.1.105]:5702 - d6b81800-2c78-4055-8a5f-7f5b65d49f30 this
        }
    */
    public DataGridNode() {
        this.hzInstance = Hazelcast.newHazelcastInstance();
    }

    /**
     * @return the hzInstance
     */
    public HazelcastInstance getHzInstance() {
        return hzInstance;
    }

    public void mockPopulateCache(){
        IMap<Long, String> cacheNode = this.hzInstance.getMap("data");
        IdGenerator idGenerator = this.hzInstance.getIdGenerator("newid");
        for (int i = 0; i < 10; i++) {
            cacheNode.put(idGenerator.newId(), "message" + i);
        }
    }
}