import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoordThread {
    String inputFile;
    Integer threadNumber;
    static HashMap<String, List<Result>> mapResults;
    public CoordThread(String inputFile, Integer threadNumber) {
        this.inputFile = inputFile;
        this.threadNumber = threadNumber;
        mapResults = new HashMap<>();
    }
    public void submitTasks() throws IOException{
        // Executor service witch will submit tasks
        ExecutorService tpe = Executors.newFixedThreadPool(threadNumber);
        try{
            File file = new File(inputFile);
            // Using scanner to read file inputs
            try (Scanner scanner = new Scanner(file)) {
                int fragmentSize = scanner.nextInt();//D - number of bites in a fragment
                int docNumber = scanner.nextInt();//Number of documents
                // Creating tasks for workers
                // Tasks will be added to the pool with tpe
                for(int i = 0 ; i < docNumber ; ++i) {
                    File currentDoc = new File("../" + scanner.next());                
                    long docSize = currentDoc.length();
                    int offset = 0;
                    // Create tasks for each file with right offset of fragmentSize
                    while(offset < docSize){
                        if(docSize - offset < fragmentSize)
                            tpe.submit(new MapRunnable(currentDoc.getPath(), offset, (int)docSize - offset));
                        else
                            tpe.submit(new MapRunnable(currentDoc.getPath(), offset, fragmentSize));
                        offset += fragmentSize;
                    }
                }
            }
            tpe.shutdown();
                for(String key:mapResults.keySet()) {
                    List<Result> lResults = mapResults.get(key);
                    //System.out.println(lResults);
                }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
