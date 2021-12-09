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
        // System.out.println("DOC NAME: "+docName +" OFFSET: "+offset);        
        
    }

}
