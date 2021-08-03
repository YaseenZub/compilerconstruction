package SemanticUtility;

import token.TokenType;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ClassBody {
    String link;
    Map<Entities, String> classBody;
    public String name,type,am,tm;
    ArrayList<Map> classList ;
    int index;

    public ClassBody(){
        classList = new ArrayList();
        index=0;
    }

    public boolean assignMap(String valuepart,String accessModifier,String TypeModifier,String Type){

        if(lookUp_CB(valuepart,Type)) {
            classBody=new TreeMap<Entities, String>();
            System.out.println("ANDAR AANA CHAIYUE");
            classBody.put(Entities.Name, valuepart);
            classBody.put(Entities.Am, accessModifier);
            classBody.put(Entities.Type, Type);
            classBody.put(Entities.Tm, TypeModifier);
            classList.add(index,classBody);
            index++;
            return true;
        }
        return false;
    }

    public void assignLink(String uuid){
        this.link=uuid;
    }

    public boolean lookUp_CB(String identifier,String Type){
        if(!classList.isEmpty()) {
            System.out.println("SECOND TIME WITH " +identifier);
            for (Map content : classList) {
                if (identifier.equals(content.get(Entities.Name).toString()) &&
                Type.equals(content.get(Entities.Type).toString())) {
                    //REDECLARATION ERROR

                    return false;
                }
            }
        }
        return true;
    }

    public void printClassList(){
        for(Map content:classList){
            System.out.println(content.get(Entities.Name));
            System.out.println(content.get(Entities.Tm));

            System.out.println(content.get(Entities.Type));
            System.out.println(content.get(Entities.Am));

        }

    }
}
