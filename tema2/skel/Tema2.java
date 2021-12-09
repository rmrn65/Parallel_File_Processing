import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Tema2 {
    static Integer threadNumber;
    static String inputFile;
    static String outputFile;
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: Tema2 <workers> <in_file> <out_file>");
            return;
        }
        // Taking values from args
        threadNumber = Integer.parseInt(args[0]); // Number of threads
        inputFile = args[1];// Input file
        outputFile = args[2];// Output file

        CoordThread coordThread = new CoordThread(inputFile, threadNumber);
        coordThread.submitTasks();

    }
}
