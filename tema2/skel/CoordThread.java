import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
    String outputFile;
    static HashMap<String, List<MapResult>> mapResults;
    public CoordThread(String inputFile, Integer threadNumber,String outputFile) {
        this.inputFile = inputFile;
        this.threadNumber = threadNumber;
        this.outputFile = outputFile;
        mapResults = new HashMap<>();
    }
    public void submitTasks() throws IOException{
        // Executor service witch will submit tasks
        ExecutorService tpe = Executors.newFixedThreadPool(threadNumber);
        //AtomicInteger inQueue = new AtomicInteger(0);
        List<MapCallable> futureList = new ArrayList<MapCallable>();
        List<Future<MapResult>> futures = new ArrayList<>();
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
                    mapResults.put(currentDoc.getName(), new ArrayList<>());              
                    long docSize = currentDoc.length();
                    int offset = 0;
                    // Create tasks for each file with right offset of fragmentSize
                    while(offset < docSize){
                        if(docSize - offset < fragmentSize)
                            futureList.add(new MapCallable(currentDoc, offset, (int)docSize - offset));
                        else
                            futureList.add(new MapCallable(currentDoc, offset, fragmentSize));
                        offset += fragmentSize;
                    }
                }
            }
            try {
                futures = tpe.invokeAll(futureList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("COMPLETED");
            tpe.shutdown();
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // New Executor service (the other one broke)
        ExecutorService tpeReduce = Executors.newFixedThreadPool(threadNumber);
        // Adding results in map so we can create new tasks.
        try {
            for(int i = 0; i < futures.size() ; i ++){
                if(futures.get(i).get() != null)
                    mapResults.get(futures.get(i).get().getDocName()).add(futures.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        List<ReduceCallable> futureListReduce = new ArrayList<ReduceCallable>();
        List<Future<ReduceResult>> futuresReduce = new ArrayList<>();
        List<MapResult> reduceInput; // value of hashmap at each key
        for(String key: mapResults.keySet()) {
            reduceInput = mapResults.get(key);
            futureListReduce.add(new ReduceCallable(key, reduceInput));
        }
        try {
            futuresReduce = tpeReduce.invokeAll(futureListReduce);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Complete REDUCE");
        System.out.println(futuresReduce.size());
        tpeReduce.shutdown();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for(int i = 0; i < futuresReduce.size() ; i ++){
                System.out.println(futuresReduce.get(i).get());
                writer.write(futuresReduce.get(i).get().toString());
                if(i != futuresReduce.size() - 1)
                    writer.write('\n');
            }
            writer.close();
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

    }
}
