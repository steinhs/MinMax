import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int nCores = Runtime.getRuntime().availableProcessors();
        long startTime, endTime;
        double totalTimeSeq, totalTimePar;

        int[] array = new ArrayReader().readFileToArray("testcase1.txt");
        System.out.println("\nNumbers read into array: " + array.length);

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
        pool.shutdown();

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
