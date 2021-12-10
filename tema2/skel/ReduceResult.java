public class ReduceResult {
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
        return "{\nDoc Name: " + docName +"\nMaximum Length: "+maxLength+"\nMaximum Words: "+maxLenWords+"\nRank: "+String.format("%.2f", rank)+"\n}" ;
        
    }
}
