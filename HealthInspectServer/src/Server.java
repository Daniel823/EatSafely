import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server implements Runnable
{
	private int port_number;
	private ServerSocket server_socket;
	private Socket client_socket;
	private DataOutputStream output;
	private DataInputStream input;
	private String coordString = "";
	private Point[] coords = new Point[2];
	private ReadCSV restaurantList;
	//private String restaurants;
	
	public Server(int port, ReadCSV csv) throws IOException 
	{
		port_number = port;
		server_socket = new ServerSocket(port_number);
		restaurantList = csv;
	}
	
	public ServerSocket getServerSocket()
	{
		return server_socket;
	}
	
	public void setSocket(HashMap<Integer,Socket> sockets, Integer socketNum)
	{
		client_socket = sockets.get(socketNum);
		try 
		{
			output = new DataOutputStream(client_socket.getOutputStream());
			input = new DataInputStream(client_socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket client)
	{
		client_socket = client;
		try 
		{
			output = new DataOutputStream(client_socket.getOutputStream());
			input = new DataInputStream(client_socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() throws IOException
	{
		BufferedReader reader = new BufferedReader( 
				 new InputStreamReader(client_socket.getInputStream()) );
		String input_line = reader.readLine();
		System.out.println( "Received from client: " );
		System.out.println( "   " + input_line );
		PrintWriter output = new PrintWriter( client_socket.getOutputStream(), true );
		output.println(input_line);
	}
	
	public void readLocations() throws IOException
	{
		/*Function flow: 
		 * read one UTF string from client
		 * parse it to get the pair of points
		 * pass the coordinates to function for determining viewable restaurant objects
		 * write the size of the restaurant arraylist to client if more than 50 restaurants
		 * write out each object to client if less than 50
		 */
			
		coordString = input.readUTF();
		
		String[] coordinatePair = new String[2];
		String[] coordinate = new String[2];
		
		coordinatePair = coordString.split(";");
		
		coordinate = coordinatePair[0].split(",");
		coords[0] = new Point(
				Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]));
		
		coordinate = coordinatePair[1].split(",");
		coords[1] = new Point(
				Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]));
		System.out.println(coords[0].lat + " " + coords[0].lang);
		System.out.println(coords[1].lat + " " + coords[1].lang);

		System.out.println("Looking for restaurants");
		ArrayList<Restaurant> viewableRestaurants = 
				restaurantList.getViewableRestaurants(coords[0], coords[1]);
		System.out.println("Found restaurants");
		
		String result = "";
		if(viewableRestaurants.size() > 50)
		{
			result = "-n;" + Integer.toString(viewableRestaurants.size());
		}
		else
		{
			result = "-l;";
			for(Restaurant r : viewableRestaurants)
			{
				result += r.output();
			}
		}
		System.out.println(result);
		output.writeUTF(result);
		output.close();
	}
	
	public void transmitFile()
	{
		byte[] fileBytes = new byte[1500];
		
		int bytesToWrite = 0;
		
		try 
		{
			FileInputStream fileInput = new FileInputStream(new File("filePath"));
			
			while((bytesToWrite = fileInput.read(fileBytes)) != -1)
			{
				output.write(fileBytes,0,bytesToWrite);
			}
			
			fileInput.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException f)
		{
			f.printStackTrace();
		}
	}
	
	public void sendLocDetails() throws IOException
	{
		String loc = input.readUTF();
		String[] parsedLoc = loc.split(",");
		String ID = parsedLoc[0] + parsedLoc[1];
		
		Restaurant locInfo = restaurantList.getRestaurantList().get(ID);
		
		System.out.println(locInfo.detailedInfo());
		output.writeUTF(locInfo.detailedInfo());
		
		
		output.close();
	}
	
	@Override
	public void run()
	{
		try {
			String flag = input.readUTF();
			if(flag.charAt(1) == 'l')
				readLocations();
			else if(flag.charAt(1) == 'r')
				sendLocDetails();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

}
