//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.StringReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Iterator;

public class AlexIOC implements IOCIfc {

    private Map<String, XMLREader.BeanDef> __map__ = new HashMap();
    private Map<String, Object> __objectMap__ = new HashMap();

    public AlexIOC(String filename) {
        XMLREader reader = new XMLREader();

        List<XMLREader.BeanDef> config = reader.readFile(filename);

        for (int i = 0; i < config.size(); i++) {
            this.__map__.put(config.get(i).id, config.get(i));
            if (config.get(i).isLazy == false) {
                Object object = this.buildObject(config.get(i).id, config.get(i).className);
                this.__objectMap__.put(config.get(i).id, object);
            }

        }

        /*System.out.println( config );
         // process ...
		
         BufferedReader bufReader = new BufferedReader (new StringReader(config));

         String line=null;
         try {
         while( (line=bufReader.readLine()) != null )
         {
         String[] parts = line.split("\\s");
         System.out.println( "line = " + line );
				 
         String key = parts[0];
         String value = parts[1];
         System.out.println( "key = " + key + " value = " + value );
         this.__map__.put(key, value);
				 
				 
         }
         } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }*/
    }

    public Map getMap() {
        return this.__map__;
    }

    
    
    
    private Object buildObject(String id, String className) {
        Map<String, XMLREader.BeanDef> map = this.getMap();
        try 
        {
            Class obClass = Class.forName(className);
            if (map.get(id).constructor != "") 
            {
                try {

                    Constructor con = obClass.getConstructor(String.class);
                    try {

                        String param = map.get(id).constructor;
                       return con.newInstance(param);
                    } catch (InstantiationException ie) {
                        System.out.println("Instantiation level error");
                        ie.printStackTrace();
                        return null;
                    } catch (IllegalAccessException iae) {
                        System.out.println("Instantiation level error");
                        iae.printStackTrace();
                        return null;
                    } catch (IllegalArgumentException iarge) {
                        System.out.println("Instantiation level error");
                        iarge.printStackTrace();
                        return null;
                    } catch (InvocationTargetException ite) {
                        System.out.println("Instantiation level error");
                        ite.printStackTrace();
                        return null;
                    }

                } catch (NoSuchMethodException nsme){
                    System.out.println("Constructor level error");
                    nsme.printStackTrace();
                    return null;
                }
            }else{
                try {
                    return obClass.newInstance();
                } catch (InstantiationException ie) {
                    System.out.println("Instantiation level error");
                        ie.printStackTrace();
                        return null;
                } catch (IllegalAccessException iae) {
                   System.out.println("Instantiation level error");
                        iae.printStackTrace();
                        return null;
                }
            }
        } catch (ClassNotFoundException cnfe) {
            // TODO Auto-generated catch block
            System.out.println("Class level error");
            cnfe.printStackTrace();
            return null;
        }
    }

    

    public Object get(String id) {
        Map<String, XMLREader.BeanDef> map = this.getMap();
        XMLREader.BeanDef def = map.get(id);

        
            if (map.get(id).isLazy == true) {
                Object object = this.buildObject(id, map.get(id).className);
                this.__objectMap__.put(id, object);
            }
        

        if (def.isSiglenton = true) {
            return this.__objectMap__.get(id);
        } else {
            String className = map.get(id).className;
            return this.buildObject(id, className);
        }

        /*Map map = getMap();
         if(map.get(id) instanceof String){
         try{
				
         System.out.println("1");
         Class obClass = Class.forName((String) map.get(id));
         System.out.println("2");
				
         Object object = obClass.newInstance();
         map.put(id, object);
         return object;
				
				
         }catch(ClassNotFoundException cnfe){
         System.out.println("Class Level Error");
         return null;
         } catch (InstantiationException e) {
         System.out.println("Instansiation Error");
         return null;
         } catch (IllegalAccessException e) {
         System.out.println("Instansiation Error");
         return null;
         }
		
	
         }else{
         return map.get(id);
         }*/
    }
}
