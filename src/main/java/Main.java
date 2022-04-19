import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int nCores = Runtime.getRuntime().availableProcessors();
        double totalTimeSeq, totalTimePar;
        int[] array;

        // If no argument given, creates new array of random numbers. Else, read file into array.
        if (args.length == 0){
            System.out.println("No argument found; Generating large array[80000000] to compute...");
            array = new int[80000000];
            for (int i = 0; i < array.length; i++) {
                array[i] = new Random().nextInt(1900000000);
            }
            System.out.println("\nNumbers read into array: " + array.length);
        } else {
            array = new ArrayReader().readFileToArray(args[0]);
            System.out.println("\nNumbers read into array: " + array.length);
        }

        // SEQUENTIAL METHOD
        long startTimeSeq = System.nanoTime();
        int[] resultSeq = new Sequential(array).findMinMax();
        long endTimeSeq = System.nanoTime();
        totalTimeSeq = (double) (endTimeSeq-startTimeSeq)/1000000;

        // PARALLEL METHOD
        long startTimePar = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(nCores);
        int[] resultPar = pool.invoke(new ParallelTask(array, 0, array.length, array.length/2));
        long endTimePar = System.nanoTime();
        totalTimePar = (double) (endTimePar-startTimePar)/1000000;

        // STATS
        double speedup = totalTimeSeq/totalTimePar;
        double efficiency = speedup/nCores;

        System.out.println("\n- - SEQUENTIAL - -");
        System.out.println("Execution time: " + totalTimeSeq + " milliseconds");
        System.out.println("Result\n- Min: " + resultSeq[0] + "\n- Max: " + resultSeq[1]);

        System.out.println("\n- - PARALLEL - -");
        System.out.println("Execution time: " + totalTimePar + " milliseconds");
        System.out.println("Result\n- Min: " + resultPar[0] + "\n- Max: " + resultPar[1]);

        System.out.println("\n- - STATS - -");
        System.out.println("- Speedup: " + speedup);
        System.out.println("- Efficiency: " + efficiency);
    }
}
