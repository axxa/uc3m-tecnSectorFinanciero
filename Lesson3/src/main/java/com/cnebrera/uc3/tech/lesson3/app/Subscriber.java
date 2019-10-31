package com.cnebrera.uc3.tech.lesson3.app;

import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;

public class Subscriber
{
    
    public static void main(final String[] args)
    {
        // Maximum number of message fragments to receive during a single 'poll' operation
        final int fragmentLimitCount = 7;

        // The channel (an endpoint identifier) to receive messages from
        final String channel = "aeron:udp?endpoint=localhost:40123";

        // A unique identifier for a stream within a channel. Stream ID 0 is reserved
        // for internal use and should not be used by applications.
        final int streamId = 10;
        
        System.out.println("Subscribing to " + channel + " on stream id " + streamId);
        final Aeron.Context ctx = new Aeron.Context();
        

        final FragmentHandler fragmentHandler =
            (buffer, offset, length, header) ->
            {
                final byte[] data = new byte[length];
                buffer.getBytes(offset, data);

                System.out.println(String.format(
                    "Received message (%s) to stream %d from session %x term id %x term offset %d (%d@%d)",
                    new String(data), streamId, header.sessionId(),
                    header.termId(), header.termOffset(), length, offset));

                // Received the intended message, time to exit the program
                //running.set(false);
            };

    
        Aeron connection = Aeron.connect(ctx);
        Subscription subscription = connection.addSubscription(channel, streamId);

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
