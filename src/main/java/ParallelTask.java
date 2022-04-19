import java.util.concurrent.RecursiveTask;

public class ParallelTask extends RecursiveTask<int[]> {
    int[] array, result = new int[2];
    int start, end;
    int threshold;

    public ParallelTask(int[] array, int start, int end, int threshold) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.result[0] = array[0];
        this.result[1] = array[0];
        this.threshold = threshold;
    }
    @Override
    protected int[] compute() {
        if (end - start <= threshold){
            System.out.println("COMPUTE SEQUENTIALLY");
            for (int x : array) {
                if (x < result[0])
                    result[0] = x;
                if (x > result[1])
                    result[1] = x;
            }
        } else {
            System.out.println("SPLIT UP INTO TWO NEW TASKS");
            int middle = (end+start)/2;
            ParallelTask l = new ParallelTask(array, start, middle+1, threshold);
            ParallelTask r = new ParallelTask(array, middle+1, end, threshold);
            l.fork();
            int[] resultR = r.compute();
            int[] resultL = l.join();
            if (resultL[0] < result[0])
                result[0] = resultL[0];
            if (resultL[1] > result[1])
                result[1] = resultL[1];
            if (resultR[0] < result[0])
                result[0] = resultR[0];
            if (resultR[1] > result[1])
                result[1] = resultR[1];
        }
        return result;
    }
}
