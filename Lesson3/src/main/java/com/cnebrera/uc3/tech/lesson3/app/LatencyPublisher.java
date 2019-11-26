package com.cnebrera.uc3.tech.lesson3.app;

import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.CHANNEL;
import static com.cnebrera.uc3.tech.lesson3.config.AeronConfiguration.STREAM_ID;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.HdrHistogram.Histogram;
import org.agrona.BitUtil;
import org.agrona.BufferUtil;
import org.agrona.concurrent.UnsafeBuffer;

import io.aeron.Aeron;
import io.aeron.Publication;

public class LatencyPublisher
{
    final static UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(512, BitUtil.CACHE_LINE_LENGTH));
    final static Aeron.Context ctx = new Aeron.Context();
    
    static private long sendTime;
    static private long receivedTime;

    final static int NUMBER_OF_MSGS_TO_PUBLISH = 100000;

    static Histogram histogram = new Histogram(3600000000000L, 3);
    static Histogram histogramCum = new Histogram(3600000000000L, 3);

    static long totalCum = 0;

    public static void main(final String[] args) throws Exception
    {
        int numberOfMessagesToPublish = NUMBER_OF_MSGS_TO_PUBLISH;

        try (Aeron aeron = Aeron.connect(ctx))
        {
            while(numberOfMessagesToPublish > 0){
                publishMessage(aeron);
                numberOfMessagesToPublish--;
            }
        }

        histogram.outputPercentileDistribution(System.out, 1000.0);
        //histogramCum.outputPercentileDistribution(System.out, 1000.0);
        //BigDecimal ratio = new BigDecimal( (double) NUMBER_OF_MSGS_TO_PUBLISH / (totalCum/1000)).setScale(10);
        
        System.out.println("Ratio envio " + NUMBER_OF_MSGS_TO_PUBLISH + " por segundo: " +  totalCum );

    }

    public static void publishMessage(Aeron aeron) throws Exception
    {
        //System.out.println("Publishing to " + CHANNEL + " on stream id " + STREAM_ID);

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

            sendTime = System.nanoTime();
            final long result = publication.offer(buffer, 0, messageBytes.length);

            if (result < 0L)
            {
                if (result == Publication.BACK_PRESSURED)
                {
                    System.out.println(" Offer failed due to back pressure");
                }
                else if (result == Publication.NOT_CONNECTED){System.out.println(" Offer failed because publisher is not connected to subscriber");}
                else if (result == Publication.ADMIN_ACTION){System.out.println("Offer failed because of an administration action in the system");}
                else if (result == Publication.CLOSED){System.out.println("Offer failed publication is closed");}
                else{System.out.println(" Offer failed due to unknown reason");}
            }
            else
            {
                //System.out.println("Msg received by subscriber");
                receivedTime = System.nanoTime();
                recordHistograms();
            }
    }

    private static void recordHistograms(){
        long total = (receivedTime - sendTime)/2;
        totalCum += total;
        //System.out.println("totalCum: " + totalCum);
        histogram.recordValue(total);
        histogramCum.recordValue(totalCum);
    }

}
