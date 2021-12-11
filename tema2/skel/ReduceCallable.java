import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ReduceCallable implements Callable<ReduceResult> {
    String docName;
    List<MapResult> results;
    public ReduceCallable(String docName ,List<MapResult> results) {
        this.docName = docName;
        this.results = results;
    }
    Integer fibbonaciGenerator(ArrayList<Integer> allreadyComputedArray, int size){
        if(allreadyComputedArray.size() == 0){
            allreadyComputedArray.add(1);
            allreadyComputedArray.add(2);
        }
        while(allreadyComputedArray.size() < size){
            int currentSize = allreadyComputedArray.size();
            allreadyComputedArray.add(allreadyComputedArray.get(currentSize-2) 
            + allreadyComputedArray.get(currentSize-1));
        }
        return allreadyComputedArray.get(size - 1);
    }
    @Override
    public ReduceResult call() throws Exception {
        Float rank = 0f;
        Integer maxLength = -1;
        Integer maxLenWords = 0;
        float sum = 0;
        int counter = 0;
        for (MapResult result : results) {
            if(result.getMaxLengthWords().size() == 0)
                continue;
            String biggestWord = result.getMaxLengthWords().get(0);
            Integer numberOfBiggestWords = result.getWordsLengthCounter().get(biggestWord.length());
            // Get size of the biggest word
            if(maxLength < biggestWord.length()) {
                maxLength = biggestWord.length();
                maxLenWords = numberOfBiggestWords;
            } else if(maxLength == biggestWord.length()) {
                //Count the number of big words
                maxLenWords += numberOfBiggestWords;
            }
            ArrayList<Integer> fibbonaciArrayList = new ArrayList<Integer>();
            for(Integer len: result.getWordsLengthCounter().keySet()) {
                    sum += fibbonaciGenerator(fibbonaciArrayList, len) * result.getWordsLengthCounter().get(len);
                    counter += result.getWordsLengthCounter().get(len);
            }
        }
        rank = sum / counter;
        System.out.println(counter + " " + docName);
        ReduceResult result = new ReduceResult(docName, rank, maxLength, maxLenWords);
        return result;
    }
    
}
