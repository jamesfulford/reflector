package listsandsorts;

import commons.Timer;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author James Fulford
 * @dateEdit 5/1/16
 * @jing http://screencast.com/t/9yhI9c5d (very briefly mentioned near the end)
 */
public class ListsAndSorts {

    //stores primenumber * multiplier random numbers in a file
    //creates numberOfPrimeFiles * 2 (one for unsorted random numbers)
    //sorts random numbers, stores in another textfile
    //times in milliseconds the sorting algorithm. Stores times in text file for each trial. (each numberOfPrimeFiles)
    public static void main(String[] args) throws IOException {
        //      VARIABLES
        int numberOfPrimeFiles = 1000; //number of randomNumberFile sets
        int primeNumberMultiplier = 10; //inputSize multiplier
        int randomNumberCap = 3443; //this - 1 is the highest random number possible.

        int autosaveIteration = 20; //export time results this many iterations.
        boolean leastToGreatest = false; //orders from least to greatest (0 to 9) if true, greatest to least (9 to 0) if false.

        boolean runBubble = false;
        boolean runFreq = false;
        boolean runBucket = false;

        boolean generateNewData = true;
        boolean consoleTraceDataGeneration = true;  //enables console message stating which files have been generated if generateNewData = true;

        //      GENERATE PRIME NUMBERS
        IntegerList primeGen = new IntegerList();
        primeGen.generatePrimes(numberOfPrimeFiles);
        primeGen.writeToTxt("allPrimes");
        int[] primes = primeGen.toArray();

        //      SET-UP TIME RECORDING EQUIPMENT
        LongList bubbleTimes = new LongList();
        LongList freqTimes = new LongList();
        LongList bucketTimes = new LongList();
        Timer timer = new Timer();

        //      COLLECT/GENERATE DATA TO SORT
        IntegerList[] randoms = new IntegerList[numberOfPrimeFiles];
        for (int i = 0; i < randoms.length; i++) {
            if (generateNewData) {
                randoms[i] = new IntegerList();
                randoms[i].generateRandomIntegers(primes[i] * primeNumberMultiplier, randomNumberCap);
                randoms[i].writeToTxt("data" + primes[i]); //save data
                if (consoleTraceDataGeneration) {
                    System.out.println("File data" + primes[i] + " has been created.");
                }
            } else {
                randoms[i].extract(new File("resources/data" + primes[i]));
            }
        }

        //      RUN THE EXPERIMENT
        for (int i = 0; i < numberOfPrimeFiles; i++) {

            if (runBubble) {
                StringList bubbleSort = randoms[i].copy().toStringList();
                timer.start();                      //startTiming
                bubbleSort.bubbleSort(leastToGreatest);
                bubbleTimes.push(timer.end());   //endTiming
                System.out.print(timer.elapsedTime + ", ");
                bubbleSort.writeToTxt("bubbleSorted" + primes[i]);
            }

            if (runBucket) {
                StringList bucketSort = randoms[i].copy().toStringList();
                timer.start();                      //startTiming
                bucketSort.bucketSort(leastToGreatest);
                bucketTimes.push(timer.end());   //endTiming
                System.out.print(timer.elapsedTime + ", ");
                bucketSort.writeToTxt("bucketSorted" + primes[i]);
            }

            if (runFreq) {
                StringList frequencySort = randoms[i].copy().toStringList();
                timer.start();                      //startTiming
                frequencySort.frequencySort(leastToGreatest);
                freqTimes.push(timer.end());      //endTiming
                System.out.print(timer.elapsedTime + ", ");
                frequencySort.writeToTxt("frequencySorted" + primes[i]);
            }

            System.out.println(" is " + primes[i] + ".");

            //      RECORD RESULTS
            if (i % autosaveIteration == 0 || i == numberOfPrimeFiles - 1) { //every autosaveIteration iterations, export data. Export on last run.
                bubbleTimes.writeToTxt("allBubbleResults");
                bucketTimes.writeToTxt("allBucketResults");
                freqTimes.writeToTxt("allFrequencyResults");
            }
        }

    }

}
