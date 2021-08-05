package SemanticUtility;

import java.util.ArrayList;
import java.util.UUID;

public class SemanticAnalyzer {
    boolean start;
    ArrayList<ClassBody> classBody;
    ArrayList<Function> function;
    ArrayList<MainTable> mainTable;
    public SemanticAnalyzer(){
        classBody=new ArrayList<>();
        function=new ArrayList<>();
        mainTable=new ArrayList<>();
        start=true;
    }
    public boolean insert_MT(String name,String type,String cat,String parent,String uuid,String am){
        //lookup for existing in main table.
        //NO INNER CLASSES KA JHANJAT
        //REFERENCE IS I GUESS LINK,
        if(lookup_MT(name)){
            System.out.println("VALUES:" +name +type+cat+parent+am);
            MainTable mt=new MainTable();
            mt.setName(name);
            mt.setType(type);
            mt.setCategory(cat);
            mt.setParent(parent);
            mt.setAm(am);
            mt.setLink(uuid);
            System.out.println(uuid);
            System.out.println("UPAR UUID HAI");
            mainTable.add(mt);
            return true;
        }
        return false;
    }

    public boolean lookup_MT(String identifier){
        if(start){
            start=false;
            return true;
        }
        for(MainTable content:mainTable){
            if(content.getName().equals(identifier)){
                return false;
            }
        }
        return true;


    }

    public String Compatibility(String dt1,String dt2,String operand){

        if((dt1.equals("String")||dt1.equals("StringClass"))&& operand=="Plus"){
            if(dt2.equals("Double") || dt2.equals("DoubleConstant") || dt2.equals("Int")||dt2.equals("IntConstant")
            ||dt2.equals("Character") || dt2.equals("CharacterClass") || dt2.equals("String") || dt2.equals("StringClass")){
                return "StringClass";
            }
        }

        else if(dt1.equals("Int")||dt1.equals("IntConstant") || dt1.equals("Double") || dt1.equals("DoubleConstant")){
            if((dt2.equals("String") || dt2.equals("StringClass")) && operand=="Plus"){
                return "StringClass";
            }
            else if(dt1.equals("Int") || dt1.equals("IntConstant")){
                if(dt2.equals("Double")||dt2.equals("DoubleConstant")){
                    return "Double";
                }
                else if(dt2.equals("Int") || dt2.equals("IntConstant")){
                    return "Int";
                }
            }
            else if((dt1.equals("Double") || dt1.equals("DoubleConstant"))&& (dt2.equals("Double") || dt2.equals("DoubleConstant") || dt2.equals("Int") ||
            dt2.equals("IntConstant")) ){
                return "Double";
            }
        }

        else if((dt1.equals("Character") || dt1.equals("CharacterClass"))&& operand=="Plus"){
            if(dt2.equals("StringClass") || dt2.equals("String")){
                return "StringClass";
            }
        }



        return "ERROR";
    }
}
