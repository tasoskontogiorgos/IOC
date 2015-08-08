// added comment
public class Calc implements CalcIfc{
	
        String m_name;
        public Calc( String s )
        {
            m_name = s;
        }
        @Override
	public double calc( double x )
	{
                System.out.println("Calc " + m_name + " --- " + x*20);
		return 100 * x;
	}

}
