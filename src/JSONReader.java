/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Konti
 */

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

public class JSONReader {
    
    public static class BeanDef
    {

        public String id;
        public String className;
        public boolean isSiglenton;
        public boolean isLazy;
        public String constructor;

        @Override
        public String toString()
        {
            return id + " " + className + " " + isSiglenton + " " + isLazy;
        }
    }
    
    public List< JSONReader.BeanDef > readFile( String filename ) throws Exception{
        
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        List<JSONObject> beanDefs = (JSONArray) jsonObject.get("Beans");
        
        List< JSONReader.BeanDef> l = new ArrayList();
        
        for (JSONObject currentBeanDef : beanDefs) {

            JSONReader.BeanDef bean = new JSONReader.BeanDef();
            
            try{
                bean.id =(String) currentBeanDef.get("id");
            }catch(Throwable x){
                bean.id = null;
            }try{    
                bean.className =(String) currentBeanDef.get("class");
            }catch(Throwable x){
                bean.className = null;
            }try{
                bean.isSiglenton =(boolean) currentBeanDef.get("siglenton");
            }catch(Throwable x){
                bean.isSiglenton = false;
            }try{
                bean.isLazy =(boolean) currentBeanDef.get("lazy");
            }catch(Throwable x){
                bean.isLazy = false;
            }try{
                bean.constructor =(String) currentBeanDef.get("constructor");
            }catch(Throwable x){
                bean.constructor = null;
            }
            
            l.add(bean);
        }
        
        return l;
        
    }
    
}
