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
	private Coordinate[] coords = new Coordinate[2];
	//private String restaurants;
	
	public Server(int port) throws IOException 
	{
		port_number = port;
		server_socket = new ServerSocket(port_number);
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
		 * read four UTF strings from client
		 * parse each string and store in coordinate objects
		 * pass the coordinates to function for determining restaurant objects
		 * write the size of the restaurant arraylist to client
		 * write out each object to client
		 */
		//while(true)
		//{
			
			coordString = input.readUTF();
			
			//if(coordString.equals("exit"))
				//break;
			
			String[] coordinatePair = new String[2];
			String[] coordinate = new String[2];
			
			coordinatePair = coordString.split(";");
			//rectangle[i].
			
			coordinate = coordinatePair[0].split(",");
			coords[0] = new Coordinate(
					Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]));
			
			coordinate = coordinatePair[1].split(",");
			coords[1] = new Coordinate(
					Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]));
			System.out.println(coords[0].getX() + " " + coords[0].getY());
			System.out.println(coords[1].getX() + " " + coords[1].getY());
		//}
		//TODO: parse each string in rectangle to obtain 2 integers and make a coordinate object
		//out of them
		//TODO: some algorithm to determine which restaurants to send back
		//restaurants = someFunction(coords);
		//substituting restaurant object with integer object
		/*output.writeInt(restaurants.size());
		for(Integer i : restaurants)
		{
			output.write(i);
		}*/
			String answer1 = "-l;44,29,1";
			String answer2 = "-n;39";
			writeLocations(answer1);
			//writeLocations(answer2);
	}

	public void writeLocations(String restaurants) throws IOException
	{
		output.writeUTF(restaurants);
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
	
	@Override
	public void run()
	{
		try {
			//listen();
			readLocations();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

}
