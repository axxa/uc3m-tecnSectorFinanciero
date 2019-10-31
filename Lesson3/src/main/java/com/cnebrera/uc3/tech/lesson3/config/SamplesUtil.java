/*
 * Copyright 2014-2019 Real Logic Ltd.
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
package com.cnebrera.uc3.tech.lesson3.config;

//import static io.aeron.driver.Configuration.DEFAULT_IDLE_STRATEGY;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.agrona.LangUtil;
import org.agrona.concurrent.BackoffIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import io.aeron.Image;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.protocol.HeaderFlyweight;

/**
 * Utility functions for the samples.
 */
public class SamplesUtil
{
    /**
     * Return a reusable, parametrised event loop that calls a default idler when no messages are received.
     *
     * @param fragmentHandler to be called back for each message.
     * @param limit           passed to {@link Subscription#poll(FragmentHandler, int)}.
     * @param running         indication for loop.
     * @return loop function.
     */
    
     /**
     * Default idle strategy for agents.
     */
    private static final String DEFAULT_IDLE_STRATEGY = "org.agrona.concurrent.BackoffIdleStrategy";

    /**
     * Spin on no activity before backing off to yielding.
     */
    public static final long IDLE_MAX_SPINS = 10;

    /**
     * Yield the thread so others can run before backing off to parking.
     */
    public static final long IDLE_MAX_YIELDS = 20;

    /**
     * Park for the minimum period of time which is typically 50-55 microseconds on 64-bit non-virtualised Linux.
     * You will typically get 50-55 microseconds plus the number of nanoseconds requested if a core is available.
     * On Windows expect to wait for at least 16ms or 1ms if the high-res timers are enabled.
     */
    public static final long IDLE_MIN_PARK_NS = 1000;

    /**
     * Maximum back-off park time which doubles on each interval stepping up from the min park idle.
     */
    public static final long IDLE_MAX_PARK_NS = TimeUnit.MILLISECONDS.toNanos(1);
    

    public static Consumer<Subscription> subscriberLoop(
        final FragmentHandler fragmentHandler, final int limit, final AtomicBoolean running)
    {
        final IdleStrategy idleStrategy = AeronConfiguration.newIdleStrategy();

        return subscriberLoop(fragmentHandler, limit, running, idleStrategy);
    }

    /**
     * Return a reusable, parametrised event loop that calls and idler when no messages are received.
     *
     * @param fragmentHandler to be called back for each message.
     * @param limit           passed to {@link Subscription#poll(FragmentHandler, int)}.
     * @param running         indication for loop.
     * @param idleStrategy    to use for loop.
     * @return loop function.
     */
    public static Consumer<Subscription> subscriberLoop(
        final FragmentHandler fragmentHandler,
        final int limit,
        final AtomicBoolean running,
        final IdleStrategy idleStrategy)
    {
        return
            (subscription) ->
            {
                try
                {
                    while (running.get())
                    {
                        final int fragmentsRead = subscription.poll(fragmentHandler, limit);
                        idleStrategy.idle(fragmentsRead);
                    }
                }
                catch (final Exception ex)
                {
                    LangUtil.rethrowUnchecked(ex);
                }
            };
    }

    /**
     * Return a reusable, parametrised {@link FragmentHandler} that prints to stdout.
     *
     * @param streamId to show when printing.
     * @return subscription data handler function that prints the message contents.
     */
    public static FragmentHandler printStringMessage(final int streamId)
    {
        return (buffer, offset, length, header) ->
        {
            final byte[] data = new byte[length];
            buffer.getBytes(offset, data);

            System.out.println(String.format(
                "Message to stream %d from session %d (%d@%d) <<%s>>",
                streamId, header.sessionId(), length, offset, new String(data)));
        };
    }

    

    /**
     * Generic error handler that just prints message to stdout.
     *
     * @param channel   for the error.
     * @param streamId  for the error.
     * @param sessionId for the error, if source.
     * @param message   indicating what the error was.
     * @param cause     of the error.
     */
    @SuppressWarnings("unused")
    public static void printError(
        final String channel,
        final int streamId,
        final int sessionId,
        final String message,
        final HeaderFlyweight cause)
    {
        System.out.println(message);
    }

    /**
     * Print the rates to stdout.
     *
     * @param messagesPerSec being reported.
     * @param bytesPerSec    being reported.
     * @param totalMessages  being reported.
     * @param totalBytes     being reported.
     */
    public static void printRate(
        final double messagesPerSec,
        final double bytesPerSec,
        final long totalMessages,
        final long totalBytes)
    {
        System.out.println(String.format(
            "%.04g msgs/sec, %.04g payload bytes/sec, totals %d messages %d MB",
            messagesPerSec, bytesPerSec, totalMessages, totalBytes / (1024 * 1024)));
    }

    /**
     * Print the information for an available image to stdout.
     *
     * @param image that has been created.
     */
    public static void printAvailableImage(final Image image)
    {
        final Subscription subscription = image.subscription();
        System.out.println(String.format(
            "Available image on %s streamId=%d sessionId=%d from %s",
            subscription.channel(), subscription.streamId(), image.sessionId(), image.sourceIdentity()));
    }

    /**
     * Print the information for an unavailable image to stdout.
     *
     * @param image that has gone inactive.
     */
    public static void printUnavailableImage(final Image image)
    {
        final Subscription subscription = image.subscription();
        System.out.println(String.format(
            "Unavailable image on %s streamId=%d sessionId=%d",
            subscription.channel(), subscription.streamId(), image.sessionId()));
    }

    /**
     * Map an existing file as a read only buffer.
     *
     * @param location of file to map.
     * @return the mapped file.
     */
    public static MappedByteBuffer mapExistingFileReadOnly(final File location)
    {
        if (!location.exists())
        {
            final String msg = "file not found: " + location.getAbsolutePath();
            throw new IllegalStateException(msg);
        }

        MappedByteBuffer mappedByteBuffer = null;
        try (RandomAccessFile file = new RandomAccessFile(location, "r");
            FileChannel channel = file.getChannel())
        {
            mappedByteBuffer = channel.map(READ_ONLY, 0, channel.size());
        }
        catch (final IOException ex)
        {
            LangUtil.rethrowUnchecked(ex);
        }

        return mappedByteBuffer;
    }

    

    /**
     * Create an {@link IdleStrategy} that can be used to.
     *
     * @param strategyName of the class to be created.
     * @return the newly created IdleStrategy.
     */
    public static IdleStrategy newIdleStrategy(final String strategyName)
    {
        IdleStrategy idleStrategy = null;

        if (DEFAULT_IDLE_STRATEGY.equals(strategyName))
        {
            idleStrategy = new BackoffIdleStrategy(
                IDLE_MAX_SPINS, IDLE_MAX_YIELDS, IDLE_MIN_PARK_NS, IDLE_MAX_PARK_NS);
        }
        else
        {
            try
            {
                idleStrategy = (IdleStrategy)Class.forName(strategyName).getConstructor().newInstance();
            }
            catch (final Exception ex)
            {
                LangUtil.rethrowUnchecked(ex);
            }
        }

        return idleStrategy;
    }
}