
public class Printer implements PrinterIfc {
    
    
        String m_name;
        
        public Printer( String name )
        {
            m_name = name;
        }
	
	public void print( String msg )
	{
		System.out.println( "Printer " + m_name + " says: " + msg );
	}

}
