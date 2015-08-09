
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tdk
 */
public class TasosIOC implements IOCIfc
{
    private final Map<String, BeanDef> m_defs = new HashMap();
    private final Map<String, Object> m_objects = new HashMap();
    
    public TasosIOC( String beanXMLFileName ) throws Exception
    {
        JSONReader reader = new JSONReader();

        List< BeanDef> beanDefs = reader.readFile( beanXMLFileName );
        
        for(  BeanDef bd : beanDefs )
        {
            process( bd );
        }
        
    }
    
    
    @Override
    public Object get( String id )  throws Exception
    {
         BeanDef bd = m_defs.get(  id );
        if( bd == null )
        {
            throw new RuntimeException( "Uknkown bean " + id );
        }
        if( !bd.isLazy)
        {
            if( bd.isSiglenton )
            {
                return m_objects.get(  id );
            } else
            {
                return createObject( bd );
            }
        } else
        {
            if( bd.isSiglenton )
            {
                Object nobj = m_objects.get(  id );
                if( nobj == null )
                {
                    nobj = createObject( bd );
                    m_objects.put(  id, nobj);
                }
                return nobj;
            } else
            {
                return createObject( bd );
            }
        }
    }
    
    private void process(  BeanDef bd ) throws Exception
    {
        String id = bd.id;
        m_defs.put( id, bd );
        if( bd.isLazy )
        {
            return;
        }
        m_objects.put( id, createObject( bd ));
        
    }
    
    private Object createObject(  BeanDef bd ) throws Exception
    {
        String className = bd.className;
        Class objClass = Class.forName(className);        
        if( bd.constructor != null )
        {
            Constructor con = objClass.getConstructor( String.class );
            return con.newInstance( bd.constructor );
        } else
        {
            Constructor con = objClass.getConstructor( );
            return con.newInstance( );
        }
        
    }

}
