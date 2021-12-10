import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

public class MapCallable implements Callable<Result> {
    String docName;
    int offset;
    int fragmentSize;
    HashMap<Integer,Integer> wordsLengthCounter;
    List<String> maxLengthWords;
    public MapCallable(String docName, int offset, int fragmentSize) {
		this.docName = docName;
        this.offset = offset;
        this.fragmentSize = fragmentSize;
        wordsLengthCounter = new HashMap<>();
        maxLengthWords = new ArrayList<>();
	}
    public boolean isNotSeparator(Character c) {
        return Character.isLetter(c) || Character.isDigit(c);
    }
    @Override
    public Result call() throws Exception {
        try {
            Boolean ignoreFirst = false;
            char next,prev;
            int index = 0;

            // Create a new RandomAccessFile with given filename
            RandomAccessFile raf = new RandomAccessFile(docName, "r");
            // // Final word
            raf.seek(offset + fragmentSize - 1); // Move cursor in file by offset
            prev = (char)raf.read();
            next = (char)raf.read();
            while(isNotSeparator(next) && isNotSeparator(prev)) { 
                next = (char)raf.read();
                index++;
            }
            byte[] b = new byte[fragmentSize + index]; // Byte array to read file
            // First word
            if(offset != 0){
                raf.seek(offset - 1);
                next = (char)raf.read();
                if(Character.isLetter(next) || Character.isDigit(next)) {
                    ignoreFirst = true;
                }
            }
            // Read content
            raf.seek(offset); // Move cursor in file by offset
            raf.read(b,0,fragmentSize + index); // Read by fragmentSize

            String s = new String(b, StandardCharsets.UTF_8);
            // Parse words in portion of file
            StringTokenizer st = new StringTokenizer(s, " ;:/?˜\\.,><‘[]{}()!@#$%ˆ&- +’=*”|\r\n");
            if(ignoreFirst) {
                st.nextToken();
            }
            while(st.hasMoreTokens()) {
                // Set number of occurances of length
                String nextWord =new String( st.nextToken().toString());

                Integer value = wordsLengthCounter.get(nextWord.length());
                if(value == null) {
                    wordsLengthCounter.put(nextWord.length(),1);
                } else {
                    wordsLengthCounter.put(nextWord.length(),value + 1);
                }
                //Set the biggest words
                if(maxLengthWords.isEmpty()) {
                    maxLengthWords.add(nextWord);
                } else if(maxLengthWords.get(0).length() < nextWord.length()) {
                    maxLengthWords.clear();
                    maxLengthWords.add(nextWord);
                } else if(maxLengthWords.get(0).length() == nextWord.length()) {
                    maxLengthWords.add(nextWord);
                } 
            }
            raf.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }

        Result result = new Result(docName, wordsLengthCounter, maxLengthWords);
        return result;
    }

}
