package com.cnebrera.uc3.tech.lesson3.config;

import com.cnebrera.uc3.tech.lesson3.util.AeronUtil;

import org.agrona.concurrent.IdleStrategy;

public class AeronConfiguration
{
    public static final String CHANNEL_PROP = "aeron.sample.channel";
    public static final String STREAM_ID_PROP = "aeron.sample.streamId";
    public static final String IDLE_STRATEGY_PROP = "aeron.sample.idleStrategy";

    public static final String CHANNEL;
    public static final String IDLE_STRATEGY_NAME;
    public static final int STREAM_ID;
    

    static
    {

        //multicast
        //CHANNEL = System.getProperty(CHANNEL_PROP, "aeron:udp?endpoint=224.0.1.1:40456||interface=192.168.1.0/24");
        //unicast
        //CHANNEL = System.getProperty(CHANNEL_PROP, "aeron:udp?endpoint=224.0.1.1:40456||interface=192.168.1.1");
        //ipc
        CHANNEL = System.getProperty(CHANNEL_PROP, "aeron:ipc?endpoint=224.0.1.1:40456");

        STREAM_ID = Integer.getInteger(STREAM_ID_PROP, 10);
        IDLE_STRATEGY_NAME = System.getProperty(IDLE_STRATEGY_PROP, "org.agrona.concurrent.BusySpinIdleStrategy");
    }

    public static IdleStrategy newIdleStrategy()
    {
        return AeronUtil.newIdleStrategy(IDLE_STRATEGY_NAME);
    }
}
