package parser;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    static int index = 0;
    List<Token> ts;

    public Parser(List<Token> ts) {
        this.ts = ts;
        this.index=0;
    }

    public List<Token> getToken() {
        return ts;
    }

    public boolean S() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Default") || s.equals("Final")
                || s.equals("Abstract") || s.equals("Interface") || s.equals("Class")) {
//            if(def()){
//
//            }
        }
        return false;
    }

    public boolean dec() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Int") || s.equals("Double")) {
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Identifier")) {
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (init()) {
                    if(list())
                        return true;
                }
            }

        }


        return false;
    }

    public boolean list() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Semicolon")) {
            index++;
            return true;
        } else if (s.equals("Comma")) {
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Identifier")) {
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (init()) {
                    if(list()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public boolean init(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Equal")){
            index++;
            if(OE()){
                return true;
            }
        }
        else if(s.equals("Semicolon") || s.equals("Comma")){
            return true;
        }

        return false;
    }

    public boolean OE() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (AE()) {
                if (OEdash()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean OEdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Or")) {
            index++;
            if (AE()) {
                if (OEdash()) {
                    return true;
                }
            }
        } else {
            if (s.equals("Comma") || s.equals("ClosingCurlyBrace") || s.equals("CloseBrace") || s.equals("Semicolon") ||
                    s.equals("CloseArray")) {
                return true;
            }
        }


        return false;
    }


    public boolean AE() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (RE()) {
                if(AEdash()){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean AEdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("And")){
            index++;
            if(RE()){
                if(AEdash()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("Or")||s.equals("Comma")||s.equals("ClosingCurlyBrace")||s.equals("Semicolon")||s.equals("CloseBrace")||s.equals("CloseArray")){
                return true;
            }
        }

        return false;
    }



    public boolean RE() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (E()) {
                if (REdash()) {
                        return true;
                }
            }
        }

        return false;
    }
    public boolean REdash(){
        String s=TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Less")||s.equals("Greater")||s.equals("EqualEqual")||s.equals("ExclameEqual")){
            index++;
            if(E()){
                if(REdash()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("And")||s.equals("Or")||s.equals("Comma")||
            s.equals("ClosingCurlyBrace") || s.equals("Semicolon") || s.equals("CloseBrace")
                   || s.equals("CloseArray")){
                return true;
            }
        }

        return false;
    }
    public boolean E() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
                if(T()){
                    if(Edash()){
                        return true;
                    }
                }
        }
        return false;
    }
    public boolean Edash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Plus") || s.equals("Minus")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(T()){
                if(Edash()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("Less")||s.equals("Greater")||s.equals("EqualEqual")||s.equals("ExclameEqual")
            || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
            || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray"))
            {
                return true;
            }
        }
        return false;
    }

    public boolean T(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if(F()){
                if(Tdash()){
                    return true;
                }
            }

        }

            return false;
    }

    public boolean Tdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Multiply") || s.equals("Divide")){
            index++;
            if(F()){
                if(Tdash()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("Plus") || s.equals("Minus") || s.equals("Less")||s.equals("Greater")||s.equals("EqualEqual")||s.equals("ExclameEqual")
            || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
                    || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray"))
            {
                return true;
            }
        }

        return false;
    }

    public boolean F(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Identifier")){
            index++;
            if(Fdash()){
                return true;
            }
        }
        else{
            if(s.equals("DoubleConstant") || s.equals("IntConstant") || s.equals("String") || s.equals("Character")){
                index++;
                return true;
            }
            else if(s.equals("OpenBrace")){
                index++;

                if(OE()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace")){
                        return true;
                    }
                }
            }
            else if(s.equals("Not")){
                index++;
                if(F()){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean Fdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println(s);
        if(s.equals("Point") || s.equals("OpenBrace")|| s.equals("Plus") || s.equals("OpenArray")|| s.equals("Minus") ||s.equals("Multiply") || s.equals("Divide")|| s.equals("Less")||s.equals("Greater")||s.equals("EqualEqual")||s.equals("ExclameEqual")
        || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
        || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray"))
        {
            System.out.println("IDHR  " + s);

            if(Xdash()){
                return true;
            }
            if(s.equals("OpenBrace")){
                index++;
                if(args()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("YAHIN SE FALSE" +s);
        return false;
    }

    public boolean Xdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Point")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(Xdash())
                    return true;
            }
        }
        else if(s.equals("OpenArray"))
        {
            index++;
            if(OE()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    return true;
                }
            }
        }
        else if (s.equals("OpenBrace")){
            index++;
            if(args()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseBrace")){
                    index++;
                    if(Xdash()){
                        return true;
                    }
                }
            }
        }
        else{
            if(s.equals("Multiply")||s.equals("Less")||s.equals("Greater")||s.equals("EqualEqual")||s.equals("ExclameEqual")
            || s.equals("And") || s.equals("Divide") || s.equals("Or") || s.equals("Plus") || s.equals("Minus")
            || s.equals("Comma") || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")
            || s.equals("Equal") || s.equals("Increment") || s.equals("Decrement")){
                return true;
            }
        }
        return false;
    }


    public boolean params(){



        return false;
    }

    public boolean args(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Identifier")||s.equals("OpenBrace")||s.equals("IntConstant")||s.equals("String")
        ||s.equals("Character")||s.equals("DoubleConstant") || s.equals("Comma") || s.equals("CloseBrace"))
        {
            if(OE()){
                if(args_1()){
                    return true;
                }
            }
        }
        else {
            if(s.equals("CloseBrace")){
                return true;
            }
        }
        return false;
    }
    public boolean args_1(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Comma")){
            index++;
            if(OE()){
                if(args_1()){
                    return true;
                }
            }
        }
        else if(s.equals("CloseBrace")){
            return true;
        }
        return false;
    }
}

