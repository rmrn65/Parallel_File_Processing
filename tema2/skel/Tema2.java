import java.io.*;

public class Tema2 {
    static Integer threadNumber;
    static String inputFile;
    static String outputFile;

    void readFromFIle(String file,int offset, int size) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(0);
        System.out.println(raf.readUTF());
        raf.seek(0);
        raf.close();
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: Tema2 <workers> <in_file> <out_file>");
            return;
        }
        // Taking values from args
        threadNumber = Integer.parseInt(args[0]); // Number of threads
        inputFile = args[1];// Input file
        outputFile = args[2];// Output file

        // CoordThread coordThread = new CoordThread(inputFile, threadNumber,outputFile);
        // try{
        //     coordThread.submitTasks();
        // } catch (IOException e) {
        //     System.err.println(e);
        // }
        
    }
}
