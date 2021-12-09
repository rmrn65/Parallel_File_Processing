import java.io.*;
import java.util.*;

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

        
    }
}
