package com.imdg.datagrid;

import java.util.Map.Entry;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import com.imdg.pojos.Person;

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

    public void addToCache(Object o){
        IMap<Long, Object> cacheNode = this.hzInstance.getMap("data");
        IdGenerator idGenerator = this.hzInstance.getIdGenerator("newid");
        cacheNode.put(idGenerator.newId(), o);
    }

    public void printCache(){
        System.out.println( "printCache\n" );
        IMap<Long, Person> map = hzInstance.getMap("data");
        for (Entry<Long, Person> entry : map.entrySet()) {
            System.out.println("Entry key: "+ entry.getKey() + " Person name: " + entry.getValue().getName() + " zipCode: " + entry.getValue().getZipCode());
        }
    }
}