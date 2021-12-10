import java.util.HashMap;
import java.util.List;

public class Result {
    private String docName;
    private HashMap<Integer, Integer> wordsLengthCounter;
    private List<String> maxLengthWords;
    public Result(String docName, HashMap<Integer, Integer> wordsLengthCounter,List<String> maxLengthWords ) {
        this.docName = docName;
        this.wordsLengthCounter = wordsLengthCounter;
        this.maxLengthWords = maxLengthWords;
    }
    public String toString(){
        return "WORDS COUNTER: " + wordsLengthCounter.toString() + "\n MaxLenWords: " + maxLengthWords.toString();
    }
    // Getters
    public String getDocName() {
        return docName;
    }
    public HashMap<Integer, Integer> getWordsLengthCounter() {
        return wordsLengthCounter;
    }
    public List<String> getMaxLengthWords() {
        return maxLengthWords;
    }
}
