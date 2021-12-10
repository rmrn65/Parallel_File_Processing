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
        System.out.println("THREAD NUMBER "+threadNumber);
        System.out.println("INPUT FILE"+inputFile);
        System.out.println("outputfile "+outputFile);

        CoordThread coordThread = new CoordThread(inputFile, threadNumber);
        try{
            coordThread.submitTasks();
        } catch (IOException e) {
            System.err.println(e);
        }
        // Tema2 tema2 = new Tema2();
        // try {
        //     tema2.readFromFIle("doc.txt", 0, 0);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // try {
        //     // create a new RandomAccessFile with filename test
        //     RandomAccessFile raf = new RandomAccessFile("doc.txt", "rw");
        //     byte[] b = new byte[60];
        //     //raf.read(b,5,6);
        //     // raf.seek(6);
        //     raf.read(b,0,60);
        //     System.out.println(raf.getFilePointer());
        //     String s = new String(b, StandardCharsets.UTF_8);
        //     System.out.println(s);
        //     // ----- SPLIT VARIANCE ------
        //     // String regex = new String("[;:/˜,><‘{}()#$%ˆ&+’=*”|\n\r .!?]+");
        //     // for(String val: s.split("[;:/˜,><‘{}()#$%ˆ&+’=*”|\n\r .!?]+"))
        //     //     System.out.println("WORD: " + val);

        //     // -----TOKANIZER VARIANCE ------
        //     StringTokenizer st = new StringTokenizer(s, " ;:/?˜\\.,><‘[]{}()!@#$%ˆ&- +’=*”|\r\n");
        //     for(int i = 0 ; st.hasMoreTokens();i++) {
        //         System.out.println("#" + i + ": "
        //                        + st.nextToken());
        //     }
        //     raf.close();
        //  } catch (IOException ex) {
        //     ex.printStackTrace();
        //  }
    }
}
