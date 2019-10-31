package com.cnebrera.uc3.tech.lesson3.util;

import java.util.concurrent.TimeUnit;

import org.agrona.LangUtil;
import org.agrona.concurrent.BackoffIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

/**
 * Utility functions for the samples.
 */
public class AeronUtil
{
    
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
