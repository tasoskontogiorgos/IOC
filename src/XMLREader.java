
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tdk
 */
public class XMLREader
{

     static String getValue( String tag, Element element )
    {
        try
        {
            NodeList nodes = element.getElementsByTagName( tag ).item( 0 ).getChildNodes();
            Node node = ( Node ) nodes.item( 0 );
            return node.getNodeValue();
        }catch( Throwable x )
        {
            return null;
        }
    }

    static class PropReader implements BeanDef.ElementReader
    {

        private Element m_element;

        PropReader( Element el )
        {
            m_element = el;
        }

        @Override
        public String getValue( String propertyName )
        {
            return XMLREader.getValue( propertyName, m_element );
        }

    }

    public List< BeanDef> readFile( String filename )
    {
        List< BeanDef> l = new ArrayList();

        try
        {

            File beans = new File( filename );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( beans );
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName( "bean" );

            for( int i = 0; i < nodes.getLength(); i ++ )
            {
                Node node = nodes.item( i );

                if( node.getNodeType() == Node.ELEMENT_NODE )
                {
                    Element element = ( Element ) node;

                    BeanDef bd = new BeanDef();
                    bd.readValues( new PropReader( element ));
                    l.add( bd );
                }
            }
        }catch( Exception ex )
        {
            ex.printStackTrace();
        }

        return l;
    }

    public static void main( String... args ) throws Exception
    {
        XMLREader r = new XMLREader();

        for( BeanDef bd : r.readFile( "Beans.xml" ) )
        {
            System.out.println( bd );
        }
    }

}
