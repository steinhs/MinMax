import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int nCores = Runtime.getRuntime().availableProcessors(), arrayLength;
        long startTime, endTime;
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
        startTime = System.nanoTime();
        int[] resultSeq = new Sequential(array).findMinMax();
        endTime = System.nanoTime();
        totalTimeSeq = (double) (endTime-startTime)/1000000;


        // PARALLEL METHOD
        startTime = System.nanoTime();
        ParallelTask task = new ParallelTask(array, 0, array.length);
        ForkJoinPool pool = new ForkJoinPool(nCores);
        pool.execute(task);
        endTime = System.nanoTime();


        // STATS
        int[] resultPar = task.get();
        totalTimePar = (double) (endTime-startTime)/1000000;

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
