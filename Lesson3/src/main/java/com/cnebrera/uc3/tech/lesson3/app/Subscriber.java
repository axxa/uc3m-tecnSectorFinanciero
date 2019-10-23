/*
 * Copyright 2015 Kaazing Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cnebrera.uc3.tech.lesson3.app;

import org.agrona.DirectBuffer;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import io.aeron.Aeron;
import io.aeron.FragmentAssembler;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;

/**
 * A very simple Aeron subscriber application which can receive small non-fragmented messages
 * on a fixed channel and stream ID. The DataHandler method 'printStringMessage' is called when data
 * is received. This application doesn't handle large fragmented messages. For an example of
 * fragmented message reception, see {@link MultipleSubscribersWithFragmentAssembly}.
 */
public class Subscriber
{

    //final FragmentHandler fragmentHandler = new FragmentAssembler(this::onFragmentReceived);
    
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

    

        
        try (Aeron aeron = Aeron.connect(ctx);
            Subscription subscription = aeron.addSubscription(channel, streamId))
        {
            
            Thread pollerThread = new Thread(() ->
            {
                
                final IdleStrategy idleStrategy = new BusySpinIdleStrategy();
                while (true)
                {
                    final int result = subscription.poll(fragmentHandler, 1);
                    idleStrategy.idle(result);
                
                }
                
            });

            pollerThread.start();
            
        }
    }

    
    void onFragmentReceived(DirectBuffer buffer, int offset, int length, Header header)
    {
        final byte[] data = new byte[length];
        buffer.getBytes(offset, data);
        final String rcvString = new String(data);
        System.out.println(rcvString);
    }



}
