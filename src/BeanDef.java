
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
public class BeanDef
{

    public static Map< String, Field> s_fields;
    public static List< String> s_members;

    private static void init() throws Exception
    {
        if( s_members == null )
        {
            Class c = Class.forName( "BeanDef" );
            s_fields = new HashMap();
            s_members = new ArrayList();
            for( Field f : c.getFields() )
            {
                if( Modifier.isStatic( f.getModifiers()))
                {
                    continue;
                }
                s_members.add( f.getName() );
                s_fields.put( f.getName(), f );
            }
        }
    }

    public static Field fieldOfMember( String name ) throws Exception
    {

        init();
        Field g = s_fields.get( name );
        if( g == null )
        {
            throw new RuntimeException( "Unknown member : " + name );
        }

        return g;
    }

    public interface ElementReader
    {

        public String getValue( String propertyName );
    }

    public String id;
    public String className;
    public boolean isSiglenton;
    public boolean isLazy;
    public String constructor;

    @Override

    public String toString()
    {
        String s = "";
        try
        {
            for( String name : s_members )
            {
                s += name + ":" + s_fields.get( name ).get( this ) + " ";
            }
        }catch( Exception x )
        {
            return "something failed";
        }
        return s;

    }

    public void readValues( ElementReader er ) throws Exception
    {
        init();
        for( String name : s_members )
        {
            String value = er.getValue( name );
            if( value != null )
            {
                setValue( name, value );
            }
        }
    }

    private void setValue( String name, String value ) throws Exception
    {
        Field f = fieldOfMember( name );

        Class c = f.getType();

        if( c == String.class )
        {
            f.set( this, value );
            return;
        }

        if( c == boolean.class )
        {
            boolean v = value.equalsIgnoreCase( "true" );
            f.set( this, v );
        }

        throw new RuntimeException( "Unexpected data type - fix the setValue function " );
    }

    public static void main( String... args ) throws Exception
    {
        Class c = Class.forName( "BeanDef" );
        for( Field f : c.getFields() )
        {
            System.out.println( f.getName() );
            System.out.println( f.getType() );
        }
    }

}
