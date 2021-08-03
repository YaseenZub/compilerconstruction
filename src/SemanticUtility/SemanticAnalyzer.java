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
}
