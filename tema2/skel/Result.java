import java.util.HashMap;
import java.util.List;

public class Result {
    HashMap<Integer, Integer> wordsLengthCounter;
    List<String> maxLengthWords;
    public Result(HashMap<Integer, Integer> wordsLengthCounter,List<String> maxLengthWords ) {
        this.wordsLengthCounter = wordsLengthCounter;
        this.maxLengthWords = maxLengthWords;
    }
    public String toString(){
        return "WORDS COUNTER: " + wordsLengthCounter.toString() + "\n MaxLenWords: " + maxLengthWords.toString();
    }
}
