package com.cnebrera.uc3.tech.lesson1;

import org.HdrHistogram.Histogram;
import org.HdrHistogram.HistogramIterationValue;

import com.cnebrera.uc3.tech.lesson1.simulator.BaseSyncOpSimulator;
import com.cnebrera.uc3.tech.lesson1.simulator.SyncOpSimulLongOperation;

/**
 * Second practice, measure latency with and without warmup
 */
public class PracticeLatency2
{
	static Histogram histogram = new Histogram(3600000000000L, 3);
    /**
     * Main method to run the practice
     * @param args command line arument
     */
    public static void main(String [] args)
    {
        System.out.println("Mediciones en microsegundos");
        //en frio
        System.out.println("Medicion en frio");
        runCalculations();

        //reset del histogram
        histogram.reset();
        //en caliente
        System.out.println("\nMedicion en caliente\n");
        for(int i = 0; i < 5; i++)
        {
            System.out.println("\nEmpieza runCalculations\n");
            runCalculations();
            //reset del histogram
            histogram.reset();
            System.out.println("\nTermina runCalculations\n ");
            System.out.println("\n------------------------------------------------------\n ");
        }
    }

    /**
     * Run the practice calculations
     */
    private static void runCalculations()
    {
        // Create a random park time simulator
        BaseSyncOpSimulator syncOpSimulator = new SyncOpSimulLongOperation();

        // Execute the operation lot of times
        for(int i = 0; i < 200000; i++)
        {
            //marcamos el starttime antes de realizar executeOp()
            long startTime = System.nanoTime();
            
            syncOpSimulator.executeOp();

            //marca el endtime despues de finalizado el executeOp()
        	long endTime = System.nanoTime();
        	//se guardan los datos del histograma
            histogram.recordValue((endTime - startTime) / 1000 );
        }

        // TODO Show min, max, mean and percentiles 99 and 99,9 with and without warm up
        System.out.println("\npercentile 99.9: " +   histogram.getValueAtPercentile(99.9) + "\n");
        System.out.println("percentile 99: " +   histogram.getValueAtPercentile(99) + "\n");
        System.out.println("Media: " + histogram.getMean() + "\n");
        System.out.println("Minima: " + histogram.getMinValue() + "\n");
        System.out.println("Maxima: " + histogram.getMaxValue() + "\n");
        

    }
}
