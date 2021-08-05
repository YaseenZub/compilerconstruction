package SemanticUtility;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

public class Function {
    private String name;
    private String type;
    private int scope;
    Map<String,String> map;
    ArrayList<Map> arrayList=new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setType(String type) {
        this.type = type;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public boolean addValues(String identifier,String type,int scope){
        if(lookup_func(identifier, scope)){
            map=new TreeMap<>();
            map.put("Identifier",identifier);
            map.put("Type",type);
            map.put("Scope",Integer.toString(scope));
            arrayList.add(map);
            return true;
        }

        return false;

    }

    public boolean lookup_func(String identifier,int scope){
        if(!arrayList.isEmpty()) {
            for (Map content : arrayList) {
                if (identifier.equals(content.get("Identifier")) && scope == Integer.parseInt(content.get("Scope").toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    public String getType(String identifier,int scope){
        for(Map content:arrayList){
            if(identifier.equals(content.get("Identifier")) && Integer.parseInt(content.get("Scope").toString())==scope){
                return content.get("Type").toString();

            }
        }

        return "CANT FIND";
    }

    public void printArrayList(){
        for(Map content:arrayList){
            System.out.println("Name: " + content.get("Identifier"));
            System.out.println("Type: " + content.get("Type"));
            System.out.println("Scope: " + content.get("Scope"));
        }

    }
}
