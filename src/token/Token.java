package token;


public class Token {

    private int beginIndex;

    private int endIndex;

    private TokenType tokenType;

    private String tokenString;

    private int lineNumber;
    public Token(int beginIndex, int endIndex, String tokenString, TokenType tokenType,int lineNumber) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.tokenType = tokenType;
        this.tokenString = tokenString;
        this.lineNumber=lineNumber;
    }


    public int getBegin() {
        return beginIndex;
    }


    public int getEnd() {
        return endIndex;
    }



    public String getTokenString() {
        return tokenString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        if (!this.getTokenType().isAuxiliary())
            return tokenType + "  '" + tokenString + "' [" + beginIndex + ";" + endIndex + "] ";
        else
            return tokenType + "   [" + beginIndex + ";" + endIndex + "] ";
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}