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
import org.w3c.dom.Element;

public class JSONReader {
    
   
    static class PropReader implements BeanDef.ElementReader
    {

        private JSONObject m_element;

        PropReader( JSONObject el )
        {
            m_element = el;
        }

        @Override
        public String getValue( String propertyName )
        {
            Object x =  m_element.get( propertyName);
            if( x == null )
            {
                return null;
            }
            return x.toString();
        }

    }
    
    public List< BeanDef > readFile( String filename ) throws Exception{
        
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        List<JSONObject> beanDefs = (JSONArray) jsonObject.get("Beans");
        
        List< BeanDef> l = new ArrayList();
        
        for (JSONObject currentBeanDef : beanDefs) {

            BeanDef bean = new BeanDef();
            
             bean.readValues( new PropReader(currentBeanDef));
            
            l.add(bean);
        }
        
        return l;
        
    }
    
}
