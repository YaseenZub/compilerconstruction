package parser;
import lexer.Lexer;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    public static int index = 0;
    List<Token> ts;
    int[]  a =new int[]{1,2};
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
                if(c_def()){
                    if(def()){
                        return true;
                    }
                }
                else if(inter_def()){
                    if(def()){
                        return true;
                    }
                }

        }
        else {
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("$")){
                return true;
            }
        }
        return false;
    }

    public boolean def(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Default") || s.equals("Final")
                || s.equals("Abstract") || s.equals("Interface") || s.equals("Class")) {
            if(c_def()){
                if(def()){
                    return true;
                }
            }
            else if(inter_def()){
                if(def()){
                    return true;
                }
            }
        }
        else{
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("$")){
                return true;
            }
        }

        return false;
    }


    public boolean inter_def(){
        return false;
    }
    public boolean c_def(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Public")||s.equals("Private")||s.equals("Protected")||
        s.equals("Final")||s.equals("Abstract") ||s.equals("Static") || s.equals("Class")){
            if(AM()){
                if(fin_ab()){
                    if(Static()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Class")){
                            index++;
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Identifier")){
                                index++;
                                if(inherit()){
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    if(s.equals("OpeningCurlyBrace")){
                                        index++;
                                        if(c_body()){
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            if(s.equals("ClosingCurlyBrace")){
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean AM(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Private") || s.equals("Public") || s.equals("Protected")){
            index++;
            return true;
        }
        else if(s.equals("Final") || s.equals("Abstract") || s.equals("Static") || s.equals("Class")|| s.equals("Int") || s.equals("Double") || s.equals("CharacterClass")||s.equals("StringClass") || s.equals("Void")){
            return true;
        }

        return false;
    }
    public boolean fin_ab(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println("fin_ab    "+s);
        if(s.equals("Final") || s.equals("Abstract")){
            index++;
            return true;
        }
        else{
            if(s.equals("Identifier") || s.equals("Static") || s.equals("Class") || s.equals("Int") || s.equals("CharacterClass")
            || s.equals("Double") || s.equals("StringClass") || s.equals("Void")){
                return true;
            }
        }


        return false;
    }

    public boolean Static(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println("IN static "  + s);
        if(s.equals("Static")){
            index++;
            return true;
        }
        else{
            if(s.equals("Final") || s.equals("Abstract") || s.equals("Class") || s.equals("Identifier")||s.equals("Double") || s.equals("StringClass")
            || s.equals("Int") || s.equals("CharacterClass") || s.equals("Void")){
                return true;
            }
        }

        return false;
    }

    public boolean inherit(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("extends")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(impl()){
                    return true;
                }
            }
        }
        else if(s.equals("OpeningCurlyBrace"))
            return true;

        return false;
    }

    public boolean impl(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Implements")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(inherit2()){
                    return true;
                }
            }

        }
        else{
            if(s.equals("OpeningCurlyBrace") || s.equals("Extends")){
                return true;
            }
        }

        return false;
    }

    public boolean inherit2(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Comma")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(inherit2()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("OpeningCurlyBrace") || s.equals("Extends")){
                return true;
            }
        }

        return false;
    }

    public boolean c_body(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Void") || s.equals("Identifier")){
            if(dec()){
                if(c_body()){
                    return true;
                }
            }else if(s.equals("Identifier")){
                index++;
                if(c_body()){
                    return true;
                }
            }
            else if(s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Void")){
                index++;
                
            }
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



    public boolean fun_def(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Static") || s.equals("Int") || s.equals("Void")
        || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Final") || s.equals("Abstract")
        ){
            if(AM()){
                System.out.println("IDHR");
                if(Static()){
                    if(fin_ab()){
                        if(return_type()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Identifier")){
                                index++;
                                s = TokenType.toString(ts.get(index).getTokenType());

                                if(s.equals("OpenBrace")){
                                    index++;


                                    if(params()){
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if(s.equals("CloseBrace")){
                                            index++;
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            if(s.equals("OpeningCurlyBrace")){
                                                index++;
                                                if(MST()) {
                                                    s = TokenType.toString(ts.get(index).getTokenType());
                                                    if (s.equals("ClosingCurlyBrace")){
                                                        index++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        else {
            if(s.equals("Identifier") || s.equals("Class")){
                return true;
            }
        }

        return false;
    }

    public boolean return_type(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Double") || s.equals("Int") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Void")){
            index++;
            return true;
        }


        return false;
    }

    public boolean if_else(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("If")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenBrace")){
                index++;
                if(OE()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        System.out.println("YAHAN OE ME  " +s);
                        if(Body()){
                            if(else_if()) {
                                if (Else())
                                    return true;
                            }
                            else {
                                return true;
                            }

                        }

                    }
                }
            }
        }
        s = TokenType.toString(ts.get(index).getTokenType());
        System.out.println("BTAO  Pehle"+s);
        if(s.equals("EndMarker")){
            System.out.println("BTAO  "+s);
            return true;
        }
        return false;
    }
    public boolean Else(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Else")){
            index++;
            if(Body()){
                return true;
            }
        }
        else if(s.equals("Identifier")||s.equals("For")||s.equals("While")||s.equals("Do")||s.equals("Continue")||s.equals("Break")
        || s.equals("Return") || s.equals("Switch") || s.equals("This") || s.equals("Super") || s.equals("Public") || s.equals("Private")
        || s.equals("Protected") || s.equals("Try") || s.equals("If") || s.equals("else") || s.equals("else if")||s.equals("Increment") || s.equals("Decrement")){
            return true;
        }


        return false;
    }
    public boolean else_if(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Else_if")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenBrace")){
                index++;
                if(OE()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace")){
                        index++;
                        if(Body()){
                            if(else_if()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if(s.equals("OpenBrace") || s.equals("Else")){
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
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Int") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(P1()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("CloseBrace")){
                return true;
            }
        }

        return false;
    }

    public boolean P1(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Comma")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Int")|| s.equals("Double")||s.equals("CharacterClass")||s.equals("StringClass")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("Identifier")) {
                    index++;
                    if (P1()) {
                        return true;
                    }
                }
            }

        }
        else if(s.equals("CloseBrace")){
            return true;
        }


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
            }



        }
        else if ( s.equals("Not") || s.equals("OpenBrace") || s.equals("Semicolon")
                || s.equals("IntConstant") || s.equals("DoubleConstant") || s.equals("String") || s.equals("Character")) {
            System.out.println("for ka scene" +s);
            return true;
        }
        else if(s.equals("Identifier")){
            index++;
            if(init()){
                 s = TokenType.toString(ts.get(index).getTokenType());
                index++;
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
        else if(s.equals("CloseBrace")){
            return true;
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
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Identifier") || s.equals("StringClass") || s.equals("CharacterClass")||s.equals("Double")||s.equals("Int") || s.equals("For")
                || s.equals("While") || s.equals("Do") || s.equals("Continue") || s.equals("Break") || s.equals("Return") || s.equals("Switch") || s.equals("If")
                || s.equals("Else_if") || s.equals("Else") || s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Decrement") ||s.equals("Increment")
                || s.equals("Try")) {
            if (SST()) {
                if(MST())
                    return true;
            }
        }
        else if(s.equals("ClosingCurlyBrace")){
            return true;
        }

        return false;
    }

    public boolean SST(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Identifier") || s.equals("StringClass") || s.equals("CharacterClass")||s.equals("Double")||s.equals("Int") || s.equals("For")
        || s.equals("While") || s.equals("Do") || s.equals("Continue") || s.equals("Break") || s.equals("Return") || s.equals("Switch") || s.equals("If")
        || s.equals("Else_if") || s.equals("Else") || s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Decrement") ||s.equals("Increment")
        || s.equals("Try")){

            if(s.equals("This")){
                index++;
                if(X()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        if(E()){
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("Super")){
                index++;
                if(X()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        if(E()){
                            return true;
                        }
                    }
                }
            }
            else if(inc_dec()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Semicolon")){
                    index++;
                    return true;
                }
            }
            else if(Array()){
                return true;
            }
            else if(init()){
                s = TokenType.toString(ts.get(index).getTokenType());
                System.out.println("init sst" + s);
                if(s.equals("Semicolon")){
                    index++;
                    return true;
                }
            }
            else if(assignment()){
                return true;

            }
            else if(s.equals("Identifier")){
                index++;
                if(SST_A()){
                    return true;
                }
            }
            else if(For()){
                return true;
            }

            else if(dec()){
                return true;
            }
            else if(if_else()){
                return true;
            }
            else if(While()){
                return true;
            }
            else if(Do()){
                return true;
            }
            else if(fun_def()){
                return true;
            }

//            else if(Continue()){
//                return true;
//            }
//            else if(Break()){
//                return true;
//            }
//            else if(Return()){
//                return true;
//            }
//
//            else if(Switch()){
//                return true;
//            }
//
//            else if(try_catch()){
//                return true;
//            }



        }

        return false;
    }
    public boolean SST_A(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Point") || s.equals("OpenBrace") || s.equals("Equal") || s.equals("Identifier") || s.equals("Increment") || s.equals("Decrement")){
            if(SSTdash()){
                return true;
            }
            else if(s.equals("Equal")){
                index++;
                if(E()){
                    return true;
                }
            }
            else if(s.equals("Identifier")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Equal")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("New")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
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
                }
            }
        }

        return false;
    }
    public boolean SSTdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Point") || s.equals("OpenBrace")){
            if(Xdash()){
                if(inc_dec()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Semicolon")){
                        index++;
                        return true;
                    }
                }
            }
            else if(s.equals("OpenBrace")){
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
        else if(s.equals("Multiply") || s.equals("Less") || s.equals("Greater") || s.equals("EqualEqual") || s.equals("ExclameEqual")
                || s.equals("And") || s.equals("Divide") || s.equals("Or") || s.equals("Plus") || s.equals("Minus")
                || s.equals("Comma") || s.equals("Semicolon") || s.equals("CloseBrace") || s.equals("CloseArray")
                || s.equals("Equal") || s.equals("Increment") || s.equals("Decrement")){
            return true;
        }

        return false;
    }

    public boolean Array(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Static") || s.equals("Final") || s.equals("Int") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Double")){
                if(Static()){
;                   if(Final()){
                        System.out.println("sssh");
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Int") || s.equals("Double") || s.equals("CharacterClass")
                        ||s.equals("StringClass")){
                            index++;
                            if(brackets()){
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(s.equals("Identifier")){
                                      index++;
                                    if(ass_arr()){
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if(s.equals("Semicolon"))
                                            index++;
                                            return true;
                                    }
                                }
                            }
                        }
                    }
                }
        }
        else if(s.equals("Int") || s.equals("Double") || s.equals("CharacterClass")
        || s.equals("StringClass") || s.equals("")||s.equals("Abstract") || s.equals("Public")
        || s.equals("Protected") || s.equals("Private") || s.equals("Class") || s.equals("Void") || s.equals("}")) {
            return true;
        }

        return false;
    }

    public boolean Final(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Final")){
            index++;
            return true;
        }
        else{
            if(s.equals("Int") || s.equals("CharacterClass") || s.equals("StringCLass") || s.equals("Double") ){
                return true;
            }
        }

        return false;
    }

    public boolean brackets(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("OpenArray")){
            index++;
            if(OE()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    if(bracket2()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean bracket2(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("OpenArray")){
            index++;
            if(OE()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    return true;
                }

            }
        }
        else if(s.equals("Identifier")){
            return true;
        }

        return false;
    }

    public boolean ass_arr(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Equal")){
            index++;
            if(ass_arrdash()){
                return true;
            }
        }
        else if(s.equals("Int") || s.equals("StringClass") || s.equals("CharacterClass")
        ||s.equals("Double")|| s.equals("Int") || s.equals("Static") || s.equals("Final")
        ||s.equals("Abstract")||s.equals("Public")||s.equals("Private")||s.equals("Protected")
        ||s.equals("Class")||s.equals("Void")||s.equals("ClosingCurlyBrace")){
            return true;
        }

        return false;
    }

    public boolean ass_arrdash(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("OpeningCurlyBrace")){
            index++;
            if(val1d()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("ClosingCurlyBrace")){
                    index++;
                    return true;
                }
            }
            else if(val2d()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("ClosingCurlyBrace")){
                    index++;
                    return true;
                }
            }
            else if(ass_arrdash2()){
                return true;
            }


        }

        return false;
    }

    public boolean ass_arrdash2(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("OpenCurlyBrace")||s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not"))
        {
            if(val1d()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("ClosingCurlyBrace")){
                    index++;
                    return true;
                }
            }
            else if(val2d()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("ClosingCurlyBrace")){
                    index++;
                    return true;
                }
            }

        }
            return false;
    }
    public boolean A(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Comma")){
            index++;
            if(OE()){
                if(A()){
                    return true;
                }
            }
        }
        else{
            if(s.equals("ClosingCurlyBrace")){
                return true;
            }
        }
        return false;
    }

    public boolean val2d(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("OpeningCurlyBrace")){
            index++;
            if(val1d()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("ClosingCurlyBrace")){
                    index++;
                    if(A2()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean val1d(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if (s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")) {
            if(OE()){
                if(A()){
                    return true;
                }
            }
        }

            return false;
    }

    public boolean A2(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Comma")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                index++;
                if(val1d()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        index++;
                        if(A2()){
                            return true;
                        }
                    }
                }
            }
        }
        else if(s.equals("ClosingCurlyBrace")){
            return true;
        }

        return false;
    }
    public boolean While(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("While")){
            index++;
            s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenBrace")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(OE()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace")){
                        index++;
                        if(Body()){
                            return true;
                        }

                    }
                }
            }


        }
        return false;
    }

    public boolean Do(){
        String s = TokenType.toString(ts.get(index).getTokenType());
        if(s.equals("Do")){
            index++;
            if(Body()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("While")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenBrace")){
                        index++;
                        if(OE()){
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
            }
        }


        return false;
    }
}

