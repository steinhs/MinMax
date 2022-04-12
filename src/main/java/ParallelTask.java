import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class ParallelTask extends RecursiveTask<int[]> {
    int[] array, result = new int[2], resultTemp1, resultTemp2;
    int minValue, maxValue;
    int start, end;
    int threshold = 10000;
    private static final long serialVersionUID = 767439978300L;

    public ParallelTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.result[0] = array[0];
        this.result[1] = array[0];
        this.minValue = array[0];
        this.maxValue = array[0];
        this.resultTemp1 = result;
        this.resultTemp2 = result;
    }

    @Override
    protected int[] compute() {
        if (end - start < threshold){
            // TODO: COMPUTE
            result = findMinMax();
        } else {
            int middle = (end+start)/2;
            ParallelTask t1 = new ParallelTask(array, start, middle+1);
            ParallelTask t2 = new ParallelTask(array, middle+1, end);
            invokeAll(t1, t2);
            try {
                resultTemp1 =  t1.get();
                if (resultTemp1[0] < result[0])
                    result[0] = resultTemp1[0];
                if (resultTemp1[1] > result[1])
                    result[1] = resultTemp1[1];
                resultTemp2 =  t2.get();
                if (resultTemp2[0] < result[0])
                    result[0] = resultTemp2[0];
                if (resultTemp2[1] > result[1])
                    result[1] = resultTemp2[1];
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int[] findMinMax() {
        for (int x : array) {
            if (x < result[0])
                result[0] = x;
            if (x > result[1])
                result[1] = x;
        }

        return result;
    }


}
