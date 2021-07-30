package exceptions;

public class SemanticException extends Exception {
    private String code;

    public SemanticException(String code,String message){
        super(message);
        this.setCode(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
