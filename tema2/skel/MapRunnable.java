import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class MapRunnable implements Runnable {
    String docName;
    int offset;
    int fragmentSize;
    public MapRunnable(String docName, int offset, int fragmentSize) {
		this.docName = docName;
        this.offset = offset;
        this.fragmentSize = fragmentSize;
	}
    @Override
    public void run() {
        try {
            Boolean ignoreFirst = false;
            // Create a new RandomAccessFile with given filename
            RandomAccessFile raf = new RandomAccessFile(docName, "r");
            byte[] b = new byte[fragmentSize + 100]; // Byte array to read file
            // First word
            char next;
            if(offset != 0){
                raf.seek(offset - 1);
                next= (char)raf.read();
                if(Character.isLetter(next) || Character.isDigit(next)) {
                    ignoreFirst = true;
                }
            }
            
            raf.seek(offset); // Move cursor in file by offset
            raf.read(b,0,fragmentSize); // Read by fragmentSize
            // Final word
            next = (char)raf.read();
            int index = 0;
            while(Character.isLetter(next) || Character.isDigit(next)) {
                b[fragmentSize + index] = (byte)next;
                next = (char)raf.read();
                index++;
            }
            //System.out.println(raf.getFilePointer());
            String s = new String(b, StandardCharsets.UTF_8);
            // Parse words in portion of file
            StringTokenizer st = new StringTokenizer(s, " ;:/?˜\\.,><‘[]{}()!@#$%ˆ&- +’=*”|\r\n");
            if(ignoreFirst) st.nextToken();
            for(int i = 0 ; st.hasMoreTokens();i++) {
                System.out.println("#" + i + ": "+ st.nextToken());
            }

            raf.close();
         } catch (IOException ex) {
            ex.printStackTrace();
         }         
    }

}
