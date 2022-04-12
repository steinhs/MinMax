import java.io.*;
import java.util.Scanner;
public class ArrayReader {
    public int[] readFileToArray(String filename) throws IOException {
        int size, i = 0;

        Scanner scanner = new Scanner(new File(filename));
        // IF COMMA SEPERATED NUMBERS USE: Scanner scanner = new Scanner(new File(filename)).useDelimiter(",\\s*");

        size = Integer.parseInt(scanner.next());
        int[] array = new int[size];

        // Scans and parses from string to int before adding to array
        while (scanner.hasNextInt()){
            array[i++] = scanner.nextInt();
        }
        scanner.close();

        return array;
    }
}
