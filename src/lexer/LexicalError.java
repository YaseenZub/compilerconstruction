package lexer;

public class LexicalError {
    private int lineNo;
    private String error;

    public int getLineNo() {
        return lineNo;
    }
    public LexicalError(int lineNo,String error){
        this.lineNo=lineNo;
        this.error=error;
    }
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
