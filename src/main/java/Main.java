import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int nCores = Runtime.getRuntime().availableProcessors();
        long startTime, endTime;
        double totalTimeSeq, totalTimePar;

        int[] array = new ArrayReader().readFileToArray("testcase1.txt");
        System.out.println("\nNumbers read into array: " + array.length);

        startTime = System.nanoTime();
        int[] resultSeq = new Sequential(array).findMinMax();
        endTime = System.nanoTime();
        totalTimeSeq = (double) (endTime-startTime)/1000000;


        startTime = System.nanoTime();
        // PARALLEL METHOD
        endTime = System.nanoTime();
        totalTimePar = (double) (endTime-startTime)/1000000;





        double speedup = totalTimeSeq/totalTimePar;
        double efficiency = speedup/nCores;

        System.out.println("\n- - SEQUENTIAL - -");
        System.out.println("Execution time: " + totalTimeSeq + " milliseconds");
        System.out.println("Result\n- Min: " + resultSeq[0] + "\n- Max: " + resultSeq[1]);

        System.out.println("\n- - PARALLEL - -");
        System.out.println("Execution time: " + totalTimePar + " milliseconds");
        System.out.println("Result\n- Min: " + 0 + "\n- Max: " + 0);

        System.out.println("\n- - STATS - -");
        System.out.println("- Speedup: " + speedup);
        System.out.println("- Efficiency: " + efficiency);
    }
}
