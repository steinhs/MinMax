public class Sequential {
    private int[] array;
    private int[] result = new int[2];
    private int minValue, maxValue;

    public Sequential(int[] array) {
        this.array = array;
        this.minValue = array[0];
        this.maxValue = array[0];
    }

    public int[] findMinMax() {

        for (int x : array) {
            if (x < minValue)
                minValue = x;
            if (x > maxValue)
                maxValue = x;
        }

        result[0] = minValue;
        result[1] = maxValue;

        return result;
    }
}
