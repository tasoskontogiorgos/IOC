

public class App {
	
	
	PrinterIfc myPrinter;
	CalcIfc myCalc;
	
	
	App() throws Exception
	{
		/*String config = "";
		
		config += "printer Printer" + "\n";
		config += "calc Calc" + "\n";*/
		
		String filename = "Beans.json";
		
		
		IOCIfc ioc = new  TasosIOC(filename);
		
		myPrinter = (PrinterIfc) ioc.get( "printer" );
		myCalc = (CalcIfc) ioc.get( "calc" );
		CalcIfc myCalc2 = (CalcIfc) ioc.get( "calc" );
		
		System.out.println( "======= " +( myCalc == myCalc2));
		
		double x = myCalc.calc(100);
		myPrinter.print(" x = " + x);
	}
	
	
	
	public static void main( String ... args )throws Exception
	{
		new App();
	}

}
