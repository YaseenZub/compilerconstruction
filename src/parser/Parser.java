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
        this.index = 0;
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

    public boolean dec() { //2 int c1
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass")) {
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Identifier")) {
                index++; // Equals
                s = TokenType.toString(ts.get(index).getTokenType());
                if (init()) { //3 dec =
                    if (list())
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
                    if (list()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean init() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Equal")) {
            index++; // intconstant dec
            if (OE()) { //OE int contsant (3) 4
                return true; //chala hai ye ;
            }
        } else if (s.equals("Semicolon") || s.equals("Comma")) {
            return true;
        }

        return false;
    }

    public boolean OE() { //4 i
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (AE()) { //5 intconstant 3
                if (OEdash()) {
                    return true;
                }
            }
        }
        System.out.println("YAHIN SE FALSE OE" + s);

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

        System.out.println("YAHIN SE FALSE OEdash" + s);

        return false;
    }


    public boolean AE() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (RE()) {
                if (AEdash()) {
                    return true;
                }
            }
        }
        System.out.println("YAHIN SE FALSE AE" + s);

        return false;
    }

    public boolean AEdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("And")) {
            index++;
            if (RE()) {
                if (AEdash()) {
                    return true;
                }
            }
        } else {
            if (s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace") || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")) {
                return true;
            }
        }
        System.out.println("YAHIN SE FALSE AEdash" + s);

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
        System.out.println("YAHIN SE FALSE RE" + s);

        return false;
    }

    public boolean REdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")) {
            index++;
            if (E()) {
                if (REdash()) {
                    return true;
                }
            }
        } else {
            if (s.equals("And") || s.equals("Or") || s.equals("Comma") ||
                    s.equals("ClosingCurlyBrace") || s.equals("Semicolon") || s.equals("CloseBrace")
                    || s.equals("CloseArray")) {
                return true;
            }
        }
        System.out.println("YAHIN SE FALSE REdash" + s);

        return false;
    }

    public boolean E() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (T()) {
                if (Edash()) {
                    return true;
                }
            }
        }
        System.out.println("YAHIN SE FALSE E" + s);

        return false;
    }

    public boolean Edash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Plus") || s.equals("Minus")) {
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if (T()) {
                if (Edash()) {
                    return true;
                }
            }
        } else {
            if (s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")
                    || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
                    || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")) {
                return true;
            }
        }
        System.out.println("YAHIN SE FALSE Edash" + s);

        return false;
    }

    public boolean T() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if (F()) {
                if (Tdash()) {
                    return true;
                }
            }

        }
        System.out.println("YAHIN SE FALSE T" + s);

        return false;
    }

    public boolean Tdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Multiply") || s.equals("Divide")) {
            index++;
            if (F()) {
                if (Tdash()) {
                    return true;
                }
            }
        } else {
            if (s.equals("Plus") || s.equals("Minus") || s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")
                    || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
                    || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")) {
                return true;
            }
        }
        System.out.println("YAHIN SE FALSE Tdash" + s);

        return false;
    }

    public boolean F() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier")) {
            index++;
            if (Fdash()) {
                return true;
            }
        } else {
            if (s.equals("DoubleConstant") || s.equals("IntConstant") || s.equals("String") || s.equals("Character")) {
                index++;
                return true;
            } else if (s.equals("OpenBrace")) {
                index++;

                if (OE()) {
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if (s.equals("CloseBrace")) {
                        return true;
                    }
                }
            } else if (s.equals("Not")) {
                index++;
                if (F()) {
                    return true;
                }
            }
        }
        System.out.println("YAHIN SE FALSE F" + s);

        return false;
    }

    public boolean Fdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println(s);
        if (s.equals("Point") || s.equals("OpenBrace") || s.equals("Plus") || s.equals("OpenArray") || s.equals("Minus") || s.equals("Multiply") || s.equals("Divide") || s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")
                || s.equals("And") || s.equals("Or") || s.equals("Comma") || s.equals("ClosingCurlyBrace")
                || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")) {
            System.out.println("IDHR  " + s);

            if (Xdash()) {
                return true;
            }
            if (s.equals("OpenBrace")) {
                index++;
                if (args()) {
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if (s.equals("CloseBrace")) {
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (s.equals("Semicolon")) {
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("YAHIN SE FALSE Fdash" + s);
        return false;
    }

    public boolean Xdash() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Point")) {
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Identifier")) {
                index++;
                if (Xdash())
                    return true;
            }
        } else if (s.equals("OpenArray")) {
            index++;
            if (OE()) {
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("CloseArray")) {
                    return true;
                }
            }
        } else if (s.equals("OpenBrace")) {
            index++;
            if (args()) {
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("CloseBrace")) {
                    index++;
                    if (Xdash()) {
                        return true;
                    }
                }
            }
        } else {
            if (s.equals("Multiply") || s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")
                    || s.equals("And") || s.equals("Divide") || s.equals("Or") || s.equals("Plus") || s.equals("Minus")
                    || s.equals("Comma") || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")
                    || s.equals("Equal") || s.equals("Increment") || s.equals("Decrement")) {
                return true;
            }
        }
        System.out.println("YAHIN SE FALSE Xdash" + s);

        return false;
    }


    public boolean params() {


        return false;
    }

    public boolean args() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("OpenBrace") || s.equals("IntConstant") || s.equals("String")
                || s.equals("Character") || s.equals("DoubleConstant") || s.equals("Comma") || s.equals("CloseBrace")) {
            if (OE()) {
                if (args_1()) {
                    return true;
                }
            }
        } else {
            if (s.equals("CloseBrace")) {
                return true;
            }
        }
        return false;
    }

    public boolean args_1() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Comma")) {
            index++;
            if (OE()) {
                if (args_1()) {
                    return true;
                }
            }
        } else if (s.equals("CloseBrace")) {
            return true;
        }
        return false;
    }

    public boolean For() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("For")) {
            index++; // (
            s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("OpenBrace")) {
                index++; // int
                if (c1()) { //1 int
                    if (c2()) { //identifier i
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (s.equals("Semicolon")) {
                            index++;
                            if (c3()) {
                                s = TokenType.toString(ts.get(index).getTokenType());
                                String f=ts.get(index).getTokenString();
                                System.out.println("FOR C3" +f);

                                if (s.equals("CloseBrace")) {
                                    index++;
                                    if (Body()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("YAHIN SE FALSE for" + s);
        return false;
    }

    public boolean c1() { //1 int
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass")
                || s.equals("This") || s.equals("Super")
        ) {
            if (dec()) { //2 int dec
                return true;
            } else if (assignment()) {
                return true;
            } else if (s.equals("Identifier") || s.equals("Not") || s.equals("OpenBrace") || s.equals("SemiColon")
                    || s.equals("IntConstant") || s.equals("DoubleConstant") || s.equals("String") || s.equals("Character")) {
                return true;
            }



        }
        System.out.println("YAHIN SE FALSE c1" + s);
        return false;
    }

    public boolean c2() {
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character")
                || s.equals("OpenBrace") || s.equals("Not")) {
            if (OE()) {
                return true;
            }

        } else {
            if (s.equals("Semicolon")) {
                return true;
            }
        }

        System.out.println("YAHIN SE FALSE c2" + s);

        return false;

    }
//    public boolean c3(){
//        String s = TokenType.toString(ts.get(index).getTokenType());
//        if(s.equals("This") || s.equals("Super") || s.equals("CloseBrace")||
//        s.equals("Identifier") || s.equals("Increment") || s.equals("Decrement")){
//            if(inc_dec()){
//                return true;
//            }
//            else if(assignment()){
//                return true;
//            }
//        }
//        else if(s.equals("CloseBrace")){
//
//            return true;
//        }
//        System.out.println("YAHIN SE FALSE c3" + s);
//
//        return false;
//    }

    public boolean c3(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("This") || s.equals("Super")){
            if(th_su()){
                if(X()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        index++;
                        if(E()){
                            return true;
                        }
                    }
                    else if(s.equals("Increment") || s.equals("Decrement")){
                        index++;
                        return true;
                    }
                }
            }

        }
        else{
            if(s.equals("Identifier")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Equal")){
                    index++;
                    if(E()){
                        return true;
                    }
                }
                else if(s.equals("Increment") || s.equals("Decrement")){
                    index++;
                    return true;
                }
            }

        }


        return false;
    }

    public  boolean assignment(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("This")||s.equals("Super")){
                if(th_su()){
                    if(X()){
                        s = TokenType.toString(ts.get(index).getTokenType());

                        if(s.equals("Equal")){
                            index++;
                            if(E()){
                                return true;
                            }
                        }
                    }
                }
        }
        else if(s.equals("Identifier")||s.equals("Not")||s.equals("OpenBrace")||
        s.equals("IntConstant")||s.equals("DoubleConstant") ||s.equals("String")||
        s.equals("Character") || s.equals("CloseBrace")){
            return true;
        }
        System.out.println("YAHIN SE FALSE assignment" + s);

        return false;
    }

    public boolean X(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Identifier")){
            index++;
            if(Xdash()){
                return true;
            }
        }

        System.out.println("YAHIN SE FALSE X" + s);

        return false;
    }

    public boolean inc_dec(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println("INC_DEC"+ s);
        if(s.equals("Increment") || s.equals("Decrement") || s.equals("Identifier")){
            if(s.equals("Increment")){
                index++;
                if(X())
                    return true;
            }
            else if(s.equals("Decrement")){
                index++;
                if(X())
                    return true;
            }
            else if(s.equals("Identifier")){
                if(X()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Increment")){
                        index++;
                        return true;
                    }
                    else if(s.equals("Decrement")){
                        index++;
                        return true;
                    }
                }
            }
        }
        System.out.println("YAHIN SE FALSE inc_dec" + s);

        return false;
    }

    public boolean th_su(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("This") || s.equals("Super")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Point")){
                    index++;
                    return true;
                }

        }
        else if(s.equals("Identifier") || s.equals("Semicolon")){
            return true;
        }
        System.out.println("YAHIN SE FALSE th_su" + s);

        return false;
    }

    public boolean Body(){
            String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println("BODY ME AYA");
            if (s.equals("Semicolon") || s.equals("OpeningCurlyBrace")) {
                if (s.equals("Semicolon")) {
                    System.out.println("YAHANA AANA CHAIYE");
                    index++;
                    return true;
                } else if (SST()) {
                    return true;
                } else if (s.equals("OpeningCurlyBrace")) {
                    index++;
                    if (MST()) {
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (s.equals("ClosingCurlyBrace")) {
                            index++;
                            return true;
                        }
                    }
                }
            }

        s = TokenType.toString(ts.get(index).getTokenType());

        System.out.println("YAHIN SE FALSE body" +s);

        return false;
    }

    public boolean MST(){
        return true;
    }

    public boolean SST(){
        return false;
    }
}

