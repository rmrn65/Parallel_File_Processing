import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoordThread {
    String inputFile;
    Integer threadNumber;
    public CoordThread(String inputFile, Integer threadNumber) {
        this.inputFile = inputFile;
        this.threadNumber = threadNumber;
    }
    public void submitTasks(){
        // Executor service witch will submit tasks
        ExecutorService tpe = Executors.newFixedThreadPool(threadNumber);
        try{
            File file = new File("inputFile");
        // Using scanner to read file inputs
            Scanner scanner = new Scanner(file);
            int fragmentSize = scanner.nextInt();//D - number of bites in a fragment
            int docNumber = scanner.nextInt();//Number of documents
            // Creating tasks for workers
            // Tasks will be added to the pool with tpe
            for(int i = 0 ; i < docNumber ; ++i) {
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}