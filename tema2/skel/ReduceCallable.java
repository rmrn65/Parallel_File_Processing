import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class ReduceCallable implements Callable<MapResult> {
    String docName;
    List<MapResult> results;
    public ReduceCallable(String docName ,List<MapResult> results) {
        this.docName = docName;
        this.results = results;
    }
    @Override
    public MapResult call() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
}
