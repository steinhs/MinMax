import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int nCores = Runtime.getRuntime().availableProcessors();
        long startTime, endTime, seqTotalTime, parTotalTime;

        int[] array = new ArrayReader().readFileToArray("testcase1.txt");
        System.out.println("Numbers read into array: " + array.length);

        int[] result = new Sequential(array).findMinMax();
        System.out.println("Result of Sequential\n- Min: " + result[0] + "\n- Max: " + result[1]);

    }
}
