import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MapRunnable implements Runnable {
    String docName;
    int offset;
    int fragmentSize;
    HashMap<Integer,Integer> wordsLengthCounter;
    List<String> maxLengthWords;
    public MapRunnable(String docName, int offset, int fragmentSize) {
		this.docName = docName;
        this.offset = offset;
        this.fragmentSize = fragmentSize;
        wordsLengthCounter = new HashMap<>();
        maxLengthWords = new ArrayList<>();
	}
    @Override
    public void run() {
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
            while((Character.isLetter(next) && Character.isLetter(prev))|| Character.isDigit(next)) { //TODO Fa un check mai bun aici
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
        // for(Integer key: wordsLengthCounter.keySet()) {
        //     System.out.println("IN DOCUMENT " + docName + " Key: "+ key + " Val: " + wordsLengthCounter.get(key));
        // }
    //    // System.out.println(wordsLengthCounter.toString());
        Result result = new Result(wordsLengthCounter, maxLengthWords);
        //System.out.println(result.toString());
        // The creation of the list must be synchronized so no 2 threads
        // are initializing the list. It is synchronized by the key where
        // the thread is going to add the result (the document's name).
        synchronized(MapRunnable.class){ //TODO SYNCRONIZEAZA PE ALTCEVA MAI LOGIC
           if(CoordThread.mapResults.get(docName) == null) {
               List<Result> resultsList = new ArrayList<>();
               resultsList.add(result);
               CoordThread.mapResults.put(docName, resultsList);
            }
        }
        if(CoordThread.mapResults.get(docName) != null) {
            CoordThread.mapResults.get(docName).add(result);
        }
    }

}
