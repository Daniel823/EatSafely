import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CoordinateTest {
	
	public static void main(String[] args) {		
		try
		{	
			
			CoordinateSocket client= new CoordinateSocket(7000);
			if(args[0].charAt(0) == 'l')
				client.transmit();
			else if(args[0].charAt(0) == 'r')
				client.rTest();
			else
				System.out.println("use a l or r");
			//client.transmit();
			//client.rTest();
			System.out.println( "Done...exiting..." );
		}
		catch ( Exception e )
		{
			System.out.println( e.getMessage() );
		}
	}

}
