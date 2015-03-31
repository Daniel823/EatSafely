import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


public class RunServer 
{
	//static HashMap<Integer,Socket> serverSockets = new HashMap<>();
	//static HashMap<Integer,Server> servers = new HashMap<>();

	public static void main(String[] args) 
	{
		if (args.length != 1)
		{
			System.out.println("Usage:");
			System.out.println("   java RunServer <port number>");
			return;
		}
		
		int port_number = Integer.valueOf(args[0]);
		try
		{
			Server server = new Server(port_number);
			//int serverCounter = 0;
			while(true)
			{
				//serverSockets.put(serverCounter, server.getServerSocket().accept());
				//server.setSocket(serverSockets, serverCounter);
				server.setSocket(server.getServerSocket().accept());
				new Thread(server).start();
				//serverCounter++;
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
