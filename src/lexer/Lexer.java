package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.AnalyzerException;

import token.Token;
import token.TokenType;

/**
 * The {@code Lexer} class represents lexical analyzer for subset of Java
 * language.
 *
 * @author Ira Korshunova
 *
 */
public class Lexer {
    static int check=0,charcheck=0;
    boolean mark=true;
    boolean charmark=true;
    int lineNumber=0;
    /** Mapping from type of token to its regular expression */
    private Map<TokenType, String> regEx;

    /** List of tokens as they appear in the input source */
    private List<Token> result;

    private List<LexicalError> error;
    /**
     * Initializes a newly created {@code Lexer} object
     */
    public Lexer() {
        regEx = new TreeMap<TokenType, String>();
        launchRegEx();
        result = new ArrayList<Token>();
        error=new ArrayList<LexicalError>();
    }

    /**
     * Performs the tokenization of the input source code.
     *
     * @param source
     *            string to be analyzed
     * @throws AnalyzerException
     *             if lexical error exists in the source
     *
     */
    public void tokenize(String source) throws AnalyzerException {
        int position = 0;
        Token token = null;
        do {
            token = separateToken(source, position);
//			System.out.println(token.getBegin() +"," + token.getEnd() +token.getTokenString() + token.getTokenType());
//			System.out.println(source.length());
            if (position != source.length()) {
                position = token.getEnd();
                result.add(token);
            }
        } while (position != source.length());
        if (position != source.length()) {
            throw new AnalyzerException("Lexical error at position # "+ position, position);

        }
    }

    /**
     * Returns a sequence of tokens
     *
     * @return list of tokens
     */
    public List<Token> getTokens() {
        return result;
    }

    public List<LexicalError> getError(){return error;};

    /**
     * Returns a sequence of tokens without types {@code BlockComment},
     * {@code LineComment} , {@code NewLine}, {@code Tab}, {@code WhiteSpace}
     *
     * @return list of tokens
     */
    public List<Token> getFilteredTokens() {
        List<Token> filteredResult = new ArrayList<Token>();
        for (Token t : this.result) {
            if (!t.getTokenType().isAuxiliary()) {
                filteredResult.add(t);
            }
        }
        return filteredResult;
    }

    /**
     * Scans the source from the specific index and returns the first separated
     * token
     *
     * @param source
     *            source code to be scanned
     * @param fromIndex
     *            the index from which to start the scanning
     * @return first separated token or {@code null} if no token was found
     *
     */
    private Token separateToken(String source, int fromIndex) {
        if (fromIndex < 0 || fromIndex >= source.length()) {
            throw new IllegalArgumentException("Illegal index in the input stream!");
        }
        for (TokenType tokenType : TokenType.values()) {
            Pattern p = Pattern.compile(".{" + fromIndex + "}" + regEx.get(tokenType),
                    Pattern.DOTALL);
//			System.out.println("HELLO"+tokenType);
            Matcher m = p.matcher(source);
            if (m.matches()) {
//				System.out.println(fromIndex);
                String lexema = m.group(1);
                if(lexema.equals("\n"))
                    lineNumber++;
//				System.out.println(lexema);
                if(lexema.equals("\"")){
                    if(check==0){
                        result.add(new Token(fromIndex, fromIndex+lexema.length(),lexema,tokenType,lineNumber));
                        check=1;
                    }
                    if(mark==true) {
                        String str = stringValidate(source.substring(fromIndex + 1));
//						System.out.println(str);
                        mark=false;
//						System.out.println(str.length());
                        return new Token(fromIndex + 1, fromIndex + 1 + str.length(), str, tokenType.String,lineNumber);
                    }
                    else if (mark==false){
                        mark = true;
                        return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType.InvertedComma,lineNumber);
                    }
                }
                else if (lexema.equals("\'")){
                    if(charcheck==0){
                        result.add(new Token(fromIndex, fromIndex+lexema.length(),lexema,tokenType,lineNumber));
                        charcheck=1;
                    }
                    if(charmark==true) {
                        char ch = characterValidate(source.substring(fromIndex + 1));
                        String str=Character.toString(ch);
//						System.out.println(str);
                        charmark=false;
//						System.out.println(str.length());
                        return new Token(fromIndex + 1, fromIndex + 1 + str.length(),str , tokenType.Character,lineNumber);
                    }
                    else if (charmark==false){
                        charmark = true;
                        return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType.SingleComma,lineNumber);
                    }
                }
                charmark=true;
                mark=true;
                return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType,lineNumber);
            }
        }
        String err=source.substring(fromIndex);
        String[] a=err.split(" ");
        LexicalError e = new LexicalError(lineNumber,a[0]);
        error.add(e);
        return null;
    }

    String stringValidate(String source){
        char[] arr=source.toCharArray();
        String s="";
        for(int i=0; i< arr.length;  i++) {
            if(Character.compare(arr[i],'\n')==0 || Character.compare(arr[i],'\"')==0)
                break;
            s = s + arr[i];

        }
        return s;
    }


    char characterValidate(String source){
        char[] arr=source.toCharArray();
        char s=arr[0];
        return s;
    }
    /**
     * Creates map from token types to its regular expressions
     *
     */
    private void launchRegEx() {
        regEx.put(TokenType.BlockComment, "(/\\*.*?\\*/).*");
        regEx.put(TokenType.LineComment, "(//(.*?)[\r$]?\n).*");
        regEx.put(TokenType.WhiteSpace, "( ).*");
        regEx.put(TokenType.OpenBrace, "(\\().*");
        regEx.put(TokenType.CloseBrace, "(\\)).*");
        regEx.put(TokenType.OpenArray, "(\\[).*");
        regEx.put(TokenType.CloseArray, "(\\]).*");

        regEx.put(TokenType.Semicolon, "(;).*");
        regEx.put(TokenType.Comma, "(,).*");
        regEx.put(TokenType.OpeningCurlyBrace, "(\\{).*");
        regEx.put(TokenType.ClosingCurlyBrace, "(\\}).*");
        regEx.put(TokenType.DoubleConstant, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regEx.put(TokenType.IntConstant, "\\b(\\d{1,9})\\b.*");
        regEx.put(TokenType.Void, "\\b(void)\\b.*");
        regEx.put(TokenType.Int, "\\b(int)\\b.*");
        regEx.put(TokenType.Double, "\\b(int|double)\\b.*");
        regEx.put(TokenType.Tab, "(\\t).*");
        regEx.put(TokenType.NewLine, "(\\n).*");
        regEx.put(TokenType.Class,"\\b(class)\\b.*");
        regEx.put(TokenType.StringClass,"\\b(String)\\b.*");
        regEx.put(TokenType.Public, "\\b(public)\\b.*");
        regEx.put(TokenType.Default, "\\b(default)\\b.*");
        regEx.put(TokenType.Private, "\\b(private)\\b.*");
        regEx.put(TokenType.Protected,"\\b(protected)\\b.*");
        regEx.put(TokenType.False, "\\b(false)\\b.*");
        regEx.put(TokenType.True, "\\b(true)\\b.*");
        regEx.put(TokenType.Null, "\\b(null)\\b.*");
        regEx.put(TokenType.Return, "\\b(return)\\b.*");
        regEx.put(TokenType.New, "\\b(new)\\b.*");
        regEx.put(TokenType.If, "\\b(if)\\b.*");
        regEx.put(TokenType.Final, "\\b(final)\\b.*");
        regEx.put(TokenType.Interface, "\\b(interface)\\b.*");
        regEx.put(TokenType.Abstract, "\\b(abstract)\\b.*");
        regEx.put(TokenType.Else_if,"\\b(else if)\\b.*");
        regEx.put(TokenType.Else, "\\b(else)\\b.*");
        regEx.put(TokenType.While, "\\b(while)\\b.*");
        regEx.put(TokenType.Static, "\\b(static)\\b.*");
        regEx.put(TokenType.This, "\\b(this)\\b.*");
        regEx.put(TokenType.Super, "\\b(super)\\b.*");
        regEx.put(TokenType.Implements, "\\b(implements)\\b.*");
        regEx.put(TokenType.Extends, "\\b(extends)\\b.*");
        regEx.put(TokenType.Switch, "\\b(switch)\\b.*");
        regEx.put(TokenType.Case, "\\b(case)\\b.*");
        regEx.put(TokenType.Exception, "\\b(Exception)\\b.*");
        regEx.put(TokenType.Try, "\\b(try)\\b.*");
        regEx.put(TokenType.Catch, "\\b(catch)\\b.*");

        regEx.put(TokenType.Colon, "(:).*");

        regEx.put(TokenType.Do, "\\b(do)\\b.*");

        regEx.put(TokenType.Point, "(\\.).*");
        regEx.put(TokenType.Plus, "(\\+{1}).*");
        regEx.put(TokenType.Minus, "(\\-{1}).*");
        regEx.put(TokenType.Increment,"(\\+\\+).*");
        regEx.put(TokenType.Decrement,"(--).*");
        regEx.put(TokenType.Multiply, "(\\*).*");
        regEx.put(TokenType.Divide, "(/).*");
        regEx.put(TokenType.EqualEqual, "(==).*");
        regEx.put(TokenType.Equal, "(=).*");
        regEx.put(TokenType.Not,"(\\!).*");
        regEx.put(TokenType.And,"(&&).*");
        regEx.put(TokenType.Or,"(\\|\\|).*");
        regEx.put(TokenType.ExclameEqual, "(\\!=).*");
        regEx.put(TokenType.Greater, "(>).*");
        regEx.put(TokenType.Less, "(<).*");
        regEx.put(TokenType.LessEqual, "(<=).*");
        regEx.put(TokenType.GreaterEqual, "(>=).*");
        regEx.put(TokenType.For,"\\b(for)\\b.*");
        regEx.put(TokenType.InvertedComma, "(\").*");
        regEx.put(TokenType.SingleComma, "(\').*");
        regEx.put(TokenType.Identifier, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
//		regEx.put(TokenType.String,"((\\s|\\W|\\w)*)");
		regEx.put(TokenType.CharacterClass,"Character");
    }
}