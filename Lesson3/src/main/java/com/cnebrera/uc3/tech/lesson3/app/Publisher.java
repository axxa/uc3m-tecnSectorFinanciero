package com.cnebrera.uc3.tech.lesson3.app;

import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.CHANNEL;
import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.STREAM_ID;

import java.util.concurrent.TimeUnit;

import org.agrona.BitUtil;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

import io.aeron.Aeron;
import io.aeron.Publication;

public class Publisher
{
    final static UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(512, BitUtil.CACHE_LINE_LENGTH));
    final static Aeron.Context ctx = new Aeron.Context();
    

    public static void main(final String[] args) throws Exception
    {
        int numberOfMessagesToPublish = 10;

        try (Aeron aeron = Aeron.connect(ctx))
        {
            while(numberOfMessagesToPublish > 0){
                publishMessage(aeron);
                numberOfMessagesToPublish--;
            }
        }

    }

    public static void publishMessage(Aeron aeron) throws Exception
    {
        System.out.println("Publishing to " + CHANNEL + " on stream id " + STREAM_ID);

        Publication publication = aeron.addPublication(CHANNEL, STREAM_ID);
            final String message = "Hello World! ";
            final byte[] messageBytes = message.getBytes();
            buffer.putBytes(0, messageBytes);

            // Wait for 5 seconds to connect to a subscriber
            final long deadlineNs = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
            while (!publication.isConnected())
            {
                if ((deadlineNs - System.nanoTime()) < 0)
                {
                    System.out.println("Failed to connect to subscriber");
                    return;
                }

                Thread.sleep(1);
            }
            
            final long result = publication.offer(buffer, 0, messageBytes.length);

            if (result < 0L)
            {
                if (result == Publication.BACK_PRESSURED)
                {
                    System.out.println(" Offer failed due to back pressure");
                }
                else if (result == Publication.NOT_CONNECTED)
                {
                    System.out.println(" Offer failed because publisher is not connected to subscriber");
                }
                else if (result == Publication.ADMIN_ACTION)
                {
                    System.out.println("Offer failed because of an administration action in the system");
                }
                else if (result == Publication.CLOSED)
                {
                    System.out.println("Offer failed publication is closed");
                }
                else
                {
                    System.out.println(" Offer failed due to unknown reason");
                }
            }
            else
            {
                System.out.println(" yay !!");
            }

            System.out.println("Done sending.");
    }
}
