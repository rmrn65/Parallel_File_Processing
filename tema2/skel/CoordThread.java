import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        //AtomicInteger inQueue = new AtomicInteger(0);
        List<MapCallable> futureList = new ArrayList<MapCallable>();
        List<Future<Result>> futures = new ArrayList<>();
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
                    mapResults.put(currentDoc.getPath(), new ArrayList<>());              
                    long docSize = currentDoc.length();
                    int offset = 0;
                    // Create tasks for each file with right offset of fragmentSize
                    while(offset < docSize){
                        if(docSize - offset < fragmentSize)
                            futureList.add(new MapCallable(currentDoc.getPath(), offset, (int)docSize - offset));
                        else
                            futureList.add(new MapCallable(currentDoc.getPath(), offset, fragmentSize));
                        offset += fragmentSize;
                    }
                }
            }
            try {
                futures = tpe.invokeAll(futureList);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("COMPLETED");
            tpe.shutdown();
            // tpe.awaitTermination(arg0, arg1)
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            for(int i = 0; i < futures.size() ; i ++)
                System.out.println(futures.get(i).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
