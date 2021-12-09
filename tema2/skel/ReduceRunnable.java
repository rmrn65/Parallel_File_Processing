import java.util.HashMap;
import java.util.List;

public class ReduceRunnable implements Runnable {
    String docName;
    List<List<String>> maxLengthWords;
    List<HashMap<Integer, Integer>> wordsCounter;
    public ReduceRunnable(String docName ,List<List<String>> maxLengthWords ,List<HashMap<Integer, Integer>> wordsCounter) {
        this.docName = docName;
        this.maxLengthWords = maxLengthWords;
        this.wordsCounter = wordsCounter;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
}
