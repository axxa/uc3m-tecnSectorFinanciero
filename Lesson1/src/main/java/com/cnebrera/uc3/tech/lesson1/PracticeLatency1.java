package com.cnebrera.uc3.tech.lesson1;

import com.cnebrera.uc3.tech.lesson1.simulator.BaseSyncOpSimulator;
import com.cnebrera.uc3.tech.lesson1.simulator.SyncOpSimulRndPark;

import java.util.concurrent.TimeUnit;

import org.HdrHistogram.Histogram;

/**
 * First practice, measure latency on a simple operation
 */
public class PracticeLatency1
{
	
	static Histogram histogram = new Histogram(3600000000000L, 3);
    /**
     * Main method to run the practice
     * @param args command line arument
     */
    public static void main(String [] args)
    {
        runCalculations();
    }

    /**
     * Run the practice calculations
     */
    private static void runCalculations()
    {
    	
        // Create a random park time simulator
        BaseSyncOpSimulator syncOpSimulator = new SyncOpSimulRndPark(TimeUnit.NANOSECONDS.toNanos(100), TimeUnit.MICROSECONDS.toNanos(100));
        
        // Execute the operation lot of times
        for(int i = 0; i < 100000; i++)
        {
        	//marcamos el starttime antes de realizar executeOp()
        	long startTime = System.nanoTime();
        	
        	syncOpSimulator.executeOp();
        	//marca el endtime despues de finalizado el executeOp()
        	long endTime = System.nanoTime();
        	//se guardan los datos del histograma
            histogram.recordValue(endTime - startTime);
            
        }

        // TODO Show the percentile distribution of the latency calculation of each executeOp call        
        histogram.outputPercentileDistribution(System.out, 1000.0);


    }
}
