public class Sequential {
    private int[] array;
    private int[] result = new int[2];

    public Sequential(int[] array) {
        this.array = array;
        this.result[0] = array[0];
        this.result[1] = array[0];
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
