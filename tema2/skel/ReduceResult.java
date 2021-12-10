public class ReduceResult implements Comparable<ReduceResult> {
    private String docName;
    private Float rank;
    private Integer maxLength;
    private Integer maxLenWords;

    public ReduceResult(String docName, Float rank, Integer maxLength, Integer maxLenWords) {
        this.docName = docName;
        this.rank = rank;
        this.maxLength = maxLength;
        this.maxLenWords = maxLenWords;
    }
    // Getters
    public String getDocName() {
        return docName;
    }
    public Integer getMaxLenWords() {
        return maxLenWords;
    }
    public Integer getMaxLength() {
        return maxLength;
    }
    public Float getRank() {
        return rank;
    }
    public String toString(){
        return docName +","+String.format("%.2f", rank)+","+maxLength+","+maxLenWords;
        
    }
    @Override
    public int compareTo(ReduceResult o) {
        if(getRank() < o.getRank()) return 1;
        else return -1;
    }
}
