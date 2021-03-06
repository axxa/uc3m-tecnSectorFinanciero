package com.cnebrera.uc3.tech.lesson3.app;

import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.CHANNEL;
import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.STREAM_ID;

import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;

public class Subscriber
{
    
    public static void main(final String[] args)
    {
        final int fragmentLimitCount = 7;
        
        System.out.println("Subscribing to " + CHANNEL + " on stream id " + STREAM_ID);
        final Aeron.Context ctx = new Aeron.Context();
        

        final FragmentHandler fragmentHandler =
            (buffer, offset, length, header) ->
            {
                final byte[] data = new byte[length];
                buffer.getBytes(offset, data);

                System.out.println(String.format(
                    "Received message (%s) to stream %d from session %x term id %x term offset %d (%d@%d)",
                    new String(data), STREAM_ID, header.sessionId(),
                    header.termId(), header.termOffset(), length, offset));
            };

    
        Aeron connection = Aeron.connect(ctx);
        Subscription subscription = connection.addSubscription(CHANNEL, STREAM_ID);

        Thread pollerThread = new Thread(() ->
        {
            
            IdleStrategy idleStrategy = new BusySpinIdleStrategy();
            while (true)
            {
                int result = subscription.poll(fragmentHandler, fragmentLimitCount);
                idleStrategy.idle(result);
            
            }
            
        });

        pollerThread.start();
               
    }

 

}
