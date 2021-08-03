package parser;
import exceptions.*;
import lexer.Lexer;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import SemanticUtility.*;

import javax.swing.*;

public class Parser {
        SemanticAnalyzer sem=new SemanticAnalyzer();
        String inherit_mt=null;
        String class_name_mt=null;
        String uuid;
        String category_name_mt="General";
        String type_mt=null;

        String am_mt=null;
        Stack stack = new Stack();
        int scope=1;
        public static int index = 0;
        List<Token> ts;
        int[]  a =new int[]{1,2};
        SemanticException semanticException;
        JFrame frame;
    String type=null;
    ClassBody cb =new ClassBody();

    //FUNCTION TABLE
    ArrayList<Function> arrayList=new ArrayList<>();
    Function function_table = new Function();
        public Parser(List<Token> ts, JFrame frame) {
            this.frame=frame;
            this.ts = ts;
            this.index = 0;

        }

        public List<Token> getToken() {
            return ts;
        }

        public boolean S() {
            String s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Default") || s.equals("Final")
                    || s.equals("Abstract") || s.equals("Interface") || s.equals("Class")||s.equals("Static")) {
                    if(c_def()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        System.out.println("IDHR S KIV ALUE HAU"+s);
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

                s = TokenType.toString(ts.get(index).getTokenType());
            System.out.println("S HAI YE"+s);
                if(s.equals("EndMarker")){
                    return true;
                }

            return false;
        }

        public boolean def(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if (s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Default") || s.equals("Final")
                    || s.equals("Abstract") || s.equals("Interface") || s.equals("Class") ||s.equals("Static")) {
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

                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("EndMarker")){
                    return true;
                }


            return false;
        }


        public boolean inter_def(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Interface")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")){
                    index++;
                    if(inter_inherit()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("OpeningCurlyBrace")){
                            //TRYING:
                            stack.push(scope);
                            System.out.println("SCOPE FOR THIS IS " + scope);
                            scope++;
                            index++;
                            if(c_body()){
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(s.equals("ClosingCurlyBrace")){
                                    //POPPING STACK SCOPE
                                    int temp_stack=Integer.parseInt(stack.pop().toString());
                                    System.out.println("POPPING OUT SCOPE");
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }
        public boolean inter_inherit(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Extends")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")){
                    index++;
                    if(inherit2()){
                        return true;
                    }
                }
            }
            else if(s.equals("OpeningCurlyBrace")){
                return true;
            }

            return false;
        }


        public boolean c_defbekar(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Public")||s.equals("Private")||s.equals("Protected")||
            s.equals("Final")||s.equals("Abstract") ||s.equals("Static") || s.equals("Class")){
                System.out.println("YAHAN AOGE");

                if(AM()){
                    if(Static()){
                        if(Final()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Class")){
                                index++;
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(s.equals("Identifier")){
                                    index++;
                                    if(inherit()){
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if(s.equals("OpeningCurlyBrace")){
                                            //TRYING:
                                            stack.push(scope);
                                            System.out.println("SCOPE FOR THIS IS " + scope);
                                            index++;
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            System.out.println("CBODY ME BHEJA"+s);
                                            if(c_body()){
                                                s = TokenType.toString(ts.get(index).getTokenType());
                                                if(s.equals("ClosingCurlyBrace")){
                                                    //POPPING STACK SCOPE
                                                    int temp_stack=Integer.parseInt(stack.pop().toString());
                                                    System.out.println("POPPING OUT SCOPE");
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

        public boolean c_def(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Public")||s.equals("Private")||s.equals("Protected")||
                    s.equals("Final")||s.equals("Abstract") ||s.equals("Static") || s.equals("Class")){
                if(AM()){
                    if(Static()){
                        if(c_defdash()){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        public boolean c_defdash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Final")|| s.equals("Class")){
                if(Final()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Class")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Identifier")){
                            class_name_mt=ts.get(index).getTokenString();
                            index++;

                            s = TokenType.toString(ts.get(index).getTokenType());

                            if(inherit()) {
                                s = TokenType.toString(ts.get(index).getTokenType());
                                uuid = UUID.randomUUID().toString();
                                if(!sem.insert_MT(class_name_mt,type,category_name_mt,inherit_mt,uuid,am_mt)){
                                    System.out.println("Redeclaration Error");
                                    JOptionPane.showMessageDialog(frame, "Redeclaration Error", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }

                                class_name_mt=null;
                                type=null;
                                category_name_mt="General";
                                inherit_mt=null;
                                am_mt=null;


                                if (s.equals("OpeningCurlyBrace")) {
                                    //TRYING:
                                    stack.push(scope);
                                    System.out.println("SCOPE FOR THIS IS " + scope);
                                    scope++;
                                    index++;
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    if (c_body()) {
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if (s.equals("ClosingCurlyBrace")) {
                                            //POPPING STACK SCOPE
                                            int temp_stack=Integer.parseInt(stack.pop().toString());
                                            System.out.println("POPPING OUT SCOPE");
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
            else if(s.equals("Abstract")){
                category_name_mt="Abstract";
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Class")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Identifier")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (inherit()) {
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("OpeningCurlyBrace")){
                                //TRYING:
                                stack.push(scope);
                                System.out.println("SCOPE FOR THIS IS " + scope);
                                scope++;
                                index++;
                                if(abstract_function()){
                                    if(c_body()){
                                        if(abstract_function()){
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            if(s.equals("ClosingCurlyBrace")) {
                                                //POPPING STACK SCOPE
                                                int temp_stack=Integer.parseInt(stack.pop().toString());
                                                System.out.println("POPPING OUT SCOPE");
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

            return false;
        }

        public boolean AM(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Private") || s.equals("Public") || s.equals("Protected")){
                am_mt=s;
                cb.am=s;
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
String staticKeyword=null;
        public boolean Static(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            System.out.println("IN static "  + s);
            if(s.equals("Static")){
                func_type=s;
                staticKeyword="Static";
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
            if(s.equals("Extends")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")){
                    inherit_mt=ts.get(index).getTokenString();
                    if(sem.lookup_MT(inherit_mt)){
                        System.out.println("CLASS NOT AVAILABLE TO INHERIT");
                        JOptionPane.showMessageDialog(frame, "Class not found Error", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

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
            int temp=index;
            cb.assignLink(uuid);
            if(s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Void") || s.equals("Identifier") || s.equals("Static")
            ||s.equals("Final") || s.equals("Default") || s.equals("Public") || s.equals("Private") || s.equals("Protected")){
                if(s.equals("Static")){
                    index++;
                    if(c_bodyB()){
                        return true;
                    }
                }
                else if(s.equals("Identifier")){
                    index++;

                    if(c_bodyA()){
                        return true;
                    }
                }else if(dec()){  //MARKING FOR SE
                    s = TokenType.toString(ts.get(index).getTokenType());
                    // NAME,TYPE,TM,AM
                    String[] identifiers=cb.name.split(",");
                    for(String content:identifiers){
                        if(!cb.assignMap(content,cb.am,cb.tm,cb.type)){
                            JOptionPane.showMessageDialog(frame, "Redeclaration Error", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    System.out.println("C_BODY SE RETURN");
                    if(c_body()){
                        return true;
                    }
                }
                else if(s.equals("Final")){
                    index++;
                    if(c_bodyC()){
                        return true;
                    }
                }
    //            else if(s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Void")){
    //                if(return_type()){
    //                    s = TokenType.toString(ts.get(index).getTokenType());
    //                    if(s.equals("Identifier")){
    //                        index++;
    //                        s = TokenType.toString(ts.get(index).getTokenType());
    //                        if(s.equals("OpenBrace")){
    //                            index++;
    //                            if(params()){
    //                                s = TokenType.toString(ts.get(index).getTokenType());
    //                                if(s.equals("CloseBrace")){
    //                                    index++;
    //                                    s = TokenType.toString(ts.get(index).getTokenType());
    //                                    if(s.equals("OpeningCurlyBrace")){
    //                                        index++;
    //                                        s = TokenType.toString(ts.get(index).getTokenType());
    //                                        if(MST()){
    //                                            s = TokenType.toString(ts.get(index).getTokenType());
    //                                            if(s.equals("ClosingCurlyBrace")){
    //                                                index++;
    //                                                s = TokenType.toString(ts.get(index).getTokenType());
    //
    //                                                if(c_body()){
    //                                                    return true;
    //                                                }
    //                                            }
    //                                        }
    //                                    }
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //
    //            }
                index=temp;

                    if (fun_def()) {
                            if (c_body()) {
                                return true;
                            }
                    }

                index=temp;
                if(arr_dec()){
                    return true;
                }
            }
            if(s.equals("ClosingCurlyBrace")||s.equals("Public")||s.equals("Protected")||s.equals("Private")||s.equals("Static")
            ||s.equals("Abstract")){
                return true;
            }

            return false;
        }

        public boolean c_bodyA(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(c_bodyAdash()){
                    index++;
                    return true;
                }
            }
            else if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    if(arr_dec7()){ /**  @PARAMS GEt BACK ON thIS */
                        return true;
                    }
                }
            }


            return false;
        }

        public boolean c_bodyAdash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Equal")) {
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("New")) {
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if (s.equals("Identifier")) {
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (s.equals("OpenBrace")) {
                            index++;
                            if (params()) {
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if (s.equals("CloseBrace")) {
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
            else if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(arr_dec8()){
                            return true;
                        }
                    }
                }
            }

            return false;
        }
        public boolean c_bodyB(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Final")){
                index++;
                if(c_bodyBdash()){
                    return true;
                }
            }
            else if(s.equals("Identifier")){
                index++;
                if(arr_dec13()){
                    return true;
                }
            }
            else if(return_type()){
                s = TokenType.toString(ts.get(index).getTokenType());
                System.out.println("IDHR ID" + s);
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
                                    //TRYING:
                                    stack.push(scope);
                                    System.out.println("SCOPE FOR THIS IS " + scope);
                                    scope++;
                                    index++;
                                    if(MST()){
                                        s = TokenType.toString(ts.get(index).getTokenType());

                                        if(s.equals("ClosingCurlyBrace")){
                                            //POPPING STACK SCOPE
                                            int temp_stack=Integer.parseInt(stack.pop().toString());
                                            System.out.println("POPPING OUT SCOPE");
                                            index++;
                                            if(c_body()){
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

            return false;
        }

        public boolean c_bodyBdash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(arr_dec9()){
                    return true;
                }
            }
            else if(return_type()){
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
                                    //TRYING:
                                    stack.push(scope);
                                    System.out.println("SCOPE FOR THIS IS " + scope);
                                    scope++;
                                    index++;
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    if(MST()){
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if(s.equals("ClosingCurlyBrace")){
                                            //POPPING STACK SCOPE
                                            int temp_stack=Integer.parseInt(stack.pop().toString());
                                            System.out.println("POPPING OUT SCOPE");
                                            index++;
                                            if(c_body()){
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


            return false;
        }

        public boolean c_bodyC(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                if(arr_dec2()){
                    return true;
                }
            }
            else if(s.equals("Void")|| s.equals("Int")||s.equals("CharacterClass")||s.equals("Double") || s.equals("StringClass")){
                if(fun_def()){
                    if(c_body()){
                        return true;
                    }
                }
            }

            return false;
        }

    //lookup

        public boolean dec() { //2 int c1
            String s = TokenType.toString(ts.get(index).getTokenType());
            System.out.println("DECLARATION SE FALSES" +s);
            if (s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass")) {
                cb.type=s;
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("Identifier")) {
                    cb.name=ts.get(index).getTokenString();
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
            System.out.println("IDHR B AYE HOGE " +s);
            if (s.equals("Semicolon")) {
                index++;

                return true;
            } else if (s.equals("Comma")) {
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if (s.equals("Identifier")) {
                    cb.name= cb.name+","+ts.get(index).getTokenString();

                    index++; // Equals
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


String return_type;
String func_name;
String func_signature;
String params_list;
String func_type;

        public boolean fun_def(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Static") || s.equals("Int") || s.equals("Void")
            || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Final")){
                if(AM()){
                    System.out. println("IDHR");
                    if(Static()){
                        if(Final()){
                            if(return_type()){
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(s.equals("Identifier")){
                                    func_name=ts.get(index).getTokenString();
                                    index++;
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    if(s.equals("OpenBrace")){
                                        index++;

                                        if(params()){
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            if(s.equals("CloseBrace")){
                                                func_signature=func_signature+"->"+return_type;
                                                if(!cb.assignMap(func_name,am_mt,func_type,func_signature)){
                                                    JOptionPane.showMessageDialog(frame, "Redeclaration Error", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                                return_type=null;
                                                am_mt="Default";
                                                func_signature=null;
                                                params_list=null;
                                                func_type=null;
                                                index++;
                                                s = TokenType.toString(ts.get(index).getTokenType());
                                                if(s.equals("OpeningCurlyBrace")){
                                                    //TRYING:
                                                    stack.push(scope);
                                                    System.out.println("SCOPE FOR THIS IS " + scope);
                                                    scope++;
                                                    index++;
                                                    if(MST()) {
                                                        s = TokenType.toString(ts.get(index).getTokenType());
                                                        if (s.equals("ClosingCurlyBrace")){
                                                            //POPPING STACK SCOPE
                                                            int temp_stack=Integer.parseInt(stack.pop().toString());
                                                            System.out.println("POPPING OUT SCOPE");
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


            return false;
        }


        public boolean abstract_function(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Public") || s.equals("Private")||s.equals("Protected")
            ||s.equals("Static")||s.equals("Abstract")){
                if(AM()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(Static()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Abstract")){
                            index++;
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
                }
            }
            if(s.equals("ClosingCurlyBrace") || s.equals("Int") || s.equals("Double") || s.equals("StringClass") || s.equals("CharacterClass") || s.equals("Void") || s.equals("Identifier") || s.equals("Static")
                    ||s.equals("Final") || s.equals("Default") || s.equals("Public") || s.equals("Private") || s.equals("Protected")){
                return true;
            }
            return false;
        }


        public boolean return_type(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Double") || s.equals("Int") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Void")){
                return_type=ts.get(index).getTokenString();
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
    //                     s = TokenType.toString(ts.get(index).getTokenType());
    //                    if(s.equals("Semicolon")){
    //                        index++;
    //                    }
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
                        index++;
                        if(Xdash())
                            return true;
                    }
                }
            } else if (s.equals("OpenBrace")) {
                index++;
                if (args()) { //ay
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
                params_list=ts.get(index).getTokenString();
                func_signature=s;
                String type_param=s;
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")){
                    String name=ts.get(index).getTokenString();
                    int temp_scope=scope+1;
                    System.out.println("FUNCTI\t"+function_table);
                    if(!function_table.addValues(name,type_param,scope)){
                        JOptionPane.showMessageDialog(frame, "Redeclaration Error", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    index++;

                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(P1()){
                        function_table.printArrayList();
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
                    String type_param=s;
                    func_signature=func_signature+","+s;
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if (s.equals("Identifier")) {
                        String name=ts.get(index).getTokenString();
                        int temp_scope=scope+1;
                        System.out.println("FUNCTI\t"+function_table);

                        if(!function_table.addValues(name,type_param,scope)){
                            JOptionPane.showMessageDialog(frame, "Redeclaration Error in 2nd or more parameter", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

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
                    || s.equals("Character") || s.equals("DoubleConstant") || s.equals("Comma")){
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
    //
    //    public boolean c3(){
    //        String s = TokenType.toString(ts.get(index).getTokenType());
    //        if(s.equals("This") || s.equals("Super")){
    //            if(th_su()){
    //                if(X()){
    //                    s = TokenType.toString(ts.get(index).getTokenType());
    //                    if(s.equals("Equal")){
    //                        index++;
    //                        if(E()){
    //                            return true;
    //                        }
    //                    }
    //                    else if(s.equals("Increment") || s.equals("Decrement")){
    //                        index++;
    //                        return true;
    //                    }
    //
    //                }
    //            }
    //
    //        }
    //        else if(s.equals("CloseBrace")){
    //            return true;
    //        }
    //        else{
    //            if(s.equals("Identifier")){
    //                index++;
    //                s = TokenType.toString(ts.get(index).getTokenType());
    //                if(s.equals("Equal")){
    //                    index++;
    //                    if(E()){
    //                        return true;
    //                    }
    //                }
    //                else if(s.equals("Increment") || s.equals("Decrement")){
    //                    index++;
    //                    return true;
    //                }
    //            }
    //
    //        }
    //
    //
    //            if(th_su()){
    //                if(X()){
    //                    s = TokenType.toString(ts.get(index).getTokenType());
    //                    if(s.equals("Equal")){
    //                        index++;
    //                        if(E()){
    //                            return true;
    //                        }
    //                    }
    //                    else if(s.equals("Increment") || s.equals("Decrement")){
    //                        index++;
    //                        return true;
    //                    }
    //
    //                }
    //            }
    //
    //        }
    //        else if(s.equals("CloseBrace")){
    //            return true;
    //        }
    //        else{
    //            if(s.equals("Identifier")){
    //                index++;
    //                s = TokenType.toString(ts.get(index).getTokenType());
    //                if(s.equals("Equal")){
    //                    index++;
    //                    if(E()){
    //                        return true;
    //                    }
    //                }
    //                else if(s.equals("Increment") || s.equals("Decrement")){
    //                    index++;
    //                    return true;
    //                }
    //            }
    //
    //        }
    //
    //
    //        return false;
    //        return false;
    //    }

        public boolean c3(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            int temp=index;
            if(s.equals("This")||s.equals("Super") || s.equals("Identifier") || s.equals("Increment") || s.equals("Decrement")) {


    //            if(X()){
    //                s = TokenType.toString(ts.get(index).getTokenType());
    //                if(s.equals("CloseBrace")) {
    //                    return true;
    //                }
    //                System.out.println("X YAAHN"+s);
    //                System.out.println("INDEX"+index);
    //                index=temp;
    //                System.out.println("INDEX2"+index);
    //            }
                 if(assignment()){
                    s = TokenType.toString(ts.get(index).getTokenType());

                    if(s.equals("CloseBrace")) {
                         return true;
                    }
                    //APNI TRF SE

                }


                if(TokenType.toString(ts.get(index).getTokenType()).equals("Increment") || TokenType.toString(ts.get(index).getTokenType()).equals("Decrement")){
                    System.out.println("IDHR ARHE H" + index);
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseBrace"))
                    {
                        return true;
                    }else if(X()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("CloseBrace"))
                        {
                            return true;}

                    }
                }

            }
            else if(s.equals("CloseBrace")){
                return true;
            }

            return false;
        }

        public  boolean assignment(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")||s.equals("This")||s.equals("Super")){
                    if(th_su()){
                        if(X()){ // mene
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
            else if(s.equals("Not")||s.equals("OpenBrace")||
            s.equals("IntConstant")||s.equals("DoubleConstant") ||s.equals("String")||
            s.equals("Character") || s.equals("CloseBrace")){
                return true;
            }
            s = TokenType.toString(ts.get(index).getTokenType());


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
            if(s.equals("Increment") || s.equals("Decrement")){
                index++;
                return true;
            }
            else if(s.equals("Semicolon") || s.equals("Identifier")){
                return true;
            }

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
                if(s.equals("Semicolon"))
                    return true;

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
                if (s.equals("Semicolon") || s.equals("OpeningCurlyBrace")||s.equals("Identifier")) {
                    if (s.equals("Semicolon")) {
                        System.out.println("YAHANA AANA CHAIYE");
                        index++;
                        return true;
                    } else if (SST()) {
                        return true;
                    } else if (s.equals("OpeningCurlyBrace")) {
                        //TRYING:
                        stack.push(scope);
                        System.out.println("SCOPE FOR THIS IS " + scope);
                        scope++;
                        index++;
                        if (MST()) {
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if (s.equals("ClosingCurlyBrace")) {
                                //POPPING STACK SCOPE
                                int temp_stack=Integer.parseInt(stack.pop().toString());
                                System.out.println("POPPING OUT SCOPE");
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
            int temp=index;
            if(s.equals("Identifier") || s.equals("StringClass") || s.equals("CharacterClass")||s.equals("Double")||s.equals("Int") || s.equals("For")
            || s.equals("While") || s.equals("Do") || s.equals("Continue") || s.equals("Break") || s.equals("Return") || s.equals("Switch") || s.equals("If")
            || s.equals("Else_if") || s.equals("Else") || s.equals("Public") || s.equals("Private") || s.equals("Protected") || s.equals("Decrement") ||s.equals("Increment")
            || s.equals("Try")){
            if(s.equals("This")||s.equals("Super")) {
                if (th_su()) {
                    if (X()) {
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if (s.equals("Equal")) {
                            if (E()) {
                                return true;
                            }
                        }
                    }
                } else
                    index = temp;
            }
                System.out.println("YAHAN AAO TO BTAO");
                if(obj_init()){
                    return true;
                }
                else index=temp;
                System.out.println("YAHAN AAO TO BTAO");

                if(arr_dec()){
                    return true;
                }else{ index=temp;}


                 if(assignment()){
                    s = TokenType.toString(ts.get(index).getTokenType());

                    if(s.equals("Semicolon")) {
                        System.out.println("IDHR to ni AYE HO ???");
                        index++;
                        return true;
                    }
                    //APNI TRF SE
                     System.out.println("IDHR AYE HO ???");
                    index=temp;
                }
                else {
                    index=temp;
                 }
                if(X()){
                     s = TokenType.toString(ts.get(index).getTokenType());
                    if(inc_dec()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }

                    }
                 }
                else{
                    index=temp;
                }
                if(inc_dec()){
                     s = TokenType.toString(ts.get(index).getTokenType());

                    if(X()){
                        System.out.println("KIYA TM IDHR");
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon"))
                        {
                            index++;
                            return true;
                        }
                    }
                 }
                else {
                    index=temp;
                }


                 if(For()){
                    return true;
                }
                 else {
                     index=temp;
                 }

                 if(dec()){
                    return true;
                    }
                 else {
                     index=temp;
                 }
                 if(if_else()){
                    return true;
                }
                 else {
                     index=temp;
                 }
                if(While()){
                    return true;
                }
                else {
                    index=temp;
                }
                if(Do()){
                    return true;
                }
                else {
                    index=temp;
                }
                if(fun_def()){
                    return true;
                }
                else {
                    index=temp;
                }
                if(c_def()){
                    return true;
                }
                else
                    index=temp;

                 if(Continue()){
                    return true;
                } else index=temp;
                 if(Break()){
                    return true;
                }
                 else index=temp;
                if(Return()){
                    return true;
                }
                else index=temp;
    //
                if(Switch()){
                    return true;
                }
                else index=temp;
    //
                if(try_catch()){
                    return true;
                }
                else index=temp;





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
                    s = TokenType.toString(ts.get(index).getTokenType());

                    System.out.println("YAHAN AYE HO KIYA"+s);
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
            }            else if(init()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    System.out.println("init sst" + s);
                    if(s.equals("Semicolon")){
                        index++;
                        return true;
                    }
                }
            System.out.println("YAHIN SE FALSE HRHE HO NA"+s);
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


        public boolean Final(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Final")){
                type=s;
                cb.tm=s;
                index++;
                return true;
            }
            else{
                if(s.equals("Int") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Double") || s.equals("Void") || s.equals("Class")){
                    return true;
                }
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

        public boolean arr_dec(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Static")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(arr_dec1()){//yahan doubt
                    return true;
                }
            }
            else if(s.equals("Final")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    if(arr_dec2()){
                        return true;
                    }
                }
            }
            else if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                if(arr_dec3()){
                    return true;
                }
            }
            return false;
        }

        public boolean arr_dec1(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Final")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    if(arr_dec9()){
                        return true;
                    }
                }
            }
            else if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                if(arr_dec13()){
                    return true;
                }
            }

            return false;
        }

        public boolean arr_dec2(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                if(arr_dec4()){
                    return true;
                }
            }
            else if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec5()){
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        public boolean arr_dec3(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec7()){
                                return true;
                            }
                        }
                    }
                }
            }
            else if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpenArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseArray")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec8()){
                                return true;
                            }
                        }
                    }
                }
            }


            return false;
        }

        public boolean arr_dec4(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        index++;
                        if(arr_dec6()){
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        public boolean arr_dec5(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if(values()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("New")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenArray")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(OE()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("CloseArray")){
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

            return false;
        }

        public boolean arr_dec6(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if(values()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("New")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier") || s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenArray")){
                        index++;
                        if(OE()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("CloseArray")){
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
            return false;
        }

        public boolean arr_dec7(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if(values()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("New")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenArray")){
                        index++;
                        if(OE()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("CloseArray")){
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
            return false;
        }
        public boolean arr_dec8(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if(values()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("New")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenArray")){
                        index++;
                        if(OE()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("CloseArray")){
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
            return false;
        }

        public boolean arr_dec9(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec10()){
                                return true;
                            }
                        }
                    }

                }
            }
            else if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                if(arr_dec11()){
                    return true;
                }
            }

            return false;
        }

        public boolean arr_dec10(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if(values()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("New")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("OpenArray")){
                        index++;
                        if(OE()){
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("CloseArray")){
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

            return false;
        }

        public boolean arr_dec11(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Equal")){
                        index++;
                        if(arr_dec11dash()){
                            return true;
                        }
                    }
                }
            }
            else if(s.equals("Equal")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpeningCurlyBrace")){
                    //TRYING:
                    stack.push(scope);
                    System.out.println("SCOPE FOR THIS IS " + scope);
                    scope++;
                    index++;
                    if(values()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("ClosingCurlyBrace")){
                            //POPPING STACK SCOPE
                            int temp_stack=Integer.parseInt(stack.pop().toString());
                            System.out.println("POPPING OUT SCOPE");
                            index++;
                            if(s.equals("Semicolon")){
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        public boolean arr_dec11dash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("New")){
                index++;
                if(s.equals("Identifier") || s.equals("Double") || s.equals("Int") || s.equals("CharacterClass") || s.equals("StringClass")){
                    index++;
                    if(arr_dec12()){
                        return true;
                    }
                }
            }
            else if(s.equals("OpeningCurlyBrace")){
                //TRYING:
                stack.push(scope);
                System.out.println("SCOPE FOR THIS IS " + scope);
                scope++;
                index++;
                if (values()) {
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("ClosingCurlyBrace")){
                        //POPPING STACK SCOPE
                        int temp_stack=Integer.parseInt(stack.pop().toString());
                        System.out.println("POPPING OUT SCOPE");
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Semicolon")){
                            index++;
                            return true;
                        }
                    }
                }
            }


            return false;
        }


        public boolean arr_dec12(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                if(arr_dec12dash()){
                    return true;
                }
            }
            return false;
        }

        public boolean arr_dec12dash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(OE()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Semicolon")){
                        index++;
                        return true;
                    }
                }
            }
            else if(s.equals("CloseArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpeningCurlyBrace")){
                    //TRYING:
                    stack.push(scope);
                    System.out.println("SCOPE FOR THIS IS " + scope);
                    scope++;
                    index++;
                    if(values()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("ClosingCurlyBrace")){
                            //POPPING STACK SCOPE
                            int temp_stack=Integer.parseInt(stack.pop().toString());
                            System.out.println("POPPING OUT SCOPE");
                            index++;
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Semicolon")){
                                return true;
                            }
                        }
                    }
                }
            }


            return false;
        }
        public boolean arr_dec13(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("OpenArray")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("CloseArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec10()){
                                return true;
                            }
                        }
                    }
                }
            }
            else if(s.equals("Identifier")|| s.equals("Double") || s.equals("CharacterClass") || s.equals("StringClass") || s.equals("Int")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpenArray")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("CloseArray")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("Equal")){
                            index++;
                            if(arr_dec10()){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        public boolean values(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(OE()){
                if(valuesdash()){
                    return true;
                }
            }
            return false;
        }
        public boolean valuesdash(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Comma")){
                index++;
                if(OE()){
                    if(valuesdash()){
                        return true;
                    }
                }
            }
            else if(s.equals("ClosingCurlyBrace")){
                return true;
            }
            return false;
        }

        public boolean Return(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Return")){
                index++;
                if(RST()){
                    return true;
                }
            }


            return false;
        }

        public boolean RST(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Semicolon")){
                index++;
                return true;
            }
            else if(s.equals("Identifier") || s.equals("DoubleConstant") || s.equals("String") || s.equals("IntConstant") || s.equals("Character") || s.equals("OpenBrace") || s.equals("Not")){
                if(OE()){
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Semicolon")){
                    index++;
                    return true;
                }
                }
            }
            else if(s.equals("This")||s.equals("Super")){
                if(th_su()){
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Semicolon")){
                        index++;
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean Break(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Break")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("SemiColon")) {
                    index++;
                    return true;
                }
            }

            return false;
    }
        public boolean Continue(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Continue")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Semicolon")){
                    index++;
                    return true;
                }

            }

            return false;
        }

        public boolean Switch(){
            String s = TokenType.toString(ts.get(index).getTokenType());

            if(s.equals("Switch")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpenBrace")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());

                    if(s.equals("Double")||s.equals("Int")||s.equals("CharacterClass")||
                    s.equals("StringClass")||s.equals("Identifier")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());

                        if(s.equals("CloseBrace")){
                            index++;
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("OpeningCurlyBrace")){
                                //TRYING:
                                stack.push(scope);
                                System.out.println("SCOPE FOR THIS IS " + scope);
                                scope++;
                                index++;
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(case_body()){
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    System.out.println("SWITHC KA LAST");

                                    if(s.equals("ClosingCurlyBrace")){
                                        //POPPING STACK SCOPE
                                        int temp_stack=Integer.parseInt(stack.pop().toString());
                                        System.out.println("POPPING OUT SCOPE");
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

        public boolean case_body(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Case")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("IntConstant")||s.equals("Double")||s.equals("StringClass")
                ||s.equals("CharacterClass")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(s.equals("Colon")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(Body()){
                                if(case_body()){
                                    return true;
                                }

                        }
                    }
                }
            }
            else if(s.equals("Default")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("Colon")){
                    index++;
                    if(Body()){
                        return true;
                    }
                }
            }
            s = TokenType.toString(ts.get(index).getTokenType());

            if(s.equals("ClosingCurlyBrace")){
                return true;
            }
            return false;
        }

        public boolean try_catch(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Try")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());
                if(s.equals("OpeningCurlyBrace")){
                    //TRYING:
                    stack.push(scope);
                    System.out.println("SCOPE FOR THIS IS " + scope);
                    scope++;
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    if(MST()){
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("ClosingCurlyBrace")){
                            //POPPING STACK SCOPE
                            int temp_stack=Integer.parseInt(stack.pop().toString());
                            System.out.println("POPPING OUT SCOPE");
                            index++;
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Catch")){
                                index++;
                                s = TokenType.toString(ts.get(index).getTokenType());
                                if(s.equals("OpenBrace")){
                                    index++;
                                    s = TokenType.toString(ts.get(index).getTokenType());
                                    if(s.equals("Exception")){
                                        index++;
                                        s = TokenType.toString(ts.get(index).getTokenType());
                                        if(s.equals("Identifier")){
                                            index++;
                                            s = TokenType.toString(ts.get(index).getTokenType());
                                            if(s.equals("CloseBrace")){
                                                index++;
                                                s = TokenType.toString(ts.get(index).getTokenType());
                                                if(s.equals("OpeningCurlyBrace")){
                                                    //TRYING:
                                                    stack.push(scope);
                                                    System.out.println("SCOPE FOR THIS IS " + scope);
                                                    scope++;
                                                    index++;
                                                    s = TokenType.toString(ts.get(index).getTokenType());
                                                    if(MST()){
                                                        s = TokenType.toString(ts.get(index).getTokenType());
                                                        if(s.equals("ClosingCurlyBrace")){
                                                            //POPPING STACK SCOPE
                                                            int temp_stack_2=Integer.parseInt(stack.pop().toString());
                                                            System.out.println("POPPING OUT SCOPE");
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

            return false;
        }

        public boolean obj_init(){
            String s = TokenType.toString(ts.get(index).getTokenType());
            if(s.equals("Identifier")){
                index++;
                s = TokenType.toString(ts.get(index).getTokenType());

                if(s.equals("Identifier")){
                    index++;
                    s = TokenType.toString(ts.get(index).getTokenType());
                    System.out.println("YAHAN AAO TO BTAO obj init");
                    if(s.equals("Equal")){
                        index++;
                        s = TokenType.toString(ts.get(index).getTokenType());
                        if(s.equals("New")){
                            index++;
                            s = TokenType.toString(ts.get(index).getTokenType());
                            if(s.equals("Identifier")){
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

            }

            return false;
        }
}



