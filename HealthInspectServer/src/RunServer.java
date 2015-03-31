import java.io.IOException;


public class RunServer 
{
	public static int port_number = 7000;
	
	public static void main(String[] args) 
	{
		if (args.length != 1)
		{
			System.out.println("Usage:");
			System.out.println("   java RunServer <port number>");
			return;
		}
		
		ReadCSV restaurantList = new ReadCSV();
		restaurantList.readCSV();
		
		System.out.println("Ready for connections");
		try
		{
			Server server = new Server(port_number, restaurantList);
			while(true)
			{
				server.setSocket(server.getServerSocket().accept());
				new Thread(server).start();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
