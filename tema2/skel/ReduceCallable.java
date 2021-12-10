import java.util.HashMap;
import java.util.List;

public class ReduceCallable implements Runnable {
    String docName;
    List<List<String>> maxLengthWords;
    List<HashMap<Integer, Integer>> wordsCounter;
    public ReduceCallable(String docName ,List<List<String>> maxLengthWords ,List<HashMap<Integer, Integer>> wordsCounter) {
        this.docName = docName;
        this.maxLengthWords = maxLengthWords;
        this.wordsCounter = wordsCounter;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
}
